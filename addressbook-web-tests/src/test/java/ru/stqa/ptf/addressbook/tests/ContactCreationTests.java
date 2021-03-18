package ru.stqa.ptf.addressbook.tests;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.ptf.addressbook.model.ContactData;
import ru.stqa.ptf.addressbook.model.Contacts;
import ru.stqa.ptf.addressbook.model.GroupData;
import ru.stqa.ptf.addressbook.model.Groups;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

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

    app.goTo().GroupPage();

    if (!app.group().isThereAGroup() || !app.group().GroupExists().equals("test1")) {
      app.group().create(new GroupData()
              .withName(properties.getProperty("web.BeforeGroupName"))
              .withHeader(properties.getProperty("web.GroupHeadere"))
              .withFooter(properties.getProperty("web.GroupFooter")));
    }
  }


  @Test(dataProvider = "validContactsFromJson")
  public void testContactCreation(ContactData contact) {

    app.goTo().HomePage();

    Contacts before = app.db().contacts();
    app.contact().create(contact, true);
    app.goTo().HomePage();
    assertThat(app.contact().count(), equalTo(before.size() + 1));

    Contacts after = app.db().contacts();
    assertThat(after, equalTo(
            before.withAdded(contact.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt()))));

    verifyContactListInUI ();
  }


  @Test
  public void testBadContactCreation() {

    Groups groups = app.db().groups();
    ContactData newContact = new ContactData()
            .withFirstName(properties.getProperty("web.BadFirstName"))
            .inGroup(groups.iterator().next());
    app.goTo().HomePage();
    Contacts before = app.db().contacts();
    app.contact().create(newContact, true);
    app.goTo().HomePage();
    assertThat(app.contact().count(), equalTo(before.size()));

    Contacts after = app.db().contacts();
    assertThat(after, equalTo(before));
    verifyContactListInUI ();
  }
}
