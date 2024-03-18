package site.stellaburgers.switchtest;

import driver.WebDriverFactory;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import site.stellaburgers.pageobjectmodels.HeaderMenuPage;
import site.stellaburgers.pageobjectmodels.HomePage;
import site.stellaburgers.pageobjectmodels.LoginPage;
import site.stellaburgers.usercreate.UserCreate;
import site.stellaburgers.usercreate.UserStep;

import java.time.Duration;

import static org.junit.Assert.assertEquals;
import static site.stellaburgers.config.Config.LOGIN_PAGE_URI;
import static site.stellaburgers.config.Config.PROFILE_PAGE_URI;

public class SwitchToPersonalAccountTest {
    private HeaderMenuPage headerMenu;
    private HomePage homePage;
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
    }

    @Test
    @DisplayName("Переход на страницу личного кабинета после клика на кнопку Личный кабинет авторизированным пользователем")
    @Description("Успешный переход авторизированного пользователя на страницу личного кабинета после клика на кнопку Личный кабинет")
    public void goToPersonalAccountRegisteredUser() {
        //Создаем нового пользователя
        userStep = new UserStep();
        userCreate = UserCreate.getDataGeneratorUser();
        ValidatableResponse userResponse = userStep.createNewUser(userCreate);
        accessTokenUser = userResponse.extract().path("accessToken").toString().substring(7);
        //Открываем главную страницу
        homePage = new HomePage(driver);
        homePage.getHomePage();
        //Ожидание загрузки кнопки Конструктор в хедере
        headerMenu = new HeaderMenuPage(driver);
        headerMenu.waitConstructorButton();
        //Нажимаем на кнопку Личный кабинет в хедере
        headerMenu.clickPersonalAccountButton();
        //Авторизация пользователя на странице формы авторизации
        loginPage = new LoginPage(driver);
        loginPage.waitLoginPage();
        loginPage.fillLoginFormForRegisteredUser(userCreate.getEmail(), userCreate.getPassword());
        //Нажимаем на кнопку Личный кабинет в хедере
        headerMenu.clickPersonalAccountButton();
        //Проверяем, что произошел переход и текущий URL равен ожидаемому
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.urlToBe(PROFILE_PAGE_URI));
        Assert.assertEquals(PROFILE_PAGE_URI, driver.getCurrentUrl());
    }

    @Test
    @DisplayName("Переход на страницу формы авторизации после клика на кнопку Личный кабинет неавторизированным пользователем")
    @Description("Успешный переход неавторизированного пользователя на страницу формы авторизации после клика на кнопку Личный кабинет")
    public void goToPersonalAccountNotRegisteredUser() {
        HomePage homePage = new HomePage(driver);
        homePage.getHomePage();
        //Ожидание загрузки кнопки Конструктор в хедере
        HeaderMenuPage headerMenu = new HeaderMenuPage(driver);
        headerMenu.waitConstructorButton();
        //Нажимаем на кнопку Личный кабинет в хедере
        headerMenu.clickPersonalAccountButton();
        //Проверяем, что пользователь находится на странице формы авторизации и текущий URL равен ожидаемому
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