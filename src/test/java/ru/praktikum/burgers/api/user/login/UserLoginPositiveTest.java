package ru.praktikum.burgers.api.user.login;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum.burgers.api.client.UserAPI;
import ru.praktikum.burgers.api.model.Credentials;
import ru.praktikum.burgers.api.model.User;
import ru.praktikum.burgers.api.util.UserGenerator;

public class UserLoginPositiveTest {
    private User user;
    private String token;

    @Before
    public void setUp() {
        user = UserGenerator.getRandom();
        ValidatableResponse response = UserAPI.register(user);
        token = response.extract().path("accessToken");
        int statusCode = response.extract().statusCode();
        Assert.assertEquals("Статус код не соответствует ожидаемому", 200, statusCode);
    }

    @After
    public void cleanUp() {
        ValidatableResponse response = UserAPI.delete(token);
        MatcherAssert.assertThat(response, CoreMatchers.notNullValue());
        boolean success = response.extract().path("success");
        Assert.assertTrue("Тело ответа не соответствует ожидаемому", success);
    }

    @Test
    @DisplayName("Авторизация юзера")
    public void userCanLogin() {
        ValidatableResponse response = UserAPI.login(new Credentials(user.getEmail(), user.getPassword()));
        int statusCode = response.extract().statusCode();
        Assert.assertEquals("Статус код не соответствует ожидаемому", 200, statusCode);
        boolean success = response.extract().path("success");
        Assert.assertTrue("Тело ответа не соответствует ожидаемому", success);
    }
}
