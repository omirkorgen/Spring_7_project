package org.example.order;

import io.qameta.allure.Step;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.List;

public class Order {
    private String firstName;
    private String lastName;
    private String address;
    private int metroStation;
    private String phone;
    private int rentTime;
    private String deliveryDate;
    private String comment;
    private List<String> color;

    public Order(String firstName, String lastName, String address, int metroStation, String phone, int rentTime, String deliveryDate, String comment, List<String> color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }

    @Step("Генерация данных для заказа")
    public static Order generateOrder(List<String> color) {
        return new Order(
                "Temirlan" + RandomStringUtils.randomAlphanumeric(10),
                "Zhu",
                "Omsk",
                4,
                "+7 800 355 35 35",
                5,
                "2020-06-06",
                "Hello",
                color
        );
    }
}
