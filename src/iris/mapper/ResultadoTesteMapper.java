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

import iris.db.model.Aluno;
import iris.db.model.ResultadoTeste;
import iris.db.model.Teste;

public interface ResultadoTesteMapper {

	@Select("SELECT * FROM resultadoteste WHERE teste = #{testeId} and rg = #{rg} and data = #{data}")
	@Results(value = {
			@Result(column = "teste", property = "testeId", javaType = Teste.class, one = @One(select = "iris.mapper.TesteMapper.select")),
			@Result(column = "rg", property = "rg", javaType = Aluno.class, one = @One(select = "iris.mapper.AlunoMapper.select")) })
	ResultadoTeste select(int testeId, long rg, Date data);
	
	@Select("SELECT EXISTS(SELECT * FROM resultadoteste WHERE teste = #{testeId})")
	int existsByTest(@Param("testeId") int testeId);
	
	@Select("SELECT EXISTS(SELECT * FROM resultadoteste WHERE teste = #{testeId} and rg = #{rg} and data = #{data})")
	int existsResult(@Param("testeId") int testeId, @Param("rg") long rg, @Param("data") String data);

	@Select("SELECT DISTINCT teste.* FROM teste RIGHT JOIN resultadoteste ON teste.id = resultadoteste.teste WHERE resultadoteste.rg = #{rg}")
	@Results({ @Result(property = "id", column = "id"),
			@Result(property = "perguntas", column = "id", javaType = List.class, many = @Many(select = "iris.mapper.PerguntaMapper.selectByTeste")) })
	List<Teste> selectStudentTestsDone(@Param("rg") long rg);

	@Select("SELECT * FROM resultadoteste where rg = #{rg} and teste = #{teste} ")
	@Results(value = {
			@Result(column = "teste", property = "teste", javaType = Teste.class, one = @One(select = "iris.mapper.TesteMapper.select")),
			@Result(column = "rg", property = "aluno", javaType = Aluno.class, one = @One(select = "iris.mapper.AlunoMapper.select")) })
	List<ResultadoTeste> selectStudentTestDoneListDates(@Param("rg") long rg, @Param("teste") int teste);

	@Insert("INSERT INTO resultadoteste (teste, rg, data, qtdAcertos, resultado) VALUES (#{resultadoTeste.teste.id}, #{resultadoTeste.aluno.rg}, #{resultadoTeste.data}, #{resultadoTeste.qtdAcertos}, #{resultadoTeste.resultado})")
	void insert(@Param("resultadoTeste") ResultadoTeste resultadoTeste);

	@Select("SELECT * FROM resultadoteste where teste = #{testeId} and data between #{inicio} and #{fim}")
	@Results(value = {
			@Result(column = "teste", property = "teste", javaType = Teste.class, one = @One(select = "iris.mapper.TesteMapper.select")),
			@Result(column = "rg", property = "aluno", javaType = Aluno.class, one = @One(select = "iris.mapper.AlunoMapper.select")) })
	List<ResultadoTeste> selectResultsByTest(@Param("testeId") int testeId, @Param("inicio") String inicio, @Param("fim") String fim);
}
