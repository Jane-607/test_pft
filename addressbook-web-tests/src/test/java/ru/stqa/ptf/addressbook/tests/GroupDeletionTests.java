package ru.stqa.ptf.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.ptf.addressbook.model.GroupData;
import ru.stqa.ptf.addressbook.model.Groups;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupDeletionTests extends TestBase {

  private final Properties properties;

  public GroupDeletionTests() throws IOException {
    properties = new Properties();
    String target = System.getProperty("target", "local");
    properties.load(new FileReader(new File(String.format("src/test/resources/%s.properties", target))));
  }

  @BeforeMethod
  public void ensurePreconditions() {

    app.goTo().GroupsPage();

    if (app.db().groups().size() == 0) {
      app.group().create(new GroupData()
              .withName(properties.getProperty("web.BeforeGroupName"))
              .withHeader(properties.getProperty("web.GroupHeadere"))
              .withFooter(properties.getProperty("web.GroupFooter")));
    }
    app.goTo().GroupsPage();
  }

  @Test
  public void testGroupDeletion() {

    Groups before = app.db().groups();
    GroupData deletedGroup = before.iterator().next();

    app.group().delete(deletedGroup);
    assertThat(app.group().count(), equalTo(before.size() - 1));

    Groups after = app.db().groups();
    assertThat(after, equalTo(before.without(deletedGroup)));
    verifyGroupListInUI ();
  }
}
