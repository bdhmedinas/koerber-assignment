/**
 * 
 */
package koerber.exceptions;

/**
 * @author Bruno Medinas
 *
 */
public abstract class KoerberAssignmentException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7461957106958295377L;

	public KoerberAssignmentException() {
		super();
	}

	public KoerberAssignmentException(String message) {
		super(message);
	}

	public KoerberAssignmentException(Throwable t) {
		super(t);
	}

	public KoerberAssignmentException(String message, Throwable t) {
		super(message, t);
	}
}
