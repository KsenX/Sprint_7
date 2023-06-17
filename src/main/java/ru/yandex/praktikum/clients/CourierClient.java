package ru.yandex.praktikum.clients;

import io.restassured.response.ValidatableResponse;
import ru.yandex.praktikum.pojo.Courier;

import static io.restassured.RestAssured.given;

public class CourierClient extends BaseClient {
    public ValidatableResponse create(Courier courier) {
        return given()
                .spec(getSpec())
                .body(courier)
                .when()
                .post("/api/v1/courier")
                .then();

    }

    public ValidatableResponse login(Courier courier) {
        return given()
                .spec(getSpec())
                .body(courier)
                .when()
                .post("/api/v1/courier/login")
                .then();

    }

    public ValidatableResponse delete(int id) {
        return given()
                .spec(getSpec())
                .pathParam("id", id)
                .when()
                .delete("/api/v1/courier/{id}")
                .then();

    }

}
