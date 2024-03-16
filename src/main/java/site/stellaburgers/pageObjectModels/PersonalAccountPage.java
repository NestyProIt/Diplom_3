package site.stellaburgers.pageObjectModels;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class PersonalAccountPage {
    private final By logOutButton = By.xpath(".//button[text()='Выход']");
    private final WebDriver driver;

    public PersonalAccountPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Ожидание загрузки страницы - отображение кнопки Выход в теле страницы")
    public void waitLogOutButton() {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(logOutButton));
    }

    @Step("Нажать на кнопку Выход")
    public void clickLogOutButton() {
        driver.findElement(logOutButton).click();
    }

    @Step("Выйти из Личного кабинета")
    public void exitUserFromPersonalAccount() {
        waitLogOutButton();
        clickLogOutButton();
    }
}
