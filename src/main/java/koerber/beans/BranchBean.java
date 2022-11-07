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
@Schema(description = "Representation of a branch")
public class BranchBean {

	@Schema(description = "Branch UUID")
	private UUID filterId;

	@Schema(description = "User object")
	private UserBean user;

	@Schema(description = "Branch name")
	private String name;

	@Schema(description = "Branch data")
	private String data;

	@Schema(description = "Branch output")
	private String outputFilter;

	@Schema(description = "Screen object")
	private ScreenBean screen;

	@Schema(description = "Version correlation UUID")
	private UUID versionCorrelation;

	@Schema(description = "Branch version")
	private Integer version;

	@Schema(description = "Branch action timestamp")
	private Long timestamp;

	@Schema(description = "Branch UUID")
	private UUID branchOfId;
}
