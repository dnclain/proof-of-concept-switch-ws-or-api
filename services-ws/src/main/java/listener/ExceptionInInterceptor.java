package listener;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.Phase;

/**
 * {@link ExceptionInInterceptor} gets some information about the current exception.
 * It does not modify anything. it should be insert as OutFaultInterceptor on server side.
 */
public class ExceptionInInterceptor extends AbstractSoapInterceptor {

	public ExceptionInInterceptor() {
		super(Phase.PRE_LOGICAL);
	}

	public void handleMessage(SoapMessage message) throws Fault {
		System.out.println("<--------------Intercept Client side--------------->");
		System.out.println(message.toString()); 
		Throwable fault = (Throwable) message.getContent(Exception.class);
		if (fault != null) {
			fault.printStackTrace();
		}
		System.out.println("<--------------Intercept Client side--------------->");
	}
}

