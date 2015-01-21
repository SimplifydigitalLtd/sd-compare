package org.elasticsearch.plugin.sdscore;

import org.apache.lucene.search.Explanation;
import org.elasticsearch.index.fielddata.ScriptDocValues;
import org.elasticsearch.plugins.AbstractPlugin;
import org.elasticsearch.rest.RestModule;
import org.elasticsearch.rest.action.compare.RestCompareAction;
import org.elasticsearch.script.*;

import java.util.Map;


public class SdScorePlugin extends AbstractPlugin {
    @Override public String name() {
        return "sd-score-plugin";
    }

    @Override public String description() {
        return "customizes the explain to make it more parse-able";
    }



    public void onModule(RestModule module) {
       System.out.println("Hit onload");
       module.addRestAction(RestCompareAction.class);

           }




}