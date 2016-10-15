package iris.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import iris.db.model.Alternativa;
import iris.db.model.Pergunta;

public interface AlternativaMapper {

	@Select("SELECT * FROM alternativa WHERE pergunta = #{id}")
	List<Pergunta> selectByPergunta(@Param("id") int id);

	@Insert("INSERT INTO alternativa (pergunta, nome) VALUES (#{perguntaId}, #{alternativa.nome})")
	@Options(useGeneratedKeys = true, keyProperty = "alternativa.id")
	void insert(@Param("alternativa") Alternativa alternativa, @Param("perguntaId") int perguntaId);

	@Update("UPDATE alternativa SET nome=#{nome} WHERE id = #{id}")
	int update(Alternativa alternativa);

	@Delete("DELETE FROM alternativa WHERE id = #{id}")
	void delete(int id);

}
