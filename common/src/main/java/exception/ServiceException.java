package exception;

import javax.xml.ws.WebFault;

@WebFault(name = "ServiceException")    
public class ServiceException extends Exception {
    /**
	 * serial.
	 */
	private static final long serialVersionUID = 1L;

	public ServiceException(Throwable cause) {
        super(cause.getMessage(), cause);
    }
    
    public ServiceException(String message) {
    	super (message);
    }
}
