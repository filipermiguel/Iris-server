package iris.service;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import iris.db.dao.StudentDB;
import iris.db.model.Student;
import iris.db.model.TestResult;
import iris.db.model.Test;

@Path("/student")
public class StudentService {

	private static final StudentDB studentDB = new StudentDB();

	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Student> getStudents() {
		return studentDB.getStudents();
	}

	@POST
	@Path("/create")
	@Produces(MediaType.APPLICATION_JSON)
	public void createStudent(Student student) {
		if(studentDB.getStudent(student.getRg()) != null){
			throw new WebApplicationException(Status.BAD_REQUEST);
		}
		studentDB.insertStudent(student);
	}

	@GET
	@Path("/rg/{rg}")
	@Produces(MediaType.APPLICATION_JSON)
	public Student getStudent(@PathParam("rg") final long rg) {
		return studentDB.getStudent(rg);
	}
	
	@GET
	@Path("/rg/{rg}/test")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Test> getStudentTestsDone(@PathParam("rg") final long rg) {
		return studentDB.getStudentTestsDone(rg);
	}
	
	@GET
	@Path("/rg/{rg}/test/{testId}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<TestResult> getStudentTestDoneListDates(@PathParam("rg") final long rg, @PathParam("testId") final int testId) {
		return studentDB.getStudentTestDoneListDates(rg, testId);
	}
}
