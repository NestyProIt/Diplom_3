package site.stellaburgers;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import site.stellaburgers.driverFactory.WebDriverFactory;
import site.stellaburgers.pageObjectModels.HomePage;

import static org.junit.Assert.assertEquals;

public class ConstructorTest {
    private HomePage homePage;
    private WebDriverFactory webDriverFactory;
    private WebDriver driver;

    @Before
    public void setUp() {
        webDriverFactory = new WebDriverFactory();
        driver = webDriverFactory.getWebDriver();
        driver.manage().window().maximize();
        //Открываем главную страницу
        homePage = new HomePage(driver);
        homePage.getHomePage();
        //Ожидание загрузки раздела Соберите бургер
        homePage.waitBurgerIngredientSection();
    }

    @Test
    @DisplayName("Переход к разделу Булки")
    @Description("Успешный переход к разделу Булки")
    public void goToBunsSection() {
        //Нажимаем на раздел Начинки
        homePage.clickFillingsSection();
        //Нажимаем на раздел Булки
        homePage.clickBunSection();
        //Проверяем, что текст раздела Ингредиенты бургера равен Булки
        assertEquals("Булки", homePage.getTextBurgerIngredientsSection());
    }

    @Test
    @DisplayName("Переход к разделу Соусы")
    @Description("Успешный переход к разделу Соусы")
    public void goToSaucesSection() {
        //Нажимаем на раздел Соусы
        homePage.clickSaucesSection();
        //Проверяем, что текст раздела Ингредиенты бургера равен Соусы
        assertEquals("Соусы", homePage.getTextBurgerIngredientsSection());
    }

    @Test
    @DisplayName("Переход к разделу Начинки")
    @Description("Успешный переход к разделу Начинки")
    public void goToFillingsSection() {
        //Нажимаем на раздел Начинки
        homePage.clickFillingsSection();
        //Проверяем, что текст раздела Ингредиенты бургера равен Начинки
        assertEquals("Начинки", homePage.getTextBurgerIngredientsSection());
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}
