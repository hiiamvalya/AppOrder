package ru.netology;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AppOrderNegativeTest {
    private WebDriver driver;

    @BeforeAll
    public static void setup() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999");
    }

    @AfterEach
    public void afterEach() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldBeFailedIncorrectNameInput() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Vasya Ivanov");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+70123456789");
        driver.findElement(By.cssSelector("label[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button.button")).click();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.",
                driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText().trim());
    }

    @Test
    void shouldBeFailedEmptyNameInput() {
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+70123456789");
        driver.findElement(By.cssSelector("label[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button.button")).click();
        assertEquals("Поле обязательно для заполнения.",
                driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText().trim());
    }

    @Test
    void shouldBeFailedIncorrectPhoneInput() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Вася Иванов");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+7012345");
        driver.findElement(By.cssSelector("label[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button.button")).click();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +70123456789.",
                driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText().trim());
    }

    @Test
    void shouldBeFailedEmptyPhoneInput() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Вася Иванов");
        driver.findElement(By.cssSelector("label[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button.button")).click();
        assertEquals("Поле обязательно для заполнения.",
                driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText().trim());
    }

    @Test
    void shouldBeFailedUncheckedCheckbox() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Вася Иванов");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+70123456789");
        driver.findElement(By.cssSelector("button.button")).click();
        assertTrue(driver.findElement(By.cssSelector("[data-test-id=agreement].input_invalid .input__sub")).isDisplayed());
    }

}

