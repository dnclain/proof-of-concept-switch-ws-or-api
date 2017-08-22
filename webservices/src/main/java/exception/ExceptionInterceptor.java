package exception;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.Phase;

/**
 * ExceptionInterceptor get some information about the current exception.
 * It does not modify anything. it should be insert as OutFaultInterceptor in server side.
 */
public class ExceptionInterceptor extends AbstractSoapInterceptor {

	public ExceptionInterceptor() {
		super(Phase.PRE_LOGICAL);
	}

	public void handleMessage(SoapMessage message) throws Fault {
		System.out.println("<--------------Intercept--------------->");
		System.out.println(message.toString()); 
		Fault fault = (Fault) message.getContent(Exception.class);
		if (fault != null) {
			fault.getCause().printStackTrace();
		}
		System.out.println("<--------------Intercept--------------->");
	}
}
