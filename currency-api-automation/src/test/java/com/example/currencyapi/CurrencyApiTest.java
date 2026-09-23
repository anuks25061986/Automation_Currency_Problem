package com.example.currencyapi;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class CurrencyApiTest {
    private static final String CURRENCY_LIST_URL ="https://cdn.jsdelivr.net/npm/@fawazahmed0/currency-api@latest/v1/currencies.json";
    private static final String CURRENT_CURRENCY_LIST_URL="https://cdn.jsdelivr.net/npm/@fawazahmed0/currency-api@latest/v1/currencies/{currencyCode}.json";

    @Test
    public void verifyCurrencyApi() {
       Response response =RestAssured.given().urlEncodingEnabled(false).when().get(CURRENCY_LIST_URL);
        System.out.println("Status Code: " + response.getStatusCode());
        System.out.println("Response Body: " + response.getBody().asString());
        Assert.assertEquals(response.getStatusCode(), 200);
        Map<String, Object> currencies=response.jsonPath().getMap("$");
        Assert.assertTrue(currencies.size()>20,"Currency list should contain more than 20 items");
        Assert.assertTrue(currencies.containsKey("gbp"),"British pound should be present");
        Assert.assertEquals(String.valueOf(currencies.get("gbp")),"British Pound","British pound should be present");
        Assert.assertTrue(currencies.containsKey("usd"),"USD should be present");
        Assert.assertEquals(String.valueOf(currencies.get("usd")),"US Dollar","USD should be present");
        List<String> currencyCodes=new ArrayList<>(currencies.keySet());
        System.out.println("Total currencies returned: "+currencyCodes.size());
        System.out.println("Currencies abbreviations: "+currencyCodes);
        int expectedCurrencyCount = 0;
        for (int i=0;i<currencyCodes.size();i++) {
         String currencyCode=currencyCodes.get(i);
         Response response1=RestAssured.given().urlEncodingEnabled(false).pathParam("currencyCode",currencyCode).when().get(CURRENT_CURRENCY_LIST_URL);
         System.out.println(currencyCode + " ----> Status Code: " + response1.getStatusCode());
         Assert.assertEquals(response1.getStatusCode(), 200, "API should return HTTP 200 for currency: " + currencyCode);
         Map<String, Object> exchangeRates = response1.jsonPath().getMap(currencyCode);
         int currentCurrencyCount=exchangeRates.size();
         if (i==0){
             expectedCurrencyCount=currentCurrencyCount;
             System.out.println("First currency : "+currencyCode+"  Currency count: "+expectedCurrencyCount);
         }
         else {
             Assert.assertEquals(currentCurrencyCount,expectedCurrencyCount,"Currency count mismatch for: "+currencyCode);
         }
        }
    }
}
