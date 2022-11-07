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
public class FilterDTO {

	/**
	 * Filter UUID
	 */
	private UUID filterId;

	/**
	 * User object
	 */
	private UserDTO user;

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
	 * Screen object
	 */
	private ScreenDTO screen;

	/**
	 * Version correlation UUID
	 */
	private UUID versionCorrelation;

	/**
	 * Filter version
	 */
	private Integer version;

	/**
	 * Branch action timestamp
	 */
	private Long timestamp;
}
