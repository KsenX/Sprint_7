package ru.yandex.praktikum.dataprovider;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import ru.yandex.praktikum.pojo.Order;

import java.time.LocalDate;
import java.util.Random;


public class OrderProvider {
    public static LocalDate randomDateOfOrder() {
        final int maxDateToOrder = 1 * 12 * 31;
        return LocalDate.now().plusDays(new Random().nextInt(maxDateToOrder));
    }

    public static Order getRandomOrder() {
        Order order = new Order();

        order.setFirstName(RandomStringUtils.randomAlphabetic(8));
        order.setLastName(RandomStringUtils.randomAlphabetic(10));
        order.setAddress(RandomStringUtils.randomAlphanumeric(20));
        order.setMetroStation(RandomStringUtils.randomNumeric(2));
        order.setPhone(RandomStringUtils.randomNumeric(10));
        order.setRentTime(RandomUtils.nextInt(0, 100));
        order.setDeliveryDate(randomDateOfOrder().toString());
        order.setComment(RandomStringUtils.randomAlphabetic(30));
        return order;
    }
}
