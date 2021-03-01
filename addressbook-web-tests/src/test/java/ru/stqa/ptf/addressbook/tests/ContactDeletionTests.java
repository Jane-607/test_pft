package ru.stqa.ptf.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.ptf.addressbook.model.ContactData;
import ru.stqa.ptf.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.List;

public class ContactDeletionTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {

    app.getNavigationHelper().gotoGroupPage();

    if (!app.getGroupHelper().isThereAGroup() || !app.getGroupHelper().GroupExists().equals("test1")) {
      app.getGroupHelper().createGroup(new GroupData("test1", null, null));
    }

    app.getNavigationHelper().returnToHomePage();

    if (!app.getContactHelper().isThereAContact()) {
      app.getContactHelper().createContact(new ContactData(
              "Eva_2",
              "Victorovna_2",
              "Orlova_2",
              "OOO Test_2",
              "84832121212",
              "e.orlova_2@bk.ru",
              "test1"), true);
    }
  }

  @Test
  public void testContactDeletion() {

    app.getNavigationHelper().returnToHomePage();

    List<ContactData> before = app.getContactHelper().getContactList();
    int index = before.size() - 1;
    
    app.getContactHelper().deleteContact(index);

    app.getNavigationHelper().returnToHomePage();

    List<ContactData> after = app.getContactHelper().getContactList();
    Assert.assertEquals(after.size(), index);

    before.remove(index);

    Comparator<? super ContactData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());

    before.sort(byId);
    after.sort(byId);

    Assert.assertEquals(after, before);
  }

}

