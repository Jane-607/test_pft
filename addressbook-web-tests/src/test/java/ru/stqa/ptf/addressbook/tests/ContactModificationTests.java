package ru.stqa.ptf.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.ptf.addressbook.model.ContactData;
import ru.stqa.ptf.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.List;

public class ContactModificationTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {

    app.goTo().GroupPage();

    if (app.group().list().size() == 0) {
      app.group().create(new GroupData("test1", null, null));
    }

    app.goTo().HomePage();

    if (app.contact().list().size() == 0) {
      app.contact().create(new ContactData(
              "Eva",
              "Victorovna",
              "Orlova",
              "OOO Test",
              "84832121212",
              "e.orlova@bk.ru",
              "test1"), true);
    }
  }

  @Test
  public void ContactModificationTests() {

    app.goTo().HomePage();

    List<ContactData> before = app.contact().list();
    int index = before.size() - 1;

    ContactData contact = new ContactData(before.get(index).getId(),
            "Eva_3",
            "Victorovna_3",
            "Orlova_3",
            "OOO Test_3",
            "84832121212",
            "e.orlova_3@bk.ru",
            null);

    app.contact().modifyContact(index, contact);
    app.goTo().HomePage();

    List<ContactData> after = app.contact().list();
    Assert.assertEquals(after.size(), before.size());

    before.remove(index);
    before.add(contact);

    Comparator<? super ContactData> byId = Comparator.comparingInt(ContactData::getId);

    before.sort(byId);
    after.sort(byId);

    Assert.assertEquals(after, before);
  }
}
