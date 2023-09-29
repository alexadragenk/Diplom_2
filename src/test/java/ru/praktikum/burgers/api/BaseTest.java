package ru.praktikum.burgers.api;

import io.restassured.response.ValidatableResponse;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import ru.praktikum.burgers.api.client.UserAPI;
import ru.praktikum.burgers.api.model.Credentials;
import ru.praktikum.burgers.api.model.User;
import ru.praktikum.burgers.api.util.UserGenerator;

public class BaseTest {
    protected String token;

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
}
