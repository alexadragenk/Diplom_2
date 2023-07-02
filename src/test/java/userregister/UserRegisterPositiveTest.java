package userregister;

import common.User;
import common.UserAPI;
import common.UserGenerator;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class UserRegisterPositiveTest {
    private String token;

    @After
    public void cleanUp() {
        ValidatableResponse response = UserAPI.delete(token);
        Assert.assertNotNull(response);
        boolean success = response.extract().path("success");
        Assert.assertTrue("Тело ответа не соответствует ожидаемому", success);
    }

    @Test
    @DisplayName("Регистрация юзера")
    public void userCanBeRegistrated() {
        User user = UserGenerator.getRandom();
        ValidatableResponse response = UserAPI.register(user);
        int statusCode = response.extract().statusCode();
        Assert.assertEquals("Статус код не соответствует ожидаемому", 200, statusCode);
        boolean success = response.extract().path("success");
        token = response.extract().path("accessToken");
        Assert.assertTrue("Тело ответа не соответствует ожидаемому", success);
    }
}
