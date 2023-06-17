package ru.yandex.praktikum;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.yandex.praktikum.clients.OrderClient;
import ru.yandex.praktikum.dataprovider.OrderProvider;
import ru.yandex.praktikum.pojo.Order;

@RunWith(Parameterized.class)
public class CreateOrderTest {

//    @Before
//    public void setUp() {
//        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
//    }

    private final String[] color;

    public CreateOrderTest(String[] color) {
        this.color = color;
    }


    OrderClient orderClient = new OrderClient();
    Integer track;
    static String[] emptyMassive = {};
    static String[] blackMassive = {"BLACK"};
    static String[] blackGreyMassive = {"BLACK", "GREY"};

    @Parameterized.Parameters
    public static Object[][] color() {
        return new Object[][]{
                {emptyMassive},
                {blackMassive},
                {blackGreyMassive}


        };

    }

    //можно указать один из цветов — BLACK или GREY;
    //можно указать оба цвета;
    //можно совсем не указывать цвет;
    //тело ответа содержит track.
    //Чтобы протестировать создание заказа, нужно использовать параметризацию.
    @Test
    public void orderCanBeCreatedDifferentColorsOrNoColor() {

        Order order = OrderProvider.getRandomOrder();
        order.setColor(color);

        track = orderClient.create(order)
                .statusCode(201)
                .body("track", Matchers.notNullValue())
                .extract().jsonPath().get("track");


    }


    @After
    public void tearDown() {
        if (track != null) {
            orderClient.cancel(track) //в приложении баг - удаление заказа не работает
                    .statusCode(200);
        }
    }

}
