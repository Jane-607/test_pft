package ru.stqa.ptf.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.ptf.addressbook.model.ContactData;
import ru.stqa.ptf.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.List;

public class ContactCreationTests extends TestBase {

  @Test
  public void testContactCreation() {

    app.getNavigationHelper().gotoGroupPage();

    if (!app.getGroupHelper().isThereAGroup() || !app.getGroupHelper().GroupExists().equals("test1")) {
      app.getGroupHelper().createGroup(new GroupData("test1", null, null));
    }

    app.getNavigationHelper().returnToHomePage();
    List<ContactData> before = app.getContactHelper().getContactList();
    ContactData contact = new ContactData(
            "Eva",
            "Victorovna",
            "Orlova",
            "OOO Test",
            "84832121212",
            "e.orlova@bk.ru",
            "test1");
    app.getContactHelper().createContact(contact, true);
    app.getNavigationHelper().returnToHomePage();
    List<ContactData> after = app.getContactHelper().getContactList();
    Assert.assertEquals(after.size(), before.size() + 1);

    contact.setId(after.stream().max((o1, o2) -> Integer.compare(o1.getId(), o2.getId())).get().getId());
    before.add(contact);
    Comparator<? super ContactData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
    before.sort(byId);
    after.sort(byId);
    Assert.assertEquals(after, before);
  }
}
