package ru.stqa.ptf.addressbook.tests;

import com.thoughtworks.xstream.XStream;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.ptf.addressbook.model.ContactData;
import ru.stqa.ptf.addressbook.model.Contacts;
import ru.stqa.ptf.addressbook.model.GroupData;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {

    app.goTo().GroupPage();

    if (!app.group().isThereAGroup() || !app.group().GroupExists().equals("test1")) {
      app.group().create(new GroupData().withName("test1").withHeader("test2").withFooter("test3"));
    }
  }

  @DataProvider
  public Iterator<Object[]> validContacts() throws IOException {
    BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/contacts.xml")));
    String xml = "";
    String line = reader.readLine();

    while (line != null) {
      xml += line;
      //String[] split = line.split(";");
      //list.add(new Object[]{new ContactData()
             // .withFirstName(split[0]).withMiddleName(split[1]).withLastName(split[2])
             // .withCompany("OOO Test")
             // .withAddress("Проживание: г. Москва, Ново-Советская, д.123/3, кв. 125(а);\n"
             //         + "Регистрация: г. Орел, пр-т Ленина, д. 7 корп. 2, кв. 5.")
             // .withHome("+7(111)11-11-23").withMobile("8-4832-12-12-12").withWork("8 900 354 33 45")
             // .withEmail("E.orlova_1@bk.ru").withEmail2("Е.Орлова-2@письмо.рф").withEmail3("e.orlova.3000@bk.ru")
             // .withGroup("test1")});
      line = reader.readLine();
    }
    XStream xstream = new XStream();
    xstream.processAnnotations(ContactData.class);
    List<ContactData> contacts = (List<ContactData>)xstream.fromXML(xml);
    return contacts.stream().map((g) -> new Object[] {g}).collect(Collectors.toList()).iterator();
  }

  @Test(dataProvider = "validContacts")
  public void testContactCreation(ContactData contact) {

    app.goTo().HomePage();

    Contacts before = app.contact().all();

    //File photo = new File("src/test/resources/file.png");

    app.contact().create(contact, true);
    app.goTo().HomePage();
    assertThat(app.contact().count(), equalTo(before.size() + 1));

    Contacts after = app.contact().all();
    assertThat(after, equalTo(
            before.withAdded(contact.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt()))));
  }


  @Test(enabled = false)
  public void testBadContactCreation() {

    app.goTo().HomePage();

    Contacts before = app.contact().all();

    ContactData contact = new ContactData()
            .withFirstName("Error'").withMiddleName("Error").withLastName("Error").withCompany("Error")
            .withHome("84832121212").withEmail("Error").withGroup("test1");

    app.contact().create(contact, true);
    app.goTo().HomePage();
    assertThat(app.contact().count(), equalTo(before.size()));

    Contacts after = app.contact().all();
    assertThat(after, equalTo(before));
  }
}
