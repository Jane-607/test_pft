package ru.stqa.ptf.addressbook.tests;

import org.testng.annotations.Test;

import ru.stqa.ptf.addressbook.model.GroupData;
import ru.stqa.ptf.addressbook.model.Groups;

import java.io.*;
import java.util.Properties;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupCreationTests extends TestBase {

  private final Properties properties;

  public GroupCreationTests() throws IOException {
    properties = new Properties();
    String target = System.getProperty("target", "local");
    properties.load(new FileReader(new File(String.format("src/test/resources/%s.properties", target))));
  }

  @Test(dataProvider = "validGroupsFromJson")
  public void testGroupCreation(GroupData group) {
    app.goTo().GroupsPage();
    Groups before = app.db().groups();
    app.group().create(group);
    app.goTo().GroupsPage();
    assertThat(app.group().count(), equalTo(before.size() + 1));
    Groups after = app.db().groups();
    assertThat(after, equalTo(
            before.withAdded(group.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt()))));
    verifyGroupListInUI ();
  }

  @Test(enabled = false)
  public void testBadGroupCreation() {
    app.goTo().GroupsPage();
    Groups before = app.db().groups();
    GroupData group = new GroupData().withName(properties.getProperty("web.BadGroupName"));
    assertThat(app.group().count(), equalTo(before.size()));
    app.group().create(group);
    app.goTo().GroupsPage();
    Groups after = app.db().groups();
    assertThat(after, equalTo(before));
    verifyGroupListInUI ();
  }
}
