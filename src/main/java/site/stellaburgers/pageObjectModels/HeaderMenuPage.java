package site.stellaburgers.pageObjectModels;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HeaderMenuPage {
    private final By constructorButton = By.xpath(".//p[text()='Конструктор']");
    private final By logoButton = By.xpath(".//div[@class='AppHeader_header__logo__2D0X2']");
    private final By personalAccountButton = By.xpath(".//p[text()='Личный Кабинет']");
    private final WebDriver driver;

    public HeaderMenuPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Нажать на кнопку Конструктор в хедере")
    public void clickConstructorButton() {
        driver.findElement(constructorButton).click();
    }

    @Step("Нажать на логотип в хедере")
    public void clickLogoButton() {
        driver.findElement(logoButton).click();
    }

    @Step("Нажать на кнопку Личный кабинет в хедере")
    public void clickPersonalAccountButton() {
        driver.findElement(personalAccountButton).click();
    }

    @Step("Ожидание загрузки страницы - отображение кнопки Конструктор в хедере")
    public void waitConstructorButton() {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(constructorButton));
    }
}


