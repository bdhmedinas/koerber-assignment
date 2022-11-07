/**
 * 
 */
package koerber.dto;

import java.util.UUID;

import lombok.Data;

/**
 * Representation of a user
 * 
 * @author Bruno Medinas
 *
 */
@Data
public class UserDTO

/**
 * User UUID
 */
{
	private UUID userId;

	/**
	 * User name
	 */
	private String name;
}
