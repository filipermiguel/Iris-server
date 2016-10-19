package iris.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import iris.db.model.Aluno;

public interface AlunoMapper {

	@Select("SELECT * FROM aluno order by nome")
	List<Aluno> selectAll();

	@Select("SELECT * FROM aluno WHERE rg = #{rg} order by nome")
	Aluno select(long rg);

	@Insert("INSERT INTO aluno (rg, nome, dataNascimento) VALUES (#{rg}, #{nome}, #{dataNascimento})")
	@Options(keyProperty = "aluno.rg")
	void insert(Aluno aluno);

	@Update("UPDATE aluno SET nome=#{nome}, dataNascimento=#{dataNascimento} WHERE rg = #{rg}")
	int update(Aluno aluno);

	@Select("SELECT * FROM aluno WHERE nome = #{nome} order by nome")
	Aluno selectByNome(String nome);
}
