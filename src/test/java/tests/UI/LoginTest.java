package tests.UI;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import jdk.jfr.Description;
import org.junit.jupiter.api.*;
import pageobject.BasePage;
import pageobject.LoginPage;
import pageobject.ProductsPage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("UI tests")
@Feature("Login form test")
@Owner("Daniil")

public class LoginTest extends BasePage {


    LoginPage loginPage;

@BeforeEach
    public void setUp(){
        super.setUp();
    loginPage = new LoginPage(driver, wait);
}

@AfterEach
    @Override
    public void tearDown(){
        super.tearDown();
}

@Test
@DisplayName("Ввод корректных данных при авторизации")
@Description("Проверяем отправку обязательных полей логина и отображение логотипа на главной странице")
    void loginWithCorrectDataTest(){

    ProductsPage productsPage = loginPage.openPage()
            .login("standard_user", "secret_sauce");

    assertTrue(productsPage.isLogoDisplayed(),"Логотип должен быть виден");

    assertEquals("Swag Labs",productsPage.getLogoText(),"Текст логотипа неверный");

}

//@Disabled
//@Test
//@DisplayName("Ввод некорректных данных при авторизации")
//
//    void loginWintIncorrectDataTest(){
//
//    ProductsPage productsPage = loginPage.openPage()
//            .login("wrong_user", "secret_sauce");
//
//}

}
