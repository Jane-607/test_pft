package ru.stqa.ptf.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.ptf.addressbook.model.ContactData;
import ru.stqa.ptf.addressbook.model.GroupData;
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
  public void ensurePreconditions() {

    app.goTo().GroupsPage();
    Groups allGroups = app.group().all();

    List<String> groupNames = new ArrayList<>();
    for (GroupData group : allGroups) {
      groupNames.add(group.getName());
    }

    if (!groupNames.contains("test1")) {
      app.group().create(new GroupData()
              .withName(properties.getProperty("web.BeforeGroupName1"))
              .withHeader(properties.getProperty("web.GroupHeadere"))
              .withFooter(properties.getProperty("web.GroupFooter")));
      app.goTo().GroupsPage();
    }
    if (!groupNames.contains("test2")) {
      app.group().create(new GroupData()
              .withName(properties.getProperty("web.BeforeGroupName2"))
              .withHeader(properties.getProperty("web.GroupHeadere"))
              .withFooter(properties.getProperty("web.GroupFooter")));
      app.goTo().GroupsPage();
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
              .inGroup(groups.iterator().next()), true);
    }
  }

  @Test
  public void testContactDeleteFromGroup() {

    app.goTo().HomePage();
    ContactData contactBefore = app.db().contacts().iterator().next();
    Groups allGroupsBefore = contactBefore.getGroups();

    if (app.contact().selectGroup(true)){
      app.contact().removeFromGroup(contactBefore);
      }
    else {
      app.contact().addToGroup(contactBefore, contactBefore);
      app.contact().removeFromGroup(contactBefore);
    }

    app.goTo().HomePage();

    ContactData allContactAfter = app.db().contacts().iterator().next();
    Groups allGroupsAfter = allContactAfter.getGroups();

    if
    (allGroupsBefore.size() <= 0)
    {assertThat(allGroupsAfter.size(), equalTo(allGroupsBefore.size()));}
    else
    {assertThat(allGroupsAfter.size(), equalTo(allGroupsBefore.size() - 1));}
    verifyContactListInUI();
  }
}



