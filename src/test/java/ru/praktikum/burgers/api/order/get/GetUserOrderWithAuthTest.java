package ru.praktikum.burgers.api.order.get;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Assert;
import org.junit.Test;
import ru.praktikum.burgers.api.BaseTest;
import ru.praktikum.burgers.api.client.UserAPI;

import java.util.List;

public class GetUserOrderWithAuthTest extends BaseTest {
    @Test
    @DisplayName("Получение списока заказов с авторизацией")
    public void orderListCanBeGottenWithAuth() {
        ValidatableResponse response = UserAPI.getOrders(token);
        int statusCode = response.extract().statusCode();
        Assert.assertEquals("Статус код не соответствует ожидаемому", 200, statusCode);
        List orderList = response.extract().path("orders");
        Assert.assertNotNull(orderList);
        boolean success = response.extract().path("success");
        Assert.assertTrue("Тело ответа не соответствует ожидаемому", success);

    }
}
