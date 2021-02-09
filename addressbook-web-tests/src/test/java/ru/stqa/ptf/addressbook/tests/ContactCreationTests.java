package ru.stqa.ptf.addressbook.tests;

import java.util.concurrent.TimeUnit;

import org.testng.annotations.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import ru.stqa.ptf.addressbook.model.ContactDate;

public class ContactCreationTests {
  private WebDriver wd;

  @BeforeMethod(alwaysRun = true)
  public void setUp() throws Exception {
    wd = new ChromeDriver();
    wd.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    wd.get("http://localhost/addressbook/");
    login("admin", "secret");
  }

  private void login(String username, String password) {
    wd.findElement(By.name("user")).click();
    wd.findElement(By.name("user")).clear();
    wd.findElement(By.name("user")).sendKeys(username);
    wd.findElement(By.name("pass")).click();
    wd.findElement(By.name("pass")).clear();
    wd.findElement(By.name("pass")).sendKeys(password);
    wd.findElement(By.xpath("//input[@value='Login']")).click();
  }
  @Test
  public void testContactCreation() throws Exception {
    initContactCreation();
    fillContactForm(new ContactDate(
            "Eva",
            "Victorovna",
            "Orlova",
            "OOO Test",
            "84832121212",
            "e.orlova@bk.ru"));
    submitContactCreation();
    returnToHomePage();
  }
  private void initContactCreation() {
    wd.findElement(By.linkText("add new")).click();
  }

  private void fillContactForm(ContactDate contactDate) {
    wd.findElement(By.name("firstname")).click();
    wd.findElement(By.name("firstname")).sendKeys(contactDate.getFirstName());
    wd.findElement(By.name("middlename")).click();
    wd.findElement(By.name("middlename")).sendKeys(contactDate.getMiddleName());
    wd.findElement(By.name("lastname")).click();
    wd.findElement(By.name("lastname")).sendKeys(contactDate.getLastName());
    wd.findElement(By.name("company")).click();
    wd.findElement(By.name("company")).sendKeys(contactDate.getCompany());
    wd.findElement(By.name("home")).click();
    wd.findElement(By.name("home")).sendKeys(contactDate.getHome());
    wd.findElement(By.name("email")).click();
    wd.findElement(By.name("email")).sendKeys(contactDate.getEmail());
  }

  private void submitContactCreation() {
    wd.findElement(By.name("submit")).click();
  }

  private void returnToHomePage() {
    wd.findElement(By.linkText("home page")).click();
  }

  @AfterMethod(alwaysRun = true)
  public void tearDown() throws Exception {
    wd.findElement(By.linkText("Logout")).click();
    wd.findElement(By.name("user")).clear();
    wd.findElement(By.name("user")).sendKeys("");
    wd.findElement(By.name("pass")).clear();
    wd.findElement(By.name("pass")).sendKeys("");
    wd.quit();
  }
}
