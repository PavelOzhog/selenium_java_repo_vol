package org.ibs.appline;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class FirstTestTraining {


    @Test
    public void test() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);
        driver.get("https://uslugi.mosreg.ru/zdrav/");
        //driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        //((JavascriptExecutor) driver).executeAsyncScript("argument[0].click", driver);

        WebElement btnIn = driver.findElement(By.xpath("//*[@class = 'b-btn block-with-icon btn-with-icon btn-icon-profile-enter-white vi-border-hf-disable js-auth-sso-login']"));
        btnIn.click();
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        WebElement btmAuto = driver.findElement(By.xpath("//*[@class = 'form-control btn btn-primary wide btn--esia']"));
        btmAuto.click();


        Thread.sleep(5000);

        //  driver.quit();              //гасит драйвер полностью
        driver.close();             //закрывает активную вкладку

    }


}
