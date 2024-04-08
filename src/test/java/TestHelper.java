import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class TestHelper {

    static WebDriver driver;
    final int waitForResposeTime = 4;

    // here write a link to your admin website (e.g. http://my-app.herokuapp.com/admin)
    String baseUrlAdmin = "http://localhost:3000/admin";

    // here write a link to your website (e.g. http://my-app.herokuapp.com/)
    String baseUrl = "http://localhost:3000/";

    @Before
    public void setUp() {

        // if you use Chrome:
        System.setProperty("webdriver.firefox.driver", "C:\\Users\\Simon\\Desktop\\SW testing\\TestCode\\geckodriver.exe");
        driver = new FirefoxDriver();

        // if you use Firefox:
        //System.setProperty("webdriver.gecko.driver", "C:\\Users\\...\\geckodriver.exe");
        //driver = new FirefoxDriver();

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get(baseUrl);

    }

    void editProduct(String productNameOriginal, String productNameNew, String description, String type, String price) {
        By product = By.linkText(productNameOriginal);
        driver.findElement(product).click();
        driver.findElement(By.linkText("Edit")).click();

        fillInProductForm(productNameNew, description, type, price);
        driver.findElement(By.name("commit")).click();
    }

    void goToPage(String page) {
        WebElement elem = driver.findElement(By.linkText(page));
        elem.click();
        waitForElementById(page);
    }

    void waitForElementById(String id) {
        new WebDriverWait(driver, waitForResposeTime).until(ExpectedConditions.presenceOfElementLocated(By.id(id)));
    }

    public boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    void login(String username, String password) {

        driver.get(baseUrlAdmin);

        driver.findElement(By.linkText("Login")).click();

        driver.findElement(By.id("name")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);


        By loginButtonXpath = By.xpath("//input[@value='Login']");
        driver.findElement(loginButtonXpath).click();

    }

    void delete(String productName){
        By deleteButton = By.xpath("/html/body/div[4]/div[2]/div/table/tbody/tr[2]/td[4]/a");
        driver.findElement(By.id(productName)).findElement(By.linkText("Delete")).click();
    }

    void logout() {
        WebElement logout = driver.findElement(By.linkText("Logout"));
        logout.click();

        waitForElementById("Admin");
    }

    void newProduct(String Title, String description, String type, String price) {
        By newProductButton = By.xpath("//*[@id=\"new_product_div\"]/a");
        driver.findElement(newProductButton).click();

        fillInProductForm(Title, description, type, price);

        By submitProduct = By.xpath("//*[@id=\"new_product\"]/div[5]/input");
        driver.findElement(submitProduct).click();

    }

    void fillInProductForm(String Title, String description, String type, String price) {
        driver.findElement(By.id("product_title")).clear();
        driver.findElement(By.id("product_title")).sendKeys(Title);

        driver.findElement(By.id("product_description")).clear();
        driver.findElement(By.id("product_description")).sendKeys(description);

        driver.findElement(By.id("product_prod_type")).sendKeys(type);

        driver.findElement(By.id("product_price")).clear();
        driver.findElement(By.id("product_price")).sendKeys(price);
    }

    void register(String username, String password) throws InterruptedException {
        driver.get(baseUrlAdmin);

        driver.findElement(By.linkText("Register")).click();

        driver.findElement(By.id("user_name")).sendKeys(username);
        driver.findElement(By.id("user_password")).sendKeys(password);
        driver.findElement(By.id("user_password_confirmation")).sendKeys(password);
        By registerButtonXpath = By.xpath("/html/body/div[4]/div[2]/div[2]/form/div[4]/input");
        driver.findElement(registerButtonXpath).click();

    }

    void deleteAccount(String username) {
        By adminButton = By.xpath("//*[@id=\"menu\"]/ul/li[1]/a");
        driver.findElement(adminButton).click();
        By deleteButton = By.xpath("//*[@id=\"" + username + "\"]/a[2]");
        driver.findElement(deleteButton).click();

    }

    @After
    public void tearDown() {
        driver.close();
    }

}