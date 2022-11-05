/**
 * 
 */
package koerber.beans;

import koerber.dto.ScreenDTO;
import koerber.dto.UserDTO;
import lombok.Data;

/**
 * @author Bruno Medinas
 *
 */
@Data
public class MyFilterBean {

	private UserDTO userId;
	private String name;
	private String data;
	private String outputFilter;
	private ScreenDTO screenId;
}
