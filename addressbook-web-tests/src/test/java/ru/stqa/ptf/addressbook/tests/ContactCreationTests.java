package ru.stqa.ptf.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.ptf.addressbook.model.ContactData;
import ru.stqa.ptf.addressbook.model.Contacts;
import ru.stqa.ptf.addressbook.model.GroupData;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

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

    Contacts before = app.contact().all();

    ContactData contact = new ContactData()
            .withFirstName("Eva").withMiddleName("Victorovna").withLastName("Orlova").withCompany("OOO Test").withHome("84832121212").withEmail("e.orlova@bk.ru").withGroup("test1");

    app.contact().create(contact, true);
    app.goTo().HomePage();
    assertThat(app.contact().count(), equalTo(before.size() + 1));

    Contacts after = app.contact().all();
    assertThat(after, equalTo(
            before.withAdded(contact.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt()))));
  }

  @Test
  public void testBadContactCreation() {

    app.goTo().HomePage();

    Contacts before = app.contact().all();

    ContactData contact = new ContactData()
            .withFirstName("Error'").withMiddleName("Error").withLastName("Error").withCompany("Error")
            .withHome("84832121212").withEmail("Error").withGroup("test1");;

    app.contact().create(contact, true);
    app.goTo().HomePage();
    assertThat(app.contact().count(), equalTo(before.size()));

    Contacts after = app.contact().all();
    assertThat(after, equalTo(before));
  }
}
