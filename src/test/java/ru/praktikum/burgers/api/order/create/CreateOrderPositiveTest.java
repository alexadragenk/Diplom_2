package ru.praktikum.burgers.api.order.create;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Assert;
import org.junit.Test;
import ru.praktikum.burgers.api.BaseTest;
import ru.praktikum.burgers.api.client.UserAPI;
import ru.praktikum.burgers.api.model.Ingredients;

import java.util.List;

public class CreateOrderPositiveTest extends BaseTest {
    @Test
    @DisplayName("Создание заказа")
    public void OrderCanBeCreated() {
        ValidatableResponse ingredients = UserAPI.getIngredients();
        String ingredient = ingredients.extract().path("data[0]._id");
        ValidatableResponse response = UserAPI.createOrder(new Ingredients(List.of(ingredient)), token);
        int statusCode = response.extract().statusCode();
        Assert.assertEquals("Статус код не соответствует ожидаемому", 200, statusCode);
        boolean success = response.extract().path("success");
        Assert.assertTrue("Тело ответа не соответствует ожидаемому", success);
    }

    @Test
    @DisplayName("Создание заказа без авторизации")
    public void OrderCanBeCreatedWithoutAuth() {
        ValidatableResponse ingredients = UserAPI.getIngredients();
        String ingredient = ingredients.extract().path("data[0]._id");
        ValidatableResponse response = UserAPI.createOrder(new Ingredients(List.of(ingredient)), "");
        int statusCode = response.extract().statusCode();
        Assert.assertEquals("Статус код не соответствует ожидаемому", 200, statusCode);
        boolean success = response.extract().path("success");
        Assert.assertTrue("Тело ответа не соответствует ожидаемому", success);
    }
}
