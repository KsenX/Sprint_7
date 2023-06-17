package ru.yandex.praktikum.dataprovider;

import org.apache.commons.lang3.RandomStringUtils;
import ru.yandex.praktikum.pojo.Courier;

public class CourierProvider {
    public static Courier getRandomCourier() {
        Courier courier = new Courier(RandomStringUtils.randomAlphabetic(8),
                RandomStringUtils.randomAlphabetic(8), RandomStringUtils.randomAlphabetic(8));
        return courier;
    }

    public static Courier getRandomCourierWithMandatoryFields() {
        Courier courier = new Courier(RandomStringUtils.randomAlphabetic(8),
                RandomStringUtils.randomAlphabetic(8));
        return courier;
    }
}
