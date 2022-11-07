/**
 * 
 */
package koerber.dto;

import java.util.UUID;

import lombok.Data;

/**
 * Representation of a branch
 * 
 * @author Bruno Medinas
 *
 */
@Data
public class DeletedBranchDTO {

	/**
	 * Filter UUID
	 */
	private UUID filterId;

	/**
	 * User object
	 */
	private UserDTO user;

	/**
	 * Branch name
	 */
	private String name;

	/**
	 * Branch data
	 */
	private String data;

	/**
	 * Branch output
	 */
	private String outputFilter;

	/**
	 * Screen object
	 */
	private ScreenDTO screen;

	/**
	 * Version correlation UUID
	 */
	private UUID versionCorrelation;

	/**
	 * Branch version
	 */
	private Integer version;

	/**
	 * Branch action timestamp
	 */
	private Long timestamp;

	/**
	 * User action object
	 */
	private UserDTO actionUser;

	/**
	 * Delete action timestamp
	 */
	private Long deleteTimestamp;

	/**
	 * Filter UUID
	 */
	private UUID branchOfId;
}
