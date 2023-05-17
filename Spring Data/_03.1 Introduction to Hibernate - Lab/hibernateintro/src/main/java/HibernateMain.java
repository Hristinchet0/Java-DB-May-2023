import entities.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class HibernateMain {
    public static void main(String[] args) {

        Configuration configuration = new Configuration();
        configuration.configure();

        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.openSession();

        session.beginTransaction();

//        Student example = new Student();
//        example.setName("Pesho");
//        session.persist(example);

//        Student fromDB = session.get(Student.class, 3);
//        System.out.println(fromDB.getId() + " " + fromDB.getName());

        List<Student> students = session.createQuery("FROM Student ", Student.class).list();
        for(Student student : students) {
            System.out.println(student.getId() + " " + student.getName());
        }

        session.getTransaction().commit();
        session.close();
    }
}
