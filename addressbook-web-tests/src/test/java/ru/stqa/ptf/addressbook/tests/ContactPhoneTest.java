package ru.stqa.ptf.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.ptf.addressbook.model.ContactData;
import ru.stqa.ptf.addressbook.model.Contacts;

public class ContactPhoneTest extends TestBase{

  @Test
  public void testContactPhone() {
    app.goTo().HomePage();
    ContactData contact = app.contact().all().iterator().next();
    Contacts infoFromEditForm = app.contact().all().infoFromEditForm(contact);
  }
}
