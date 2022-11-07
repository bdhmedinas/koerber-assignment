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
@Schema(description = "Representation of a deleted branch")
public class DeletedBranchBean {
	
	@Schema(description = "Filter UUID")
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

	@Schema(description = "User action object")
	private UserBean actionUser;

	@Schema(description = "Delete action timestamp")
	private Long deleteTimestamp;
	
	@Schema(description = "Filter UUID")
	private UUID branchOfId;
}
