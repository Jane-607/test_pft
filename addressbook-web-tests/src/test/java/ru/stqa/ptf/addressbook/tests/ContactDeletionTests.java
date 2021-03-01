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

    app.goTo().GroupPage();

    if (app.group().list().size() == 0 || !app.group().GroupExists().equals("test1")) {
      app.group().create(new GroupData("test1", null, null));
    }

    app.goTo().HomePage();

    if (app.contact().list().size() == 0) {
      app.contact().create(new ContactData(
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

    app.goTo().HomePage();

    List<ContactData> before = app.contact().list();
    int index = before.size() - 1;

    app.contact().delete(index);
    app.goTo().HomePage();

    List<ContactData> after = app.contact().list();
    Assert.assertEquals(after.size(), index);

    before.remove(index);

    Comparator<? super ContactData> byId = Comparator.comparingInt(ContactData::getId);

    before.sort(byId);
    after.sort(byId);

    Assert.assertEquals(after, before);
  }

}

