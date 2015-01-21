package org.elasticsearch.rest.action.compare;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.rest.BaseRestHandler;
import org.elasticsearch.rest.RestChannel;
import org.elasticsearch.rest.RestController;
import org.elasticsearch.rest.RestRequest;
import org.elasticsearch.rest.action.search.RestSearchAction;

import static org.elasticsearch.rest.RestRequest.Method.GET;
import static org.elasticsearch.rest.RestRequest.Method.POST;
/**
 * Created by kstenson on 21/01/2015.
 */
public class RestCompareAction extends BaseRestHandler {
    @Inject
    public RestCompareAction(Settings settings, Client client, RestController controller) {
        super(settings,client);


        controller.registerHandler(GET, "/_compare", this);
        controller.registerHandler(POST, "/_compare", this);
        controller.registerHandler(GET, "/{index}/_compare", this);
        controller.registerHandler(POST, "/{index}/_compare", this);
        controller.registerHandler(GET, "/{index}/{type}/_compare", this);
        controller.registerHandler(POST, "/{index}/{type}/_compare", this);
        controller.registerHandler(GET, "/_compare/template", this);
        controller.registerHandler(POST, "/_compare/template", this);
        controller.registerHandler(GET, "/{index}/_compare/template", this);
        controller.registerHandler(POST, "/{index}/_compare/template", this);
        controller.registerHandler(GET, "/{index}/{type}/_compare/template", this);
        controller.registerHandler(POST, "/{index}/{type}/_compare/template", this);
    }

    @Override
    protected void handleRequest(RestRequest restRequest, RestChannel restChannel, Client client) throws Exception {
        SearchRequest searchRequest;
        searchRequest = RestSearchAction.parseSearchRequest(restRequest);
        searchRequest.listenerThreaded(false);
        client.search(searchRequest, new CompareFormatterListener(restChannel));
    }
}
