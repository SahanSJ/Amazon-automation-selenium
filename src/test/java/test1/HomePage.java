package test1;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.*;

public class HomePage {

    WebDriver driver;

    //Sync Thread Sleep
    public static void syncThreadSleep(int milSec) {

        /*
         * A custom Thread sleep function is created here.
         * This is made in order to avoid the repeated duplicates caused in creating the Thread Sleep
         */

        try {
            Thread.sleep(milSec);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //Launch a Chrome Browser
    //Navigate to URL https://www.amazon.com/
    //Get the page URL and verify if it is the correct page that is opened
    @Test (priority = 1)
    @Parameters({"url"})
    public void openBrowser(String url) throws Exception {

        String exePath = System.getProperty("user.dir") + "\\resources\\drivers\\chromedriver.exe";
        System.setProperty("webdriver.chrome.driver", exePath);

        driver = new ChromeDriver();

        driver.get(url);

        driver.manage().window().maximize();

        String currentUrl = driver.getCurrentUrl();
        String assertionError = null;

        try {
            Assert.assertEquals(currentUrl,url);
        }catch (AssertionError ae){
            assertionError = ae.toString();
        }

        if(assertionError == null){
            System.out.println("Correct url is opened");
        }else {
            System.out.println("Wrong url is opened " +assertionError);
        }

        //syncThreadSleep(5000);
    }

    //Select “Men’s Fashion” from main drop down box.
    @Test(priority = 2)
    public void selectMensFashion() {

        Select fashionType = new Select(driver.findElement(By.id("searchDropdownBox")));
        fashionType.selectByValue("search-alias=fashion-mens-intl-ship");
        System.out.println("Men's fashion selected");
    }

    //Type “Shirt” on search bar
    //Click on Search button icon [ Left hand side ]
    @Test(priority = 3)
    @Parameters({"itemName"})
    public void searchTshirt(String itemName){
        driver.findElement(By.id("twotabsearchtextbox")).sendKeys(itemName);
        driver.findElement(By.xpath("//header/div[@id='navbar']/div[@id='nav-belt']/div[2]/div[1]/form[1]/div[3]/div[1]/span[1]/input[1]")).click();

        syncThreadSleep(2000);
    }

    //In the Brand Category select first brand [checkbox]
    @Test(priority = 4)
    public void selectBrand(){
        driver.findElement(By.xpath("//body[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[3]/span[1]/div[1]/span[1]/div[1]/div[1]/div[3]/ul[2]/li[1]/span[1]/a[1]/div[1]/label[1]/i[1]")).click();

        syncThreadSleep(2000);
    }

    //Select the first search item
    @Test(priority = 5)
    public void selectTshirt(){
        driver.findElement(By.xpath("//span[contains(text(),\"Under Armour Men's Tech 2.0 Short Sleeve T-Shirt\")]")).click();

        syncThreadSleep(3000);
    }

    //Select a size (available size).
    @Test(priority = 6)
    public void selectSize(){
        driver.findElement(By.xpath("//body/div[@id='a-page']/div[@id='dp']/div[@id='dp-container']/div[@id='ppd']/div[2]/div[2]/div[1]/div[1]/div[1]/div[12]/div[1]/div[2]/form[1]/div[2]/span[1]/span[1]/span[1]/span[1]/span[1]")).click();
        driver.findElement(By.xpath("//a[@id='native_dropdown_selected_size_name_2']")).click();
        System.out.println("Size selected");

        syncThreadSleep(5000);
    }

    //Print the Item Title (Name) in console
    //Get the Title Length and print in console
    @Test(priority = 7)
    public void getTitleInfo(){
        String titleOfTshirt = driver.findElement(By.id("productTitle")).getText();
        int lengthOfTitleOfTshirt = titleOfTshirt.length();

        System.out.println("Item name is "+titleOfTshirt);
        System.out.println("Length of the item name is "+lengthOfTitleOfTshirt);
    }

    //Select “Add To Cart”
    @Test(priority = 8)
    public void addToCart(){
        driver.findElement(By.id("add-to-cart-button")).click();
        System.out.println("Successfully added to the cart");
        syncThreadSleep(5000);
    }

    //Print Item Price in console
    @Test(priority = 9)
    public void getItemPrice(){
        String price = driver.findElement(By.xpath("//body/div[@id='a-page']/div[@id='cart-page-wrap']/div[@id='huc-page-container']/div[@id='huc-v2-order-row-with-divider']/div[@id='huc-v2-order-row-container']/div[1]/div[1]/div[3]/div[1]/div[1]/div[1]/div[1]/div[1]/span[1]/span[2]")).getText();
        System.out.println("Price is "+price);
        syncThreadSleep(5000);
    }

    //Click on Cart
    @Test(priority = 10)
    public void proceedWithCart(){
        driver.findElement(By.id("hlb-view-cart-announce")).click();
    }

    //Verify selected details in Shopping Cart page [Name, Price]
    @Test(priority = 11)
    @Parameters({"itemTitle", "price"})
    public void verifyCart(String itemTitle,String price) throws Exception{

        String verifyTitle = driver.findElement(By.xpath("//body/div[@id='a-page']/div[4]/div[1]/div[6]/div[1]/div[2]/div[4]/div[1]/form[1]/div[2]/div[3]/div[4]/div[1]/div[1]/div[1]/div[1]/div[2]/ul[1]/li[1]/span[1]/a[1]/span[1]")).getText();
        String verifyPrice = driver.findElement(By.xpath("//body/div[@id='a-page']/div[4]/div[1]/div[6]/div[1]/div[2]/div[4]/div[1]/form[1]/div[2]/div[3]/div[4]/div[1]/div[2]/p[1]/span[1]")).getText();

        String assertionError = null;

        try{
            Assert.assertEquals(verifyTitle,itemTitle);
            Assert.assertEquals(verifyPrice,price);
        }
        catch (AssertionError ae){
            assertionError = ae.toString();
        }

        if(assertionError == null){
            System.out.println("Cart details verified");
        }else {
            System.out.println("Error on cart details " +assertionError);
        }
    }

    //Assert the ‘quantity of the Item(should not be empty) and print in console
    @Test(priority = 12)
    public void assertQuantity() throws Exception{
        String quantity = driver.findElement(By.xpath("//body/div[@id='a-page']/div[4]/div[1]/div[6]/div[1]/div[2]/div[4]/div[1]/form[1]/div[2]/div[3]/div[4]/div[1]/div[1]/div[1]/div[1]/div[2]/div[1]/span[1]/span[1]/span[1]/span[1]/span[1]/span[1]/span[2]")).getText();

        String assertionError = null;

        try{
            Assert.assertNotNull(quantity);
        }
        catch (AssertionError ae){
            assertionError = ae.toString();
        }

        if(assertionError == null){
            System.out.println("Cart quantity verified");
        }else {
            System.out.println("Error on cart quantity " +assertionError);
        }

    }

    //Assert the Shopping Cart Subtotal (should be equal) with Proceed to checkout Subtotal
    @Test(priority = 13)
    @Parameters({"price"})
    public void assertSubTotal(String price) throws Exception{
        String subTotal = driver.findElement(By.xpath("//body/div[@id='a-page']/div[4]/div[1]/div[6]/div[1]/div[1]/div[2]/div[1]/form[1]/div[1]/div[1]/div[1]/span[2]/span[1]")).getText();

        String assertionError = null;

        try{
            Assert.assertEquals(subTotal,price);
        }
        catch (AssertionError ae){
            assertionError = ae.toString();
        }

        if(assertionError == null){
            System.out.println("Cart subtotal verified");
        }else {
            System.out.println("Error on cart subtotal (miss match) " +assertionError);
        }

    }

    //Click on ‘Proceed to Checkout’ button
    @Test(priority = 14)
    public void clickProceedToCheckout(){
        driver.findElement(By.xpath("//body/div[@id='a-page']/div[4]/div[1]/div[6]/div[1]/div[1]/div[2]/div[1]/form[1]/div[1]/div[1]/span[1]/span[1]/input[1]")).click();
        System.out.println("Proceeding to checkout........");

        syncThreadSleep(3000);
    }

    //Click on ‘Create your Amazon Account’ button..
    @Test(priority = 15)
    public void createAccount(){
        driver.findElement(By.id("createAccountSubmit")).click();

        System.out.println("Let's create an AMAZON account");
    }

    //Type sample username, email, password and re-enter password
    @Test(priority = 16)
    @Parameters({"name", "email", "pw"})
    public void fillDetails(String name, String email, String pw){

        driver.findElement(By.id("ap_customer_name")).sendKeys(name);
        driver.findElement(By.id("ap_email")).sendKeys(email);
        driver.findElement(By.id("ap_password")).sendKeys(pw);
        driver.findElement(By.id("ap_password_check")).sendKeys(pw);

        syncThreadSleep(3000);
    }

    //Close the Browser.
    @AfterTest
    public void afterTest() {
        System.out.println("Browser about to Close");
        System.out.println("...");
        System.out.println("...");
        System.out.println("...");

        //Sync time
        syncThreadSleep(5000);
        driver.close();
    }
}