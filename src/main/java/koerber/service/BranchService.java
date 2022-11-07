/**
 * 
 */
package koerber.service;

import java.util.List;
import java.util.UUID;

import koerber.dto.BranchDTO;
import koerber.dto.CreateBranchDTO;
import koerber.dto.DeleteBranchDTO;
import koerber.dto.DeletedBranchDTO;
import koerber.dto.FilterDTO;
import koerber.dto.MergeBranchDTO;
import koerber.dto.UpdateBranchDTO;
import koerber.exceptions.KoerberAssignmentException;

/**
 * Service layer responsible for managing branches
 * 
 * @author Bruno Medinas
 *
 */
public interface BranchService {

	/**
	 * Creates a branch
	 * 
	 * @param createBranchDTO - contains properties required to identify which
	 *                        filter should be branched from
	 * @return BranchDTO - Created branch
	 * @throws KoerberAssignmentException
	 */
	BranchDTO create(CreateBranchDTO createBranchDTO) throws KoerberAssignmentException;

	/**
	 * Updates a branch
	 * 
	 * @param updateBranchDTO - contains properties required to identify which
	 *                        branch should be updates and with what values.
	 * @return BranchDTO - Updated branch
	 * @throws KoerberAssignmentException
	 */
	BranchDTO update(UpdateBranchDTO updateBranchDTO) throws KoerberAssignmentException;

	/**
	 * Deletes a branch
	 * 
	 * @param deleteBranchDTO - contains properties required to identify which
	 *                        branch should be deleted.
	 * @return DeletedBranchDTO - Deleted branch
	 * @throws KoerberAssignmentException
	 */
	DeletedBranchDTO delete(DeleteBranchDTO deleteBranchDTO) throws KoerberAssignmentException;

	/**
	 * Fetches a list of branches belonging to a given filter.
	 * 
	 * @param correlationId - identifier of the filter
	 * @param version       - version of the filter (optional - if null, latest will
	 *                      be used)
	 * @return List<BranchDTO> - List of branches belonging to the filter
	 * @throws KoerberAssignmentException
	 */
	List<BranchDTO> getBranchesOfFilter(UUID correlationId, Integer version) throws KoerberAssignmentException;

	/**
	 * Merges a given branch to its origin filter
	 * 
	 * @param mergeBranchDto - contains properties required to identify which branch
	 *                       should be merged.
	 * @return FilterDTO - new Filter version
	 * @throws KoerberAssignmentException
	 */
	FilterDTO mergeBranch(MergeBranchDTO mergeBranchDto) throws KoerberAssignmentException;
}
