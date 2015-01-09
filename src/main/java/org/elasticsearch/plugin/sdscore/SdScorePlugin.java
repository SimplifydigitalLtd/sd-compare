package org.elasticsearch.plugin.sdscore;

import org.apache.lucene.search.Explanation;
import org.elasticsearch.index.fielddata.ScriptDocValues;
import org.elasticsearch.plugins.AbstractPlugin;
import org.elasticsearch.script.*;

import java.util.Map;


public class SdScorePlugin extends AbstractPlugin {
    @Override public String name() {
        return "sd-score-plugin";
    }

    @Override public String description() {
        return "customizes the explain to make it more parse-able";
    }



    public void onModule(ScriptModule module) {
       System.out.println("Hit onload");
        module.registerScript("sd-score-plugin", SDExplainFactory.class);

           }

    static class SDExplainFactory implements NativeScriptFactory{

        @Override
        public ExecutableScript newScript(Map<String, Object> map) {
            return new SDExplainer();
        }
    }

    static class SDExplainer extends AbstractDoubleSearchScript implements ExplainableSearchScript{

        @Override
        public Explanation explain(Explanation explanation) {
           System.out.println("we hit it");
            return new Explanation((float) (runAsDouble()), "This script returned " + runAsDouble() + ". _score was: " + score() + " but did not use that.");
        }

        @Override
        public double runAsDouble() {
            return ((Number) ((ScriptDocValues) doc().get("number_field")).getValues().get(0)).doubleValue();
        }
    }
}