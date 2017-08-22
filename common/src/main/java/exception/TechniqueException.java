package exception;

public class TechniqueException extends RuntimeException {
	/**
	 * serial
	 */
	private static final long serialVersionUID = 1L;

	public TechniqueException(String message) {
		super(message);
	}
	
	public TechniqueException(Throwable cause) {
		super(cause);
	}	
}
