/**
 * 
 */
package koerber.dto;

import java.util.UUID;

import lombok.Data;

/**
 * @author Bruno Medinas
 *
 */
@Data
public class UserDTO {
	
	private UUID id;
	private String name;
}
