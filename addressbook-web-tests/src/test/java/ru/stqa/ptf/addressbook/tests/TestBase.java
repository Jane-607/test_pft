package ru.stqa.ptf.addressbook.tests;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.hamcrest.CoreMatchers;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.BrowserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.*;
import ru.stqa.ptf.addressbook.appmanager.ApplicationManager;
import ru.stqa.ptf.addressbook.model.ContactData;
import ru.stqa.ptf.addressbook.model.Contacts;
import ru.stqa.ptf.addressbook.model.GroupData;
import ru.stqa.ptf.addressbook.model.Groups;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestBase {

  Logger logger = LoggerFactory.getLogger(TestBase.class);

  protected static final ApplicationManager app
          = new ApplicationManager(System.getProperty("browser", BrowserType.CHROME));
  WebDriver wd;

  @BeforeSuite(alwaysRun = true)
  public void setUp() throws Exception {
    app.init();
  }

  @AfterSuite(alwaysRun = true)
  public void tearDown() throws Exception {
    app.logout();
    app.stop();
  }

  @BeforeMethod
  public void logTestStart(Method m, Object [] p) {
    logger.info("Start test" + m.getName() + "with parameters" + Arrays.asList(p));
  }

  @AfterMethod(alwaysRun = true)
  public void logTestStop(Method m, Object [] p) {
    logger.info("Stop test" + m.getName());
  }

  @DataProvider
  public Iterator<Object[]> validContactsFromJson() throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/contacts.json")))) {
      String json = "";
      String line = reader.readLine();
      while (line != null) {
        json += line;
        line = reader.readLine();
      }
      Gson gson = new Gson();
      List<ContactData> contacts = gson.fromJson(json, new TypeToken<List<ContactData>>() {
      }.getType());
      return contacts.stream().map((g) -> new Object[]{g}).collect(Collectors.toList()).iterator();
    }
  }

  @DataProvider
  public Iterator<Object[]> validGroupsFromJson() throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/groups.json")))) {
      String json = "";
      String line = reader.readLine();
      while (line != null) {
        json += line;
        line = reader.readLine();
      }
      Gson gson = new Gson();
      List<GroupData> groups = gson.fromJson(json, new TypeToken<List<GroupData>>() {
      }.getType());
      return groups.stream().map((g) -> new Object[]{g}).collect(Collectors.toList()).iterator();
    }
  }

  public void verifyGroupListInUI() {
    if (Boolean.getBoolean("verifyUI")){
    Groups dbGroups = app.db().groups();
    Groups uiGroups = app.group().all();
    assertThat(uiGroups, equalTo(dbGroups.stream()
            .map((g) -> new GroupData().withId(g.getId()).withName(g.getName()))
            .collect(Collectors.toSet())));}
  }

  public void verifyContactListInUI() {
    if (Boolean.getBoolean("verifyUI")){
      Contacts dbContacts = app.db().contacts();
      Contacts uiContacts = app.contact().all();
      assertThat(uiContacts, equalTo(dbContacts.stream()
              .map((g) -> new ContactData().withId(g.getId()).withFirstName(g.getFirstName()).withMiddleName(g.getMiddleName()).withLastName(g.getLastName()))
              .collect(Collectors.toSet())));}
  }
}
