package com.bhima.AutomationAnywhere.utils;

import io.restassured.response.Response;
import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.Assert;

import java.io.InputStream;

public class ApiValidator {

    public static void validateStatusCode(Response response, int expected) {
        Assert.assertEquals(response.getStatusCode(), expected,
                "Expected status code " + expected + " but got " + response.getStatusCode());
    }

    public static void validateResponseTime(Response response, long maxMs) {
        long time = response.time();
        Assert.assertTrue(time <= maxMs, "Response time " + time + "ms exceeded max " + maxMs + "ms");
    }

    public static void validateFieldNotNull(Response response, String jsonPath) {
        Object val = response.jsonPath().get(jsonPath);
        Assert.assertNotNull(val, "Field " + jsonPath + " is null or missing");
    }

    public static void validateFieldEquals(Response response, String jsonPath, Object expected) {
        Object actual = response.jsonPath().get(jsonPath);
        Assert.assertEquals(actual, expected, "Field " + jsonPath + " mismatch");
    }

    public static void validateJsonSchema(Response response, String schemaResourcePath) {
        try (InputStream is = ApiValidator.class.getResourceAsStream(schemaResourcePath)) {
            if (is == null) {
                Assert.fail("Schema resource not found: " + schemaResourcePath);
            }
            JSONObject rawSchema = new JSONObject(new JSONTokener(is));
            Schema schema = SchemaLoader.load(rawSchema);
            JSONObject jsonResponse = new JSONObject(response.getBody().asString());
            schema.validate(jsonResponse);
        } catch (Exception e) {
            Assert.fail("Schema validation failed: " + e.getMessage());
        }
    }
}
