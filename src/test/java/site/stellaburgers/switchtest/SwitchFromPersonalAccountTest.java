package site.stellaburgers.switchtest;

import driver.WebDriverFactory;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import site.stellaburgers.pageobjectmodels.HeaderMenuPage;
import site.stellaburgers.pageobjectmodels.LoginPage;
import site.stellaburgers.usercreate.UserCreate;
import site.stellaburgers.usercreate.UserStep;

import java.time.Duration;

import static org.junit.Assert.assertEquals;
import static site.stellaburgers.config.Config.BASE_URI_STELLABURGERS;

public class SwitchFromPersonalAccountTest {
    private HeaderMenuPage headerMenu;
    private UserStep userStep;
    private String accessTokenUser;
    private UserCreate userCreate;
    private LoginPage loginPage;
    private WebDriver driver;
    private WebDriverFactory webDriverFactory;

    @Before
    public void setUp() {
        webDriverFactory = new WebDriverFactory();
        driver = webDriverFactory.getWebDriver();
        driver.manage().window().maximize();
        //Создаем нового пользователя
        userStep = new UserStep();
        userCreate = UserCreate.getDataGeneratorUser();
        ValidatableResponse userResponse = userStep.createNewUser(userCreate);
        accessTokenUser = userResponse.extract().path("accessToken").toString().substring(7);
    }

    @Test
    @DisplayName("Переход на страницу конструктора после клика на кнопку Конструктор на странице личного кабинета")
    @Description("Успешный переход на страницу конструктора после клика на кнопку Конструктор на странице личного кабинета")
    public void goToConConstructorBurgerByClickOnConstructorButton() {
        //Авторизация пользователя на странице формы авторизации
        loginPage = new LoginPage(driver);
        loginPage.openLoginPage();
        loginPage.waitLoginPage();
        loginPage.fillLoginFormForRegisteredUser(userCreate.getEmail(), userCreate.getPassword());
        //Нажимаем на кнопку Личный кабинет в хедере
        headerMenu = new HeaderMenuPage(driver);
        headerMenu.clickPersonalAccountButton();
        //Нажимаем на кнопку  Конструктор в хедере
        headerMenu.clickConstructorButton();
        //Проверяем, что произошел переход и текущий URL равен ожидаемому
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.urlToBe(BASE_URI_STELLABURGERS));
        assertEquals("Текущий URL не соответствует ожидаемому", BASE_URI_STELLABURGERS, driver.getCurrentUrl());

    }

    @Test
    @DisplayName("Переход на страницу конструктора после клика на логотип Stellar Burgers на странице личного кабинета")
    @Description("Успешный переход на страницу конструктора после клика на логотип Stellar Burgers на странице личного кабинета")
    public void goToConConstructorBurgerByClickOnLogo() {
        //Авторизация пользователя на странице формы авторизации
        loginPage = new LoginPage(driver);
        loginPage.openLoginPage();
        loginPage.waitLoginPage();
        loginPage.fillLoginFormForRegisteredUser(userCreate.getEmail(), userCreate.getPassword());
        //Нажимаем на кнопку Личный кабинет в хедере
        headerMenu = new HeaderMenuPage(driver);
        headerMenu.clickPersonalAccountButton();
        //Нажимаем на логотип Stellar Burgers в хедере
        headerMenu.clickLogoButton();
        //Проверяем, что произошел переход и текущий URL равен ожидаемому
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.urlToBe(BASE_URI_STELLABURGERS));
        assertEquals("Текущий URL не соответствует ожидаемому", BASE_URI_STELLABURGERS, driver.getCurrentUrl());
    }


    @After//очистка данных после теста
    public void tearDown() {
        if (accessTokenUser != null) {
            userStep.deleteUser(accessTokenUser);
        }
        driver.quit();
    }
}