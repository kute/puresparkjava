package com.kute.controller;

import com.google.gson.Gson;
import com.kute.exception.CustomException;
import com.kute.po.Comment;
import com.kute.route.DefaultRoute;
import com.kute.util.JsonTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

import static spark.Spark.*;

public class HelloController {

    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);

    public static void main(String[] args) {

        get("/", (request, response) -> {

            request.attributes();             // the attributes list
            request.attribute("foo");         // value of foo attribute
            request.attribute("A", "V");      // sets value of attribute A to V
            request.body();                   // request body sent by the client
            request.bodyAsBytes();            // request body as bytes
            request.contentLength();          // length of request body
            request.contentType();            // content type of request.body
            request.contextPath();            // the context path, e.g. "/hello"
            request.cookies();                // request cookies sent by the client
            request.headers();                // the HTTP header list
            request.headers("BAR");           // value of BAR header
            request.host();                   // the host, e.g. "example.com"
            request.ip();                     // client IP address
            request.params("foo");            // value of foo path parameter
            request.params();                 // map with all parameters
            request.pathInfo();               // the path info
            request.port();                   // the server port
            request.protocol();               // the protocol, e.g. HTTP/1.1
            request.queryMap();               // the query map
            request.queryMap("foo");          // query map for a certain parameter
            request.queryParams();            // the query param list
            request.queryParams("FOO");       // value of FOO query param
            request.queryParamsValues("FOO");  // all values of FOO query param
            request.raw();                    // raw request handed in by Jetty
            request.requestMethod();          // The HTTP method (GET, ..etc)
            request.scheme();                 // "http"
            request.servletPath();            // the servlet path, e.g. /result.jsp
            request.session();                // session management
            request.splat();                  // splat (*) parameters
            request.uri();                    // the uri, e.g. "http://example.com/foo"
            request.url();                    // the url. e.g. "http://example.com/foo"
            request.userAgent();              // user agent


            request.queryMap().get("user", "name").value();
            request.queryMap().get("user").get("name").value();
            request.queryMap("user").get("age").integerValue();
            request.queryMap("user").toMap();

            request.cookies();                         // get map of all request cookies
            request.cookie("foo");                     // access request cookie by name
            response.cookie("foo", "bar");             // set cookie with a value
            response.cookie("foo", "bar", 3600);       // set cookie with a max-age
            response.cookie("foo", "bar", 3600, true); // secure cookie
            response.removeCookie("foo");              // remove cookie

            request.session(true);                     // create and return session
            request.session().attribute("user");       // Get session attribute 'user'
            request.session().attribute("user","foo"); // Set session attribute 'user'
            request.session().removeAttribute("user"); // Remove session attribute 'user'
            request.session().attributes();            // Get all session attributes
            request.session().id();                    // Get session id
            request.session().isNew();                 // Check if session is new
            request.session().raw();                   // Return servlet object


            response.body();               // get response content
            response.body("Hello");        // sets content to Hello
            response.header("FOO", "bar"); // sets header FOO with value bar
            response.raw();                // raw response handed in by Jetty
            response.redirect("/example"); // browser redirect to /example
            response.status();             // get the response status
            response.status(401);          // set status code to 401
            response.type();               // get the content type
            response.type("text/xml");     // set content type to text/xml

            return "hello";
        });

        get("/test/:name", (request, response) -> {
            return "get name param:" + request.params("name");
        });

        get("/test/*/splat/*", (request, response) -> {
            String[] params = request.splat();
            logger.info("splat params:" + Arrays.asList(params));
            return request.splat().length;
        });

        // stop server async
        get("/stop", ((request, response) -> {
            stop();

            // immediately stop a request
            halt();                // halt
            halt(401);             // halt with status
            halt("Body Message");  // halt with message
            halt(401, "Go away!"); // halt with status and message

            return "stop ok";
        }));

        path("/api", () -> {

            // before filter
            before("/*", (request, response) -> {
                logger.info("before receive api call");
            });

            path("/user", () -> {
                post("/add", (request, response) -> {
                    return "add user";
                });
                delete("/del", (request, response) -> {
                    return "done";
                });
            });

            path("/hotel", () -> {
                get("/get", DefaultRoute.GET_HOTEL_API);
            });

            // after filter
            after((request, response) -> {

            });

            // after filter
            after("/*", (request, response) -> {
                logger.info("after api call");
            });

            afterAfter((request, response) -> {
                logger.info("final block");
            });

        });


        get("/redirect", (request, response) -> {

            response.redirect("/newapi", 201);

            redirect.get("/", "/newapi");

            redirect.post("/", "/newapi");

            redirect.any("/", "/newapi");

            return "redirect usage";
        });


        initExceptionHandler(e -> {
            logger.error("this is error info when server-starting init failed", e);
            System.exit(100);
        });

        notFound("<html><body><h1>Custom 404 handling</h1></body></html>");

        notFound(DefaultRoute.ERROR);

        // Using string/html
        internalServerError("<html><body><h1>Custom 500 handling</h1></body></html>");

        // Using Route
        internalServerError((req, res) -> {
            res.type("application/json");
            return "{\"message\":\"Custom 500 handling\"}";
        });

        // deal special exception
        exception(CustomException.class, (exception, request, response) -> {

        });

        staticFiles.location("/public");

        staticFiles.expireTime(600); // ten minutes

        staticFiles.externalLocation(System.getProperty("java.io.tmpdir"));

        staticFiles.header("Key-1", "Value-1");
        staticFiles.header("Key-1", "New-Value-1"); // Using the same key will overwrite value
        staticFiles.header("Key-2", "Value-2");
        staticFiles.header("Key-3", "Value-3");


        get("/json", (request, response) -> {
            return new Comment("json format");
        }, new JsonTransformer());

        Gson gson = new Gson();
        get("/json2", (request, response) -> {
            return new Comment("json format");
        }, gson::toJson);


    }

}
