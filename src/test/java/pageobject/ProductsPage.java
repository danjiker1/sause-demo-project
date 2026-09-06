package pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProductsPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By mainLogoLocator = By.xpath("//div[@class='app_logo']");


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
}
