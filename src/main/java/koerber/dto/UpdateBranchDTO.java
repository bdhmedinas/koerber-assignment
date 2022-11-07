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
public class UpdateBranchDTO {

	/**
	 * Correlation UUID
	 */
	private UUID correlationId;

	/**
	 * User UUID
	 */
	private UUID userId;

	/**
	 * Branch version
	 */
	private Integer version;

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
	 * Screen UUID
	 */
	private UUID screenId;
}
