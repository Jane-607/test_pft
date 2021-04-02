package ru.stqa.ptf.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.ptf.addressbook.model.ContactData;
import ru.stqa.ptf.addressbook.model.Contacts;
import ru.stqa.ptf.addressbook.model.Groups;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactDeleteFromGroupTest extends TestBase {

  private final Properties properties;

  public ContactDeleteFromGroupTest() throws IOException {
    properties = new Properties();
    String target = System.getProperty("target", "local");
    properties.load(new FileReader(new File(String.format("src/test/resources/%s.properties", target))));
  }

  @BeforeMethod
  public void ensurePreconditions() throws IOException {

    app.goTo().GroupsPage();
    app.contact().addGroup();
  }

  @Test

  public void testContactDeleteFromGroup() {

    app.goTo().HomePage();

    List<ContactData> contactsWithGroupBefore = new ArrayList<>();
    Contacts allContactsBefore = app.db().contacts();
    for (ContactData contactWithGroup : allContactsBefore) {
      if (contactWithGroup.getGroups().size() != 0) {
        contactsWithGroupBefore.add(contactWithGroup);
      }
    }
    Integer beforeContactsWithGroup = contactsWithGroupBefore.size();

    if (allContactsBefore.size() == 0) {
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
              .withGroup(properties.getProperty("web.BeforeGroupName1")), true);

      app.goTo().HomePage();

    }

    ContactData allContactsWithGroup = app.db().contacts().iterator().next();
    Groups afterAllContactsWithGroup = allContactsWithGroup.getGroups();

    if (afterAllContactsWithGroup.size() == 0) {
      app.contact().contactAddToGroup(allContactsWithGroup.getId());
      app.goTo().GroupPage();
      app.contact().contactRemoveFromGroup(allContactsWithGroup.getId());
      app.contact().listOfAllGroups();
    } else {
      app.contact().contactRemoveFromGroup(allContactsWithGroup.getId());
      app.contact().listOfAllGroups();
    }

    ContactData contactsAllWithGroupAfter = app.db().contacts().iterator().next();
    Integer afterContactsWithGroup = contactsAllWithGroupAfter.getGroups().size();

    if (beforeContactsWithGroup == 0) {
      assertThat(beforeContactsWithGroup, equalTo(afterContactsWithGroup));

    } else {
      assertThat(beforeContactsWithGroup, equalTo(afterContactsWithGroup + 1));
    }
    verifyContactListInUI();
  }
}







