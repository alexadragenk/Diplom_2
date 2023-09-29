package ru.praktikum.burgers.api.user.update;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Assert;
import org.junit.Test;
import ru.praktikum.burgers.api.client.UserAPI;
import ru.praktikum.burgers.api.model.User;
import ru.praktikum.burgers.api.util.UserGenerator;

public class UserUpdateNegativeTest {
    @Test
    @DisplayName("Изменение данных пользователя невозможно без авторизацией")
    public void userDataCannotBeUpdatedWithoutAuth() {
        User user = UserGenerator.getRandom();
        ValidatableResponse response = UserAPI.update(user, "");
        int statusCode = response.extract().statusCode();
        Assert.assertEquals("Статус код не соответствует ожидаемому", 401, statusCode);
        boolean success = response.extract().path("success");
        Assert.assertFalse("Тело ответа не соответствует ожидаемому", success);
    }
}
