/**
 * 
 */
package koerber.service;

import java.util.List;
import java.util.UUID;

import koerber.dto.CreateFilterDTO;
import koerber.dto.DeleteFilterDTO;
import koerber.dto.DeletedFilterDTO;
import koerber.dto.FilterDTO;
import koerber.dto.UpdateFilterDTO;
import koerber.exceptions.KoerberAssignmentException;

/**
 * Service layer responsible for managing filters
 * 
 * @author Bruno Medinas
 *
 */
public interface MyFilterService {

	/**
	 * Creates a filter
	 * 
	 * @param createFilterDTO - contains properties required to create a filter
	 * @return FilterDTO - created filter
	 * @throws KoerberAssignmentException
	 */
	FilterDTO create(CreateFilterDTO createFilterDTO) throws KoerberAssignmentException;

	/**
	 * Updates a filter
	 * 
	 * @param updateFilterDTO   - contains properties required to identify and
	 *                          update a filter
	 * @param deprecateBranches - Boolean flag that decides if associated branches
	 *                          should be migrated or deprecated
	 * @return FilterDTO - created filter
	 * @throws KoerberAssignmentException
	 */
	FilterDTO update(UpdateFilterDTO updateFilterDTO, Boolean deprecateBranches) throws KoerberAssignmentException;

	/**
	 * Deletes a filter
	 * 
	 * @param deleteFilterDTO- contains properties required to identify and delete a
	 *                         filter
	 * @return DeletedFilterDTO - deleted filter
	 * @throws KoerberAssignmentException
	 */
	DeletedFilterDTO delete(DeleteFilterDTO deleteFilterDTO) throws KoerberAssignmentException;

	/**
	 * Fetch a list of all filters on the latest version
	 * 
	 * @return List<FilterDTO> - List of latest filters
	 * @throws KoerberAssignmentException
	 */
	List<FilterDTO> listLatest() throws KoerberAssignmentException;

	/**
	 * Fetch a list of all versions of a given filter
	 * 
	 * @param correlation - UUID of version correlation of a filter
	 * @return List<FilterDTO> - list of all versions of a filter
	 * @throws KoerberAssignmentException
	 */
	List<FilterDTO> listVersionsOfFilter(UUID correlation) throws KoerberAssignmentException;

	/**
	 * Get a specific version of a filter
	 * 
	 * @param correlation - UUID of version correlation of a filter
	 * @param version     - version number
	 * @return FilterDTO - requested filter
	 * @throws KoerberAssignmentException
	 */
	FilterDTO getFilterVersion(UUID correlation, Integer version) throws KoerberAssignmentException;

}
