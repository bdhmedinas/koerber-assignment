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
@Schema(description = "Representation of the values needed to delete a branch")
public class BranchDeleteBean {

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
}
