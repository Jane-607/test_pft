package ru.stqa.ptf.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import ru.stqa.ptf.addressbook.model.ContactData;
import ru.stqa.ptf.addressbook.model.Contacts;
import ru.stqa.ptf.addressbook.model.GroupData;
import ru.stqa.ptf.addressbook.model.Groups;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.testng.Assert.assertFalse;
import static ru.stqa.ptf.addressbook.tests.TestBase.app;


public class ContactHelper extends HelperBase {

  private Properties properties;

  public ContactHelper(WebDriver wd) {
    super(wd);
  }

  public void initContactCreation() {
    click(By.linkText("add new"));
  }

  public void submitContactCreation() {
    click(By.name("submit"));
  }

  public void deleteSelectedContacts() {
    click(By.xpath("//input[@value='Delete']"));
  }

  public void submitContacModification() {
    click(By.name("update"));
  }

  public void selectAddTo() {
    click(By.cssSelector("input[value='Add to']"));
  }

  public void removeFrom() {
    click(By.xpath("//*[@id=\"content\"]/form[2]/div[3]/input"));
  }

  public void selectContactById(int id) {
    wd.findElement(By.cssSelector("input[value='" + id + "']")).click();
  }

  public ContactData getContactById(int id) {
    Contacts allContacts = app.db().contacts();
    for (ContactData contact : allContacts) {
      if (contact.getId() == id) {
        return contact;
      }
    }

    return null;
  }


  public int count() {
    return wd.findElements(By.name("selected[]")).size();
  }


  public void createContactWithoutGroup(ContactData contact, boolean b) {
    initContactCreation();
    fillContactFormWithoutGroup(contact, true);
    submitContactCreation();
    contactCache = null;
  }

  public void createContactWithGroup(ContactData contact, boolean b) {
    initContactCreation();
    fillContactFormWithGroup(contact, true);
    submitContactCreation();
    contactCache = null;
  }

  public void delete(ContactData contact) {
    selectContactById(contact.getId());
    deleteSelectedContacts();
    acceptNextAlert = true;
    assertTrue(closeAlertAndGetItsText().matches("^Delete 1 addresses[\\s\\S]$"));
    contactCache = null;
  }

  public void listOfAllGroups() {
    app.goTo().GroupPage();
    new Select(wd.findElement(By.name("group"))).selectByVisibleText("[all]");
  }

  public void listOfTestGroup() {
    new Select(wd.findElement(By.name("group"))).selectByVisibleText("test1");
  }

  public void modify(ContactData contact) {
    initContactModificationById(contact.getId());
    fillContactFormWithoutGroup(contact, false);
    submitContacModification();
    contactCache = null;
  }

  public void addGroup() throws IOException {
    properties = new Properties();
    String target = System.getProperty("target", "local");
    properties.load(new FileReader(new File(String.format("src/test/resources/%s.properties", target))));

    Groups allGroups = app.group().all();
    List<String> groupNames = new ArrayList<>();
    for (GroupData group : allGroups) {
      groupNames.add(group.getName());
    }

    if (!groupNames.contains("test1")) {
      app.group().create(new GroupData()
              .withName(properties.getProperty("web.BeforeGroupName1"))
              .withHeader(properties.getProperty("web.GroupHeadere"))
              .withFooter(properties.getProperty("web.GroupFooter")));
      app.goTo().GroupsPage();
    }
  }

  private void initContactModificationById(int id) {
    WebElement checkbox = wd.findElement(By.cssSelector(String.format(("input[value='%s']"), id)));
    System.out.println(id);
    WebElement element = checkbox.findElement(By.xpath("./../.."));
    List<WebElement> cells = element.findElements((By.tagName("td")));
    cells.get(7).findElement(By.tagName("a")).click();
  }

  public void fillContactFormWithGroup(ContactData contactData, boolean creation) {
    type(By.name("firstname"), contactData.getFirstName());
    type(By.name("middlename"), contactData.getMiddleName());
    type(By.name("lastname"), contactData.getLastName());
    //attach (By.name("photo"), contactData.getPhoto());
    type(By.name("company"), contactData.getCompany());
    type(By.name("address"), contactData.getAddress());
    type(By.name("home"), contactData.getHome());
    type(By.name("mobile"), contactData.getMobile());
    type(By.name("work"), contactData.getWork());
    type(By.name("email"), contactData.getEmail());
    type(By.name("email2"), contactData.getEmail2());
    type(By.name("email3"), contactData.getEmail3());

    if (creation) {
      new Select(wd.findElement(By.name("new_group"))).selectByIndex(1);
    } else assertFalse(isElementPresent(By.name("new_group")));
  }

  public void fillContactFormWithoutGroup(ContactData contactData, boolean creation) {
    type(By.name("firstname"), contactData.getFirstName());
    type(By.name("middlename"), contactData.getMiddleName());
    type(By.name("lastname"), contactData.getLastName());
    //attach (By.name("photo"), contactData.getPhoto());
    type(By.name("company"), contactData.getCompany());
    type(By.name("address"), contactData.getAddress());
    type(By.name("home"), contactData.getHome());
    type(By.name("mobile"), contactData.getMobile());
    type(By.name("work"), contactData.getWork());
    type(By.name("email"), contactData.getEmail());
    type(By.name("email2"), contactData.getEmail2());
    type(By.name("email3"), contactData.getEmail3());
  }

  public ContactData infoFromEditForm(ContactData contact) {
    initContactModificationById(contact.getId());
    String firstname = wd.findElement(By.name("firstname")).getAttribute("value");
    String lastname = wd.findElement(By.name("lastname")).getAttribute("value");
    String address = wd.findElement(By.name("address")).getAttribute("value");
    String home = wd.findElement(By.name("home")).getAttribute("value");
    String mobile = wd.findElement(By.name("mobile")).getAttribute("value");
    String work = wd.findElement(By.name("work")).getAttribute("value");
    String email = wd.findElement(By.name("email")).getAttribute("value");
    String email2 = wd.findElement(By.name("email2")).getAttribute("value");
    String email3 = wd.findElement(By.name("email3")).getAttribute("value");
    wd.navigate().back();
    return new ContactData().withId(contact.getId()).withFirstName(firstname).withMiddleName(null).withLastName(lastname)
            .withAddress(address)
            .withHome(home).withMobile(mobile).withWork(work)
            .withEmail(email).withEmail2(email2).withEmail3(email3);
  }

  private Contacts contactCache = null;

  public Contacts all() {
    if (contactCache != null) {
      return new Contacts(contactCache);
    }
    contactCache = new Contacts();
    List<WebElement> elements = wd.findElements(By.cssSelector("tr[name='entry']"));
    for (WebElement element : elements) {
      List<WebElement> cells = element.findElements(By.tagName("td"));
      int id = Integer.parseInt(cells.get(0).findElement(By.tagName("input")).getAttribute("id"));
      String lastname = cells.get(1).getText();
      String firstname = cells.get(2).getText();
      String address = cells.get(3).getText();
      String allEmails = cells.get(4).getText();
      String allPhones = cells.get(5).getText();
      contactCache.add(new ContactData().withId(id).withFirstName(firstname).withMiddleName(null).withLastName(lastname)
              .withAddress(address).withAllEmails(allEmails).withAllPhones(allPhones));
    }
    return new Contacts(contactCache);
  }

  public void contactAddToGroup(int id) {
    // listOfTestGroup();
    selectContactById(id);
    selectAddTo();
  }

  public void contactRemoveFromGroup(int id) {
    listOfTestGroup();
    selectContactById(id);
    removeFrom();
    app.goTo().GroupPage();
  }

  public List<ContactData> getContactsWithoutGroups() {
    List<ContactData> contactsWithoutGroup = new ArrayList<ContactData>();
    Contacts allContacts = app.db().contacts();
    for (ContactData contact : allContacts) {
      if (contact.getGroups().size() == 0) {
        contactsWithoutGroup.add(contact);
      }
    }
    return contactsWithoutGroup;
  }

  public List<ContactData> getContactsWithGroups() {
    List<ContactData> contactsWithGroup = new ArrayList<ContactData>();
    Contacts allContacts = app.db().contacts();
    for (ContactData contact : allContacts) {
      if (contact.getGroups().size() != 0) {
        contactsWithGroup.add(contact);
      }
    }
    return contactsWithGroup;
  }
}