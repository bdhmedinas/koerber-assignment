/**
 * 
 */
package koerber.exceptions;

/**
 * @author Bruno Medinas
 *
 */
public class KoerberAssignmentNotFoundException extends KoerberAssignmentException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8540375226664654047L;

	public KoerberAssignmentNotFoundException() {
		super();
	}

	public KoerberAssignmentNotFoundException(String message) {
		super(message);
	}

	public KoerberAssignmentNotFoundException(Throwable t) {
		super(t);
	}

	public KoerberAssignmentNotFoundException(String message, Throwable t) {
		super(message, t);
	}
}
