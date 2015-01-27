package org.elasticsearch.rest.action.compare;

import org.apache.lucene.search.Explanation;

import java.util.AbstractMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by kstenson on 26/01/2015.
 */
public class ScoreParser {


    public AbstractMap.SimpleEntry<String, Object> Parse(String pattern, Explanation explanation) {
        Pattern compiledPattern = Pattern.compile(pattern); //todo - maybe compile once and reuse for performance

        Matcher matcher = compiledPattern.matcher(explanation.getDescription());

        if (matcher.find()) {
            return new AbstractMap.SimpleEntry<String, Object>(matcher.group(1), explanation.getValue());
        }

        return null;
    }
}
