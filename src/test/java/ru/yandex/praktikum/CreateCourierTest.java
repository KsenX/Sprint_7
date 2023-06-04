package ru.yandex.praktikum;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.apache.commons.lang3.RandomStringUtils;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.yandex.praktikum.clients.BaseClient;
import ru.yandex.praktikum.clients.CourierClient;
import ru.yandex.praktikum.dataprovider.CourierProvider;
import ru.yandex.praktikum.pojo.Courier;

@RunWith(Parameterized.class)
public class CreateCourierTest extends BaseClient {

//    @Before
//    public void setUp() {
//        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
//    }

    private final String login;
    private final String password;
    private final String firstName;


    CourierClient courierClient = new CourierClient();

    private Integer id;

    //курьера можно создать;
    //успешный запрос возвращает ok: true;
    //запрос возвращает правильный код ответа - код успешного создания курьера 201
    @Test
    public void courierCanBeCreated() {
        Courier courier = CourierProvider.getRandomCourier();
        courierClient.create(courier)
                .statusCode(201)
                .body("ok", Matchers.equalTo(true));


        id = courierClient.login(courier)
                .statusCode(200)
                .extract().jsonPath().get("id");


    }


    //нельзя создать двух одинаковых курьеров;
    //запрос возвращает правильный код ответа - код запроса с повторяющимся логином 409
    @Test
    public void identicalCouriersCanNotBeCreated() {
        Courier courier = CourierProvider.getRandomCourier();
        courierClient.create(courier)
                .statusCode(201)
                .body("ok", Matchers.equalTo(true));

        courierClient.create(courier)
                .statusCode(409)
                .body("message", Matchers.equalTo("Этот логин уже используется. Попробуйте другой."));


        id = courierClient.login(courier)
                .statusCode(200)
                .extract().jsonPath().get("id");

    }


    //чтобы создать курьера, нужно передать в ручку все обязательные поля;
    @Test
    public void courierCanBeCreatedWithOnlyMandatoryFields() {


        Courier courier = CourierProvider.getRandomCourierWithMandatoryFields();

        courierClient.create(courier)
                .statusCode(201)
                .body("ok", Matchers.equalTo(true));


        id = courierClient.login(courier)
                .statusCode(200)
                .extract().jsonPath().get("id");

    }


    //TODOесли одного из полей нет, запрос возвращает ошибку;
    //запрос возвращает правильный код ответа -запрос без логина или пароля 400
    @Parameterized.Parameters
    public static Object[][] courierIncomplete() {
        return new Object[][]{
                {null, RandomStringUtils.randomAlphabetic(8), null},
                {RandomStringUtils.randomAlphabetic(8), null, null},
                {null, null, RandomStringUtils.randomAlphabetic(8)}


        };

    }


    public CreateCourierTest(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }


    @Test
    public void courierCanNotBeCreatedWithoutMandatoryFields() {
        Courier courierIncomplete = new Courier(login, password, firstName);

        courierClient.create(courierIncomplete)
                .statusCode(400)
                .body("message", Matchers.equalTo("Недостаточно данных для создания учетной записи"));

//TODO Как-то лучше проверять, что курьер не создался ненароком
//        id = courierClient.login(courierIncomplete)
//                .body("id", Matchers.nullValue())
//                .extract().jsonPath().get("id");

    }

    //если создать пользователя с логином, который уже есть, возвращается ошибка.
    @Test
    public void courierWithTheSameLoginCanNotBeCreated() {


        Courier courier = CourierProvider.getRandomCourierWithMandatoryFields();

        courierClient.create(courier)
                .statusCode(201)
                .body("ok", Matchers.equalTo(true));
        Courier courier2 = new Courier(courier.getLogin(), RandomStringUtils.randomAlphabetic(8));

        courierClient.create(courier2)
                .statusCode(409)
                .body("message", Matchers.equalTo("Этот логин уже используется. Попробуйте другой."));

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
