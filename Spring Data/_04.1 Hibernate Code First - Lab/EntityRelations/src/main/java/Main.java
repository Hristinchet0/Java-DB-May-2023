import com.mysql.cj.protocol.x.XAuthenticationProvider;
import entities.*;
import hasEntities.Article;
import hasEntities.Motor;
import hasEntities.PlateNumber;
import hasEntities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Main {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("relations");

        EntityManager entityManager = emf.createEntityManager();

        entityManager.getTransaction().begin();

//        Vehicle car = new Car("Mercedes", 5);
//        Vehicle bike = new Bike("Cross");
//        Vehicle plane = new Plane("Airbus", "Aviation kerosene", 450);
//        Vehicle truck = new Truck("Scania", "diesel", 2, 450000);
//
//        entityManager.persist(car);
//        entityManager.persist(bike);
//        entityManager.persist(plane);
//        entityManager.persist(truck);
//
//        Car fromDB = entityManager.find(Car.class, 1L);
//
//        System.out.println(fromDB.getModel() + " " + fromDB.getType() + " " + fromDB.getNumOfSeats());
//
//        PlateNumber number = new PlateNumber("E3939MA");
//        Motor motor1 = new Motor(number);
//
//        entityManager.persist(number);
//        entityManager.persist(motor1);
//
//
//        PlateNumber number2 = new PlateNumber("CA0101HA");
//        Motor motor2 = new Motor(number2);
//
//        entityManager.persist(number2);
//        entityManager.persist(motor2);

        Article article = new Article("Lab: Hibernate Code First...");
        User author = new User("Nakov");

        author.addArticle(article);
        article.setAuthor(author);

        entityManager.persist(author);


        entityManager.getTransaction().commit();

        entityManager.close();

    }
}
