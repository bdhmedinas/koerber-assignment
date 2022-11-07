/**
 * 
 */
package koerber.exceptions;

/**
 * @author Bruno Medinas
 *
 */
public class KoerberAssignmentBadRequestException extends KoerberAssignmentException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6427977779754305146L;

	public KoerberAssignmentBadRequestException() {
		super();
	}

	public KoerberAssignmentBadRequestException(String message) {
		super(message);
	}

	public KoerberAssignmentBadRequestException(Throwable t) {
		super(t);
	}

	public KoerberAssignmentBadRequestException(String message, Throwable t) {
		super(message, t);
	}
}
