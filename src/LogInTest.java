import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Arrays;
import java.util.Collection;
import java.util.logging.Logger;

@RunWith(Parameterized.class)
public class LogInTest {

    private static Logger log = Logger.getLogger("logger");

    private static WebDriver driver;

    private String username;
    private String password;

    @BeforeClass
    public static void setUp() {
        driver = new ChromeDriver();
    }

    @Before
    public void goToHome() {
        driver.get("http://cs.uky.edu/~stla227/CS405/bookStoreHome.php");
    }

    public LogInTest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Parameters
    public static Collection data() {
        Object[][] data = new Object[][] {{"bestchef@hotmail.com", "iheartpizzarolls"},
                {"girl@gmail.com", "somegirlypassword"}, {"hggr789@hogwarts.edu", "IHateDracoMalfoy"},
                {"highnoon@gmail.com", "it'shighnoon"}, {"nextHokage@gmail.com", "BelieveIt"},
                {"slambert227@uky.edu", "bryanspassword"}, {"sam@gmail.com", "sam"}};
        return Arrays.asList(data);
    }

    @Test
    public void shouldBeAbleToLogInAsManager() {
        driver.findElement(By.linkText("LogIn")).click();
        WebElement email = driver.findElement(By.id("Email"));
        WebElement password = driver.findElement(By.id("Password"));
        email.sendKeys(this.username);
        password.sendKeys(this.password);
        driver.findElement(By.xpath("/html/body/div[2]/form/button")).click();
        try {
            driver.switchTo().alert().dismiss();
            log.info(this.username + " : " + this.password);
            Assert.assertEquals("http://cs.uky.edu/~stla227/CS405/managerHome.php", driver.getCurrentUrl());
        }
        catch(NoAlertPresentException e) {
            Assert.assertEquals("http://cs.uky.edu/~stla227/CS405/managerHome.php", driver.getCurrentUrl());
        }
    }

    @Test
    public void shouldCatchIncorrectPassword() {
        driver.findElement(By.linkText("LogIn")).click();
        WebElement email = driver.findElement(By.id("Email"));
        WebElement password = driver.findElement(By.id("Password"));
        email.sendKeys(this.username);
        password.sendKeys("wrongPassword");
        driver.findElement(By.xpath("/html/body/div[2]/form/button")).click();
        Assert.assertEquals("Incorrect email or password. Please create an account if one does not exist.", driver.switchTo().alert().getText());
        driver.switchTo().alert().dismiss();
    }

    @AfterClass
    public static void tearDown() {
        driver.close();
        driver.quit();
    }
}