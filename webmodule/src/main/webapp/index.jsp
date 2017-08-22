<%@page import="java.io.PrintWriter"%>
<%@page import="java.io.ByteArrayInputStream"%>
<%@page import="java.io.StringWriter"%>
<%@page import="java.io.StringReader"%>
<%@page import="java.io.StringBufferInputStream"%>
<%@page import="vo.Student"%>
<%@page import="coordination.Coordination"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Appel WS/API</title>
</head>
<body>
	<%
		Student student = new Student();
		student.setName("JSP");
		long start = System.nanoTime();
		student = Coordination.students.changeName(student);
		out.print(student.getName());
		out.print("<br />");
		long time = System.nanoTime() - start;
		out.print(time/1000000.0 + " ms");
		
		try {
			Coordination.students.changeNameWithException(student);
			out.print("<br /> NO EXCEPTION");
		} catch (Throwable t) {
			out.print("<br />" + t.getClass().getCanonicalName());
			out.print("<pre>");
			Throwable st = t;
			do {
				out.println(st.getClass().getName());
				for (StackTraceElement ste : st.getStackTrace()) {
					out.println(ste.toString());
				}
				out.println("--------------");
			} while ((st = st.getCause()) != null);
			
			// StringWriter buf = new StringWriter();
			// t.printStackTrace(new PrintWriter(buf));
			//out.print(buf.toString());
			//buf.close();
			out.print("</pre>");
		}
	%>
</body>
</html>