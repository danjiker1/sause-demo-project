package tests.UI;

import io.qameta.allure.*;
import jdk.jfr.Description;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
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

    private static final String VALID_USERNAME = "standard_user";
    private static final String VALID_PASSWORD = "secret_sauce";
    private static final String INVALID_USERNAME = "wrong_user";
    private static final String EXPECTED_LOGO_TEXT = "Swag Labs";
    private static final String EXPECTED_ERROR_MESSAGE = "Epic sadface: Username and password do not match any user in this service";

    @BeforeEach
    public void setUp() {
        super.setUp();
        loginPage = new LoginPage(driver, wait);
    }

    @AfterEach
    @Override
    public void tearDown() {
        super.tearDown();
    }

    @Nested
    @Test
    @DisplayName("Ввод корректных данных при авторизации")
    @Description("Проверяем отправку обязательных полей логина и отображение логотипа на главной странице")
    @Severity(SeverityLevel.CRITICAL)
    void loginWithCorrectDataTest() {

        ProductsPage productsPage = loginPage.openPage()
                .login(VALID_USERNAME, VALID_PASSWORD);

        assertAll(
                () -> assertTrue(productsPage.isLogoDisplayed(), "Логотип должен быть виден"),
                () -> assertEquals(EXPECTED_LOGO_TEXT, productsPage.getLogoText(), "Текст логотипа неверный")
        );
    }

    @Nested
    @Test
    @DisplayName("Ввод некорректных данных при авторизации")
    @Description("Проверяем отображение ошибки при введении некорретных данных")
    @Severity(SeverityLevel.NORMAL)
    void loginWithIncorrectDataTest() {

        ProductsPage productsPage = loginPage.openPage()
                .login(INVALID_USERNAME, VALID_PASSWORD);

        assertAll(
                () -> assertNull(productsPage, "Мы не должны перейти на страницу продуктов"),
                () -> assertTrue(loginPage.isErrorDisplayed(), "Должно появиться сообщение об ошибке"),
                () -> assertEquals(EXPECTED_ERROR_MESSAGE, loginPage.getErrorMessageText(), "Текст ошибки не соответствует ожидаемому")
        );

    }

    @Test
    @DisplayName("Ввод пустого логина при авторизации")
    @Description("Проверяем отображение ошибки ,если оставить логин пустым")
    @Severity(SeverityLevel.NORMAL)
    void loginWithEmptyUsernameTest() {

        ProductsPage productsPage = loginPage
                .openPage()
                .login("", VALID_PASSWORD);

        assertAll(
                () -> assertNull(productsPage, "Мы не должны перейти на страницу продуктов"),
                () -> assertTrue(loginPage.isErrorDisplayed(), "Должно появиться сообщение об ошибке"),
                () -> assertEquals("Epic sadface: Username is required", loginPage.getErrorMessageText(), "Текст ошибки не соответствует ожидаемому")

        );

    }

    @ParameterizedTest(name = "Пользователь {0} -> Ожидаемый результат {2}")
    @CsvSource({
            "standard_user, secret_sauce, true",
            "problem_user, secret_sauce, true",
            "performance_glitch_user, secret_sauce, true"
    })
    @DisplayName("Проверка успешного входа на нескольких пользователях")
    @Description("Проверяем, что разные пользователи могут войти в систему")
    @Severity(SeverityLevel.CRITICAL)
    void loginWithDifferentUsersTest(String username, String password, boolean expectedSuccess) {

        ProductsPage productsPage = loginPage
                .openPage()
                .login(username, password);

        if (expectedSuccess) {
            assertAll(
                    () -> assertTrue(productsPage.isLogoDisplayed(), "Логотип должен быть виден"),
                    () -> assertEquals(EXPECTED_LOGO_TEXT, productsPage.getLogoText(), "Текст логотипа неверный")
            );
        } else {
            assertNull(productsPage, "Мы не должны перейти на страницу продуктов");
        }
    }


    @ParameterizedTest(name = "Сценарий: {0} + {1}")
    @CsvSource({
            "wrong_user, secret_sauce",
            "problem_user, wrong_password",
            "blocked_user, blocked_password"
    })
    @DisplayName("Проверка входа с неверными данными на нескольких пользователях")
    @Description("Проверяем, что разные пользователи могут войти в систему")
    @Severity(SeverityLevel.CRITICAL)
    void loginWithInvalidUsersTest(String username, String password) {

        ProductsPage productsPage = loginPage
                .openPage()
                .login(username, password);


        assertAll(
                () -> assertNull(productsPage, "Мы не должны перейти на страницу продуктов"),
                () -> assertTrue(loginPage.isErrorDisplayed(), "Должно появиться сообщение об ошибке"),
                () -> assertFalse(loginPage.getErrorMessageText().isEmpty(), "Текст ошибки не должен быть пустым")
        );


    }
}
