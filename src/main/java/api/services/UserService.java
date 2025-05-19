package api.services;

import config.ConfigManager;
import config.ConfigType;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.restassured.RestAssured.given;

public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private static final RequestSpecification REQUEST_SPEC;

    static {
        RestAssured.baseURI = ConfigManager.getProperty(ConfigType.API, "BASE_URL");
        REQUEST_SPEC = given()
                .header("Content-Type", ConfigManager.getProperty(ConfigType.API, "CONTENT_TYPE"))
                .header("x-api-key", ConfigManager.getProperty(ConfigType.API, "API_KEY"));
    }

    private static void logResponse(String action, Response response) {
        logger.info("{} - Status Code: {}", action, response.getStatusCode());
        logger.debug("{} - Response Body: \n{}", action, response.asPrettyString());
    }

    public static Response createUser(String jsonBody) {
        logger.info("Creating user with raw JSON payload");
        Response response = REQUEST_SPEC.body(jsonBody).post(ConfigManager.getProperty(ConfigType.API, "ENDPOINT"));
        logResponse("Create User", response);
        return response;
    }

    public static Response getUser(String userId) {
        logger.info("Getting user with ID: {}", userId);
        Response response = given().get(ConfigManager.getProperty(ConfigType.API,"ENDPOINT") + "/{id}", userId);
        logResponse("Get User", response);
        return response;
    }

    public static Response updateUser(String userId, String jsonBody) {
        logger.info("Updating user ID {} with raw JSON payload", userId);
        Response response = REQUEST_SPEC.body(jsonBody).put(ConfigManager.getProperty(ConfigType.API, "ENDPOINT") + "/{id}", userId);
        logResponse("Update User", response);
        return response;
    }
}
