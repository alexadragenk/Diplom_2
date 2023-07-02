package userlogin;

import common.Credentials;
import common.User;
import common.UserAPI;
import common.UserGenerator;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UserLoginNegativeTests {
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
        boolean status = response.extract().path("success");
        Assert.assertTrue("Тело ответа не соответствует ожидаемому", status);
    }

    @Test
    @DisplayName("Авторизация юзера невозможна с неправильным паролем")
    public void loginWithWrongPassword() {
        User newUser = new User(user.getEmail(), "WrongPassword", user.getName());
        ValidatableResponse loginResponse = UserAPI.login(new Credentials(newUser.getEmail(), newUser.getPassword()));
        int statusCode = loginResponse.extract().statusCode();
        Assert.assertEquals("Статус код не соответствует ожидаемому", 401, statusCode);
        String isMessageCorrect = loginResponse.extract().path("message");
        Assert.assertEquals("Сообщение об ошибке не соответствует ожидаемому", "email or password are incorrect", isMessageCorrect);
    }

    @Test
    @DisplayName("Авторизация юзера невозможна с неправильным паролем")
    public void loginWithWrongEmail() {
        User newUser = new User("WrongLogin", user.getPassword(), user.getName());
        ValidatableResponse loginResponse = UserAPI.login(new Credentials(newUser.getEmail(), newUser.getPassword()));
        int statusCode = loginResponse.extract().statusCode();
        Assert.assertEquals("Статус код не соответствует ожидаемому", 401, statusCode);
        String message = loginResponse.extract().path("message");
        Assert.assertEquals("Сообщение об ошибке не соответствует ожидаемому", "email or password are incorrect", message);
    }
}

