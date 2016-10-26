package iris.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import iris.db.model.Student;

public interface StudentMapper {

	@Select("SELECT * FROM student order by name")
	List<Student> selectAll();

	@Select("SELECT * FROM student WHERE rg = #{rg} order by name")
	Student select(long rg);

	@Insert("INSERT INTO student (rg, name, birthDate) VALUES (#{rg}, #{name}, #{birthDate})")
	@Options(keyProperty = "student.rg")
	void insert(Student student);

	@Select("SELECT * FROM student WHERE name = #{name} order by name")
	Student selectByName(String name);
}
