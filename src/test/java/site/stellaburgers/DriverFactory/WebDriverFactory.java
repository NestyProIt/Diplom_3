package site.stellaburgers.DriverFactory;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


//Класс выбора нужного браузера перед запуском теста
public class WebDriverFactory {
    public WebDriver getWebDriver() {
        ChromeOptions options = new ChromeOptions();
        String browser = "yandex";
        //String browser = "chrome";

        switch (browser) {
            case "yandex":
                System.setProperty("webdriver.chrome.driver", "src/main/resources/yandexdriver.exe");
                options.addArguments("--remote-allow-origins=*");
                return new ChromeDriver(options);
            case "chrome":
                System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
                options.addArguments("--remote-allow-origins=*");
                return new ChromeDriver(options);
            default:
                throw new RuntimeException("Необходимо указать браузер для запуска");
        }
    }
}






























