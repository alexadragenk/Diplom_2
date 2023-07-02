package userupdate;

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

public class UserUpdatePositiveTest {
    private String token;

    @Before
    public void setUp() {
        User user = UserGenerator.getRandom();
        ValidatableResponse response = UserAPI.register(user);
        token = response.extract().path("accessToken");
        int statusCode = response.extract().statusCode();
        Assert.assertEquals("Статус код не соответствует ожидаемому", 200, statusCode);
        ValidatableResponse response2 = UserAPI.login(new Credentials(user.getEmail(), user.getPassword()));
        int statusCode2 = response2.extract().statusCode();
        Assert.assertEquals("Статус код не соответствует ожидаемому", 200, statusCode2);
        boolean success = response2.extract().path("success");
        Assert.assertTrue("Тело ответа не соответствует ожидаемому", success);
    }

    @After
    public void cleanUp() {
        ValidatableResponse response = UserAPI.delete(token);
        MatcherAssert.assertThat(response, CoreMatchers.notNullValue());
        boolean success = response.extract().path("success");
        Assert.assertTrue("Тело ответа не соответствует ожидаемому", success);
    }

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
