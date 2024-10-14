package org.example.order;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import static java.net.HttpURLConnection.HTTP_CREATED;
import static java.net.HttpURLConnection.HTTP_OK;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertNotEquals;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.greaterThan;

public class OrderChecks {

    @Step("Провека успешного создания заказа")
    public int creationSuccessfullyOrder(ValidatableResponse response) {
        int track = response.assertThat()
                .statusCode(HTTP_CREATED)
                .body("track", notNullValue())
                .extract()
                .path("track")
                ;
        assertNotEquals(0, track);
        return track;
    }

    @Step("Провека успешного получения списка заказов")
    public void getListOfOrdersSuccessfully(ValidatableResponse response) {
        response.assertThat()
                .statusCode(HTTP_OK)
                .body("orders", notNullValue())
                .body("orders", hasSize(greaterThan(0)));
        ;
    }
}
