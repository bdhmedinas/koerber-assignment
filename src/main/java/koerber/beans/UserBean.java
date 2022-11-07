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
@Schema(description = "Representation of the values needed to represent a user")
public class UserBean {

	@NotNull
	@Valid
	@Schema(description = "User UUID")
	private UUID userId;

	@Valid
	@NotNull
	@Schema(description = "User name")
	private String name;
}
