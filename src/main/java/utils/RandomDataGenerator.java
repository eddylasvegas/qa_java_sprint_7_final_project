package utils;

import models.Courier;
import org.apache.commons.lang3.RandomStringUtils;

public class RandomDataGenerator {
    public static String randomString(int length) {
        return RandomStringUtils.randomAlphabetic(length);
    }

    public static String randomDigits(int length) {
        return RandomStringUtils.randomNumeric(length);
    }

    public static Courier createRandomCourier() {
        return new Courier(
                RandomStringUtils.randomAlphanumeric(5), // Логин: 5 символов (буквы + цифры)
                RandomStringUtils.randomAlphanumeric(8), // Пароль: 8 символов
                RandomStringUtils.randomAlphabetic(10)   // Имя: 10 букв
        );
    }
}