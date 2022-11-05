/**
 * 
 */
package koerber.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import koerber.entity.User;

/**
 * @author Bruno Medinas
 *
 */
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

}
