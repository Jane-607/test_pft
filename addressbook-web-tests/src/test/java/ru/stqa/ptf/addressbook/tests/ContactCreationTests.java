package ru.stqa.ptf.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.ptf.addressbook.model.ContactData;
import ru.stqa.ptf.addressbook.model.GroupData;
import java.util.Set;

public class ContactCreationTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {

    app.goTo().GroupPage();

    if (!app.group().isThereAGroup() || !app.group().GroupExists().equals("test1")) {
      app.group().create(new GroupData().withName("test1").withHeader("test2").withFooter("test3"));
    }
  }

  @Test
  public void testContactCreation() {

    app.goTo().HomePage();

    Set<ContactData> before = app.contact().all();

    ContactData contact = new ContactData()
            .withFirstName("Eva").withMiddleName("Victorovna").withLastName("Orlova").withCompany("OOO Test").withHome("84832121212").withEmail("e.orlova@bk.ru").withGroup("test1");

    app.contact().create(contact, true);
    app.goTo().HomePage();

    Set<ContactData> after = app.contact().all();
    Assert.assertEquals(after.size(), before.size() + 1);

    contact.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt());
    before.add(contact);
    Assert.assertEquals(after, before);
  }
}
