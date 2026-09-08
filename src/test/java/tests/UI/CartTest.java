package tests.UI;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pageobject.BasePage;
import pageobject.LoginPage;
import pageobject.ProductsPage;

import static org.junit.jupiter.api.Assertions.*;

@Epic("UI tests")
@Feature("Cart tests")
@Owner("Daniil")
public class CartTest extends BasePage {

     LoginPage loginPage;
     ProductsPage productsPage;

    private static final String VALID_USERNAME = "standard_user";
    private static final String VALID_PASSWORD = "secret_sauce";
    private static final String PRODUCT_NAME = "Sauce Labs Backpack";


    @BeforeEach
    void loginUser(){

        loginPage = new LoginPage(driver,wait);

        productsPage = loginPage
                .openPage()
                .login(VALID_USERNAME,VALID_PASSWORD);

        assertNotNull(productsPage, "Не удалось войти");
    }

    @Test
    @DisplayName("Добавление товара в корзину")
    @Description("Проверяем что товар добавляется в корзину ипри добавлении меняется его количество")
    void addProductToCartTest(){

        int beforeCount = productsPage.getProductCountInCart();

        productsPage.addProductToCart(PRODUCT_NAME);

        int afterCount = productsPage.getProductCountInCart();

        assertAll(
                () -> assertEquals(1, afterCount, "В корзине должен быть добавленный товар"),
                () -> assertEquals(beforeCount + 1, afterCount, "Должно увеличиться кол-во товаров на 1")
        );

    }

}
