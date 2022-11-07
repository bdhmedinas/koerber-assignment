/**
 * 
 */
package koerber.dto;

import java.util.UUID;

import lombok.Data;

/**
 * Representation of a screen
 * 
 * @author Bruno Medinas
 *
 */
@Data
public class ScreenDTO {

	/**
	 * Screen UUID
	 */
	private UUID screenId;

	/**
	 * Screen name
	 */
	private String name;

	/**
	 * Screen content json
	 */
	private String contentJson;
}
