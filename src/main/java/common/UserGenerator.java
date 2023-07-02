package common;

import io.qameta.allure.Step;
import org.apache.commons.lang3.RandomStringUtils;
public class UserGenerator {
    @Step("Генерация данных пользователя")
    public static User getRandom() {
        final String email = RandomStringUtils.randomAlphabetic(8).toLowerCase() + "@gmail.com";
        final String password = RandomStringUtils.randomAlphabetic(8);
        final String name = RandomStringUtils.randomAlphabetic(8);
        return new User(email, password, name);
    }

    @Step("Генерация данных пользователя без email")
    public static User getRandomWithoutEmail() {
        final String password = RandomStringUtils.randomAlphabetic(8);
        final String name = RandomStringUtils.randomAlphabetic(8);
        return new User(null, password, name);
    }

    @Step("Генерация данных пользователя без пароля")
    public static User getRandomWithoutPassword() {
        final String email = RandomStringUtils.randomAlphabetic(8).toLowerCase()+ "@gmail.com";
        final String name = RandomStringUtils.randomAlphabetic(8);
        return new User(email, null, name);
    }
    @Step("Генерация данных пользователя без имени")
    public static User getRandomWithoutName() {
        final String email = RandomStringUtils.randomAlphabetic(8).toLowerCase()+ "@gmail.com";
        final String password = RandomStringUtils.randomAlphabetic(8);
        return new User(email, password, null);
    }
}
