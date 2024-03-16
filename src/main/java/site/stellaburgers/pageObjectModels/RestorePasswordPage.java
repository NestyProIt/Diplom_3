package site.stellaburgers.pageObjectModels;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static site.stellaburgers.config.Config.RESTORE_PASSWORD_URI;

public class RestorePasswordPage {
    private final By loginButton = By.xpath(".//a[text()='Войти']");
    private final By passwordRecoverySection = By.xpath(".//div[@class='Auth_login__3hAey']");
    private final WebDriver driver;

    public RestorePasswordPage(WebDriver driver) {
        this.driver = driver;
    }


    @Step("Открыть страницу восстановления пароля")
    public void getRestorePasswordPage() {
        driver.get(RESTORE_PASSWORD_URI);
    }

    @Step("Ожидание загрузки страницы - отображение Восстановление пароля")
    public void waitRestorePasswordPage() {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(passwordRecoverySection));
    }

    @Step("Нажать на кнопку Войти")
    public void clickLoginButton() {
        // Добавлено ожидание загрузки кнопки
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(loginButton))
                .click();
    }
}
