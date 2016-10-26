package iris.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import iris.db.model.Student;
import iris.db.model.TestResult;
import iris.db.model.Test;

public interface TestResultMapper {

	@Select("SELECT * FROM testresult WHERE test = #{testId} and rg = #{rg} and date = #{date}")
	@Results(value = {
			@Result(column = "test", property = "testId", javaType = Test.class, one = @One(select = "iris.mapper.TestMapper.select")),
			@Result(column = "rg", property = "rg", javaType = Student.class, one = @One(select = "iris.mapper.StudentMapper.select")) })
	TestResult select(int testId, long rg, Date date);
	
	@Select("SELECT EXISTS(SELECT * FROM testresult WHERE test = #{testId})")
	int existsByTest(@Param("testId") int testId);
	
	@Select("SELECT EXISTS(SELECT * FROM testresult WHERE test = #{testId} and rg = #{rg} and date = #{date})")
	int existsResult(@Param("testId") int testId, @Param("rg") long rg, @Param("date") String date);

	@Select("SELECT DISTINCT test.* FROM test RIGHT JOIN testresult ON test.id = testresult.test WHERE testresult.rg = #{rg}")
	@Results({ @Result(property = "id", column = "id"),
			@Result(property = "questions", column = "id", javaType = List.class, many = @Many(select = "iris.mapper.QuestionMapper.selectByTest")) })
	List<Test> selectStudentTestsDone(@Param("rg") long rg);

	@Select("SELECT * FROM testresult where rg = #{rg} and test = #{test} ")
	@Results(value = {
			@Result(column = "test", property = "test", javaType = Test.class, one = @One(select = "iris.mapper.TestMapper.select")),
			@Result(column = "rg", property = "student", javaType = Student.class, one = @One(select = "iris.mapper.StudentMapper.select")) })
	List<TestResult> selectStudentTestDoneListDates(@Param("rg") long rg, @Param("test") int test);

	@Insert("INSERT INTO testresult (test, rg, date, score, result) VALUES (#{testresult.test.id}, #{testresult.student.rg}, #{testresult.date}, #{testresult.score}, #{testresult.result})")
	void insert(@Param("testresult") TestResult testResult);

	@Select("SELECT * FROM testresult where test = #{testId} and date between #{initialDate} and #{endDate}")
	@Results(value = {
			@Result(column = "test", property = "test", javaType = Test.class, one = @One(select = "iris.mapper.TestMapper.select")),
			@Result(column = "rg", property = "student", javaType = Student.class, one = @One(select = "iris.mapper.StudentMapper.select")) })
	List<TestResult> selectResultsByTest(@Param("testId") int testId, @Param("initialDate") String initialDate, @Param("endDate") String endDate);
}
