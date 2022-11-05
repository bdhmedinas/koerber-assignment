package koerber.controller;

import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import koerber.beans.MyFilterBean;
import koerber.dto.MyFilterDTO;
import koerber.service.MyFilterService;

@RestController
@RequestMapping(path = "myfilter")
public class MyFilterController {

	@Autowired
	private MyFilterService myFilterService;

	@Autowired
	private ModelMapper modelMapper;

	@PostMapping
	public ResponseEntity<UUID> create(@RequestBody MyFilterBean myFilterBean) {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(myFilterService.create(modelMapper.map(myFilterBean, MyFilterDTO.class)));
	}

	@PutMapping
	public ResponseEntity<Object> update(@RequestBody MyFilterBean myFilterBean) {
		myFilterService.update(modelMapper.map(myFilterBean, MyFilterDTO.class));
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@DeleteMapping(path = "{uuid}")
	public ResponseEntity<Object> delete(@PathVariable UUID uuid) {
		myFilterService.delete(uuid);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@GetMapping
	public ResponseEntity<List<MyFilterDTO>> getAll() {
		return ResponseEntity.status(HttpStatus.OK).body(myFilterService.list());
	}

}
