package ru.stqa.ptf.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.ptf.addressbook.model.GroupData;
import ru.stqa.ptf.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupCreationTests extends TestBase {

  @Test
  public void testGroupCreation() {

    app.goTo().GroupPage();

    Groups before = app.group().all();

    GroupData group = new GroupData().withName("test1").withHeader("test2").withFooter("test3");

    app.group().create(group);
    app.goTo().GroupPage();
    assertThat(app.group().count(), equalTo(before.size() + 1));

    Groups after = app.group().all();
    assertThat(after, equalTo(
            before.withAdded(group.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt()))));
  }

  @Test
  public void testBadGroupCreation() {

    app.goTo().GroupPage();

    Groups before = app.group().all();

    GroupData group = new GroupData().withName("test1'");
    assertThat(app.group().count(), equalTo(before.size()));

    app.group().create(group);
    app.goTo().GroupPage();

    Groups after = app.group().all();
    assertThat(after, equalTo(before));
  }

}
