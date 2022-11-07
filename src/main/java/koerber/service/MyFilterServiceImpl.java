/**
 * 
 */
package koerber.service;

import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import koerber.dto.CreateFilterDTO;
import koerber.dto.DeleteFilterDTO;
import koerber.dto.DeletedFilterDTO;
import koerber.dto.FilterDTO;
import koerber.dto.UpdateFilterDTO;
import koerber.entity.AssignmentUser;
import koerber.entity.Branch;
import koerber.entity.DeletedBranch;
import koerber.entity.DeletedFilter;
import koerber.entity.MyFilter;
import koerber.entity.Screen;
import koerber.exceptions.KoerberAssignmentBadRequestException;
import koerber.exceptions.KoerberAssignmentException;
import koerber.exceptions.KoerberAssignmentNotFoundException;
import koerber.repository.BranchRepository;
import koerber.repository.DeletedBranchRepository;
import koerber.repository.DeletedFilterRepository;
import koerber.repository.MyFilterRepository;
import koerber.repository.ScreenRepository;
import koerber.repository.UserRepository;
import koerber.utils.GenericUtils;

/**
 * @author Bruno Medinas
 *
 */
@Service
public class MyFilterServiceImpl implements MyFilterService {
	Logger logger = LoggerFactory.getLogger(MyFilterServiceImpl.class);

	@Autowired
	private MyFilterRepository myFilterRepository;

	@Autowired
	private DeletedFilterRepository deletedFilterRepository;

	@Autowired
	private BranchRepository branchRepository;

	@Autowired
	private DeletedBranchRepository deletedBranchRepository;

	@Autowired
	private ScreenRepository screenRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public FilterDTO create(CreateFilterDTO createFilterDTO) throws KoerberAssignmentException {
		if (createFilterDTO.getUserId() == null || createFilterDTO.getScreenId() == null) {
			throw new KoerberAssignmentBadRequestException("Invalid id values used.");
		}

		logger.debug("Creating filter with user id = " + createFilterDTO.getUserId() + " and screen id = "
				+ createFilterDTO.getScreenId());

		AssignmentUser assignmentUser = userRepository.getReferenceById(createFilterDTO.getUserId());
		if (assignmentUser == null) {
			throw new KoerberAssignmentNotFoundException(
					"User with Id (" + createFilterDTO.getUserId() + ") does not exist.");
		}
		Screen screen = screenRepository.getReferenceById(createFilterDTO.getScreenId());
		if (screen == null) {
			throw new KoerberAssignmentNotFoundException(
					"Screen with Id (" + createFilterDTO.getScreenId() + ") does not exist.");
		}

		MyFilter myFilter = modelMapper.map(createFilterDTO, MyFilter.class);
		myFilter.setUser(assignmentUser);
		myFilter.setScreen(screen);
		myFilter.setTimestamp(System.currentTimeMillis());
		myFilter.setVersion(1);
		myFilter.setVersionCorrelation(UUID.randomUUID());

		logger.debug("Created filter with id = " + myFilter.getFilterId());

		return modelMapper.map(myFilterRepository.save(myFilter), FilterDTO.class);
	}

	@Override
	public FilterDTO update(UpdateFilterDTO updateFilterDTO, Boolean deprecateBranches)
			throws KoerberAssignmentException {
		if (updateFilterDTO.getCorrelationId() == null || updateFilterDTO.getUserId() == null
				|| deprecateBranches == null) {
			throw new KoerberAssignmentBadRequestException("Invalid parameter values used.");
		}

		logger.debug("Updating filter with correlationId " + updateFilterDTO.getCorrelationId() + " and version"
				+ updateFilterDTO.getVersion() + " with user " + updateFilterDTO.getUserId());

		MyFilter myFilter = updateFilterDTO.getVersion() == null
				? myFilterRepository.findByVersionCorrelationAndLatestVersion(updateFilterDTO.getCorrelationId())
				: myFilterRepository.findByVersionCorrelationAndVersion(updateFilterDTO.getCorrelationId(),
						updateFilterDTO.getVersion());
		if (myFilter == null) {
			throw new KoerberAssignmentNotFoundException("Filter (correlation<" + updateFilterDTO.getCorrelationId()
					+ ">, version<" + updateFilterDTO.getVersion() + ">) not found.");
		}

		MyFilter updatedFilter = new MyFilter();
		long currentTimeMillis = System.currentTimeMillis();
		if (updateFilterDTO.getUserId() != null) {
			updatedFilter.setUser(userRepository.getReferenceById(updateFilterDTO.getUserId()));
		}

		updatedFilter.setName(updateFilterDTO.getName() != null ? updateFilterDTO.getName() : myFilter.getName());
		updatedFilter.setData(updateFilterDTO.getData() != null ? updateFilterDTO.getData() : myFilter.getData());
		updatedFilter.setOutputFilter(updateFilterDTO.getOutputFilter() != null ? updateFilterDTO.getOutputFilter()
				: myFilter.getOutputFilter());
		updatedFilter.setScreen(
				updateFilterDTO.getScreenId() != null ? screenRepository.getReferenceById(updateFilterDTO.getScreenId())
						: myFilter.getScreen());

		updatedFilter.setTimestamp(currentTimeMillis);
		updatedFilter.setVersion(myFilter.getVersion() + 1);
		updatedFilter.setVersionCorrelation(myFilter.getVersionCorrelation());
		MyFilter createdFilter = myFilterRepository.save(updatedFilter);

		List<Branch> branches = branchRepository.findByFilterFilterId(myFilter.getFilterId());
		if (branches == null) {
			throw new KoerberAssignmentNotFoundException(
					"Branches matching id (" + myFilter.getFilterId() + ") not found.");
		}

		if (deprecateBranches) {
			DeletedBranch deprecated;
			for (Branch branch : branches) {
				deprecated = new DeletedBranch();
				deprecated.setUser(branch.getUser());
				deprecated.setTimestamp(branch.getTimestamp());
				deprecated.setVersion(branch.getVersion());
				deprecated.setData(branch.getData());
				deprecated.setFilter(branch.getFilter());
				deprecated.setName(branch.getName());
				deprecated.setOutputFilter(branch.getOutputFilter());
				deprecated.setScreen(branch.getScreen());
				deprecated.setVersionCorrelation(branch.getVersionCorrelation());
				deprecated.setActionUser(createdFilter.getUser());
				deprecated.setDeleteTimestamp(System.currentTimeMillis());

				deletedBranchRepository.save(deprecated);
				branchRepository.deleteById(branch.getFilterId());
			}
		} else {
			for (Branch branch : branches) {
				branch.setFilter(createdFilter);

				branchRepository.save(branch);
			}
		}

		logger.debug("Updated filter with id = " + createdFilter.getFilterId());

		return modelMapper.map(createdFilter, FilterDTO.class);
	}

	@Override
	public DeletedFilterDTO delete(DeleteFilterDTO deleteFilterDTO) throws KoerberAssignmentException {
		if (deleteFilterDTO.getCorrelationId() == null || deleteFilterDTO.getUserId() == null) {
			throw new KoerberAssignmentBadRequestException("Invalid id values used.");
		}

		logger.debug("Deleting filter with correlationId " + deleteFilterDTO.getCorrelationId() + " and version"
				+ deleteFilterDTO.getVersion() + " with user " + deleteFilterDTO.getUserId());

		MyFilter myFilter = deleteFilterDTO.getVersion() == null
				? myFilterRepository.findByVersionCorrelationAndLatestVersion(deleteFilterDTO.getCorrelationId())
				: myFilterRepository.findByVersionCorrelationAndVersion(deleteFilterDTO.getCorrelationId(),
						deleteFilterDTO.getVersion());
		if (myFilter == null) {
			throw new KoerberAssignmentNotFoundException("Filter (correlation<" + deleteFilterDTO.getCorrelationId()
					+ ">, version<" + deleteFilterDTO.getVersion() + ">) not found.");
		}
		DeletedFilter deletedFilter = new DeletedFilter();
		AssignmentUser userDeleting = userRepository.getReferenceById(deleteFilterDTO.getUserId());
		if (userDeleting == null) {
			throw new KoerberAssignmentNotFoundException(
					"User with Id (" + deleteFilterDTO.getUserId() + ") does not exist.");
		}

		deletedFilter.setData(myFilter.getData());
		deletedFilter.setName(myFilter.getName());
		deletedFilter.setOutputFilter(myFilter.getOutputFilter());
		deletedFilter.setScreen(myFilter.getScreen());
		deletedFilter.setActionUser(userDeleting);
		deletedFilter.setVersion(myFilter.getVersion());
		deletedFilter.setVersionCorrelation(myFilter.getVersionCorrelation());
		deletedFilter.setTimestamp(myFilter.getTimestamp());
		deletedFilter.setDeleteTimestamp(System.currentTimeMillis());
		deletedFilter.setUser(myFilter.getUser());
		DeletedFilter deleted = deletedFilterRepository.save(deletedFilter);
		myFilterRepository.deleteById(myFilter.getFilterId());

		List<Branch> branches = branchRepository.findByFilterFilterId(myFilter.getFilterId());
		if (branches == null) {
			throw new KoerberAssignmentNotFoundException(
					"Branches matching id (" + myFilter.getFilterId() + ") not found.");
		}
		DeletedBranch deprecated;
		for (Branch branch : branches) {
			deprecated = new DeletedBranch();
			deprecated.setUser(branch.getUser());
			deprecated.setTimestamp(branch.getTimestamp());
			deprecated.setVersion(branch.getVersion());
			deprecated.setData(branch.getData());
			deprecated.setFilter(branch.getFilter());
			deprecated.setName(branch.getName());
			deprecated.setOutputFilter(branch.getOutputFilter());
			deprecated.setScreen(branch.getScreen());
			deprecated.setVersionCorrelation(branch.getVersionCorrelation());
			deprecated.setActionUser(userDeleting);
			deprecated.setDeleteTimestamp(System.currentTimeMillis());

			deletedBranchRepository.save(deprecated);
			branchRepository.deleteById(branch.getFilterId());
		}

		logger.debug("Deleted filter with id = " + deleted.getFilterId());
		return modelMapper.map(deleted, DeletedFilterDTO.class);
	}

	@Override
	public List<FilterDTO> listLatest() {
		logger.debug("Listing lastest");
		return GenericUtils.mapAll(myFilterRepository.findAllLatest(), FilterDTO.class);
	}

	@Override
	public List<FilterDTO> listVersionsOfFilter(UUID correlation) {
		logger.debug("Listing version of filter with correlation id = " + correlation);
		return GenericUtils.mapAll(myFilterRepository.findByVersionCorrelation(correlation), FilterDTO.class);
	}

	@Override
	public FilterDTO getFilterVersion(UUID correlation, Integer version) throws KoerberAssignmentException {
		if (correlation == null) {
			throw new KoerberAssignmentBadRequestException("Invalid id value used.");
		}
		logger.debug("Getting filter by " + (version == null ? "lastest version" : ("version " + version))
				+ " and correlation id = " + correlation);
		MyFilter filter = version != null ? myFilterRepository.findByVersionCorrelationAndVersion(correlation, version)
				: myFilterRepository.findByVersionCorrelationAndLatestVersion(correlation);

		if (filter == null) {
			throw new KoerberAssignmentNotFoundException(
					"Branch (correlation<" + correlation + ">, version<" + version + ">) not found.");
		}
		return modelMapper.map(filter, FilterDTO.class);
	}
}
