import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class ResultsTest {

    private static WebDriver driver;

    @BeforeClass
    public static void setUp() {
        driver = new ChromeDriver();
    }

    @Before
    public void goToHome() {
        driver.get("http://cs.uky.edu/~stla227/CS405/bookStoreHome.php");
    }

    @Test
    public void shouldReturnResultsSortedByTitle() {
        //search " " to return all books
        driver.findElement(By.id("searchBar")).sendKeys(" ");
        Select searchKey = new Select(driver.findElement(By.id("sortOptions")));
        searchKey.selectByValue("Title");
        driver.findElement(By.xpath("/html/body/div[2]/form/button")).click();
        //store title elements in list
        List<WebElement> resultTitles = driver.findElements(By.xpath("/html/body/div[3]/div[2]/b"));
        boolean sorted = true;
        if (resultTitles.isEmpty()) {
            sorted = false;
        }
        //check if string components of list are sorted
        for (int i = 1; i < resultTitles.size(); i++) {
            if (resultTitles.get(i).getText().compareTo(resultTitles.get(i - 1).getText()) == -1) {
                sorted = false;
            }
        }
        Assert.assertTrue(sorted);
    }

    @Test
    public void shouldReturnResultsSortedByDate() {
        driver.findElement(By.id("searchBar")).sendKeys(" ");
        Select searchKey = new Select(driver.findElement(By.id("sortOptions")));
        searchKey.selectByValue("Date");
        driver.findElement(By.xpath("/html/body/div[2]/form/button")).click();
        //store date elements in list
        List<WebElement> resultTitles = driver.findElements(By.xpath("/html/body/div[3]/div[2]/span[contains(.,'Published')]"));
        boolean sorted = true;
        if (resultTitles.isEmpty()) {
            sorted = false;
        }
        //check if string components of list are sorted
        for (int i = 1; i < resultTitles.size(); i++) {
            if (resultTitles.get(i).getText().compareTo(resultTitles.get(i - 1).getText()) == -1) {
                sorted = false;
            }
        }
        Assert.assertTrue(sorted);
    }

    @AfterClass
    public static void tearDown() {
        driver.close();
        driver.quit();
    }
}
