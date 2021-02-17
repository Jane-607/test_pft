package ru.stqa.ptf.addressbook.tests;

import org.testng.annotations.Test;

public class ContactDeletionTests extends TestBase {

  @Test
  public void testContactDeletion() {
    app.getNavigationHelper().returnToHomePage();
    app.getContactHelper().selectContact();
    app.getContactHelper().deleteSelectedContacts();
    app.getContactHelper().acceptNextAlert = true;
    app.getContactHelper().assertTrue(app.getContactHelper().closeAlertAndGetItsText().matches("^Delete 1 addresses[\\s\\S]$"));
    app.getNavigationHelper().returnToHomePage();
  }

}

