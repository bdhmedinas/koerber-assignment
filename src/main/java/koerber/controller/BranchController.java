package koerber.controller;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
import koerber.beans.BranchBean;
import koerber.beans.BranchDeleteBean;
import koerber.beans.BranchMergeBean;
import koerber.beans.BranchUpdateBean;
import koerber.beans.CreateBranchBean;
import koerber.beans.DeletedBranchBean;
import koerber.beans.FilterBean;
import koerber.dto.BranchDTO;
import koerber.dto.CreateBranchDTO;
import koerber.dto.DeleteBranchDTO;
import koerber.dto.DeletedBranchDTO;
import koerber.dto.FilterDTO;
import koerber.dto.MergeBranchDTO;
import koerber.dto.UpdateBranchDTO;
import koerber.exceptions.KoerberAssignmentBadRequestException;
import koerber.exceptions.KoerberAssignmentNotFoundException;
import koerber.service.BranchService;
import koerber.utils.GenericUtils;

@RestController
@RequestMapping(path = "branch")
@Tag(name = "branch", description = "The branch controller")
public class BranchController {
	Logger logger = LoggerFactory.getLogger(BranchController.class);

	@Autowired
	private BranchService branchService;

	@Autowired
	private ModelMapper modelMapper;

	@Operation(summary = "Create branch", description = "Creates a branch based on values provided.", tags = {
			"branch" })
	@ApiResponses(value = { @ApiResponse(description = "successful operation", responseCode = "201", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = BranchBean.class)) }) })
	@PostMapping(consumes = { "application/json" }, produces = { "application/json" })
	public ResponseEntity<BranchBean> create(
			@Parameter(description = "Parameter containing the required values to create a branch.", required = true) @NotNull @Valid @RequestBody CreateBranchBean createbranchBean) {
		logger.debug("Create branch request initiated");
		try {
			BranchDTO create = branchService.create(modelMapper.map(createbranchBean, CreateBranchDTO.class));
			logger.debug("Create branch request completed");
			return ResponseEntity.status(HttpStatus.CREATED).body(modelMapper.map(create, BranchBean.class));
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

	@Operation(summary = "Update branch", description = "Updates a branch based on values provided.", tags = {
			"branch" })
	@ApiResponses(value = { @ApiResponse(description = "successful operation", responseCode = "200", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = BranchBean.class)) }) })
	@PutMapping(consumes = { "application/json" }, produces = { "application/json" })
	public ResponseEntity<BranchBean> update(
			@Parameter(description = "Parameter containing the required values to update a branch.", required = true) @NotNull @Valid @RequestBody BranchUpdateBean updateBranchBean) {
		logger.debug("Update branch request initiated");
		try {
			BranchDTO update = branchService.update(modelMapper.map(updateBranchBean, UpdateBranchDTO.class));
			logger.debug("Update branch request completed");
			return ResponseEntity.status(HttpStatus.OK).body(modelMapper.map(update, BranchBean.class));
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

	@Operation(summary = "Delete branch", description = "Deletes a branch based on values provided.", tags = {
			"branch" })
	@ApiResponses(value = { @ApiResponse(description = "successful operation", responseCode = "200", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = BranchBean.class)) }) })
	@DeleteMapping(consumes = { "application/json" }, produces = { "application/json" })
	public ResponseEntity<DeletedBranchBean> delete(
			@Parameter(description = "Parameter containing the required values to delete a branch.", required = true) @NotNull @Valid @RequestBody BranchDeleteBean deleteBranchBean) {
		logger.debug("Delete branch request initiated");
		try {
			DeletedBranchDTO delete = branchService.delete(modelMapper.map(deleteBranchBean, DeleteBranchDTO.class));
			logger.debug("Delete branch request completed");
			return ResponseEntity.status(HttpStatus.OK).body(modelMapper.map(delete, DeletedBranchBean.class));
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

	@Operation(summary = "Get branches of filter", description = "Fetches a list of all branches of a filter. Uses correlation id and version to identify target filter. If no version is provided, latest is targeted.", tags = {
			"branch" })
	@ApiResponses(value = { @ApiResponse(description = "successful operation", responseCode = "200", content = {
			@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = BranchBean.class))) }) })
	@GetMapping(produces = { "application/json" })
	public ResponseEntity<List<BranchBean>> getBranchesOfFilter(
			@Parameter(description = "UUID value that corresponds to the target filter", required = true) @NotNull @Valid @RequestParam UUID correlationId,
			@RequestParam(required = false) Integer version) {
		logger.debug("Get branches of a filter request initiated");
		try {
			List<BranchDTO> branchesOfFilter = branchService.getBranchesOfFilter(correlationId, version);
			logger.debug("Get branches of a filter request completed");
			return ResponseEntity.status(HttpStatus.OK).body(GenericUtils.mapAll(branchesOfFilter, BranchBean.class));
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

	@Operation(summary = "Merge branch", description = "Merges a branch with its referenced filter.", tags = {
			"branch" })
	@ApiResponses(value = { @ApiResponse(description = "successful operation", responseCode = "200", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = FilterBean.class)) }) })
	@PostMapping(path = "/merge", consumes = { "application/json" }, produces = { "application/json" })
	public ResponseEntity<FilterBean> mergeBranch(
			@Parameter(description = "Parameter containing the required values to merge a branch.", required = true) @NotNull @Valid BranchMergeBean mergeBranchBean) {
		logger.debug("Merge branch request initiated");
		try {
			FilterDTO mergeBranch = branchService.mergeBranch(modelMapper.map(mergeBranchBean, MergeBranchDTO.class));
			logger.debug("Merge branch request completed");
			return ResponseEntity.status(HttpStatus.OK).body(modelMapper.map(mergeBranch, FilterBean.class));
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
