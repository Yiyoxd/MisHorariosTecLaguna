package com.yiyoalfredo.mishorariosteclaguna.service;

import com.yiyoalfredo.mishorariosteclaguna.excepcion.InvalidCredentialsException;
import com.yiyoalfredo.mishorariosteclaguna.excepcion.LoginException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class DataExtractService {
    private static final ChromeOptions options = new ChromeOptions();
    private static final String WEB_HORARIOS = "http://apps.itlalaguna.edu.mx/servicios/academicos/horario_materias_2020/index.htm";

    static {
        System.setProperty("webdriver.chrome.driver", "./libs/chromedriver.exe");
        options.setBinary("C:\\Program Files\\BraveSoftware\\Brave-Browser\\Application\\brave.exe");
        options.addArguments("--headless");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");

        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.managed_default_content_settings.images", 2);
        options.setExperimentalOption("prefs", prefs);
    }

    public static WebDriver getWebDriver() {
        return new ChromeDriver(options);
    }

    public static String fetchHtmlForCareer(String carreraValue) {
        try {
            WebDriver driver = getWebDriver();
            driver.get(WEB_HORARIOS);

            WebElement carreraDropdown = driver.findElement(By.name("ESPECIALIDAD"));
            carreraDropdown.sendKeys(carreraValue);
            WebElement consultarButton = driver.findElement(By.name("BotConsultar"));
            consultarButton.click();
            esperarHastaEncontrar(driver, 3, "table");

            return driver.getPageSource();
        } catch (Exception e) {
            System.err.println("Error durante la consulta: " + e.getMessage());
            return null;
        }
    }

    public static void esperarHastaEncontrar(WebDriver driver, int tiempoMaximoEspera, String elemento) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(tiempoMaximoEspera));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName(elemento)));
    }
}
