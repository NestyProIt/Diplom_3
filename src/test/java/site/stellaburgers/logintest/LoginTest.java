package site.stellaburgers.logintest;

import driver.WebDriverFactory;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import site.stellaburgers.pageobjectmodels.*;
import site.stellaburgers.usercreate.UserCreate;
import site.stellaburgers.usercreate.UserStep;

import static org.junit.Assert.assertTrue;

public class LoginTest {
    private UserStep userStep;
    private String accessTokenUser;
    private HomePage homePage;
    private UserCreate userCreate;
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
    @DisplayName("Вход пользователя в систему по кнопке Войти в аккаунт на главной странице")
    @Description("Успешный вход пользователя в систему")
    public void loginByLoginButtonOnHomePage() {
        //Открываем главную страницу
        homePage = new HomePage(driver);
        homePage.getHomePage();
        //Ожидание загрузки раздела Соберите бургер
        homePage.waitBurgerIngredientSection();
        //Нажимаем на кнопку Войти в аккаунт
        homePage.clickLoginButton();
        //Авторизация пользователя на странице формы авторизации
        LoginPage loginPage = new LoginPage(driver);
        loginPage.waitLoginPage();
        loginPage.fillLoginFormForRegisteredUser(userCreate.getEmail(), userCreate.getPassword());
        //Проверяем, что пользователь вошел в систему
        homePage.waitBurgerIngredientSection();
        assertTrue(homePage.checkOrderCreateButton());
    }

    @Test
    @DisplayName("Вход пользователя в систему по кнопке Личный кабинет")
    @Description("Успешный вход пользователя в систему")
    public void loginByPersonalAccountButtonOnHomePage() {
        //Открываем главную страницу
        homePage = new HomePage(driver);
        homePage.getHomePage();
        //Ожидание загрузки раздела Соберите бургер
        homePage.waitBurgerIngredientSection();
        HeaderMenuPage headerMenuPage = new HeaderMenuPage(driver);
        //Нажимаем на кнопку Личный кабинет
        headerMenuPage.clickPersonalAccountButton();
        //Авторизация пользователя на странице формы авторизации
        LoginPage loginPage = new LoginPage(driver);
        loginPage.waitLoginPage();
        loginPage.fillLoginFormForRegisteredUser(userCreate.getEmail(), userCreate.getPassword());
        //Проверяем, что пользователь вошел в систему
        homePage.waitBurgerIngredientSection();
        assertTrue(homePage.checkOrderCreateButton());

    }

    @Test
    @DisplayName("Вход пользователя в систему через кнопку Войти в форме регистрации")
    @Description("Успешный вход пользователя в систему")
    public void loginByLoginButtonOnRegistrationPage() {
        //Открываем страницу формы регистрации
        RegistrationPage registrationPage = new RegistrationPage(driver);
        registrationPage.getRegistrationPage();
        registrationPage.waitRegistrationForm();
        //Нажимаем на кнопку Войти
        registrationPage.clickLoginButtonFromRegistrationPage();
        //Авторизация пользователя на странице формы авторизации
        LoginPage loginPage = new LoginPage(driver);
        loginPage.waitLoginPage();
        loginPage.fillLoginFormForRegisteredUser(userCreate.getEmail(), userCreate.getPassword());
        //Проверяем, что пользователь вошел в систему
        homePage = new HomePage(driver);
        homePage.waitBurgerIngredientSection();
        assertTrue(homePage.checkOrderCreateButton());
    }

    @Test
    @DisplayName("Вход пользователя в систему через кнопку Войти в форме восстановления пароля")
    @Description("Успешный вход пользователя в систему")
    public void loginByLoginButtonOnRestorePasswordPage() {
        //Открываем страницу восстановления пароля
        RestorePasswordPage restorePasswordPage = new RestorePasswordPage(driver);
        restorePasswordPage.getRestorePasswordPage();
        restorePasswordPage.waitRestorePasswordPage();
        //Нажимаем на кнопку Войти
        restorePasswordPage.clickLoginButton();
        //Авторизация пользователя на странице формы авторизации
        LoginPage loginPage = new LoginPage(driver);
        loginPage.openLoginPage();
        loginPage.waitLoginPage();
        loginPage.fillLoginFormForRegisteredUser(userCreate.getEmail(), userCreate.getPassword());
        //Проверяем, что пользователь вошел в систему
        homePage = new HomePage(driver);
        homePage.waitBurgerIngredientSection();
        assertTrue(homePage.checkOrderCreateButton());
    }

    @After//очистка данных после теста
    public void tearDown() {
        if (accessTokenUser != null) {
            userStep.deleteUser(accessTokenUser);
        }
        driver.quit();
    }
}