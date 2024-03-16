package site.stellaburgers.registrationTest;

import driver.WebDriverFactory;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import site.stellaburgers.pageObjectModels.RegistrationPage;
import site.stellaburgers.userCreate.UserCreate;

import static org.junit.Assert.assertTrue;

public class MessageErrorForInvalidPasswordTest {
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
        userCreate = UserCreate.getDataGeneratorUser();

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

    @After
    public void tearDown() {
        driver.quit();
    }
}