/**
 * 
 */
package koerber.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import koerber.entity.AssignmentUser;

/**
 * @author Bruno Medinas
 *
 */
@Repository
public interface UserRepository extends JpaRepository<AssignmentUser, UUID> {

}
