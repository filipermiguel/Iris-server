package iris.db.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import iris.db.ConnectionDB;
import iris.db.model.Student;
import iris.db.model.TestResult;
import iris.db.model.Test;
import iris.mapper.StudentMapper;
import iris.mapper.TestResultMapper;

public class StudentDB {

	private final SqlSessionFactory sqlMapper;

	public StudentDB() {
		this.sqlMapper = ConnectionDB.getSqlMapper();
	}
	
	public List<Student> getStudents() {
		final SqlSession session = this.sqlMapper.openSession();
		try {
			StudentMapper studentMapper = session.getMapper(StudentMapper.class);
			return studentMapper.selectAll();
		} finally {
			session.close();
		}
	}
	
	public void insertStudent(Student student) {
		final SqlSession session = this.sqlMapper.openSession();
		try {
			StudentMapper alunoMapper = session.getMapper(StudentMapper.class);
			alunoMapper.insert(student);
			session.commit();
		} finally {
			session.close();
		}
	}

	public Student getStudent(String name) {
		final SqlSession session = this.sqlMapper.openSession();
		try {
			StudentMapper studentMapper = session.getMapper(StudentMapper.class);
			return studentMapper.selectByName(name);
		} finally {
			session.close();
		}
	}
	
	public Student getStudent(long rg) {
		final SqlSession session = this.sqlMapper.openSession();
		try {
			StudentMapper studentMapper = session.getMapper(StudentMapper.class);
			return studentMapper.select(rg);
		} finally {
			session.close();
		}
	}

	public List<Test> getStudentTestsDone(long rg){
		final SqlSession session = this.sqlMapper.openSession();
		try {
			TestResultMapper testResultMapper = session.getMapper(TestResultMapper.class);
			return testResultMapper.selectStudentTestsDone(rg);
		} finally {
			session.close();
		}
	}
	
	public List<TestResult> getStudentTestDoneListDates(long rg, int testId){
		final SqlSession session = this.sqlMapper.openSession();
		try {
			TestResultMapper testResultMapper = session.getMapper(TestResultMapper.class);
			return testResultMapper.selectStudentTestDoneListDates(rg, testId);
		} finally {
			session.close();
		}
	}
}


