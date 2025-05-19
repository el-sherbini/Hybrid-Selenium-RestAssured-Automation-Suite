package api.utils;

import api.models.User;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

public class ValidationUtil {
    private static final Logger logger = LoggerFactory.getLogger(ValidationUtil.class);

    public static void validateResponse(Response response, int expectedStatus, long maxResponseTimeMs) {
        logger.info("Validating response with expected status: {}", expectedStatus);
        Assert.assertEquals(response.getStatusCode(), expectedStatus, "Unexpected HTTP status code");
        Assert.assertTrue(response.getTime() < maxResponseTimeMs, "Response time exceeded " + maxResponseTimeMs + "ms");
    }

    public static void validateUserResponse(Response response, User expectedUser) {
        String prefix = response.jsonPath().get("data") != null ? "data." : "";
        Assert.assertEquals(response.jsonPath().getString(prefix + "name"), expectedUser.getName(), "Name mismatch");
        Assert.assertEquals(response.jsonPath().getString(prefix + "job"), expectedUser.getJob(), "Job mismatch");
    }
}
