/**
 * 
 */
package koerber.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import koerber.entity.DeletedBranch;

/**
 * @author Bruno Medinas
 *
 */
@Repository
public interface DeletedBranchRepository extends JpaRepository<DeletedBranch, UUID> {
}
