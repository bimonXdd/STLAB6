import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static junit.framework.TestCase.*;

public class BasicTest extends TestHelper {


    private String username = "admin2";
    private String password = "admin2";

    //@Test
    public void titleExistsTest(){
        String expectedTitle = "ST Online Store";
        String actualTitle = driver.getTitle();

        assertEquals(expectedTitle, actualTitle);
    }


    //NEGATIVE TESTCASE

    @Test
    public void registerExsistingAccountTest() throws InterruptedException {
        register(username,password);
        assertTrue(isElementPresent(By.id("error_explanation")));
}

//test the registration fo new account (need to add new addon to name and password for it to be a new user)
    @Test
    public void registerNewAccountTest() throws InterruptedException {
        register(username+"NEW",password+"NEW");
        assertEquals("http://localhost:3000/products",driver.getCurrentUrl());

    }
    @Test
    public void deleteNEWAccountTest() throws InterruptedException {
        register(username,password);
        deleteAccount(username);
        assertEquals("User was successfully deleted.",driver.findElement(By.xpath("//*[@id=\"notice\"]")).getText());
    }

    @Test
    public void newProductTest() throws InterruptedException {
        String title = "BookCASDASDA";
        login(username,password);
        newProduct(title, "a new test book", "Books", "23");
        By newProductTag = By.linkText(title);
        driver.get("http://localhost:3000/");
        assertEquals(title,driver.findElement(newProductTag).getText());
    }

    @Test
    public void changeProductTitleTest() throws InterruptedException {
    String title = "carBook";
    String newTitle = "CARZ";

    String description = "carBook fo dummies";
    String type = "Books";
    String price = "300";

    login("admin","admin");
    newProduct(title, description, type, price);
    editProduct(title,newTitle,description, type,price);
    assertTrue(isElementPresent(By.linkText(newTitle)));

    }

    @Test
    public void changeProductDescTest() throws InterruptedException {
        String title = "carBook";

        String description = "carBook fo dummies";
        String newdescription = "TEST";

        String type = "Books";
        String price = "300";

        login("admin","admin");
        newProduct(title, description, type, price);
        editProduct(title,title,newdescription, type,price);

        waitForElementById("notice");
        assertTrue(isElementPresent(By.xpath("//*[@id=\"main\"]/div/p[3]"))); }

    //!!!!!!!!!!!!!!!!!!!
    //FAIL 1 the test doesnt pass because the type of the product is not changed When changing from boot to other
    @Test
    public void changeProductTypeTest(){
        String title = "carBook";

        String description = "carBook fo dummies";

        String type = "Books";
        String newType = "Sunglasses";
        String price = "300";

        login("admin","admin");
        newProduct(title, description, type, price);
        editProduct(title,title,description, newType,price);
        logout();
        driver.get("http://localhost:3000/");
        driver.findElement(By.linkText(newType)).click();
        assertTrue(isElementPresent(By.linkText(title)));


    }


    @Test
    public void changeProductPriceTest(){
        String title = "carBook";

        String description = "carBook fo dummies";

        String type = "Books";
        String price = "300";
        String NEWprice = "200";

        login("admin","admin");

        newProduct(title, description, type, price);
        editProduct(title,title,description, type,NEWprice);
        logout();
        driver.get("http://localhost:3000/");
        driver.findElement(By.linkText(type)).click();

        assertTrue(isElementPresent(By.linkText(title)));
    }

    @Test
    public void deleteProduct(){
        login("admin","admin");
        newProduct("delete", "delete", "Books", "666");
        delete("delete");
        assertFalse(isElementPresent(By.id("delete")));
    }


    //NEGATIVE TESTCASE
    @Test
    public void addExcistingProduct(){
        String title = "TestSunglasses";
        String description = "sunglasses fo dummies";
        String type = "Sunglasses";
        String price = "5";

        login("admin","admin");
        newProduct(title, description, type, price);
        newProduct(title, description, type, price);
        assertTrue(isElementPresent(By.id("error_explanation")));
    }





}
