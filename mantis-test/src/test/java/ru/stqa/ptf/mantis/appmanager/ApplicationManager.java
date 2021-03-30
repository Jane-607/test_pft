package ru.stqa.ptf.mantis.appmanager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.BrowserType;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;


public class ApplicationManager {

  private WebDriver wd;
  private String browser;
  private final Properties properties;
  private RegistrationHelper registrationHelper;
  private LoginHelper loginHelper;
  private FtpHelper ftp;
  private MailHelper mailHelper;
  private JamesHelper jamesHelper;
  private DbHelper dbHelper;
  private UsersHelper usersHelper;

  public ApplicationManager(String browser) {
    this.browser = browser;
    properties = new Properties();
  }

  @SuppressWarnings("deprecation")
  public WebDriver getDriver() {
    if (wd == null) {
      if (browser.equals(BrowserType.CHROME)) { wd = new ChromeDriver();}
      else if (browser.equals(BrowserType.FIREFOX)) { wd = new FirefoxDriver();}
      else if (browser.equals(BrowserType.IE)) { wd = new InternetExplorerDriver();}

      wd.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
      wd.get(properties.getProperty("web.baseUrl"));

    }
    return wd;
  }

  public FtpHelper ftp () {
    if (ftp == null) {
      ftp = new FtpHelper(this);
    }
    return ftp;
  }

  public RegistrationHelper registration() {
    if (registrationHelper == null) {
      registrationHelper = new RegistrationHelper(this);
    }
    return registrationHelper;
  }

  public UsersHelper user() {
    if (usersHelper == null) {
      usersHelper = new UsersHelper(this);
    }
    return usersHelper;}


  public LoginHelper login() {
    if (loginHelper == null) {
      loginHelper = new LoginHelper(this);
    }
    return loginHelper;
  }

  public  HttpSession newSession() {
    return new HttpSession (this);
  }


  public void init() throws IOException {
    String target = System.getProperty("target", "local");
    properties.load(new FileReader(new File(String.format("src/test/resources/%s.properties", target))));
  }


  public String getProperty(String key) { return properties.getProperty(key);
  }

  public MailHelper mail () {
    if (mailHelper == null) {
      mailHelper = new MailHelper(this);
    }
    return mailHelper;
  }


  public JamesHelper james() {
    if (jamesHelper == null) {
      jamesHelper = new JamesHelper(this);
    }
    return jamesHelper;
  }

  public DbHelper db() {
    if (dbHelper == null) {
      dbHelper = new DbHelper(this);
    }

    return dbHelper;
  }

  public void stop() {
    if (wd != null) {
      wd.quit();
    }
  }
}
