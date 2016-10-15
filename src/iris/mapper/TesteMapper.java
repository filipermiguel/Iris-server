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
import org.apache.ibatis.annotations.Update;

import iris.db.model.Pergunta;
import iris.db.model.Teste;

public interface TesteMapper {

	@Select("SELECT id, nome FROM teste WHERE id = #{id}")
	@Results({ @Result(property = "id", column = "id"),
			@Result(property = "perguntas", column = "id", javaType = List.class, many = @Many(select = "iris.mapper.PerguntaMapper.selectByTeste")) })
	Teste select(@Param("id") int id);

	@Select("SELECT * FROM teste")
	List<Teste> selectAll();

	@Insert("INSERT INTO teste (nome) VALUES (#{nome})")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	void insert(Teste teste);

	@Update("UPDATE teste SET nome=#{nome} WHERE id = #{id}")
	int update(Teste teste);

	@Delete("DELETE FROM teste WHERE id = #{id}")
	void delete(int id);

}
