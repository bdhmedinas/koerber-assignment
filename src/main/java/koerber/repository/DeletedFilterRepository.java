/**
 * 
 */
package koerber.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import koerber.entity.DeletedFilter;

/**
 * @author Bruno Medinas
 *
 */
@Repository
public interface DeletedFilterRepository extends JpaRepository<DeletedFilter, UUID> {
}
