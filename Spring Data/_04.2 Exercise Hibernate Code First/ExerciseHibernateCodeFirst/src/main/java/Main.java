import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

/* All task databases are made in one project so
that the table connections can be seen at the same time with EER diagram.*/

public class Main {
    public static void main(String[] args) {
        EntityManager entityManager = Persistence.createEntityManagerFactory("test")
                .createEntityManager();

        entityManager.getTransaction().begin();

//        Sale sale = new Sale();
//        sale.setDateTime(LocalDateTime.now());
//
//        Product product = new Product();
//        product.setName("TestProduct");
//        product.setPrice(BigDecimal.TEN);
//        product.setQuantity(5);
//        product.getSales().add(sale);
//        sale.setProduct(product);
//
//        entityManager.persist(product);

//        Product found = entityManager.find(Product.class, 3L);
//        entityManager.remove(found);


        entityManager.getTransaction().commit();
    }
}
