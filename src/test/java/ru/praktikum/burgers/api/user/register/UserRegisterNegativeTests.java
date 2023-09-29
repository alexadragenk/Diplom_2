package ru.praktikum.burgers.api.user.register;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Assert;
import org.junit.Test;
import ru.praktikum.burgers.api.client.UserAPI;
import ru.praktikum.burgers.api.model.User;
import ru.praktikum.burgers.api.util.UserGenerator;

public class UserRegisterNegativeTests {
    @Test
    @DisplayName("Нельзя создать двух одинаковых юзеров")
    public void sameUserCannotBeRegistrated() {
        User user = UserGenerator.getRandom();
        UserAPI.register(user);
        ValidatableResponse response = UserAPI.register(user);
        int statusCode = response.extract().statusCode();
        Assert.assertEquals("Статус код не соответствует ожидаемому", 403, statusCode);
        boolean success = response.extract().path("success");
        Assert.assertFalse("Тело ответа не соответствует ожидаемому", success);
        String message = response.extract().path("message");
        Assert.assertEquals("Сообщение об ошибке не соответствует ожидаемому", "User already exists", message);
    }

    @Test
    @DisplayName("Нельзя создать юзера, не указав пароль")
    public void passwordIsRequired() {
        User user = UserGenerator.getRandomWithoutPassword();
        ValidatableResponse response = UserAPI.register(user);
        int statusCode = response.extract().statusCode();
        Assert.assertEquals("Статус код не соответствует ожидаемому", 403, statusCode);
        boolean success = response.extract().path("success");
        Assert.assertFalse("Тело ответа не соответствует ожидаемому", success);
        String message = response.extract().path("message");
        Assert.assertEquals("Сообщение об ошибке не соответствует ожидаемому", "Email, password and name are required fields", message);
    }

    @Test
    @DisplayName("Нельзя создать юзера, не указав email")
    public void emailIsRequired() {
        User user = UserGenerator.getRandomWithoutEmail();
        ValidatableResponse response = UserAPI.register(user);
        int statusCode = response.extract().statusCode();
        Assert.assertEquals("Статус код не соответствует ожидаемому", 403, statusCode);
        boolean success = response.extract().path("success");
        Assert.assertFalse("Тело ответа не соответствует ожидаемому", success);
        String message = response.extract().path("message");
        Assert.assertEquals("Сообщение об ошибке не соответствует ожидаемому", "Email, password and name are required fields", message);
    }

    @Test
    @DisplayName("Нельзя создать юзера, не указав имя")
    public void nameIsRequired() {
        User user = UserGenerator.getRandomWithoutName();
        ValidatableResponse response = UserAPI.register(user);
        int statusCode = response.extract().statusCode();
        Assert.assertEquals("Статус код не соответствует ожидаемому", 403, statusCode);
        boolean success = response.extract().path("success");
        Assert.assertFalse("Тело ответа не соответствует ожидаемому", success);
        String message = response.extract().path("message");
        Assert.assertEquals("Сообщение об ошибке не соответствует ожидаемому", "Email, password and name are required fields", message);
    }
}
