package ru.yandex.praktikum.clients;

import io.restassured.response.ValidatableResponse;
import ru.yandex.praktikum.pojo.Order;

import static io.restassured.RestAssured.given;

public class OrderListClient extends BaseClient {
    public ValidatableResponse getOrders() {
        return given()
                .spec(getSpec())
                .when()
                .get("/api/v1/orders")
                .then();

    }
}
