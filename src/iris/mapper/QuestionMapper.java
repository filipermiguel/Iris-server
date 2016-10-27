package iris.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import iris.db.model.Question;

public interface QuestionMapper {

	@Select("SELECT * FROM question WHERE test = #{id}")
	@Results({ @Result(property = "id", column = "id"),
			@Result(property = "alternatives", column = "id", javaType = List.class, many = @Many(select = "iris.mapper.AlternativeMapper.selectByQuestion")) })
	List<Question> selectByTest(@Param("id") int id);

	@Insert("INSERT INTO question (test, name, correctAlternative, image) VALUES (#{testId}, #{question.name}, #{question.correctAlternative}, #{question.image})")
	@Options(useGeneratedKeys = true, keyProperty = "question.id")
	void insert(@Param("question") Question questin, @Param("testId") int testId);
	
	@Select("SELECT image FROM question WHERE test = #{test} and id = #{id}")
	String selectQuestionImageByTest(@Param("test") int test, @Param("id") int id);

}
