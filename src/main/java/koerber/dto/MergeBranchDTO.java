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
public class MergeBranchDTO {

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
}
