package ru.stqa.ptf.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.ptf.addressbook.model.ContactData;
import ru.stqa.ptf.addressbook.model.GroupData;

import java.util.HashSet;
import java.util.List;

public class ContactModificationTests extends TestBase {

  @Test
  public void ContactModificationTests() {

    app.getNavigationHelper().gotoGroupPage();
    if (!app.getGroupHelper().isThereAGroup()) {
      app.getGroupHelper().createGroup(new GroupData("test1", null, null));
    }

    app.getNavigationHelper().returnToHomePage();
    if (!app.getContactHelper().isThereAContact()) {
      app.getContactHelper().createContact(new ContactData(
              "Eva",
              "Victorovna",
              "Orlova",
              "OOO Test",
              "84832121212",
              "e.orlova@bk.ru",
              "test1"), true);
    }
    app.getNavigationHelper().returnToHomePage();
    List<ContactData> before = app.getContactHelper().getContactList();
    app.getContactHelper().initContactModification(before.size() -1);
    ContactData contact = new ContactData(before.get(before.size() -1).getId(),
            "Eva_3",
            "Victorovna_3",
            "Orlova_3",
            "OOO Test_3",
            "84832121212",
            "e.orlova_3@bk.ru",
            null);
    app.getContactHelper().fillContactForm(contact,false);
    app.getContactHelper().submitContacModification();
    app.getNavigationHelper().returnToHomePage();
    List<ContactData> after = app.getContactHelper().getContactList();
    Assert.assertEquals(after.size(), before.size());

    before.remove(before.size() -1);
    before.add(contact);
    Assert.assertEquals(new HashSet<Object>(after),new HashSet<Object>(before));
    }
}
