package ru.yandex.praktikum;

import org.hamcrest.Matchers;
import org.junit.Test;
import ru.yandex.praktikum.clients.OrderListClient;

public class OrderListTest {
    //Проверь, что в тело ответа возвращается список заказов.
    @Test
    public void orderListIsReturned() {
        OrderListClient orderListClient = new OrderListClient();
        orderListClient.getOrders()
                .statusCode(200)
                .body("orders.courierId",Matchers.notNullValue() );
    }
}
