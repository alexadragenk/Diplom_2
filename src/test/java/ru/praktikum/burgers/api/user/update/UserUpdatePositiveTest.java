package ru.praktikum.burgers.api.user.update;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Assert;
import org.junit.Test;
import ru.praktikum.burgers.api.BaseTest;
import ru.praktikum.burgers.api.client.UserAPI;
import ru.praktikum.burgers.api.model.Credentials;
import ru.praktikum.burgers.api.model.User;
import ru.praktikum.burgers.api.util.UserGenerator;

public class UserUpdatePositiveTest extends BaseTest {

    @Test
    @DisplayName("Изменение данных пользователя с авторизацией")
    public void userDataCanBeUpdated() {
        User newUser = UserGenerator.getRandom();
        ValidatableResponse response = UserAPI.update(newUser, token);
        int statusCode = response.extract().statusCode();
        Assert.assertEquals("Статус код не соответствует ожидаемому", 200, statusCode);
        boolean success = response.extract().path("success");
        String name = response.extract().path("user.name");
        String email = response.extract().path("user.email");
        Assert.assertTrue("Тело ответа не соответствует ожидаемому", success);
        Assert.assertEquals("Имя не соответствует ожидаемому", newUser.getName(), name);
        Assert.assertEquals("Email не соответствует ожидаемому", newUser.getEmail(), email);
        ValidatableResponse response2 = UserAPI.login(new Credentials(newUser.getEmail(), newUser.getPassword()));
        int statusCode2 = response2.extract().statusCode();
        Assert.assertEquals("Статус код не соответствует ожидаемому", 200, statusCode2);
    }
}
