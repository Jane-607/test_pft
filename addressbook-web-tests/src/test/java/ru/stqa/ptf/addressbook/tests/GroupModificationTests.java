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


public class GroupModificationTests extends TestBase {

  private final Properties properties;

  public GroupModificationTests() throws IOException {
    properties = new Properties();
    String target = System.getProperty("target", "local");
    properties.load(new FileReader(new File(String.format("src/test/resources/%s.properties", target))));
  }


  @BeforeMethod
  public void ensurePreconditionsBase(){

    if (app.db().groups().size() == 0) {
      app.goTo().GroupPage();
      app.group().create(new GroupData()
              .withName(properties.getProperty("web.BeforeGroupName"))
              .withHeader(properties.getProperty("web.BeforeGroupHeadere"))
              .withFooter(properties.getProperty("web.BeforeGroupFooter")));
      app.goTo().GroupPage();
    }
  }


  @Test
  public void GroupModificationTests() {

    Groups before = app.db().groups();
    GroupData modifiedGroup = before.iterator().next();

    GroupData group = new GroupData()
            .withId(modifiedGroup.getId())
            .withName(properties.getProperty("web.NewGroupName"))
            .withHeader(properties.getProperty("web.NewGroupHeadere"))
            .withFooter(properties.getProperty("web.NewGroupFooter"));
    app.goTo().GroupPage();
    app.group().modify(group);
    assertThat(app.group().count(), equalTo(before.size()));

    Groups after = app.db().groups();
    assertThat(after, equalTo(before.without(modifiedGroup).withAdded(group)));
    verifyGroupListInUI ();
  }
}