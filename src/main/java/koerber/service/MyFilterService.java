/**
 * 
 */
package koerber.service;

import java.util.List;
import java.util.UUID;

import koerber.dto.MyFilterDTO;

/**
 * @author Bruno Medinas
 *
 */
public interface MyFilterService {

	UUID create(MyFilterDTO myFilterDTO);

	void update(MyFilterDTO myFilterDTO);

	void delete(UUID myFilterId);

	List<MyFilterDTO> list();

	// List<VersionDTO> listVersions(UUID myFilterId);

	// MyFilterDTO getFilterByVersion(Integer version);

}
