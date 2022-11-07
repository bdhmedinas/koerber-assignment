package koerber.controller;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import koerber.beans.CreateFilterBean;
import koerber.beans.DeletedFilterBean;
import koerber.beans.FilterBean;
import koerber.beans.FilterDeleteBean;
import koerber.beans.FilterUpdateBean;
import koerber.dto.CreateFilterDTO;
import koerber.dto.DeleteFilterDTO;
import koerber.dto.DeletedFilterDTO;
import koerber.dto.FilterDTO;
import koerber.dto.UpdateFilterDTO;
import koerber.exceptions.KoerberAssignmentBadRequestException;
import koerber.exceptions.KoerberAssignmentNotFoundException;
import koerber.service.MyFilterService;
import koerber.utils.GenericUtils;

@RestController
@RequestMapping(path = "myfilter")
@Tag(name = "myfilter", description = "The filter controller")
public class MyFilterController {

	Logger logger = LoggerFactory.getLogger(MyFilterController.class);

	@Autowired
	private MyFilterService myFilterService;

	@Autowired
	private ModelMapper modelMapper;

	@Operation(summary = "Create filter", description = "Creates a filter based on values provided.", tags = {
			"myfilter" })
	@ApiResponses(value = { @ApiResponse(description = "successful operation", responseCode = "201", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = FilterBean.class)) }) })
	@PostMapping(consumes = { "application/json" }, produces = { "application/json" })
	public ResponseEntity<FilterBean> create(
			@Parameter(description = "Parameter containing the required values to create a filter.", required = true) @NotNull @Valid @RequestBody CreateFilterBean createFilterBean) {
		logger.debug("Create filter request initiated");
		try {
			FilterDTO create = myFilterService.create(modelMapper.map(createFilterBean, CreateFilterDTO.class));
			logger.debug("Create filter request completed");
			return ResponseEntity.status(HttpStatus.CREATED).body(modelMapper.map(create, FilterBean.class));
		} catch (KoerberAssignmentNotFoundException k) {
			logger.debug("Not found", k);
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found", k);
		} catch (KoerberAssignmentBadRequestException b) {
			logger.debug("Bad request", b);
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad request", b);
		} catch (Exception e) {
			logger.debug("Exception", e);
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong", e);
		}
	}

	@Operation(summary = "Update filter", description = "Updates a filter based on values provided. Uses correlation id and version to identify which filter and what version should be updated. If no version is provided, latest is targeted.", tags = {
			"myfilter" })
	@ApiResponses(value = { @ApiResponse(description = "successful operation", responseCode = "200", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = FilterBean.class)) }) })
	@PutMapping(consumes = { "application/json" }, produces = { "application/json" })
	public ResponseEntity<FilterBean> update(
			@Parameter(description = "Parameter containing the required values to update a filter.", required = true) @NotNull @Valid @RequestBody FilterUpdateBean updateFilterBean,
			@RequestParam Boolean deprecateBranches) {
		logger.debug("Update filter request initiated");
		try {
			FilterDTO update = myFilterService.update(modelMapper.map(updateFilterBean, UpdateFilterDTO.class),
					deprecateBranches);
			logger.debug("Update filter request completed");
			return ResponseEntity.status(HttpStatus.OK).body(modelMapper.map(update, FilterBean.class));
		} catch (KoerberAssignmentNotFoundException k) {
			logger.debug("Not found", k);
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found", k);
		} catch (KoerberAssignmentBadRequestException b) {
			logger.debug("Bad request", b);
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad request", b);
		} catch (Exception e) {
			logger.debug("Exception", e);
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong", e);
		}
	}

	@Operation(summary = "Delete filter", description = "Deletes a filter. Uses correlation id and version to identify which filter and what version should be deleted. If no version is provided, latest is targeted. Also deletes related branches", tags = {
			"myfilter" })
	@ApiResponses(value = { @ApiResponse(description = "successful operation", responseCode = "200", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = DeletedFilterBean.class)) }) })
	@DeleteMapping(consumes = { "application/json" }, produces = { "application/json" })
	public ResponseEntity<DeletedFilterBean> delete(
			@Parameter(description = "Parameter containing the required values to delete a filter.", required = true) @NotNull @Valid @RequestBody FilterDeleteBean deleteFilterBean) {
		logger.debug("Delete filter request initiated");
		try {
			DeletedFilterDTO delete = myFilterService.delete(modelMapper.map(deleteFilterBean, DeleteFilterDTO.class));
			logger.debug("Delete filter request completed");
			return ResponseEntity.status(HttpStatus.OK).body(modelMapper.map(delete, DeletedFilterBean.class));
		} catch (KoerberAssignmentNotFoundException k) {
			logger.debug("Not found", k);
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found", k);
		} catch (KoerberAssignmentBadRequestException b) {
			logger.debug("Bad request", b);
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad request", b);
		} catch (Exception e) {
			logger.debug("Exception", e);
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong", e);
		}
	}

	@Operation(summary = "Get latest version of filters", description = "Fetches a list of latest version filters", tags = {
			"myfilter" })
	@ApiResponses(value = { @ApiResponse(description = "successful operation", responseCode = "200", content = {
			@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = FilterBean.class))) }) })
	@GetMapping(path = "/latest", produces = { "application/json" })
	public ResponseEntity<List<FilterBean>> getLatest() {
		logger.debug("Get latest version of filter request initiated");
		try {
			List<FilterDTO> listLatest = myFilterService.listLatest();
			logger.debug("Get latest version of filter request completed");
			return ResponseEntity.status(HttpStatus.OK).body(GenericUtils.mapAll(listLatest, FilterBean.class));
		} catch (KoerberAssignmentNotFoundException k) {
			logger.debug("Not found", k);
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found", k);
		} catch (KoerberAssignmentBadRequestException b) {
			logger.debug("Bad request", b);
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad request", b);
		} catch (Exception e) {
			logger.debug("Exception", e);
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong", e);
		}
	}

	@Operation(summary = "Get versions of filter", description = "Fetches a list of all version of a filter. Uses correlation id to identify target filter", tags = {
			"myfilter" })
	@ApiResponses(value = { @ApiResponse(description = "successful operation", responseCode = "200", content = {
			@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = FilterBean.class))) }) })
	@GetMapping(path = "/{uuid}", produces = { "application/json" })
	public ResponseEntity<List<FilterBean>> getVersionsOfFilter(
			@Parameter(description = "UUID value that corresponds to the target filter", required = true) @NotNull @Valid @PathVariable("uuid") UUID correlation) {
		logger.debug("Get versions of filter request initiated");
		try {
			List<FilterDTO> listVersionsOfFilter = myFilterService.listVersionsOfFilter(correlation);
			logger.debug("Get versions of filter request completed");
			return ResponseEntity.status(HttpStatus.OK)
					.body(GenericUtils.mapAll(listVersionsOfFilter, FilterBean.class));
		} catch (KoerberAssignmentNotFoundException k) {
			logger.debug("Not found", k);
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found", k);
		} catch (KoerberAssignmentBadRequestException b) {
			logger.debug("Bad request", b);
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad request", b);
		} catch (Exception e) {
			logger.debug("Exception", e);
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong", e);
		}
	}

	@Operation(summary = "Get specific version of filter", description = "Fetches a filter based on provided version. If no version is provided, latest is returned.", tags = {
			"myfilter" })
	@ApiResponses(value = { @ApiResponse(description = "successful operation", responseCode = "200", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = DeletedFilterBean.class)) }) })
	@GetMapping(path = "/version/{uuid}", produces = { "application/json" })
	public ResponseEntity<FilterBean> getFilterVersion(
			@Parameter(description = "UUID value that corresponds to the target filter", required = true) @NotNull @Valid @PathVariable("uuid") UUID correlation,
			@Parameter(description = "UUID value that corresponds to the target filter", required = false) @Min(value = 1) @RequestParam(required = false) Integer version) {
		logger.debug("Get specific filter version request initiated");
		try {
			FilterDTO filterVersion = myFilterService.getFilterVersion(correlation, version);
			logger.debug("Get specific filter version request completed");
			return ResponseEntity.status(HttpStatus.OK).body(modelMapper.map(filterVersion, FilterBean.class));
		} catch (KoerberAssignmentNotFoundException k) {
			logger.debug("Not found", k);
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found", k);
		} catch (KoerberAssignmentBadRequestException b) {
			logger.debug("Bad request", b);
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad request", b);
		} catch (Exception e) {
			logger.debug("Exception", e);
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong", e);
		}
	}
}
