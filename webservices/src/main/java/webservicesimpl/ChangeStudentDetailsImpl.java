package webservicesimpl;

import javax.jws.WebService;

import exception.ServiceException;
import iservice.ChangeStudentDetails;
import vo.Student;

@WebService(endpointInterface = "iservice.ChangeStudentDetails", targetNamespace="http://dcn.ovh/pocwservices")
@org.apache.cxf.interceptor.OutFaultInterceptors (interceptors = {"exception.ExceptionInterceptor" })
public class ChangeStudentDetailsImpl implements ChangeStudentDetails {
	
	static ChangeStudentDetails api = new servicesimpl.ChangeStudentDetailsImpl();
	
	@Override
	public Student changeName(Student student) throws ServiceException {
		Student result = api.changeName(student);
		result.setName("WS-"+result.getName());
		return result;
	}

	@Override
	public Student changeNameWithException(Student student) throws ServiceException, RuntimeException {
		Student result = api.changeNameWithException(student);
		result.setName("WS-"+result.getName());
		return result;
	}
}
