package pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class ProductsPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By mainLogoLocator = By.xpath("//div[@class='app_logo']");
    private final By productItemLocator = By.xpath("//div[@class='inventory_item']");
    private final By productNameLocator = By.xpath("//*[contains(@class,'inventory_item_name')]");
    private final By buttonAddToCart = By.xpath("//*[@name='add-to-cart-sauce-labs-backpack']");
    private final By cartBadgeLocator = By.xpath("//*[@class='shopping_cart_badge']");


    public ProductsPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        wait.until(ExpectedConditions.visibilityOfElementLocated(mainLogoLocator));
    }

    public boolean isLogoDisplayed(){
        try {
            return driver.findElement(mainLogoLocator).isDisplayed();
        }
        catch (Exception e){
            return false;
        }
    }

    public String getLogoText(){
        return driver.findElement(mainLogoLocator).getText();
    }

    public WebElement getProductByName(String productName){

        List<WebElement> products = driver.findElements(productItemLocator);

        for (WebElement product: products) {

            WebElement nameElement = product.findElement(productNameLocator);

            String currentName = nameElement.getText();

            if (currentName.equals(productName)) {
                return product;
            }
        }
            return null;
    }

    public ProductsPage addProductToCart(String productName){

        WebElement product = getProductByName(productName);

        if (product == null){
            throw new RuntimeException("Товар не найден");
        }

        WebElement addButton = product.findElement(buttonAddToCart);
        addButton.click();

        return this;
    }

    public int getProductCountInCart(){
        try {
            WebElement badge = driver.findElement(cartBadgeLocator);
            String text = badge.getText();
            return Integer.parseInt(text);
        } catch (Exception e) {
            return 0;
        }
    }


}

