package com.divyanshi.RestAssuredTest;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.util.List;
import java.util.Map;

public class UserApiTest {

    @BeforeClass
    public void setUp() {
        RestAssured.baseURI = "https://fakestoreapi.com";
    }

    @Test
    public void testFirstNamesPresent() {
        Response response = RestAssured.get("/users");

        // Print response status code
        int statusCode = response.getStatusCode();
        System.out.println("Response Status Code: " + statusCode);

        // Validate status code
        Assert.assertEquals(statusCode, 200, "Unexpected status code");

        // Print response content type
        String contentType = response.getContentType();
        System.out.println("Response Content Type: " + contentType);

        // Validate content type
        Assert.assertEquals(contentType, "application/json; charset=utf-8", "Unexpected content type");

        // Get firstnames from the response payload
        List<String> firstNames = response.jsonPath().getList("name.firstname");

        // Validate presence of specific firstnames
        Assert.assertTrue(firstNames.contains("david"), "David not found");
        Assert.assertTrue(firstNames.contains("don"), "Don not found");
        Assert.assertTrue(firstNames.contains("miriam"), "Miriam not found");
    }

    @Test
    public void testLatAndLongNotNull() {
        Response response = RestAssured.get("/users");

        // Print response status code
        int statusCode = response.getStatusCode();
        System.out.println("Response Status Code: " + statusCode);

        // Validate status code
        Assert.assertEquals(statusCode, 200, "Unexpected status code");

        // Print response content type
        String contentType = response.getContentType();
        System.out.println("Response Content Type: " + contentType);

        // Validate content type
        Assert.assertEquals(contentType, "application/json; charset=utf-8", "Unexpected content type");

        List<Map<String, String>> geolocationList = response.jsonPath().getList("address.geolocation");

        for (Map<String, String> geolocation : geolocationList) {
            Assert.assertNotNull(geolocation.get("lat"), "Lat is null");
            Assert.assertNotNull(geolocation.get("long"), "Long is null");
        }
    }

    @Test
    public void testPasswordComplexity() {
        Response response = RestAssured.get("/users");

        // Print response status code
        int statusCode = response.getStatusCode();
        System.out.println("Response Status Code: " + statusCode);

        // Validate status code
        Assert.assertEquals(statusCode, 200, "Unexpected status code");

        // Print response content type
        String contentType = response.getContentType();
        System.out.println("Response Content Type: " + contentType);

        // Validate content type
        Assert.assertEquals(contentType, "application/json; charset=utf-8", "Unexpected content type");

        List<String> passwords = response.jsonPath().getList("password");

        for (String password : passwords) {
            // Validate password complexity (at least 1 character, 1 special character, 1 number)
            Assert.assertTrue(password.matches("^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=]).+$"), "Password does not meet complexity requirements");
        }
    }
}
