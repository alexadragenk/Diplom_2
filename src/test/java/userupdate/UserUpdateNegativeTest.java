package userupdate;

import common.User;
import common.UserAPI;
import common.UserGenerator;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Assert;
import org.junit.Test;

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
