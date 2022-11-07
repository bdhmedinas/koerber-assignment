/**
 * 
 */
package koerber.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import koerber.entity.MyFilter;

/**
 * @author Bruno Medinas
 *
 */
@Repository
public interface MyFilterRepository extends JpaRepository<MyFilter, UUID> {
	
	@Query(value = "SELECT * FROM my_filter WHERE (version_correlation,\"version\") IN ( SELECT version_correlation, MAX(\"version\") FROM my_filter GROUP BY version_correlation)", nativeQuery = true)
	public List<MyFilter> findAllLatest();
	
	public List<MyFilter> findByVersionCorrelation(UUID versionCorrelation);
	
	public MyFilter findByVersionCorrelationAndVersion(UUID versionCorrelation, Integer version);
	
	@Query(value = "SELECT * FROM my_filter WHERE \"version\" = ( SELECT MAX(version) FROM my_filter mf2 where version_correlation = ?1) and version_correlation = ?1", nativeQuery = true)
	public MyFilter findByVersionCorrelationAndLatestVersion(UUID versionCorrelation);
	
		
}
