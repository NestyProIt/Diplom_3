package site.stellaburgers.config;

import io.qameta.allure.Step;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.ErrorLoggingFilter;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class Config {
    public static final String BASE_URI_STELLABURGERS = "https://stellarburgers.nomoreparties.site/";//Базовый URL
    public static final String LOGIN_PAGE_URI = "https://stellarburgers.nomoreparties.site/login";//Страница формы авторизации
    public static final String REGISTRATION_PAGE = "https://stellarburgers.nomoreparties.site/register";//Страница формы регистрации
    public static final String PROFILE_PAGE_URI = "https://stellarburgers.nomoreparties.site/account/profile";//Страница личного кабинета
public  static final String RESTORE_PASSWORD_URI = "https://stellarburgers.nomoreparties.site/forgot-password";//Страница восстановления пароля
    @Step("Метод для создания настроек запроса: тип контента (JSON), базовый URI, логирование информации")
    protected RequestSpecification getSpec() {
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri(BASE_URI_STELLABURGERS)
                .setRelaxedHTTPSValidation()
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter())
                .addFilter(new ErrorLoggingFilter())
                .build();
    }

}