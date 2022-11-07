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
public class CreateFilterDTO {

	/**
	 * User UUID
	 */
	private UUID userId;

	/**
	 * Filter name
	 */
	private String name;

	/**
	 * Filter data
	 */
	private String data;

	/**
	 * Filter output
	 */
	private String outputFilter;

	/**
	 * Screen UUID
	 */
	private UUID screenId;
}
