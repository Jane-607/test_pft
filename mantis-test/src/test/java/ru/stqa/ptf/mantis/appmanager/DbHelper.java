package ru.stqa.ptf.mantis.appmanager;

import org.hibernate.QueryException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.hql.internal.ast.QuerySyntaxException;
import ru.stqa.ptf.mantis.model.UserData;
import ru.stqa.ptf.mantis.model.Users;

import java.util.List;

public class DbHelper {

  private final SessionFactory sessionFactory;

  public DbHelper(ApplicationManager applicationManager) {
    // A SessionFactory is set up once for an application!
    final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure() // configures settings from hibernate.cfg.xml
            .build();
    sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
  }

  public Users users() {
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    try {
      List<UserData> result = session.createQuery("from UserData").list();
      for (UserData user : result) {
        System.out.println(user);
        System.out.println(user.getName());
        System.out.println(user.getEmail());
      }
      session.getTransaction().commit();
      session.close();

      return new Users(result);
    }catch (QuerySyntaxException ex) {
      System.out.println(ex);
    }

    return null;
  }

}
