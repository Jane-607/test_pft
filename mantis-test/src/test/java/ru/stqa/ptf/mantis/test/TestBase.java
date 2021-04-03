package ru.stqa.ptf.mantis.test;

import biz.futureware.mantis.rpc.soap.client.IssueData;
import biz.futureware.mantis.rpc.soap.client.ObjectRef;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.BrowserType;
import org.testng.SkipException;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import ru.stqa.ptf.mantis.appmanager.ApplicationManager;

import javax.xml.rpc.ServiceException;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;

public class TestBase {


  public static final ApplicationManager app
          = new ApplicationManager(System.getProperty("browser", BrowserType.CHROME));
  WebDriver wd;

  @BeforeSuite(alwaysRun = true)
  public void setUp() throws Exception {
    app.init();
    app.ftp().upload(new File("src/test/resources/config_inc.php"), "config_inc.php", "config_inc.php.bak");
  }

  @AfterSuite(alwaysRun = true)
  public void tearDown() throws IOException {
    app.ftp().restore("config_inc.php.bak", "config_inc.php");
    app.stop();
  }

  public boolean isIssueOpen(int issueId) throws IOException, ServiceException {
    IssueData issue = app.soap().getIssue(issueId);
    if(issue.getStatus().getName().equals("closed")) {
      return false;
    }

    return true;
  }

  public void skipIfNotFixed(int issueId) throws IOException, ServiceException {
    if (isIssueOpen(issueId)) {
      throw new SkipException("Ignored because of issue " + issueId);
    }
  }
}