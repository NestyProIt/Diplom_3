package site.stellaburgers.UserCreate;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import site.stellaburgers.Config.Config;

import static io.restassured.RestAssured.given;

public class UserStep extends Config {
    public static final String POST_USER_CREATE = "api/auth/register";//API Создание пользователя
    public static final String DELETE_USER = "api/auth/user";//API Удаление пользователя

    @Step("Создать пользователя")
    public ValidatableResponse createNewUser(UserCreate userCreate) {
        return given()
                .spec(getSpec())
                .body(userCreate)
                .when()
                .post(POST_USER_CREATE)
                .then();
    }

    @Step("Удалить пользователя")
    public ValidatableResponse deleteUser(String accessTokenUser) {
        return given()
                .spec(getSpec())
                .auth().oauth2(accessTokenUser)
                .when()
                .delete(DELETE_USER)
                .then();
    }
}

