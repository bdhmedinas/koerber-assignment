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
 * @author Bruno Medinas
 *
 */
public interface BranchService {

	BranchDTO create(CreateBranchDTO createBranchDTO) throws KoerberAssignmentException;

	BranchDTO update(UpdateBranchDTO updateBranchDTO) throws KoerberAssignmentException;

	DeletedBranchDTO delete(DeleteBranchDTO deleteBranchDTO) throws KoerberAssignmentException;

	List<BranchDTO> getBranchesOfFilter(UUID correlationId, Integer version) throws KoerberAssignmentException;

	FilterDTO mergeBranch(MergeBranchDTO mergeBranchDto) throws KoerberAssignmentException;
}
