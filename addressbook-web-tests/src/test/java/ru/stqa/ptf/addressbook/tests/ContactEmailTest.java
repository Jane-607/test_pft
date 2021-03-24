package ru.stqa.ptf.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.ptf.addressbook.model.ContactData;
import ru.stqa.ptf.addressbook.model.GroupData;
import ru.stqa.ptf.addressbook.model.Groups;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactEmailTest extends TestBase{

  private final Properties properties;

  public ContactEmailTest() throws IOException {
    properties = new Properties();
    String target = System.getProperty("target", "local");
    properties.load(new FileReader(new File(String.format("src/test/resources/%s.properties", target))));
  }

  @BeforeMethod
  public void ensurePreconditions() {

    app.goTo().GroupsPage();

    if (app.db().groups().size() == 0 || !app.group().GroupExists().equals("test1")) {
      app.group().create(new GroupData()
              .withName(properties.getProperty("web.BeforeGroupName1"))
              .withHeader(properties.getProperty("web.GroupHeadere"))
              .withFooter(properties.getProperty("web.GroupFooter")));
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
              .inGroup(groups.iterator().next()),true);
    }
  }

  @Test
  public void testContactPhone() {
    app.goTo().HomePage();
    ContactData contact = app.db().contacts().iterator().next();

    String email1 = contact.getEmail();
    String email2  = contact.getEmail2();
    String email3  = contact.getEmail3();
    String allEmail  = cleanedEmail (email1 + email2 + email3);
    ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);

    assertThat(allEmail, equalTo(mergeEmail(contactInfoFromEditForm)));
  }

  private String mergeEmail(ContactData contact) {
    return Arrays.asList(contact.getEmail(),contact.getEmail2(),contact.getEmail3())
            .stream().filter((s) -> !s.equals(""))
            .map(ContactEmailTest::cleanedEmail)
            .collect(Collectors.joining(""));
  }

  public static String cleanedEmail(String email){
    return email.replaceAll("[^a-z.а-я+A-Z_А-Я@0-9-]","");
  }

}
