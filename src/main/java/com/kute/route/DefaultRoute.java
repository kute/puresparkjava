package com.kute.route;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Route;

public class DefaultRoute {

    private static final Logger logger = LoggerFactory.getLogger(DefaultRoute.class);

    public static final Route GET_HOTEL_API = (request, response) -> {
        logger.info("get hotel api route");
        return "hotel-info";
    };

    public static final Route ERROR = (req, res) -> {
        res.type("application/json");
        return "{\"message\":\"Custom 404\"}";
    };

}
