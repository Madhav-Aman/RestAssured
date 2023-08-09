package com.divyanshi.RestAssuredTest;

import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.util.List;
import java.util.Map;

public class CartsApiTest {

    @BeforeClass
    public void setUp() {
        RestAssured.baseURI = "https://fakestoreapi.com";
    }

    @Test
    public void testResponseSchema() {
        Response response = RestAssured.get("/carts");

        // Log response details
        logResponse(response);

        // Validate status code
        response.then().assertThat().statusCode(200);

        // Validate content type
        response.then().assertThat().contentType("application/json; charset=utf-8");

        // Validate JSON schema
        response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("carts-schema.json"));
    }

    @Test
    public void testProductsFields() {
        Response response = RestAssured.get("/carts");

        // Log response details
        logResponse(response);

        // Validate status code
        response.then().assertThat().statusCode(200);

        // Validate content type
        response.then().assertThat().contentType("application/json; charset=utf-8");

        List<Map<String, Object>> carts = response.jsonPath().getList("");

        for (Map<String, Object> cart : carts) {
            List<Map<String, Object>> products = (List<Map<String, Object>>) cart.get("products");
            Assert.assertFalse(products.isEmpty(), "Products list is empty");

            for (Map<String, Object> product : products) {
                Assert.assertNotNull(product.get("productId"), "productId is null");
                Assert.assertNotNull(product.get("quantity"), "quantity is null");
            }
        }
    }

    // Helper method to log response details
    private void logResponse(Response response) {
        System.out.println("Response Status Code: " + response.getStatusCode());
//        System.out.println("Response Body: " + response.getBody().asString());
    }
}
