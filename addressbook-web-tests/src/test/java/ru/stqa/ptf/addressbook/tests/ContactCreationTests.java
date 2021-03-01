package ru.stqa.ptf.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.ptf.addressbook.model.ContactData;
import ru.stqa.ptf.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.List;

public class ContactCreationTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {

    app.goTo().GroupPage();

    if (!app.group().isThereAGroup() || !app.group().GroupExists().equals("test1")) {
      app.group().create(new GroupData("test1", null, null));
    }
  }

  @Test
  public void testContactCreation() {

    app.goTo().HomePage();

    List<ContactData> before = app.contact().list();

    ContactData contact = new ContactData(
            "Eva",
            "Victorovna",
            "Orlova",
            "OOO Test",
            "84832121212",
            "e.orlova@bk.ru",
            "test1");

    app.contact().create(contact, true);
    app.goTo().HomePage();

    List<ContactData> after = app.contact().list();
    Assert.assertEquals(after.size(), before.size() + 1);

    contact.setId(after.stream().max(Comparator.comparingInt(ContactData::getId)).get().getId());

    before.add(contact);

    Comparator<? super ContactData> byId = Comparator.comparingInt(ContactData::getId);

    before.sort(byId);
    after.sort(byId);

    Assert.assertEquals(after, before);
  }
}
