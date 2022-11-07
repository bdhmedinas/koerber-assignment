/**
 * 
 */
package koerber.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import koerber.entity.Branch;

/**
 * @author Bruno Medinas
 *
 */
@Repository
public interface BranchRepository extends JpaRepository<Branch, UUID> {
	
	public Branch findByVersionCorrelationAndVersion(UUID versionCorrelation, Integer version);
	
	@Query(value = "SELECT * FROM branch WHERE \"version\" = ( SELECT MAX(version) FROM branch b where version_correlation = ?1) and version_correlation = ?1", nativeQuery = true)
	public Branch findByVersionCorrelationAndLatestVersion(UUID versionCorrelation);
	
	public List<Branch> findByFilterFilterId(UUID filterId);
}
