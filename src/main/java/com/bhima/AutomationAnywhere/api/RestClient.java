package com.bhima.AutomationAnywhere.api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestClient {
    private final String baseUrl;

    public RestClient(String baseUrl) {
        this.baseUrl = baseUrl;
        RestAssured.baseURI = baseUrl;
    }

    public Response get(String path) {
        RequestSpecification req = RestAssured.given().relaxedHTTPSValidation().contentType("application/json");
        return req.when().get(path).then().extract().response();
    }

    public Response post(String path, Object body) {
        RequestSpecification req = RestAssured.given().relaxedHTTPSValidation().contentType("application/json").body(body);
        return req.when().post(path).then().extract().response();
    }
}
