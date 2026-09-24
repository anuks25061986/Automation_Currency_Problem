package com.example.currenapi;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.Map;

public class ParseJsonObjectResponseToMap {

    public static void main(String[] args) {
        Map<String,Object>
        responseBody = RestAssured.given().urlEncodingEnabled(false).baseUri("https://cdn.jsdelivr.net")
                .basePath("/npm/@fawazahmed0/currency-api@latest/v1")
                .contentType(ContentType.JSON)
                .when().get("/currencies.json").then().extract().body().jsonPath()
                        .getMap("$");
        System.out.println("Currency count: " + responseBody.size());
        System.out.println(responseBody);
    }
}
