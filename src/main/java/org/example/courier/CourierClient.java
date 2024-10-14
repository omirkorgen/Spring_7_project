package org.example.courier;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class CourierClient {

    public static final String BASE_URI = "https://qa-scooter.praktikum-services.ru";

    @Step("Создание курьера")
    public ValidatableResponse createCourier(Courier courier) {
        return given()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .body(courier)
                .when()
                .post("/api/v1/courier")
                .then();
    }

    @Step("Авторизация курьера")
    public ValidatableResponse logIn(CourierCredentials courierCredentials) {
        return given()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .body(courierCredentials)
                .when()
                .post("/api/v1/courier/login")
                .then();
    }

    @Step("Удаление курьера")
    public ValidatableResponse deleteCourier(int courierId) {
        return given()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .body(Map.of("courierId", courierId))
                .when()
                .delete("/api/v1/courier/" + courierId)
                .then();
    }
}
