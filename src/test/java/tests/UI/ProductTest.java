package tests.UI;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Nested;
import org.openqa.selenium.WebElement;
import pageobject.BasePage;
import pageobject.LoginPage;
import pageobject.ProductsPage;

import static org.junit.jupiter.api.Assertions.*;

@Epic("UI tests")
@Feature("Product tests")
@Owner("Daniil")

public class ProductTest extends BasePage {

    private LoginPage loginPage;
    private ProductsPage productsPage;

    private static final String VALID_USERNAME = "standard_user";
    private static final String VALID_PASSWORD = "secret_sauce";
    private static final String VALID_PRODUCT_NAME = "Sauce Labs Backpack";
    private static final String INVALID_PRODUCT_NAME = "Invalid name";

    @BeforeEach
    void loginUser(){

        loginPage = new LoginPage(driver,wait);

        productsPage = loginPage
                .openPage()
                .login(VALID_USERNAME,VALID_PASSWORD);

        assertNotNull(productsPage, "Не удалось войти");
    }

    @Nested
    @Test
    @DisplayName("Поиск товара по названию")
    @Description("Проверяем наличие нужного товара по имени")
    void searchProductByNameTest(){

        WebElement foundProduct = productsPage.getProductByName(VALID_PRODUCT_NAME);

        String actualName = foundProduct.getText();

        assertAll(
                () -> assertNotNull(foundProduct, "Товар не найден"),
                () ->assertTrue(actualName.contains(VALID_PRODUCT_NAME), "Имя товара не совпадает с " + VALID_PRODUCT_NAME)
        );
    }

    @Test
    @DisplayName("Поиск товара с некорректным названием")
    @Description("Проверяем отсутствие товара по несуществующему имени")
    void searchProductByInvalidNameTest(){

        WebElement foundProduct = productsPage.getProductByName(INVALID_PRODUCT_NAME);

        assertNull(foundProduct, "Товар с таким именем существует");

    }
}
