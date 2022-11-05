/**
 * 
 */
package koerber.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import koerber.entity.MyFilter;

/**
 * @author Bruno Medinas
 *
 */
@Repository
public interface MyFilterRepository extends JpaRepository<MyFilter, UUID> {

}
