package org.elasticsearch.plugin.sd;

import org.elasticsearch.plugins.AbstractPlugin;
import org.elasticsearch.rest.RestModule;
import org.elasticsearch.rest.action.compare.RestCompareAction;


public class ComparePlugin extends AbstractPlugin {
    @Override
    public String name() {
        return "sd-compare-plugin";
    }

    @Override
    public String description() {
        return "produces a easier to parse document score breakdown";
    }


    public void onModule(RestModule module) {
        module.addRestAction(RestCompareAction.class);
    }


}