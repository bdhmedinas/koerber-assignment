/**
 * 
 */
package koerber.dto;

import java.util.UUID;

import lombok.Data;

/**
 * Representation of a filter
 * 
 * @author Bruno Medinas
 *
 */
@Data
public class DeleteFilterDTO {

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
