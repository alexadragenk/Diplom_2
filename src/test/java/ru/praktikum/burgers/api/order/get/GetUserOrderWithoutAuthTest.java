package ru.praktikum.burgers.api.order.get;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Assert;
import org.junit.Test;
import ru.praktikum.burgers.api.BaseTest;
import ru.praktikum.burgers.api.client.UserAPI;

public class GetUserOrderWithoutAuthTest extends BaseTest {

    @Test
    @DisplayName("Нельзя получить список заказов без авторизации")
    public void orderListCannotBeGottenWithoutAuth() {
        ValidatableResponse response = UserAPI.getOrders("");
        int statusCode = response.extract().statusCode();
        Assert.assertEquals("Статус код не соответствует ожидаемому", 401, statusCode);
        String isMessageCorrect = response.extract().path("message");
        Assert.assertEquals("Сообщение об ошибке не соответствует ожидаемому", "You should be authorised", isMessageCorrect);

    }
}
