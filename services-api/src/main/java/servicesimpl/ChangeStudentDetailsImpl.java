package servicesimpl;

import exception.ServiceException;
import exception.TechniqueException;
import iservice.ChangeStudentDetails;
import vo.Student;

public class ChangeStudentDetailsImpl implements ChangeStudentDetails {

	@Override
	public Student changeName(Student student) throws ServiceException {
		student.setName("API");
		return student;
	}
	
	@Override
	public Student changeNameWithException(Student student) throws ServiceException {
		throw new TechniqueException("Argh");
		//throw new ServiceException("Argh");
	}

}
