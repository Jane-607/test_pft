package ru.stqa.ptf.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.ptf.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.List;

public class GroupDeletionTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {

    app.goTo().GroupPage();

    if (app.group().list().size() == 0) {
      app.group().create(new GroupData("test1", "test2", "test3"));
    }
  }

  @Test
  public void testGroupDeletion() {

    List<GroupData> before = app.group().list();
    int index = before.size() - 1;

    app.group().delete(index);

    List<GroupData> after = app.group().list();
    Assert.assertEquals(after.size(), index);

    before.remove(index);

    Comparator<? super GroupData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());

    before.sort(byId);
    after.sort(byId);

    Assert.assertEquals(after, before);
  }
}
