package ru.stqa.ptf.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.ptf.addressbook.model.ContactDate;
import ru.stqa.ptf.addressbook.model.GroupDate;

public class ContactModificationTests extends TestBase {

  @Test
  public void ContactModificationTests() {

    app.getNavigationHelper().gotoGroupPage();
    if (!app.getGroupHelper().isThereAGroup()) {
      app.getGroupHelper().createGroup(new GroupDate("test1", null, null));
    }

    app.getNavigationHelper().returnToHomePage();
    if (!app.getContactHelper().isThereAContact()) {
      app.getContactHelper().createContact(new ContactDate(
              "Eva",
              "Victorovna",
              "Orlova",
              "OOO Test",
              "84832121212",
              "e.orlova@bk.ru",
              "test1"), true);
    }
    app.getNavigationHelper().returnToHomePage();
    int before = app.getContactHelper().getContactCount();
    app.getContactHelper().initContactModification(before -1);
    app.getContactHelper().fillContactForm(new ContactDate(
            "Eva_3",
            "Victorovna_3",
            "Orlova_3",
            "OOO Test_3",
            "84832121212",
            "e.orlova_3@bk.ru",
            null),false);
    app.getContactHelper().submitContacModification();
    app.getNavigationHelper().returnToHomePage();
    int after = app.getContactHelper().getContactCount();
    Assert.assertEquals(after, before);
    }
}
