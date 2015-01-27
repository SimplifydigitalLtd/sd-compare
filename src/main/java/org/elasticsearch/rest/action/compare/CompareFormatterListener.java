package org.elasticsearch.rest.action.compare;

import org.apache.lucene.search.Explanation;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.rest.BytesRestResponse;
import org.elasticsearch.rest.RestChannel;
import org.elasticsearch.rest.RestResponse;
import org.elasticsearch.rest.action.support.RestResponseListener;
import org.elasticsearch.search.SearchHit;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kstenson on 21/01/2015.
 */
public class CompareFormatterListener extends RestResponseListener<SearchResponse> {

   private Map<String, String> functions;


    private ScoreParser parser = new ScoreParser();

    protected CompareFormatterListener(RestChannel channel) {
        super(channel);

        functions = new HashMap<String, String>(); //TODO- Export this to external source
        functions.put("field value function", "\\['(.*?)'\\]");
        functions.put("Function for field ","([^\\s]+)\\:"); //decay function scoring
    }

    @Override
    public final RestResponse buildResponse(SearchResponse searchResponse) throws Exception {
        return buildResponse(searchResponse, channel.newBuilder());
    }

    public final RestResponse buildResponse(SearchResponse response, XContentBuilder builder) throws Exception {
        if (response.getHits() == null || response.getHits().getHits() == null || response.getHits().getHits().length == 0) {
            builder.startArray().endArray();
        } else {
            builder.startObject();

            response.toXContent(builder, channel.request());

            builder.startArray("comparison");
            for (SearchHit hit : response.getHits().getHits()) {
              builder.startObject();
                builder.field("id", hit.getId());
                Map<String, Object> scores = parseScores(hit.getExplanation());
                builder.field("scores", scores);
                builder.endObject();
            }

            builder.endArray();
            builder.endObject();
        }

        return new BytesRestResponse(response.status(), builder);
    }

    private Map<String, Object> parseScores(Explanation e){
         Map<String, Object> scores = new HashMap<String, Object>();

         recurseExplanation(e, scores);

        return scores;
    }
    private void recurseExplanation(Explanation e, Map<String, Object> scores) {

        for (String key : functions.keySet()) {
            if (e.getDescription().contains(key)) { //if we match the function hashmap then pass it to be parsed for score
                AbstractMap.SimpleEntry<String, Object> entry = parser.Parse(functions.get(key), e);
                if (entry != null) {
                    scores.put(entry.getKey(), entry.getValue());
                }
            }
        }

        if (e.getDetails() != null && e.getDetails().length > 0) { //recurse down if there  are any details to the explain
            for (Explanation ex : e.getDetails()) {
                recurseExplanation(ex, scores);
            }
        }

    }
}
