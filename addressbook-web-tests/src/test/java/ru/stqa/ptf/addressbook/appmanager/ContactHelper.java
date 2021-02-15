package ru.stqa.ptf.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import ru.stqa.ptf.addressbook.model.ContactDate;

public class ContactHelper extends HelperBase {

  public ContactHelper(WebDriver wd) {
    super(wd);
  }

  public void returnToHomePage() {
    click(By.linkText("home"));
  }

  public void submitContactCreation() { click(By.name("submit")); }

  public void fillContactForm(ContactDate contactDate) {
    type(By.name("firstname"), contactDate.getFirstName());
    type(By.name("middlename"), contactDate.getMiddleName());
    type(By.name("lastname"), contactDate.getLastName());
    type(By.name("company"), contactDate.getCompany());
    type(By.name("home"), contactDate.getHome());
    type(By.name("email"), contactDate.getEmail());
  }

  public void initContactCreation() {
    click(By.linkText("add new"));
  }

  public void deleteSelectedContacts() {
    click(By.xpath("//input[@value='Delete']"));
  }

  public void selectContact() {
    click(By.name("selected[]"));
  }

  public void initContactModification() { click(By.xpath("//img[@alt='Edit']")); }

  public void submitContacModification() {click(By.name("update")); }
}

