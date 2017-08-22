package listener;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import iservice.ChangeStudentDetails;
import servicesimpl.ChangeStudentDetailsImpl;

@WebListener
public class StartClientListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		System.out.println(">>>Initialisation du client CXF");

		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();

		factory.setServiceClass(ChangeStudentDetails.class);
		factory.setAddress("http://localhost:8080/webservices/services/ChangeStudent?wsdl");
		factory.getInInterceptors().add(new LoggingInInterceptor());
		factory.getOutInterceptors().add(new LoggingOutInterceptor());
		factory.getInFaultInterceptors().add(new ExceptionInInterceptor());
		
		Map<String,Object> props = new HashMap();
		// Boolean.TRUE or "true" will work as the property value below
		props.put("mtom-enabled", Boolean.TRUE);
		factory.setProperties(props);
		
		ChangeStudentDetailsImpl.setInternal((ChangeStudentDetails) factory.create());
		System.out.println("<<<Initialisation du client CXF Terminé");
	}

}
