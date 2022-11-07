/**
 * 
 */
package koerber.beans;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author Bruno Medinas
 *
 */
@Data
@Schema(description = "Representation of a filter")
public class FilterBean {

	@Schema(description = "Filter UUID")
	private UUID filterId;

	@Schema(description = "User object")
	private UserBean user;

	@Schema(description = "Filter name")
	private String name;

	@Schema(description = "Filter data")
	private String data;

	@Schema(description = "Filter output")
	private String outputFilter;

	@Schema(description = "Screen object")
	private ScreenBean screen;

	@Schema(description = "Version correlation UUID")
	private UUID versionCorrelation;

	@Schema(description = "Filter version")
	private Integer version;

	@Schema(description = "Filter action timestamp")
	private Long timestamp;
}
