/**
 * 
 */
package koerber.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import koerber.entity.Screen;

/**
 * @author Bruno Medinas
 *
 */
@Repository
public interface ScreenRepository extends JpaRepository<Screen, UUID> {

}
