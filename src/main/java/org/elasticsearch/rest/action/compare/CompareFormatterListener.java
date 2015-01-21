package org.elasticsearch.rest.action.compare;

import org.apache.lucene.search.Explanation;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.rest.BytesRestResponse;
import org.elasticsearch.rest.RestChannel;
import org.elasticsearch.rest.RestResponse;
import org.elasticsearch.rest.action.support.RestResponseListener;
import org.elasticsearch.search.SearchHit;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by kstenson on 21/01/2015.
 */
public class CompareFormatterListener extends RestResponseListener<SearchResponse> {

    private Map<String, Object> scores;
    protected CompareFormatterListener(RestChannel channel) {

        super(channel);
        scores = new HashMap<String,Object>();

    }

    @Override
    public final RestResponse buildResponse(SearchResponse searchResponse) throws Exception
    {
       return buildResponse(searchResponse, channel.newBuilder());
    }

    public final RestResponse buildResponse(SearchResponse response, XContentBuilder builder) throws Exception{
        if (response.getHits() == null || response.getHits().getHits() == null || response.getHits().getHits().length == 0) {
            builder.startArray().endArray();
        } else {
            builder.startArray();
            for (SearchHit hit : response.getHits().getHits()) {
                builder.map(hit.getSource());
                parseScores(hit.getExplanation());
            }
            builder.map(scores);
            builder.endArray();



        }


        return new BytesRestResponse(response.status(), builder);
    }

    private void parseScores(Explanation e){
        System.out.println(e.getDescription());

        if (e.getDescription().contains("field value function")){
            try{
            Pattern pattern = Pattern.compile("['(.*?)']");
            Matcher matcher = pattern.matcher(e.getDescription());
            scores.put(matcher.group(), e.getValue());
            System.out.println(matcher.group());}catch(Exception ex){}

        }

        if (e.getDetails() != null && e.getDetails().length > 0){
            for (Explanation ex: e.getDetails()){
                parseScores(ex);
            }
        }

    }
}
