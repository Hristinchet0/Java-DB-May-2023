package softuni.exam.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.exam.models.DTO.ExportPassengerDto;
import softuni.exam.models.entities.Passenger;

import java.util.List;
import java.util.Optional;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Long> {

    Optional<Passenger> findByEmail(String email);


    @Query("SELECT new softuni.exam.models.DTO.ExportPassengerDto( " +
            "p.firstName, p.lastName, p.email, p.phoneNumber, COUNT(t.passenger.id) AS countTickets) " +
            "FROM Passenger p " +
            "JOIN Ticket t ON t.passenger.id = p.id " +
            "GROUP BY p.email " +
            "ORDER BY countTickets DESC, p.email")
    List<ExportPassengerDto> passengerInfo();
}
