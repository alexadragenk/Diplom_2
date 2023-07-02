package common;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class UserAPI extends BaseSettings{
    private static final String REGISTER_PATH= "/api/auth/register";
    private static final String LOGIN_PATH= "/api/auth/login";
    private static final String UPDATE_PATH= "/api/auth/user";
    private static final String CREATE_ORDER_PATH= "/api/orders";
    private static final String ORDER_PATH= "/api/orders";
    private static final String GET_INGREDIENTS= "/api/ingredients";

    @Step("Регистрация пользователя")
    public static ValidatableResponse register(User user) {
        return given()
                .spec(getBaseSettings())
                .body(user)
                .post(REGISTER_PATH)
                .then();
    }
    @Step("Авторизация пользователя")
    public static ValidatableResponse login(Credentials credentials) {
        return given()
                .spec(getBaseSettings())
                .body(credentials)
                .post(LOGIN_PATH)
                .then();
    }
    @Step("Изменение данных пользователя")
    public static ValidatableResponse update(User user, String token) {
        return given()
                .spec(getBaseSettings())
                .body(user)
                .header("Authorization", token)
                .patch(UPDATE_PATH)
                .then();
    }
    @Step("Удаление пользователя")
    public static ValidatableResponse delete(String token) {
        return given()
                .spec(getBaseSettings())
                .header("Authorization", token)
                .delete(UPDATE_PATH)
                .then();
    }
    @Step("Создание заказа")
    public static ValidatableResponse createOrder(Ingredients ingredients, String token) {
        return given()
                .spec(getBaseSettings())
                .body(ingredients)
                .header("Authorization", token)
                .post(CREATE_ORDER_PATH)
                .then();
    }
    @Step("Получение заказов конкретного пользователя")
    public static ValidatableResponse getOrders(String token) {
        return given()
                .spec(getBaseSettings())
                .header("Authorization", token)
                .get(ORDER_PATH)
                .then();
    }
    @Step("Получение списка ингредиентов")
    public static ValidatableResponse getIngredients() {
        return given()
                .spec(getBaseSettings())
                .get(GET_INGREDIENTS)
                .then();
    }
}
