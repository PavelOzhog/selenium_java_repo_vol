package org.ibs.appline;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;


import java.util.concurrent.TimeUnit;

public class FirstTest {

    WebDriver driver;


    @Before
    public void testBefore() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-nofications");
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        String baseUrl = "https://www.rgs.ru/";
        driver.get(baseUrl);

        closeDynamicPopUp();

    }

    @Test

    public void testCase() {

        WebElement btnCookie = driver.findElement(By.xpath("//div[@class = 'col-md-1 btn-container']/div[@class='btn btn-default text-uppercase']"));
        btnCookie.click();

        WebElement btnMenu = driver.findElement(By.xpath("//li[@class = 'dropdown adv-analytics-navigation-line1-link current']//a [contains (text(), 'Меню')]"));
        btnMenu.click();

        WebElement health = driver.findElement(By.xpath("//div[@class='h3 adv-analytics-navigation-line2-link']//a [@class='hidden-xs' and contains(text(),'Здоровье')]"));
        health.click();


        WebElement volMedIns = driver.findElement(By.xpath("//*[contains(text(),'Добровольное медицинское страхование (ДМС)')]"));
        volMedIns.click();

        WebElement header = driver.findElement(By.tagName("h1"));
        Assert.assertEquals("объекта не найдено", "ДМС — добровольное медицинское страхование", header.getText());                       //проверка страницы на содержание заголовка

        WebElement sendAppForm = driver.findElement(By.xpath("//*[@class='btn btn-default text-uppercase hidden-xs adv-analytics-navigation-desktop-floating-menu-button' and contains(text(), 'Отправить заявку')]"));         //открываем форму заявки
        sendAppForm.click();

        WebElement headerSendForm = driver.findElement(By.tagName("h4"));
        Assert.assertEquals("Форма не открылась", "Заявка на добровольное медицинское страхование", headerSendForm.getAttribute("innerText"));

//заполнение формы

        fill(By.xpath("//*[@name='LastName']"), "Имя");             //заполнение поля Имя
        fill(By.xpath("//*[@name='FirstName']"), "Фамилия");        //заполнение поля Фамилия
        fill(By.xpath("//*[@name='MiddleName']"), "Отчество");      //заполнение поля Отчество
        fill(By.xpath("//div[@class='form-group col-md-6 col-xs-12']/*[contains(@data-bind,'value: Email')]"), "qwertyqwerty");             //заполнение поля email
        fill(By.xpath("//div[@class='form-group col-md-12 col-xs-12']/*[contains(@data-bind,'value: Comment')]"), "Коментарий");            //заполнение поля коментарий


        Select selectRegion = new Select(driver.findElement(By.xpath("//*[@class = 'row']//select[@name='Region']")));                      //выбор региона
        selectRegion.selectByVisibleText("Москва");


        fillPhone(By.xpath("//*[contains(@data-bind,'999')]"), "1111111111");                                                                   //заполнение телефона
        fillDate(By.xpath("//div[@class='form-group col-md-6 col-xs-12']/*[contains(@data-bind,'ContactDate')]"), "04082021");                  //заполнение даты

        WebElement checkBoxFill = driver.findElement(By.xpath("//input[@class='checkbox' and @type='checkbox']"));              //заполнение check-box элемента
        checkBoxFill.click();

        WebElement btnSend = driver.findElement(By.xpath("//div[@class='form-footer']//button[@id ='button-m' and @type='button']"));               //нажимаем на кнопку
        btnSend.click();

        WebElement attentionEmail = driver.findElement(By.xpath("//span[@class='validation-error-text']"));                                         //проверка сообщения при неккоректно введном поле
        Assert.assertEquals("Данного элемента нет на странице", "Введите адрес электронной почты", attentionEmail.getAttribute("innerText"));

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @After

    public void testAfter() {
        driver.close();

    }

    public void closeDynamicPopUp() {
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        try {
            WebElement element = driver.findElement(By.xpath("//*[@class='Ribbon-close']"));
            element.click();
        } catch (NoSuchElementException ignore) {
        } finally {
            driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        }
    }


    /**
     * Функция, предназначенная для
     *
     * @param locator - параметр предназначен для ввода элемента By (xpath,id)
     * @param value   - параметр предназначен для ввода элемента в форму заполнения
     */


    public void fill(By locator, String value) {
        WebElement element = driver.findElement(locator);
        element.click();
        element.clear();
        element.sendKeys(value);
        Assert.assertEquals("Поле не заполнено", value, value);

    }


    /**
     * Функция, заполняющая поле Дата
     * Пример:
     * fillDate(By.xpath("//path"), "Date(format:03082021)");
     *
     * @param locator - параметр предназначен для ввода элемента By (xpath,id)
     * @param value   - параметр предназначен для ввода элемента в форму заполнения
     */

    public void fillDate(By locator, String value) {
        WebElement dateFiled = driver.findElement(locator);
        dateFiled.click();
        dateFiled.clear();
        dateFiled.sendKeys(value);
        dateFiled.sendKeys(Keys.RETURN);
        Assert.assertEquals("Данные введены не корректна", "04.08.2021", dateFiled.getAttribute("value"));
    }


    /**
     * Функция, заполняющая поле Телефон, содержащее маску
     * Пример:
     * fillPhone(By.xpath("//path"), "1111111111");
     *
     * @param locator - параметр предназначен для ввода элемента By (xpath,id)
     * @param value   -   параметр предназначен для ввода элемента в форму заполнения
     */
    public void fillPhone(By locator, String value) {
        String maskRu = "+7";
        WebElement phoneField = driver.findElement(By.xpath("//*[contains(@data-bind,'999')]"));
        phoneField.clear();
        phoneField.click();
        phoneField.sendKeys("1111111111");
        Assert.assertEquals("Данные введены не верно", phoneField.getAttribute("value"), maskRu + " (111) 111-11-11");

    }

}


//rubish
//WebElement closePOPup = driver.findElement(By.xpath(""));

//WebElement formLastName = driver.findElement(By.xpath("//*[@name='LastName"));


//fill(By.xpath("//*[contains(@data-bind,'999')]"), "1111111111");

//fill(By.xpath("//div[@class='form-group col-md-6 col-xs-12']/*[contains(@data-bind,'ContactDate')]"), "03082021");

//        WebElement phoneField = driver.findElement(By.xpath("//*[contains(@data-bind,'999')]"));                //заполнение полес телефон
//        phoneField.clear();
//        phoneField.click();
//        phoneField.sendKeys("1111111111");

//        WebElement dateFiled = driver.findElement(By.xpath("//div[@class='form-group col-md-6 col-xs-12']/*[contains(@data-bind,'ContactDate')]"));  //заполнение поле дата
//        dateFiled.click();
//        dateFiled.clear();
//        dateFiled.sendKeys("03082021");
//        dateFiled.sendKeys(Keys.RETURN);

//fiiledMaskForm(By.xpath("//*[contains(@data-bind,'999')]"),"11111111");
//fill(By.xpath("//*[contains(@data-bind,'999')]"), "1111111111");
//  fill(By.xpath("//*[@name='LastName']"), "Имя");

//    public void fiiledMaskForm(By locator, String value) {
//        WebElement phoneField = driver.findElement(locator);
//        phoneField.click();
//        phoneField.clear();
//        phoneField.sendKeys("value");
//    }