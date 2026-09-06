package tests.UI;

import io.qameta.allure.*;
import jdk.jfr.Description;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Nested;
import pageobject.BasePage;
import pageobject.LoginPage;
import pageobject.ProductsPage;

import static org.junit.jupiter.api.Assertions.*;

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

    private static final String VALID_USERNAME = "standard_user";
    private static final String VALID_PASSWORD = "secret_sauce";
    private static final String INVALID_USERNAME = "wrong_user";
    private static final String EXPECTED_LOGO_TEXT = "Swag Labs";
    private static final String EXPECTED_ERROR_MESSAGE = "Epic sadface: Username and password do not match any user in this service";


@Nested
@Test
@DisplayName("Ввод корректных данных при авторизации")
@Description("Проверяем отправку обязательных полей логина и отображение логотипа на главной странице")
@Severity(SeverityLevel.CRITICAL)
    void loginWithCorrectDataTest(){

    ProductsPage productsPage = loginPage.openPage()
            .login(VALID_USERNAME, VALID_PASSWORD);

    assertAll(
            () -> assertTrue(productsPage.isLogoDisplayed(),"Логотип должен быть виден"),
            () -> assertEquals(EXPECTED_LOGO_TEXT,productsPage.getLogoText(),"Текст логотипа неверный")
    );
}

@Nested
@Test
@DisplayName("Ввод некорректных данных при авторизации")
@Description("Проверяем отображение ошибки при введении некорретных данных")
@Severity(SeverityLevel.NORMAL)
    void loginWithIncorrectDataTest(){

    ProductsPage productsPage = loginPage.openPage()
            .login(INVALID_USERNAME,VALID_PASSWORD);

    assertAll(
            () -> assertNull(productsPage, "Мы не должны перейти на страницу продуктов"),
            () -> assertTrue(loginPage.isErrorDisplayed(),"Должно появиться сообщение об ошибке"),
    () -> assertEquals(EXPECTED_ERROR_MESSAGE, loginPage.getErrorMessageText(), "Текст ошибки не соответствует ожидаемому")
    );

}

@Test
@DisplayName("Ввод пустого логина при авторизации")
@Description("Проверяем отображение ошибки ,если оставить логин пустым")
@Severity(SeverityLevel.NORMAL)
    void loginWithEmptyUsernameTest(){

    ProductsPage productsPage = loginPage
            .openPage()
            .login("", VALID_PASSWORD);

    assertAll(
            () -> assertNull(productsPage, "Мы не должны перейти на страницу продуктов"),
            () -> assertTrue(loginPage.isErrorDisplayed(), "Должно появиться сообщение об ошибке"),
            () -> assertEquals("Epic sadface: Username is required", loginPage.getErrorMessageText(), "Текст ошибки не соответствует ожидаемому")

    );
}

}
