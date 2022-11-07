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
@Schema(description = "Representation of the values needed to represent a screen")
public class ScreenBean {

	@Valid
	@NotNull
	@Schema(description = "Screen UUID")
	private UUID screenId;

	@Valid
	@NotNull
	@Schema(description = "Screen name")
	private String name;

	@Valid
	@NotNull
	@Schema(description = "Screen content json")
	private String contentJson;
}
