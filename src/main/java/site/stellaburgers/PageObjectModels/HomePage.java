package site.stellaburgers.PageObjectModels;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static site.stellaburgers.Config.Config.BASE_URI_STELLABURGERS;

public class HomePage {
    private static final By bunSection = By.xpath(".//span[text()='Булки']");
    private static final By fillingsSection = By.xpath(".//span[text()='Начинки']");
    private static final By saucesSection = By.xpath(".//span[text()='Соусы']");
    private static final By burgerIngredientsSectionName = By.xpath(".//div[contains(@class,'tab_tab_type_current')]");
    private final By loginButton = By.xpath("//button[text()='Войти в аккаунт']");
    private final By burgerIngredientSection = By.className("BurgerIngredients_ingredients__1N8v2");
    private final By orderCreateButton = By.xpath(".//*[text()='Оформить заказ']");
    private final WebDriver driver;

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Открыть главную страницу")
    public void getHomePage() {
        driver.get(BASE_URI_STELLABURGERS);
    }

    @Step("Ожидание загрузки раздела Соберите бургер")
    public void waitBurgerIngredientSection() {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(burgerIngredientSection));
    }

    @Step("Нажать на кнопку Войти в аккаунт")
    public void clickLoginButton() {
        // Добавлено ожидание загрузки кнопки
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(loginButton))
                .click();
    }

    @Step("Отображение кнопки Оформить заказ")
    public boolean checkOrderCreateButton() {
        return driver.findElement(orderCreateButton).isDisplayed();

    }

    @Step("Перейти к разделу Булки")
    public void clickBunSection() {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(bunSection))
                .click();
    }

    @Step("Перейти к разделу Соусы")
    public void clickSaucesSection() {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(saucesSection))
                .click();
    }

    @Step("Перейти к разделу Начинки")
    public void clickFillingsSection() {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(fillingsSection))
                .click();
    }

    @Step("Получить название раздела конструктора")
    public String getTextBurgerIngredientsSection() {
        return driver.findElement(burgerIngredientsSectionName).getText();

    }
}
