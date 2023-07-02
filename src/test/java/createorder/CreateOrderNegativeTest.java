package createorder;

import common.*;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class CreateOrderNegativeTest {
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
        Assert.assertNotNull(response);
        boolean success = response.extract().path("success");
        Assert.assertTrue("Тело ответа не соответствует ожидаемому", success);
    }

    @Test
    public void OrderCannotBeCreatedWithoutIngredients() {
        ValidatableResponse response = UserAPI.createOrder(new Ingredients(List.of()), token);
        int statusCode = response.extract().statusCode();
        Assert.assertEquals("Статус код не соответствует ожидаемому", 400, statusCode);
    }

    @Test
    public void OrderCannotBeCreatedWithWrongIngredients() {
        ValidatableResponse response = UserAPI.createOrder(new Ingredients(List.of("плохая документация")), token);
        int statusCode = response.extract().statusCode();
        Assert.assertEquals("Статус код не соответствует ожидаемому", 500, statusCode);
    }
}
