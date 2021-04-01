package ru.stqa.ptf.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.ptf.addressbook.model.ContactData;
import ru.stqa.ptf.addressbook.model.Contacts;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactAddToGroupTest extends TestBase {

  private final Properties properties;

  public ContactAddToGroupTest() throws IOException {
    properties = new Properties();
    String target = System.getProperty("target", "local");
    properties.load(new FileReader(new File(String.format("src/test/resources/%s.properties", target))));
  }

  @BeforeMethod
  public void ensurePreconditions() throws IOException {

    app.goTo().GroupsPage();
    app.contact().addBeforeGroup();
  }

  @Test
  public void testContactAddToGroup() {

    app.goTo().HomePage();

    List<ContactData> contactsWithoutGroupBefore = new ArrayList<>();
    Contacts contactsBefore = app.db().contacts();
    for (ContactData contact : contactsBefore) {
      if (contact.getGroups().size() == 0) {
        contactsWithoutGroupBefore.add(contact);
      }
    }
    Integer beforeContacts = contactsWithoutGroupBefore.size();
    if (contactsWithoutGroupBefore.size() == 0) {
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
              .withEmail3(properties.getProperty("web.BeforeEmail3")), true);
    }

    app.goTo().HomePage();
    List<ContactData> contactsWithoutGroup = new ArrayList<>();
    Contacts contactsAll = app.db().contacts();
    for (ContactData contact : contactsAll) {
      if (contact.getGroups().size() == 0) {
        contactsWithoutGroup.add(contact);
        break;
      }
    }

    app.contact().contactAddToGroup(contactsWithoutGroup.get(0));
    app.contact().listOfAllGroups();

    List<ContactData> contactsWithoutGroupAfter = new ArrayList<>();
    Contacts contactsAfter = app.db().contacts();
    for (ContactData contact : contactsAfter) {
      if (contact.getGroups().size() == 0) {
        contactsWithoutGroupAfter.add(contact);
      }
    }

    Integer afterContacts = contactsWithoutGroupAfter.size();

    if (beforeContacts == 0) {
      assertThat(beforeContacts, equalTo(afterContacts));
    }
    else {
      assertThat(beforeContacts,
              equalTo(afterContacts + 1));
    }
    verifyContactListInUI();

  }
}



