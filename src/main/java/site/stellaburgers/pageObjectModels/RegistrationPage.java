package site.stellaburgers.pageObjectModels;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static site.stellaburgers.config.Config.REGISTRATION_PAGE;

public class RegistrationPage {
    private final By loginButtonFromRegistrationPage = By.xpath(".//*[text()='Войти']");
    private final By registrationForm = By.xpath(".//*[text()='Регистрация']");
    private final By nameFieldRegistration = By.xpath("//div/form/fieldset[1]//input[@name='name']");
    private final By emailFieldRegistration = By.xpath("//div/form/fieldset[2]//input[@name='name']");
    private final By passwordFieldRegistration = By.xpath(".//input[@name='Пароль']");
    private final By registrationButton = By.xpath(".//button[text()='Зарегистрироваться']");
    private final By errorMessagePassword = By.xpath(".//p[text() = 'Некорректный пароль']");
    private final WebDriver driver;

    public RegistrationPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Открыть страницу регистрации")
    public void getRegistrationPage() {
        driver.get(REGISTRATION_PAGE);
    }

    @Step("Ожидание загрузки страницы - отображение формы регистрации")
    public void waitRegistrationForm() {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(registrationForm));
    }

    @Step("Нажать на кнопку Зарегистрироваться")
    public void clickRegistrationButton() {
        driver.findElement(registrationButton).click();
    }

    @Step("Отображается ошибка Некорректный пароль")
    public boolean errorMessagePasswordIsDisplayed() {
        return driver.findElement(errorMessagePassword).isDisplayed();
    }

    @Step("Нажать на кнопку Войти")
    public void clickLoginButtonFromRegistrationPage() {
        // Добавлено ожидание загрузки кнопки
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(loginButtonFromRegistrationPage))
                .click();
    }

    @Step("Заполнить поле Имя")
    public void setNameFieldRegistration(String name) {
        driver.findElement(nameFieldRegistration).sendKeys(name);

    }

    @Step("Заполнить поле Email")
    public void setEmailFieldRegistration(String email) {
        driver.findElement(emailFieldRegistration).sendKeys(email);
    }

    @Step("Заполнить поле Пароль")
    public void setPasswordFieldRegistration(String password) {
        driver.findElement(passwordFieldRegistration).sendKeys(password);
    }

    @Step("Заполнить поля формы регистрации: name, email, password")
    public void registrationNewUser(String name, String email, String password) {
        waitRegistrationForm();
        setNameFieldRegistration(name);
        setEmailFieldRegistration(email);
        setPasswordFieldRegistration(password);
    }
}
