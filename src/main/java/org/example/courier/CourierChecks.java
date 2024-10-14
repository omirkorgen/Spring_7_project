package org.example.courier;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import java.net.HttpURLConnection;

import static java.net.HttpURLConnection.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class CourierChecks {

    @Step("Проверка ответа успешной авторизации")
    public  int checkLoggedIn(ValidatableResponse loginResponse) {
        int id = loginResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .extract()
                .path("id");
        assertNotEquals(0, id);
        return id;
    }

    @Step("Проверка ответа успешного создания курьера")
    public void checkCreated(ValidatableResponse response) {
        boolean created = response
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_CREATED)
                .extract()
                .path("ok");
        assertTrue(created);
    }

    @Step("Проверка ответа удаления курьера")
    public void deletedSuccessfully(ValidatableResponse response) {
        response.assertThat()
                .statusCode(HTTP_OK)
                .body("ok", is(true))
        ;
    }

    @Step("Проверка успешного создания курьера")
    public String creationFailed(ValidatableResponse response) {
        return response.assertThat()
                .statusCode(HTTP_BAD_REQUEST)
                .body("message", notNullValue())
                .extract()
                .path("message")
                ;
    }

    @Step("Проверка ответа при создание двух одинаковых курьеров")
    public String creationTwoIdenticalCouriers(ValidatableResponse response) {
        return response.assertThat()
                .statusCode(HTTP_CONFLICT)
                .body("message", notNullValue())
                .extract()
                .path("message")
                ;
    }

    @Step("Проверка ответа при попытке авторизации без обязательного параметра")
    public String loginFailedWithoutOneParameter(ValidatableResponse response) {
        return response.assertThat()
                .statusCode(HTTP_BAD_REQUEST)
                .body("message", notNullValue())
                .extract()
                .path("message")
                ;
    }

    @Step("Проверка ответа при авторизации под несуществующим логином")
    public String testLoginFailedForNonExistentUser(ValidatableResponse response) {
        return response.assertThat()
                .statusCode(HTTP_NOT_FOUND)
                .body("message", notNullValue())
                .extract()
                .path("message")
                ;
    }


}
