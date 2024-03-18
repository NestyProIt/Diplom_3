package site.stellaburgers.registrationtest;

import driver.WebDriverFactory;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import site.stellaburgers.pageobjectmodels.HomePage;
import site.stellaburgers.pageobjectmodels.LoginPage;
import site.stellaburgers.pageobjectmodels.RegistrationPage;
import site.stellaburgers.usercreate.UserCreate;
import site.stellaburgers.usercreate.UserLogin;
import site.stellaburgers.usercreate.UserStep;

import static org.junit.Assert.assertTrue;

public class RegistrationNewUserTest {
    private UserStep userStep;
    private String accessTokenUser;
    private WebDriver driver;
    private UserCreate userCreate;
    private RegistrationPage registrationPage;
    private WebDriverFactory webDriverFactory;

    @Before
    public void setUp() {
        webDriverFactory = new WebDriverFactory();
        driver = webDriverFactory.getWebDriver();
        driver.manage().window().maximize();
        //Генерация случайного логина, пароля и имени для регистрации пользователя
        userStep = new UserStep();
        userCreate = UserCreate.getDataGeneratorUser();

    }

    @Test
    @DisplayName("Регистрация нового пользователя")
    @Description("Успешная регистрация нового пользователя")
    public void checkUserRegistration() {
        //Открываем страницу формы регистрации
        registrationPage = new RegistrationPage(driver);
        registrationPage.getRegistrationPage();
        //Заполняем поля name,email,password в форме регистрации
        registrationPage.registrationNewUser(userCreate.getName(), userCreate.getEmail(), userCreate.getPassword());
        //Нажимаем на кнопку Зарегистрироваться на странице формы регистрации
        registrationPage.clickRegistrationButton();
        //Проверяем, что пользователь действительно зарегистрировался и может войти в систему
        LoginPage loginPage = new LoginPage(driver);
        loginPage.openLoginPage();
        loginPage.waitLoginPage();
        loginPage.fillLoginFormForRegisteredUser(userCreate.getEmail(), userCreate.getPassword());
        HomePage homePage = new HomePage(driver);
        homePage.waitBurgerIngredientSection();
        assertTrue(homePage.checkOrderCreateButton());
    }

    @After//очистка данных после теста
    public void tearDown() {
        ValidatableResponse loginResponse = userStep.loginUser(UserLogin.getUserLogin(userCreate));
        accessTokenUser = loginResponse.extract().path("accessToken").toString().substring(7);
        userStep.deleteUser(accessTokenUser);
        driver.quit();
    }
}