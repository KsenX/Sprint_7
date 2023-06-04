package ru.yandex.praktikum;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.praktikum.clients.CourierClient;
import ru.yandex.praktikum.dataprovider.CourierProvider;
import ru.yandex.praktikum.pojo.Courier;

public class LoginCourierTest {
//    @Before
//    public void setUp() {
//        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
//    }

    Integer id;
    CourierClient courierClient = new CourierClient();

    //курьер может авторизоваться;
    //успешный запрос возвращает id.
    //для авторизации нужно передать все обязательные поля;
    @Test
    public void courierCanLogin() {

        Courier courier = CourierProvider.getRandomCourierWithMandatoryFields();

        courierClient.create(courier)
                .statusCode(201)
                .body("ok", Matchers.equalTo(true));


        id = courierClient.login(courier)
                .statusCode(200)
                .body("id", Matchers.notNullValue())
                .extract().jsonPath().get("id");

    }

    //курьер может авторизоваться;
    //мы также можем использовать firstName, и это работает
    @Test
    public void courierCanLoginWithOptionalField() {

        Courier courier = CourierProvider.getRandomCourier();

        courierClient.create(courier)
                .statusCode(201)
                .body("ok", Matchers.equalTo(true));


        id = courierClient.login(courier)
                .statusCode(200)
                .body("id", Matchers.notNullValue())
                .extract().jsonPath().get("id");

    }


    //система вернёт ошибку, если неправильно указать логин или пароль;
    @Test
    public void courierCanNotLoginWithIncorrectPassword() {

        Courier courier = CourierProvider.getRandomCourierWithMandatoryFields();

        courierClient.create(courier)
                .statusCode(201)
                .body("ok", Matchers.equalTo(true));

        Courier invalidCourier = new Courier(courier.getLogin(), courier.getPassword() + "11");
        courierClient.login(invalidCourier)
                .statusCode(404)
                .body("message", Matchers.equalTo("Учетная запись не найдена"))
                .extract().jsonPath().get("id");


        id = courierClient.login(courier)
                .statusCode(200)
                .extract().jsonPath().get("id");

    }

    //если авторизоваться под несуществующим пользователем, запрос возвращает ошибку;
    //система вернёт ошибку, если неправильно указать логин или пароль;
    //TODO можно еще проверить существующие логин-пароль разных пользователей
    @Test
    public void courierCanNotLoginWithIncorrectLogin() {

        Courier courier = CourierProvider.getRandomCourierWithMandatoryFields();

        courierClient.create(courier)
                .statusCode(201)
                .body("ok", Matchers.equalTo(true));

        Courier invalidCourier = new Courier(courier.getLogin() + "11", courier.getPassword());

        courierClient.login(invalidCourier)
                .statusCode(404)
                .body("message", Matchers.equalTo("Учетная запись не найдена"));


        id = courierClient.login(courier)
                .statusCode(200)
                .extract().jsonPath().get("id");

    }


    //если какого-то поля нет, запрос возвращает ошибку;
    @Test
    public void courierCanNotLoginWithOutLogin() {

        Courier courier = CourierProvider.getRandomCourierWithMandatoryFields();

        courierClient.create(courier)
                .statusCode(201)
                .body("ok", Matchers.equalTo(true));

        Courier invalidCourier = new Courier(null, courier.getPassword());

        courierClient.login(invalidCourier)
                .statusCode(400)
                .body("message", Matchers.equalTo("Недостаточно данных для входа"));


        id = courierClient.login(courier)
                .statusCode(200)
                .extract().jsonPath().get("id");

    }

    @Test
    public void courierCanNotLoginWithOutPassword() {

        Courier courier = CourierProvider.getRandomCourierWithMandatoryFields();

        courierClient.create(courier)
                .statusCode(201)
                .body("ok", Matchers.equalTo(true));

        Courier invalidCourier = new Courier(courier.getLogin(), null);

        courierClient.login(invalidCourier)
                .statusCode(400)
                .body("message", Matchers.equalTo("Недостаточно данных для входа"));


        id = courierClient.login(courier)
                .statusCode(200)
                .extract().jsonPath().get("id");

    }


    @After
    public void tearDown() {
        if (id != null) {
            courierClient.delete(id)
                    .statusCode(200);
        }
    }
}
