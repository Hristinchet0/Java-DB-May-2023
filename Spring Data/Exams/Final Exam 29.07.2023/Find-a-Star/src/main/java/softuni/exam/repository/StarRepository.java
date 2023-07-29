package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.exam.models.dto.ExportStarDto;
import softuni.exam.models.entity.Star;

import java.util.List;
import java.util.Optional;

@Repository
public interface StarRepository extends JpaRepository<Star, Long> {

    Optional<Star> findByName(String name);

    @Query("SELECT new softuni.exam.models.dto.ExportStarDto(" +
            "s.name, s.lightYears AS lYears, s.description, c.name AS nameConst)" +
            "FROM Star s " +
            "LEFT JOIN Astronomer a ON s.id = a.observingStar.id " +
            "JOIN Constellation c ON c.id = s.constellation.id " +
            "WHERE s.starType = 'RED_GIANT' AND a.observingStar.id IS NULL " +
            "ORDER BY s.lightYears")
    List<ExportStarDto> findRedGiantStars();
}
