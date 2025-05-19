package api.tests;

import api.models.User;
import api.services.UserService;
import api.utils.PayloadUtil;
import api.utils.ValidationUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class UserTest {
    private static final Logger logger = LoggerFactory.getLogger(UserTest.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static String createdUserId;
    private static User createdUserPayload;

    @Test(priority = 1)
    public void testCreateUser() {
        logger.info("Starting testCreateUser");

        String userJson = PayloadUtil.loadPayload("createUser.json");
        Response response = UserService.createUser(userJson);

        ValidationUtil.validateResponse(response, 201, 1000);

        String id = response.jsonPath().getString("id");
        Assert.assertNotNull(id, "User ID should not be null");
        createdUserId = id;

        try {
            createdUserPayload = objectMapper.readValue(userJson, User.class);
            ValidationUtil.validateUserResponse(response, createdUserPayload);
        } catch (IOException e) {
            logger.error("Failed to parse createUser.json", e);
            Assert.fail("Failed to parse createUser.json: " + e.getMessage());
        }

        logger.info("testCreateUser completed successfully");
    }

    @Test(priority = 2, dependsOnMethods = "testCreateUser")
    public void testRetrieveUser() {
        logger.info("Starting testRetrieveUser for user ID: {}", createdUserId);
        Response response = UserService.getUser(createdUserId);

        ValidationUtil.validateResponse(response, 200, 1000);

        String id = response.jsonPath().getString("data.id");
        Assert.assertEquals(id, createdUserId, "User ID should match requested ID");

        ValidationUtil.validateUserResponse(response, createdUserPayload);

        logger.info("testRetrieveUser completed successfully");
    }

    @Test(priority = 3, dependsOnMethods = "testCreateUser")
    public void testUpdateUser() {
        logger.info("Starting testUpdateUser");

        String updateJson = PayloadUtil.loadPayload("updateUser.json");
        Response response = UserService.updateUser(createdUserId, updateJson);

        ValidationUtil.validateResponse(response, 200, 1000);

        try {
            User updateUserPayload = objectMapper.readValue(updateJson, User.class);
            ValidationUtil.validateUserResponse(response, updateUserPayload);
        } catch (IOException e) {
            logger.error("Failed to parse updateUser.json", e);
            Assert.fail("Failed to parse updateUser.json: " + e.getMessage());
        }

        logger.info("testUpdateUser completed successfully");
    }
}