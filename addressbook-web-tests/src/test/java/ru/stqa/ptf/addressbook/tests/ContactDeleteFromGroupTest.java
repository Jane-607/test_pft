package ru.stqa.ptf.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.ptf.addressbook.model.ContactData;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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

    List<ContactData> contactsWithGroup = app.contact().getContactsWithGroups();

    if (contactsWithGroup.size() == 0) {
      app.contact().createContactWithGroup(new ContactData()
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

      contactsWithGroup = app.contact().getContactsWithGroups();

      app.goTo().HomePage();
    }

    ContactData contactWithGroup = contactsWithGroup.get(0);
    Integer groupSizeBefore = contactWithGroup.getGroups().size();

    app.contact().contactAddToGroup(contactWithGroup.getId());
    app.goTo().GroupPage();
    app.contact().contactRemoveFromGroup(contactWithGroup.getId());
    app.contact().listOfAllGroups();

    ContactData contactWithGroupAfter = app.contact().getContactById(contactWithGroup.getId());
    Integer groupSizeAfter = contactWithGroupAfter.getGroups().size();

    assertThat(groupSizeBefore, equalTo(groupSizeAfter + 1));
    verifyContactListInUI();
  }
}







