package servlet;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;

import org.apache.cxf.jaxws.JaxWsServerFactoryBean;
import org.apache.cxf.transport.servlet.CXFNonSpringServlet;

import webservicesimpl.ChangeStudentDetailsImpl;

/**
 * {@link CXFServlet} is the entrypoint of webservices.
 * <br />
 * It initializes the jaxb server.
 * <br />
 * To deactivate, please comment this entry in web.xml :
 * <pre>
 *   <servlet>
 *    <servlet-name>CXFServlet</servlet-name>
 *    <servlet-class>
 *        servlet.CXFServlet
 *    </servlet-class>
 *    <load-on-startup>1</load-on-startup>
 *  </servlet>
 * 
 *  <servlet-mapping>
 *    <servlet-name>CXFServlet</servlet-name>
 *    <url-pattern>/services/*</url-pattern>
 *  </servlet-mapping>  
 * </pre>
 * @author dclain
 */
public class CXFServlet extends CXFNonSpringServlet {
	/**
	 * serial.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void loadBus(ServletConfig servletConfig) {
		super.loadBus(servletConfig);

		System.out.println(">>> Initilisation du front-end jax-ws");
		JaxWsServerFactoryBean factory = new JaxWsServerFactoryBean();
		Map<String, Object> properties = new HashMap<>();
		factory.setProperties(properties);
		factory.getProperties().put("faultStackTraceEnabled", Boolean.TRUE);
		factory.getProperties().put("exceptionMessageCauseEnabled", Boolean.TRUE);
		factory.setBus(bus);
		factory.setServiceClass(ChangeStudentDetailsImpl.class);
		factory.setAddress("/ChangeStudent");
		factory.create();
		
		System.out.println("<<< Initialisation du front-end terminé");
	}
}
