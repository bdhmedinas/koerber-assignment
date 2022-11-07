/**
 * 
 */
package koerber.beans;

import java.util.UUID;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author Bruno Medinas
 *
 */
@Data
@Schema(description = "Representation of the values needed to update a branch")
public class BranchUpdateBean {

	@NotNull
	@Valid
	@Schema(description = "Correlation UUID")
	private UUID correlationId;

	@NotNull
	@Valid
	@Schema(description = "User UUID")
	private UUID userId;

	@NotNull
	@Valid
	@Schema(description = "Version")
	private Integer version;

	@Valid
	@NotNull
	@Schema(description = "Branch name")
	private String name;

	@Valid
	@NotNull
	@Schema(description = "Branch data")
	private String data;

	@Valid
	@NotNull
	@Schema(description = "Branch output")
	private String outputFilter;

	@Valid
	@NotNull
	@Schema(description = "Screen UUID")
	private UUID screenId;
}
