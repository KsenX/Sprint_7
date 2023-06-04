package ru.yandex.praktikum.clients;

import io.restassured.response.ValidatableResponse;
import ru.yandex.praktikum.pojo.Order;
import ru.yandex.praktikum.pojo.OrderNumber;

import static io.restassured.RestAssured.given;

public class OrderClient extends BaseClient {
    public ValidatableResponse create(Order order) {
        return given()
                .spec(getSpec())
                .body(order)
                .when()
                .post("/api/v1/orders")
                .then();

    }


    public ValidatableResponse cancel(Integer track) {
        OrderNumber orderNumber = new OrderNumber(track.toString());
        return given()
                .spec(getSpec())
                .body(orderNumber)
                .when()
                .put("/api/v1/orders/cancel")
                .then();

    }
}
