package eu.nerdfactor.bowling.repo;

import eu.nerdfactor.bowling.entity.BowlingGame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for data access to a database.
 */
@Repository
public interface BowlingGameRepository extends JpaRepository<BowlingGame, Integer> {
}
