/**
 * 
 */
package koerber.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import koerber.dto.BranchDTO;
import koerber.dto.CreateBranchDTO;
import koerber.dto.DeleteBranchDTO;
import koerber.dto.DeletedBranchDTO;
import koerber.dto.FilterDTO;
import koerber.dto.MergeBranchDTO;
import koerber.dto.UpdateBranchDTO;
import koerber.entity.AssignmentUser;
import koerber.entity.Branch;
import koerber.entity.DeletedBranch;
import koerber.entity.MyFilter;
import koerber.exceptions.KoerberAssignmentBadRequestException;
import koerber.exceptions.KoerberAssignmentException;
import koerber.exceptions.KoerberAssignmentNotFoundException;
import koerber.repository.BranchRepository;
import koerber.repository.DeletedBranchRepository;
import koerber.repository.MyFilterRepository;
import koerber.repository.ScreenRepository;
import koerber.repository.UserRepository;

/**
 * @author Bruno Medinas
 *
 */
@Service
public class BranchServiceImpl implements BranchService {
	Logger logger = LoggerFactory.getLogger(BranchServiceImpl.class);

	@Autowired
	private BranchRepository branchRepository;

	@Autowired
	private DeletedBranchRepository deletedBranchRepository;

	@Autowired
	private MyFilterRepository myFilterRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ScreenRepository screenRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public BranchDTO create(CreateBranchDTO createBranchDTO) throws KoerberAssignmentException {
		if (createBranchDTO.getCorrelationId() == null || createBranchDTO.getUserId() == null) {
			throw new KoerberAssignmentBadRequestException("Invalid id values used.");
		}

		logger.debug("Creating branch with user id = " + createBranchDTO.getUserId() + ", correlation id = "
				+ createBranchDTO.getCorrelationId() + " and version = " + createBranchDTO.getVersion());

		AssignmentUser user = userRepository.getReferenceById(createBranchDTO.getUserId());
		if (user == null) {
			throw new KoerberAssignmentNotFoundException(
					"User with Id (" + createBranchDTO.getUserId() + ") does not exist.");
		}
		MyFilter filter = createBranchDTO.getVersion() == null
				? myFilterRepository.findByVersionCorrelationAndLatestVersion(createBranchDTO.getCorrelationId())
				: myFilterRepository.findByVersionCorrelationAndVersion(createBranchDTO.getCorrelationId(),
						createBranchDTO.getVersion());

		if (filter == null) {
			throw new KoerberAssignmentNotFoundException("Filter (correlation<" + createBranchDTO.getCorrelationId()
					+ ">, version<" + createBranchDTO.getVersion() + ">) not found.");
		}

		Branch branch = new Branch();
		branch.setUser(user);
		branch.setScreen(filter.getScreen());
		branch.setData(filter.getData());
		branch.setName(filter.getName());
		branch.setOutputFilter(filter.getOutputFilter());
		branch.setTimestamp(System.currentTimeMillis());
		branch.setVersion(1);
		branch.setVersionCorrelation(UUID.randomUUID());
		branch.setFilter(filter);

		BranchDTO branchDTO = modelMapper.map(branchRepository.save(branch), BranchDTO.class);
		branchDTO.setBranchOfId(branch.getFilter().getFilterId());

		logger.debug("Created branch with id = " + branchDTO.getFilterId());

		return branchDTO;
	}

	@Override
	public BranchDTO update(UpdateBranchDTO updateBranchDTO) throws KoerberAssignmentException {
		if (updateBranchDTO.getCorrelationId() == null || updateBranchDTO.getUserId() == null) {
			throw new KoerberAssignmentBadRequestException("Invalid id values used.");
		}

		logger.debug("Updating branch with user id = " + updateBranchDTO.getUserId() + ", correlation id = "
				+ updateBranchDTO.getCorrelationId() + " and version = " + updateBranchDTO.getVersion());

		AssignmentUser user = userRepository.getReferenceById(updateBranchDTO.getUserId());

		if (user == null) {
			throw new KoerberAssignmentNotFoundException(
					"User with Id (" + updateBranchDTO.getUserId() + ") does not exist.");
		}

		Branch branch = updateBranchDTO.getVersion() == null
				? branchRepository.findByVersionCorrelationAndLatestVersion(updateBranchDTO.getCorrelationId())
				: branchRepository.findByVersionCorrelationAndVersion(updateBranchDTO.getCorrelationId(),
						updateBranchDTO.getVersion());

		if (branch == null) {
			throw new KoerberAssignmentNotFoundException("Branch (correlation<" + updateBranchDTO.getCorrelationId()
					+ ">, version<" + updateBranchDTO.getVersion() + ">) not found.");
		}

		Branch toUpdateBranch = new Branch();
		toUpdateBranch.setUser(user);
		toUpdateBranch.setName(updateBranchDTO.getName() != null ? updateBranchDTO.getName() : branch.getName());
		toUpdateBranch.setData(updateBranchDTO.getData() != null ? updateBranchDTO.getData() : branch.getData());
		toUpdateBranch.setOutputFilter(updateBranchDTO.getOutputFilter() != null ? updateBranchDTO.getOutputFilter()
				: branch.getOutputFilter());
		toUpdateBranch.setScreen(
				updateBranchDTO.getScreenId() != null ? screenRepository.getReferenceById(updateBranchDTO.getScreenId())
						: branch.getScreen());

		toUpdateBranch.setTimestamp(System.currentTimeMillis());
		toUpdateBranch.setVersion(branch.getVersion() + 1);
		toUpdateBranch.setVersionCorrelation(branch.getVersionCorrelation());
		toUpdateBranch.setFilter(branch.getFilter());
		Branch updatedBranch = branchRepository.save(toUpdateBranch);

		BranchDTO branchDTO = modelMapper.map(updatedBranch, BranchDTO.class);
		branchDTO.setBranchOfId(updatedBranch.getFilter().getFilterId());

		logger.debug("Updated branch with id = " + branchDTO.getFilterId());
		return branchDTO;
	}

	@Override
	public DeletedBranchDTO delete(DeleteBranchDTO deleteBranchDTO) throws KoerberAssignmentException {
		if (deleteBranchDTO.getCorrelationId() == null || deleteBranchDTO.getUserId() == null) {
			throw new KoerberAssignmentBadRequestException("Invalid id values used.");
		}

		logger.debug("Deleting branch with user id = " + deleteBranchDTO.getUserId() + ", correlation id = "
				+ deleteBranchDTO.getCorrelationId() + " and version = " + deleteBranchDTO.getVersion());

		AssignmentUser userDeleting = userRepository.getReferenceById(deleteBranchDTO.getUserId());

		if (userDeleting == null) {
			throw new KoerberAssignmentNotFoundException(
					"User with Id (" + deleteBranchDTO.getUserId() + ") does not exist.");
		}

		Branch branch = deleteBranchDTO.getVersion() == null
				? branchRepository.findByVersionCorrelationAndLatestVersion(deleteBranchDTO.getCorrelationId())
				: branchRepository.findByVersionCorrelationAndVersion(deleteBranchDTO.getCorrelationId(),
						deleteBranchDTO.getVersion());

		if (branch == null) {
			throw new KoerberAssignmentNotFoundException("Branch (correlation<" + deleteBranchDTO.getCorrelationId()
					+ ">, version<" + deleteBranchDTO.getVersion() + ">) not found.");
		}

		DeletedBranch toDelete = new DeletedBranch();
		toDelete.setData(branch.getData());
		toDelete.setName(branch.getName());
		toDelete.setOutputFilter(branch.getOutputFilter());
		toDelete.setScreen(branch.getScreen());
		toDelete.setActionUser(userDeleting);
		toDelete.setVersion(branch.getVersion());
		toDelete.setVersionCorrelation(branch.getVersionCorrelation());
		toDelete.setTimestamp(branch.getTimestamp());
		toDelete.setDeleteTimestamp(System.currentTimeMillis());
		toDelete.setUser(branch.getUser());
		toDelete.setFilter(branch.getFilter());
		DeletedBranch deletedBranch = deletedBranchRepository.save(toDelete);
		branchRepository.deleteById(branch.getFilterId());

		DeletedBranchDTO branchDTO = modelMapper.map(deletedBranch, DeletedBranchDTO.class);
		branchDTO.setBranchOfId(deletedBranch.getFilter().getFilterId());

		logger.debug("Deleted branch with id = " + branchDTO.getFilterId());
		return branchDTO;
	}

	@Override
	public List<BranchDTO> getBranchesOfFilter(UUID correlation, Integer version) throws KoerberAssignmentException {

		if (correlation == null) {
			throw new KoerberAssignmentBadRequestException("Invalid id value used.");
		}

		logger.debug("Getting branchs of a filter with correlation id = " + correlation + " and version = " + version);

		MyFilter filter = version == null ? myFilterRepository.findByVersionCorrelationAndLatestVersion(correlation)
				: myFilterRepository.findByVersionCorrelationAndVersion(correlation, version);

		if (filter == null) {
			throw new KoerberAssignmentNotFoundException(
					"Filter (correlation<" + correlation + ">, version<" + version + ">) not found.");
		}

		List<Branch> branches = branchRepository.findByFilterFilterId(filter.getFilterId());

		if (branches == null) {
			throw new KoerberAssignmentNotFoundException(
					"Branches matching id (" + filter.getFilterId() + ") not found.");
		}

		return branches.stream().map(branch -> {
			BranchDTO dto = modelMapper.map(branch, BranchDTO.class);
			dto.setBranchOfId(branch.getFilter().getFilterId());
			return dto;
		}).collect(Collectors.toList());

	}

	@Override
	public FilterDTO mergeBranch(MergeBranchDTO mergeBranchDto) throws KoerberAssignmentException {
		if (mergeBranchDto.getCorrelationId() == null || mergeBranchDto.getUserId() == null) {
			throw new KoerberAssignmentBadRequestException("Invalid id values used.");
		}

		logger.debug("Merging branch with correlationId " + mergeBranchDto.getCorrelationId() + " and version"
				+ mergeBranchDto.getVersion() + " with user " + mergeBranchDto.getUserId());

		Branch branch = mergeBranchDto.getVersion() == null
				? branchRepository.findByVersionCorrelationAndLatestVersion(mergeBranchDto.getCorrelationId())
				: branchRepository.findByVersionCorrelationAndVersion(mergeBranchDto.getCorrelationId(),
						mergeBranchDto.getVersion());

		if (branch == null) {
			throw new KoerberAssignmentNotFoundException("Branch (correlation<" + mergeBranchDto.getCorrelationId()
					+ ">, version<" + mergeBranchDto.getVersion() + ">) not found.");
		}
		AssignmentUser user = userRepository.getReferenceById(mergeBranchDto.getUserId());

		if (user == null) {
			throw new KoerberAssignmentNotFoundException(
					"User with Id (" + mergeBranchDto.getUserId() + ") does not exist.");
		}

		MyFilter filter = branch.getFilter();
		MyFilter mergedFilter = new MyFilter();

		mergedFilter.setName(branch.getName());
		mergedFilter.setData(branch.getData());
		mergedFilter.setOutputFilter(branch.getOutputFilter());
		mergedFilter.setUser(user);
		mergedFilter.setScreen(branch.getScreen());
		mergedFilter.setTimestamp(System.currentTimeMillis());
		mergedFilter.setVersion(filter.getVersion() + 1);
		mergedFilter.setVersionCorrelation(filter.getVersionCorrelation());

		logger.debug("Branch merged.");

		return modelMapper.map(myFilterRepository.save(mergedFilter), FilterDTO.class);
	}

}
