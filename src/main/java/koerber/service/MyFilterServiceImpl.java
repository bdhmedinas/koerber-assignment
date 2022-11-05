/**
 * 
 */
package koerber.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import koerber.dto.MyFilterDTO;
import koerber.entity.MyFilter;
import koerber.repository.MyFilterRepository;
import koerber.util.GenericUtils;

/**
 * @author Bruno Medinas
 *
 */
public class MyFilterServiceImpl implements MyFilterService {

	@Autowired
	private MyFilterRepository myFilterRepository;

	@Autowired
	private ModelMapper modelMapper;

	public UUID create(MyFilterDTO myFilterDTO) {

		MyFilter myFilter = modelMapper.map(myFilterDTO, MyFilter.class);
		return myFilterRepository.save(myFilter).getId();
	}

	public void update(MyFilterDTO myFilterDTO) {
		Optional<MyFilter> toUpdate = myFilterRepository.findById(myFilterDTO.getId());
		if (toUpdate.isPresent() && !toUpdate.get().getDeprecated()) {
			MyFilter myFilter = modelMapper.map(myFilterDTO, MyFilter.class);
			myFilterRepository.save(myFilter);
		}
	}

	@Override
	public void delete(UUID myFilterId) {
		Optional<MyFilter> toDelete = myFilterRepository.findById(myFilterId);
		if (toDelete.isPresent()) {
			MyFilter myFilter = toDelete.get();
			myFilter.setDeprecated(true);
			myFilterRepository.save(myFilter);
		}
	}

	public List<MyFilterDTO> list() {
		return GenericUtils.mapList(myFilterRepository.findAll(), MyFilterDTO.class);
	}

}
