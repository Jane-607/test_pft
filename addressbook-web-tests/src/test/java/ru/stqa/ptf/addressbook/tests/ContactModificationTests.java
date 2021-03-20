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
import static org.testng.Assert.assertEquals;

public class ContactModificationTests extends TestBase {

  private final Properties properties;

  public ContactModificationTests() throws IOException {
    properties = new Properties();
    String target = System.getProperty("target", "local");
    properties.load(new FileReader(new File(String.format("src/test/resources/%s.properties", target))));
  }

  @BeforeMethod
  public void ensurePreconditions() {

    app.goTo().GroupPage();

    if (app.db().groups().size() == 0 || !app.group().GroupExists().equals("test1")) {
      app.group().create(new GroupData()
              .withName(properties.getProperty("web.BeforeGroupName"))
              .withHeader(properties.getProperty("web.GroupHeadere"))
              .withFooter(properties.getProperty("web.GroupFooter")));
    }

    app.goTo().HomePage();

    if (app.db().contacts().size() == 0) {
      Groups groups = app.db().groups();
      app.contact().create(new ContactData()
              .withFirstName(properties.getProperty("web.FirstName"))
              .withMiddleName(properties.getProperty("web.MiddleName"))
              .withLastName(properties.getProperty("web.LastName"))
              .withCompany(properties.getProperty("web.Company"))
              .withAddress(properties.getProperty("web.Address"))
              .withHome(properties.getProperty("web.BeforeHomePhone"))
              .withMobile(properties.getProperty("web.BeforeMobilePhone"))
              .withWork(properties.getProperty("web.BeforeWorkPhone"))
              .withEmail(properties.getProperty("web.BeforeEmail"))
              .withEmail2(properties.getProperty("web.BeforeEmail2"))
              .withEmail3(properties.getProperty("web.BeforeEmail3"))
              .inGroup(groups.iterator().next()),true);
    }
  }

  @Test
  public void ContactModificationTests() {

    app.goTo().HomePage();

    Contacts before = app.db().contacts();
    ContactData modifiedContact = before.iterator().next();
    Groups groups = app.db().groups();
    ContactData contact = new ContactData()
            .withId(modifiedContact.getId())
            .withFirstName(properties.getProperty("web.NewFirstName"))
            .withMiddleName(properties.getProperty("web.NewMiddleName"))
            .withLastName(properties.getProperty("web.NewLastName"))
            .withCompany(properties.getProperty("web.NewCompany"))
            .withAddress(properties.getProperty("web.NewAddress"))
            .withHome(properties.getProperty("web.NewHomePhone"))
            .withMobile(properties.getProperty("web.NewMobilePhone"))
            .withWork(properties.getProperty("web.NewWorkPhone"))
            .withEmail(properties.getProperty("web.NewEmail"))
            .withEmail2(properties.getProperty("web.NewEmail2"))
            .withEmail3(properties.getProperty("web.NewEmail3"));

    app.contact().modify(contact);
    app.goTo().HomePage();
    assertThat(app.contact().count(), equalTo(before.size()));

    Contacts after = app.db().contacts();
    assertThat(after, equalTo(before.without(modifiedContact).withAdded(contact)));
    verifyContactListInUI ();
  }
}
