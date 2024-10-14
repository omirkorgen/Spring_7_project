package org.example.order;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderManager {
    public static final String BASE_URI = "https://qa-scooter.praktikum-services.ru";

    @Step("Отправка запроса на создания заказа")
    public ValidatableResponse createOrder(Order order) {
        return given()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .body(order)
                .when()
                .post("/api/v1/orders")
                .then();
    }

    @Step("Отправка запроса на получения списка заказов")
    public ValidatableResponse getListOfOrder() {
        return given()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .when()
                .get("/api/v1/orders")
                .then();
    }

}
