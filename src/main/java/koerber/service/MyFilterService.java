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
 * @author Bruno Medinas
 *
 */
public interface MyFilterService {

	FilterDTO create(CreateFilterDTO createFilterDTO) throws KoerberAssignmentException;

	FilterDTO update(UpdateFilterDTO updateFilterDTO, Boolean deprecateBranches) throws KoerberAssignmentException;

	DeletedFilterDTO delete(DeleteFilterDTO deleteFilterDTO) throws KoerberAssignmentException;

	List<FilterDTO> listLatest() throws KoerberAssignmentException;

	List<FilterDTO> listVersionsOfFilter(UUID correlation) throws KoerberAssignmentException;

	FilterDTO getFilterVersion(UUID correlation, Integer version) throws KoerberAssignmentException;

}
