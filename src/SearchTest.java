import org.junit.*;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class SearchTest {

    private static WebDriver driver;

    private String title;
    private String author;

    @BeforeClass
    public static void setUp() {
        driver = new ChromeDriver();
    }

    @Before
    public void goToHome() {
        driver.get("http://cs.uky.edu/~stla227/CS405/bookStoreHome.php");
    }

    public SearchTest(String title, String author) {
        this.title = title;
        this.author = author;
    }

    @Parameters
    public static Collection data() {
        Object[][] data = {{"Clay Tablet No. 1", "Unintelligible Grunt"},
                {"The Ultimate Guide to Throwing A Garden Party", "James Trickington"}, {"Book 4", "F. Forman"},
                {"Drink Mead Every Day", "King Author"}, {"Sample Book", "Sample Author"}, {"Wumbo", "Patrick Star"},
                {"The Cook Book", "Gordon Ramsey"}, {"Check it Out", "Dr. Steve Brule"},
                {"Hello There", "General Kenobi"}};
        return Arrays.asList(data);
    }

    @Test
    public void shouldReturnResultOnTitleSearch() {
        WebElement searchBar = driver.findElement(By.id("searchBar"));
        searchBar.sendKeys(this.title);
        Select searchKey = new Select(driver.findElement(By.id("searchAttributes")));
        searchKey.selectByValue("Title");
        driver.findElement(By.xpath("/html/body/div[2]/form/button")).click();
        WebElement results = driver.findElement(By.className("searchedBooks"));
        Assert.assertTrue(results.getText().contains(this.title));
    }

    @Test
    public void shouldReturnResultOnAuthorSearch() {
        WebElement searchBar = driver.findElement(By.id("searchBar"));
        searchBar.sendKeys(this.author);
        Select searchKey = new Select(driver.findElement(By.id("searchAttributes")));
        searchKey.selectByValue("Author");
        driver.findElement(By.xpath("/html/body/div[2]/form/button")).click();
        WebElement results = driver.findElement(By.className("searchedBooks"));
        Assert.assertTrue(results.getText().contains(this.author));
    }

    @AfterClass
    public static void tearDown() {
        driver.close();
        driver.quit();
    }
}
