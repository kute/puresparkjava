package com.kute.filter;

import spark.http.matching.MatcherFilter;
import spark.route.Routes;
import spark.staticfiles.StaticFilesConfiguration;

public class DefaultFilter extends MatcherFilter {

    public DefaultFilter(Routes routeMatcher, StaticFilesConfiguration staticFiles, boolean externalContainer, boolean hasOtherHandlers) {
        super(routeMatcher, staticFiles, externalContainer, hasOtherHandlers);
    }
}
