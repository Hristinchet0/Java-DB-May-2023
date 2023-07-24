package com.example.productsshop.repository;

import com.example.productsshop.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u " +
            "WHERE (SELECT COUNT(p) FROM Product p " +
            "WHERE p.seller.id = u.id AND p.buyer IS NOT NULL) > 0 " +
            "ORDER BY u.lastName, u.lastName")
   List<User> findAllUsersWithMoreThanOneSoldProduct();

    @Query("SELECT u " +
            "FROM User u " +
            "JOIN u.soldProducts p " +
            "WHERE p.buyer IS NOT NULL " +
            "order by size(u.soldProducts) DESC, u.lastName ")
    Optional<List<User>> findUsersAndProducts();
}
