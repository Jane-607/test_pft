package ru.stqa.ptf.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.ptf.addressbook.model.ContactDate;
import ru.stqa.ptf.addressbook.model.GroupDate;

public class ContactDeletionTests extends TestBase {

  @Test
  public void testContactDeletion() {

    app.getNavigationHelper().gotoGroupPage();
    if (!app.getGroupHelper().isThereAGroup()) {
      app.getGroupHelper().createGroup(new GroupDate("test1", null, null));
    }
    app.getNavigationHelper().returnToHomePage();
    if (!app.getContactHelper().isThereAContact()) {
      app.getContactHelper().createContact(new ContactDate(
              "Eva_2",
              "Victorovna_2",
              "Orlova_2",
              "OOO Test_2",
              "84832121212",
              "e.orlova_2@bk.ru",
              "test1"), true);
    }
    app.getNavigationHelper().returnToHomePage();
    int before = app.getContactHelper().getContactCount();
    app.getContactHelper().selectContact(before-1);
    app.getContactHelper().deleteSelectedContacts();
    app.getContactHelper().acceptNextAlert = true;
    app.getContactHelper().assertTrue(app.getContactHelper().closeAlertAndGetItsText().matches("^Delete 1 addresses[\\s\\S]$"));
    app.getNavigationHelper().returnToHomePage();
    int after = app.getContactHelper().getContactCount();
    Assert.assertEquals(after, before - 1);
  }

}

