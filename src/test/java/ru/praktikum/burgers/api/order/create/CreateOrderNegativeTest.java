package ru.praktikum.burgers.api.order.create;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Assert;
import org.junit.Test;
import ru.praktikum.burgers.api.BaseTest;
import ru.praktikum.burgers.api.client.UserAPI;
import ru.praktikum.burgers.api.model.Ingredients;

import java.util.List;

public class CreateOrderNegativeTest extends BaseTest {


    @Test
    @DisplayName("Нельзя создать заказ без указания ингредиентов")
    public void OrderCannotBeCreatedWithoutIngredients() {
        ValidatableResponse response = UserAPI.createOrder(new Ingredients(List.of()), token);
        int statusCode = response.extract().statusCode();
        Assert.assertEquals("Статус код не соответствует ожидаемому", 400, statusCode);
        String isMessageCorrect = response.extract().path("message");
        Assert.assertEquals("Сообщение об ошибке не соответствует ожидаемому", "Ingredient ids must be provided", isMessageCorrect);
    }

    @Test
    @DisplayName("Нельзя создать заказ с неправильным ингредиентом")
    public void OrderCannotBeCreatedWithWrongIngredients() {
        ValidatableResponse response = UserAPI.createOrder(new Ingredients(List.of("плохая документация")), token);
        int statusCode = response.extract().statusCode();
        Assert.assertEquals("Статус код не соответствует ожидаемому", 500, statusCode);
    }
}
