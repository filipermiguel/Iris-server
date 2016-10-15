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

public interface PerguntaMapper {

	@Select("SELECT * FROM pergunta WHERE teste = #{id}")
	@Results({ @Result(property = "id", column = "id"),
			@Result(property = "alternativas", column = "id", javaType = List.class, many = @Many(select = "iris.mapper.AlternativaMapper.selectByPergunta")) })
	List<Pergunta> selectByTeste(@Param("id") int id);

	@Insert("INSERT INTO pergunta (teste, nome, alternativaCorreta, imagem) VALUES (#{testeId}, #{pergunta.nome}, #{pergunta.alternativaCorreta}, #{pergunta.imagem})")
	@Options(useGeneratedKeys = true, keyProperty = "pergunta.id")
	void insert(@Param("pergunta") Pergunta pergunta, @Param("testeId") int testeId);

	@Update("UPDATE pergunta SET nome=#{nome} WHERE id = #{id}")
	int update(Pergunta pergunta);

	@Delete("DELETE FROM pergunta WHERE id = #{id}")
	void delete(int id);
	
	@Select("SELECT imagem FROM pergunta WHERE teste = #{teste} and id = #{id}")
	String selectImagemPerguntaByTest(@Param("teste") int teste, @Param("id") int id);

}
