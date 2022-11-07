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
public class CreateBranchDTO {

	/**
	 * User UUID
	 */
	private UUID userId;

	/**
	 * Correlation UUID
	 */
	private UUID correlationId;

	/**
	 * Branch version
	 */
	private Integer version;
}
