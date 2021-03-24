package ru.stqa.ptf.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.ptf.addressbook.model.ContactData;
import ru.stqa.ptf.addressbook.model.Contacts;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertFalse;
import static ru.stqa.ptf.addressbook.tests.TestBase.app;


public class ContactHelper extends HelperBase {

  public ContactHelper(WebDriver wd) {
    super(wd);
  }

  public void create(ContactData contact, boolean b) {
    initContactCreation();
    fillContactForm(contact, true);
    submitContactCreation();
    contactCache = null;
  }

  public void initContactCreation() {
    click(By.linkText("add new"));
  }

  public void submitContactCreation() {
    click(By.name("submit"));
  }

  public void selectContactById(int id) {
    wd.findElement(By.cssSelector("input[value='" + id + "']")).click();
  }

  public void selectAddTo() {
    click(By.cssSelector("input[value='Add to']"));
  }

  public void selectToGroup() {
    String groupName = app.db().contacts().iterator().next().getGroups().iterator().next().getName();
    if (groupName.equals("test1")) {
      new Select(wd.findElement(By.name("to_group"))).selectByVisibleText("test2");
    } else {
      new Select(wd.findElement(By.name("to_group"))).selectByVisibleText("test1");
    }
  }
  public void selectGroup() {

    Contacts allContacts = app.db().contacts();
    List<String> contacts = new ArrayList<>();
    for (ContactData contact : allContacts) {
      contacts.add(contact.getGroup());
    }


    ContactData groupName = app.db().contacts().iterator().next();
    String test = groupName.getGroup();
    new Select(wd.findElement(By.name("to_group"))).selectByVisibleText(test);
  }

  public void deleteSelectedContacts() {
    click(By.xpath("//input[@value='Delete']"));
  }

  public void submitContacModification() {
    click(By.name("update"));
  }

  public int count() {
    return wd.findElements(By.name("selected[]")).size();
  }

  public void delete(ContactData contact) {
    selectContactById(contact.getId());
    deleteSelectedContacts();
    acceptNextAlert = true;
    assertTrue(closeAlertAndGetItsText().matches("^Delete 1 addresses[\\s\\S]$"));
    contactCache = null;
  }

  public void addToGroup(ContactData contact) {
    selectContactById(contact.getId());
    selectToGroup();
    selectAddTo();
  }

  public void modify(ContactData contact) {
    initContactModificationById(contact.getId());
    fillContactForm(contact, false);
    submitContacModification();
    contactCache = null;
  }

  private void initContactModificationById(int id) {
    WebElement checkbox = wd.findElement(By.cssSelector(String.format(("input[value='%s']"), id)));
    System.out.println(id);
    WebElement element = checkbox.findElement(By.xpath("./../.."));
    List<WebElement> cells = element.findElements((By.tagName("td")));
    cells.get(7).findElement(By.tagName("a")).click();
  }

  public void fillContactForm(ContactData contactData, boolean creation) {
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
      if (contactData.getGroups().size() == 0) {
        new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroup());
      } else if (contactData.getGroups().size() > 0) {
        Assert.assertTrue(contactData.getGroups().size() == 1);
        new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroups().iterator().next().getName());
      }
    } else assertFalse(isElementPresent(By.name("new_group")));
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
}