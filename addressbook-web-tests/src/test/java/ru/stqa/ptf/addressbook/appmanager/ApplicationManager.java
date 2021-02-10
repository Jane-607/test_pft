package ru.stqa.ptf.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class ApplicationManager {

  ChromeDriver wd;

  private NavigationHelper navigationHelper;
  private GroupHelper groupHelper;


  public void init() {
    wd = new ChromeDriver();
    wd.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    wd.get("http://localhost/addressbook/group.php?selected%5B%5D=3&delete=Delete+group%28s%29");
    groupHelper = new GroupHelper(wd);
    navigationHelper = new NavigationHelper(wd);
    SessionHelper sessionHelper = new SessionHelper(wd);
    sessionHelper.login("admin", "secret");
  }

  public void logout() {
    wd.findElement(By.linkText("Logout")).click();
    wd.findElement(By.name("user")).clear();
    wd.findElement(By.name("user")).sendKeys("");
    wd.findElement(By.name("pass")).clear();
    wd.findElement(By.name("pass")).sendKeys("");
  }

  public void stop() {
    wd.quit();
  }

  public GroupHelper getGroupHelper() {
    return groupHelper;
  }

  public NavigationHelper getNavigationHelper() {
    return navigationHelper;
  }
}
