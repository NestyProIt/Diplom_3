package site.stellaburgers;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import site.stellaburgers.driverFactory.WebDriverFactory;
import site.stellaburgers.pageObjectModels.HeaderMenuPage;
import site.stellaburgers.pageObjectModels.LoginPage;
import site.stellaburgers.pageObjectModels.PersonalAccountPage;
import site.stellaburgers.userCreate.UserCreate;
import site.stellaburgers.userCreate.UserStep;

import java.time.Duration;

import static org.junit.Assert.assertEquals;
import static site.stellaburgers.config.Config.LOGIN_PAGE_URI;


public class LogoutTest {
    private UserStep userStep;
    private String accessTokenUser;
    private WebDriver driver;

    @Before
    public void setUp() {
        WebDriverFactory webDriverFactory = new WebDriverFactory();
        driver = webDriverFactory.getWebDriver();
        driver.manage().window().maximize();
        //Создаем нового пользователя
        userStep = new UserStep();
        UserCreate userCreate = UserCreate.getDataGeneratorUser();
        ValidatableResponse userResponse = userStep.createNewUser(userCreate);
        accessTokenUser = userResponse.extract().path("accessToken").toString().substring(7);
        //Авторизация пользователя на странице формы авторизации
        LoginPage loginPage = new LoginPage(driver);
        loginPage.openLoginPage();
        loginPage.waitLoginPage();
        loginPage.fillLoginFormForRegisteredUser(userCreate.getEmail(), userCreate.getPassword());
        //Нажимаем на кнопку Личный кабинет в хедере
        HeaderMenuPage headerMenu = new HeaderMenuPage(driver);
        headerMenu.clickPersonalAccountButton();

    }

    @Test
    @DisplayName("Проверка выхода из личного кабинета после клика на кнопку Выход на странице личного кабинета")
    @Description("Успешный выход пользователя из Личного кабинета, переход на страницу формы входа ")
    public void logOutOfPersonalAccount() {
        //Нажимаем на кнопку Выход
        PersonalAccountPage profilePage = new PersonalAccountPage(driver);
        profilePage.exitUserFromPersonalAccount();
        //Проверяем, что произошел выход из Личного кабинета и текущий URL равен ожидаемому
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.urlToBe(LOGIN_PAGE_URI));
        assertEquals("Текущий URL не соответствует ожидаемому", LOGIN_PAGE_URI, driver.getCurrentUrl());
    }

    @After//очистка данных после теста
    public void tearDown() {
        if (accessTokenUser != null) {
            userStep.deleteUser(accessTokenUser);
        }
        driver.quit();
    }
}





