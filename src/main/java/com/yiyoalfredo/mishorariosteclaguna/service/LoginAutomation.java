package com.yiyoalfredo.mishorariosteclaguna.service;

import com.yiyoalfredo.mishorariosteclaguna.excepcion.InvalidCredentialsException;
import com.yiyoalfredo.mishorariosteclaguna.excepcion.LoginException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static com.yiyoalfredo.mishorariosteclaguna.service.DataExtractService.esperarHastaEncontrar;
import static com.yiyoalfredo.mishorariosteclaguna.service.DataExtractService.getWebDriver;

public class LoginAutomation {
    private static final String WEB_LINK = "http://apps2.itlalaguna.edu.mx/StatusAlumno/login.aspx";

    public static String getHTMLKardex(String matricula, String pass) {
        WebDriver driver = getWebDriver();
        driver.get(WEB_LINK);

        WebElement matriculaField = driver.findElement(By.id("tbLogin"));
        matriculaField.sendKeys(matricula);

        WebElement passwordField = driver.findElement(By.id("tbPassword"));
        passwordField.sendKeys(pass);

        WebElement loginButton = driver.findElement(By.id("Button2"));
        loginButton.click();

        if (!driver.findElements(By.id("lblError")).isEmpty()) {
            WebElement errorElement = driver.findElement(By.id("lblError"));
            if (errorElement.getText().contains("Error de autenticacion")) {
                System.out.println("Xdddd");
                throw new InvalidCredentialsException("Credenciales incorrectas.");
            }
        }

        esperarHastaEncontrar(driver, 5, "table");
        WebElement kardexLink = driver.findElement(By.id("linkKardex"));
        kardexLink.click();

        return driver.getPageSource();
    }
}
