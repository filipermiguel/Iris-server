package iris.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import iris.db.model.Test;

public interface TestMapper {

	@Select("SELECT id, name FROM test WHERE id = #{id}")
	@Results({ @Result(property = "id", column = "id"),
			@Result(property = "questions", column = "id", javaType = List.class, many = @Many(select = "iris.mapper.QuestionMapper.selectByTest")) })
	Test select(@Param("id") int id);

	@Select("SELECT * FROM test")
	@Results({ @Result(property = "id", column = "id"),
			@Result(property = "questions", column = "id", javaType = List.class, many = @Many(select = "iris.mapper.QuestionMapper.selectByTest")) })
	List<Test> selectAll();

	@Insert("INSERT INTO test (name) VALUES (#{name})")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	int insert(Test test);

	@Delete("DELETE FROM test WHERE id = #{id}")
	void delete(int id);

}
