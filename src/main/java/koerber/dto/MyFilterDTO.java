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
public class MyFilterDTO {
	
	private UUID id;
	private UserDTO userId;
	private String name;
	private String data;
	private String outputFilter;
	private ScreenDTO screenId;
	private Boolean deprecated;
}
