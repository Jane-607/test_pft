package ru.stqa.ptf.mantis.test;

import biz.futureware.mantis.rpc.soap.client.IssueData;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.jayway.restassured.RestAssured;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.BrowserType;
import org.testng.SkipException;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import ru.stqa.ptf.mantis.appmanager.ApplicationManager;

import javax.xml.rpc.ServiceException;
import java.io.File;
import java.io.IOException;
import java.util.Set;

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

  public int createIssue(Issue newIssue) throws IOException {
    String json = RestAssured.given()
            .parameter("subject", newIssue.getSubject())
            .parameter("description", newIssue.getDescription())
            .post("https://bugify.stqa.ru/api/issues.json").asString();
    JsonElement parsed = new JsonParser().parse(json);
    return parsed.getAsJsonObject().get("issue_id").getAsInt();
  }

  public boolean isIssueOpenSoap(int issueId) throws IOException, ServiceException {
    IssueData issue = app.soap().getIssue(issueId);
    if(issue.getStatus().getName().equals("closed")) {
      return false;
    }

    return true;
  }

  public void skipIfNotFixedSoap(int issueId) throws IOException, ServiceException {
    if (isIssueOpenSoap(issueId)) {
      throw new SkipException("Ignored because of issue " + issueId);
    }
  }

  public Set<Issue> getIssue() throws IOException {
    String json = RestAssured.get("https://bugify.stqa.ru/api/issues.json").asString();
    JsonElement parsed = new JsonParser().parse(json);
    JsonElement issues = parsed.getAsJsonObject().get("issues");
    Set<Issue> fromJson = new Gson().fromJson(issues, new TypeToken<Set<Issue>>() {}.getType());
    return fromJson;
  }

  public Issue getIssue(int issueId) throws IOException {

    String json = RestAssured.get("https://bugify.stqa.ru/api/issues/"+ issueId + ".json").asString();
    JsonElement parsed = new JsonParser().parse(json);
    JsonElement issues = parsed.getAsJsonObject().get("issues");
    Set<Issue> fromJson = new Gson().fromJson(issues, new TypeToken<Set<Issue>>() {}.getType());
    return fromJson.iterator().next();
  }

  public boolean isIssueOpenRest(int issueId) throws IOException, ServiceException {
    Issue issue = getIssue(issueId);
    if(issue.getState() != 0) {
      return false;
    }
    return true;
  }

  public void skipIfNotFixedRest(int issueId) throws IOException, ServiceException {
    if (isIssueOpenRest(issueId)) {
      throw new SkipException("Ignored because of issue " + issueId);
    }
  }
}