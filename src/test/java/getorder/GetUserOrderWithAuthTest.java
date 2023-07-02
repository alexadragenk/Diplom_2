package getorder;

import common.Credentials;
import common.User;
import common.UserAPI;
import common.UserGenerator;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class GetUserOrderWithAuthTest {
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
        boolean isUserTrue = response2.extract().path("success");
        Assert.assertTrue("Тело ответа не соответствует ожидаемому", isUserTrue);
    }

    @After
    public void cleanUp() {
        ValidatableResponse response = UserAPI.delete(token);
        Assert.assertNotNull(response);
        boolean success = response.extract().path("success");
        Assert.assertTrue("Тело ответа не соответствует ожидаемому", success);
    }

    @Test
    public void orderListCanBeGottenWithAuth() {
        ValidatableResponse response = UserAPI.getOrders(token);
        int statusCode = response.extract().statusCode();
        Assert.assertEquals("Статус код не соответствует ожидаемому", 200, statusCode);
        List orderList = response.extract().path("orders");
        Assert.assertNotNull(orderList);

    }
}
