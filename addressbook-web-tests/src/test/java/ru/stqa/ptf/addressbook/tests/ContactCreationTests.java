package ru.stqa.ptf.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.ptf.addressbook.model.ContactData;
import ru.stqa.ptf.addressbook.model.Contacts;
import ru.stqa.ptf.addressbook.model.GroupData;
import ru.stqa.ptf.addressbook.model.Groups;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

  private final Properties properties;

  public ContactCreationTests() throws IOException {
    properties = new Properties();
    String target = System.getProperty("target", "local");
    properties.load(new FileReader(new File(String.format("src/test/resources/%s.properties", target))));
  }


  @BeforeMethod
  public void ensurePreconditions() {

    app.goTo().GroupsPage();

    if (!app.group().isThereAGroup() || !app.group().GroupExists().equals("test1")) {
      app.group().create(new GroupData()
              .withName(properties.getProperty("web.BeforeGroupName1"))
              .withHeader(properties.getProperty("web.GroupHeadere"))
              .withFooter(properties.getProperty("web.GroupFooter")));
    }
  }


  @Test(dataProvider = "validContactsFromJson")
  public void testContactCreation(ContactData contact) {

    app.goTo().HomePage();

    Contacts before = app.db().contacts();
    app.contact().createContactWithoutGroup(contact, true);
    app.goTo().HomePage();
    assertThat(app.contact().count(), equalTo(before.size() + 1));

    Contacts after = app.db().contacts();
    assertThat(after, equalTo(
            before.withAdded(contact.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt()))));

    verifyContactListInUI ();
  }


  @Test(enabled = false)
  public void testBadContactCreation() {

    Groups groups = app.db().groups();
    ContactData newContact = new ContactData()
            .withFirstName(properties.getProperty("web.BadFirstName"))
            .inGroup(groups.iterator().next());
    app.goTo().HomePage();
    Contacts before = app.db().contacts();
    app.contact().createContactWithoutGroup(newContact, true);
    app.goTo().HomePage();
    assertThat(app.contact().count(), equalTo(before.size()));

    Contacts after = app.db().contacts();
    assertThat(after, equalTo(before));
    verifyContactListInUI ();
  }
}
