package ru.stqa.ptf.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.ptf.addressbook.model.ContactData;
import ru.stqa.ptf.addressbook.model.Contacts;
import ru.stqa.ptf.addressbook.model.GroupData;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class ContactModificationTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {

    app.goTo().GroupPage();

    if (app.group().all().size() == 0 || !app.group().GroupExists().equals("test1")) {
      app.group().create(new GroupData().withName("test1").withHeader("test2").withFooter("test3"));
    }

    app.goTo().HomePage();

    if (app.contact().all().size() == 0) {
      app.contact().create(new ContactData()
              .withFirstName("Eva").withMiddleName("Victorovna").withLastName("Orlova").withCompany("OOO Test")
              .withHome("84832121212").withEmail("e.orlova@bk.ru").withGroup("test1"), true);
    }
  }

  @Test
  public void ContactModificationTests() {

    app.goTo().HomePage();

    Contacts before = app.contact().all();
    ContactData modifiedContact = before.iterator().next();

    ContactData contact = new ContactData()
            .withId(modifiedContact.getId()).withFirstName("Eva_3").withMiddleName("Victorovna_3").withLastName("Orlova_3")
            .withCompany("OOO Test_3").withHome("84832121212").withEmail("e.orlova_3@bk.ru").withGroup(null);

    app.contact().modify(contact);

    app.goTo().HomePage();
    assertThat(app.contact().count(), equalTo(before.size()));

    Contacts after = app.contact().all();
    assertThat(after, equalTo(before.without(modifiedContact).withAdded(contact)));
  }
}
