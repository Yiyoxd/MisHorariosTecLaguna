package com.yiyoalfredo.mishorariosteclaguna.service;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static com.yiyoalfredo.mishorariosteclaguna.service.DataExtractService.esperarHastaEncontrar;
import static com.yiyoalfredo.mishorariosteclaguna.service.DataExtractService.getWebDriver;

public class LoginAutomation {
    private static final String WEB_LINK = "http://apps2.itlalaguna.edu.mx/StatusAlumno/login.aspx";

    public static String getHTML(String matricula, String pass) {
        WebDriver driver = getWebDriver();
        try {
            driver.get(WEB_LINK);
            WebElement matriculaField = driver.findElement(By.id("tbLogin"));
            matriculaField.sendKeys(matricula);
            WebElement passwordField = driver.findElement(By.id("tbPassword"));
            passwordField.sendKeys(pass);
            WebElement loginButton = driver.findElement(By.id("Button2"));
            loginButton.click();
            esperarHastaEncontrar(driver, 5, "a");

            WebElement kardexLink = driver.findElement(By.id("linkKardex"));
            kardexLink.click();

            String kardexHtml = driver.getPageSource();
            return kardexHtml;
        } catch (Exception e) {
            System.err.println("Error durante extraccion del Kardex: ");
            System.out.println("Datos incorrectos, carrera no soportada o problemas con el servidor");
        } finally {
            driver.quit();
        }

        return null;
    }
}
