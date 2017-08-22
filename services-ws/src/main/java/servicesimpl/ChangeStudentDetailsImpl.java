package servicesimpl;

import org.apache.cxf.binding.soap.SoapFault;

import exception.ServiceException;
import exception.TechniqueException;
import iservice.ChangeStudentDetails;
import vo.Student;

public class ChangeStudentDetailsImpl implements ChangeStudentDetails {
	// cxf client
	private static ChangeStudentDetails internal;	
	
	public static void setInternal(ChangeStudentDetails internal) {
		ChangeStudentDetailsImpl.internal = internal;
	}

	@Override
	public Student changeName(Student student) throws ServiceException {
		Student result = null;
		try {
			result = this.internal.changeName(student);
		} catch (SoapFault s) {
			if (s instanceof SoapFault) {
				throw new TechniqueException(s.getCause());
			}
		} catch (ServiceException s) {
			throw s;
		}
		return result;
	}
	
	@Override
	public Student changeNameWithException(Student student) throws ServiceException {
		Student result = null;
		try {
			result = this.internal.changeNameWithException(student);
		} catch (SoapFault s) {
			if (s instanceof SoapFault) {
				throw new TechniqueException(s.getCause());
			}
		} catch (ServiceException s) {
			throw s;
		}
		return result;		
	}	

}
