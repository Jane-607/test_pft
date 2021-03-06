package ru.stqa.ptf.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.ptf.addressbook.model.ContactData;
import ru.stqa.ptf.addressbook.model.Contacts;
import ru.stqa.ptf.addressbook.model.GroupData;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class ContactDeletionTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {

    app.goTo().GroupPage();

    if (app.group().all().size() == 0 || !app.group().GroupExists().equals("test1")) {
      app.group().create(new GroupData().withName("test1"));
    }

    app.goTo().HomePage();

    if (app.contact().all().size() == 0) {
      app.contact().create(new ContactData()
              .withFirstName("Eva").withMiddleName("Victorovna").withLastName("Orlova").withCompany("OOO Test")
              .withHome("84832121212").withEmail("e.orlova@bk.ru").withGroup("test1"), true);
    }
  }
  @Test
  public void testContactDeletion() {

    app.goTo().HomePage();

    Contacts before = app.contact().all();
    ContactData deletedContact = before.iterator().next();

    app.contact().delete(deletedContact);
    app.goTo().HomePage();
    assertThat(app.contact().count(), equalTo(before.size() - 1));

    Contacts after = app.contact().all();
    assertThat(after, equalTo(before.without(deletedContact)));
  }

}

