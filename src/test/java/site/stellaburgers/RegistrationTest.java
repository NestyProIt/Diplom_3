package site.stellaburgers;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import site.stellaburgers.driverFactory.WebDriverFactory;
import site.stellaburgers.pageObjectModels.HomePage;
import site.stellaburgers.pageObjectModels.LoginPage;
import site.stellaburgers.pageObjectModels.RegistrationPage;
import site.stellaburgers.userCreate.UserCreate;
import site.stellaburgers.userCreate.UserStep;

import static org.junit.Assert.assertTrue;


public class RegistrationTest {
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
        ValidatableResponse userResponse = userStep.createNewUser(userCreate);
        accessTokenUser = userResponse.extract().path("accessToken").toString().substring(7);
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


    @Test
    @DisplayName("Проверка наличия ошибки для некорректного пароля при регистрации пользователя")
    @Description("При вводе некорректного пароля выводится ошибка")
    public void checkUserCanNotRegistrationWithInvalidPassword() {
        //Открываем страницу формы регистрации
        registrationPage = new RegistrationPage(driver);
        registrationPage.getRegistrationPage();
        //Устанавливаем невалидный пароль
        userCreate.setPassword("123");
        //Заполняем поля name,email,password в форме регистрации
        registrationPage.registrationNewUser(userCreate.getName(), userCreate.getEmail(), userCreate.getPassword());
        //Нажимаем на кнопку Зарегистрироваться на странице формы регистрации
        registrationPage.clickRegistrationButton();
        //Проверяем, что выводится ошибка для некорректного пароля
        assertTrue(registrationPage.errorMessagePasswordIsDisplayed());
    }

    @After//очистка данных после теста
    public void tearDown() {
        if (accessTokenUser != null) {
            userStep.deleteUser(accessTokenUser);
        }
        driver.quit();
    }
}
