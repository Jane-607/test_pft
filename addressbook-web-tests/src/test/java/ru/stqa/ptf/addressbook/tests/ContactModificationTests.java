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
      app.group().create(new GroupData().withName("test1").withHeader("test2").withFooter("test3"));
    }

    app.goTo().HomePage();

    if (app.contact().list().size() == 0) {
      app.contact().create(new ContactData()
              .withFirstName("Eva").withMiddleName("Victorovna").withLastName("Orlova").withCompany("OOO Test")
              .withHome("84832121212").withEmail("e.orlova@bk.ru").withGroup("test1"), true);
    }
  }

  @Test
  public void ContactModificationTests() {

    app.goTo().HomePage();

    List<ContactData> before = app.contact().list();
    int index = before.size() - 1;

    ContactData contact = new ContactData()
            .withId(before.get(index).getId()).withFirstName("Eva_3").withMiddleName("Victorovna_3").withLastName("Orlova_3")
            .withCompany("OOO Test_3").withHome("84832121212").withEmail("e.orlova_3@bk.ru").withGroup(null);

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
