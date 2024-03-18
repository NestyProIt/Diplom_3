package site.stellaburgers.pageobjectmodels;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static site.stellaburgers.config.Config.LOGIN_PAGE_URI;


public class LoginPage {
    private final By emailField = By.xpath(".//label[text() = 'Email']/../input[contains(@name, 'name')]");
    private final By passwordField = By.xpath(".//label[text() = 'Пароль']/../input[contains(@type, 'password')]");
    private final By loginEnterButton = By.xpath(".//*[text()='Войти']");
    private final By authorizationForm = By.className("Auth_login__3hAey");
    private final WebDriver driver;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Открыть страницу формы авторизации")
    public void openLoginPage() {
        driver.get(LOGIN_PAGE_URI);
    }

    @Step("Ожидание загрузки страницы формы авторизации - отображение формы авторизации")
    public void waitLoginPage() {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(authorizationForm));
    }

    @Step("Заполнить поле email")
    public void setEmailField(String email) {
        driver.findElement(emailField).sendKeys(email);
    }

    @Step("Заполнить поле password")
    public void setPasswordField(String password) {
        driver.findElement(passwordField).sendKeys(password);
    }

    @Step("Нажать на кнопку Войти")
    public void clickLoginEnterButton() {
        // Добавлено ожидание загрузки кнопки
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(loginEnterButton))
                .click();
    }

    @Step("Заполнить форму авторизации для зарегистрированного пользователя")
    public void fillLoginFormForRegisteredUser(String email, String password) {
        setEmailField(email);
        setPasswordField(password);
        clickLoginEnterButton();
    }
}



