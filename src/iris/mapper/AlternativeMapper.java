package iris.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import iris.db.model.Alternative;
import iris.db.model.Question;

public interface AlternativeMapper {

	@Select("SELECT * FROM alternative WHERE question = #{id}")
	List<Question> selectByQuestion(@Param("id") int id);

	@Insert("INSERT INTO alternative (question, name) VALUES (#{questionId}, #{alternative.name})")
	@Options(useGeneratedKeys = true, keyProperty = "alternative.id")
	void insert(@Param("alternative") Alternative alternative, @Param("questionId") int questionId);

	@Update("UPDATE alternative SET name=#{name} WHERE id = #{id}")
	int update(Alternative alternative);

	@Delete("DELETE FROM alternative WHERE id = #{id}")
	void delete(int id);

}
