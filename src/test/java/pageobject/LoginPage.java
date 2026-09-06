package pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By usernameLocator = By.xpath("//*[@id='user-name']");
    private final By passwordLocator = By.xpath("//*[@id='password']");
    private final By submitLoginLocator = By.xpath("//*[@id='login-button']");
    private final By errorMessageLocator = By.xpath("//*[@data-test='error']");


    public LoginPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }


    public LoginPage openPage() {
        driver.get("https://www.saucedemo.com/");
        return this;
    }

    public ProductsPage login(String username, String password) {

        wait.until(ExpectedConditions.visibilityOfElementLocated(usernameLocator)).sendKeys(username);

        driver.findElement(passwordLocator).sendKeys(password);

        driver.findElement(submitLoginLocator).click();

        if (isErrorDisplayed()){
            return null;
        }

        return new ProductsPage(driver,wait);
    }

    public boolean isErrorDisplayed(){
        try {
           return driver.findElement(errorMessageLocator).isDisplayed();
        }
        catch (Exception e){
            return false;
        }
    }

    public String getErrorMessageText(){
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessageLocator)).getText();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


//    public LoginPage setLogin(String username){
//        wait.until(ExpectedConditions.visibilityOfElementLocated(usernameLocator)).sendKeys(username);
//        return this;
//
//    }
//
//    public LoginPage setPassword(String password){
//        wait.until(ExpectedConditions.visibilityOfElementLocated(passwordLocator)).sendKeys(password);
//        return this;
//    }
//
//    public LoginPage submitLogin(){
//        wait.until(ExpectedConditions.elementToBeClickable(submitLoginLocator)).click();
//        return this;
//    }
}
