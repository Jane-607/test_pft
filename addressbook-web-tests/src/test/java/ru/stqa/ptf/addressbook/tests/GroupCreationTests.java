package ru.stqa.ptf.addressbook.tests;

import org.testng.annotations.*;
import ru.stqa.ptf.addressbook.model.GroupDate;

public class GroupCreationTests extends TestBase {

  @Test
  public void testGroupCreation() {

    app.gotoGroupPage();
    app.initGroupCreation();
    app.fillGroupForm(new GroupDate("test1", "test2", "test3"));
    app.submitGroupCreation();
    app.returnToGroupPage();
  }

}
