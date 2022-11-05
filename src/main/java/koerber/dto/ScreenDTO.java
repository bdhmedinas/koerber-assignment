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
public class ScreenDTO {

	private UUID id;
	private String name;
	private String contentJson;
}
