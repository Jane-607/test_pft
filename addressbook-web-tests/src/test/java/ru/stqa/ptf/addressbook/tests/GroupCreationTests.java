package ru.stqa.ptf.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.ptf.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.List;

public class GroupCreationTests extends TestBase {

  @Test
  public void testGroupCreation() {

    app.goTo().GroupPage();

    List<GroupData> before = app.group().list();
    GroupData group = new GroupData().withName("test1").withHeader("test2").withFooter("test3");

    app.group().create(group);

    List<GroupData> after = app.group().list();
    Assert.assertEquals(after.size(), before.size() + 1);

    group.withId(after.stream().max(Comparator.comparingInt(GroupData::getId)).get().getId());

    before.add(group);

    Comparator<? super GroupData> byId = Comparator.comparingInt(GroupData::getId);

    before.sort(byId);
    after.sort(byId);

    Assert.assertEquals(after, before);
  }

}
