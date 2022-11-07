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
@Schema(description = "Representation of the values needed to create a filter")
public class CreateFilterBean {

	@NotNull
	@Valid
	@Schema(description = "User UUID")
	private UUID userId;

	@Valid
	@NotNull
	@Schema(description = "Filter name")
	private String name;

	@Valid
	@NotNull
	@Schema(description = "Filter data")
	private String data;

	@Valid
	@NotNull
	@Schema(description = "Filter output")
	private String outputFilter;

	@Valid
	@NotNull
	@Schema(description = "Screen UUID")
	private UUID screenId;

}
