package iservice;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;

import exception.ServiceException;
import exception.TechniqueException;
import vo.Student;

@WebService
@SOAPBinding(style=Style.RPC, use=Use.LITERAL)
public interface ChangeStudentDetails {
  Student changeName(Student student) throws ServiceException, TechniqueException;
  Student changeNameWithException(Student student) throws ServiceException, TechniqueException;
}
