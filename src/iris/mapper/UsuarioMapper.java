package iris.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import iris.db.model.Usuario;

public interface UsuarioMapper {

	@Select("SELECT * FROM usuario")
	List<Usuario> selectAll();
	
	@Select("SELECT * FROM usuario WHERE id = #{id}")
	Usuario select(int id);
	
	@Select("SELECT * FROM usuario WHERE nome = #{nome} AND senha = #{senha}")
	Usuario login(@Param("nome") String nome, @Param("senha") String senha);

	@Insert("INSERT INTO usuario (nome, senha) VALUES (#{nome}, #{senha})")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	void insert(Usuario usuario);
	
	@Update("UPDATE usuario SET senha=#{senha} WHERE id = #{id}")
	int update(@Param("id") int id, @Param("senha") String senha);
	
	@Delete("DELETE FROM usuario WHERE id = #{id}")
	void delete(@Param("id") int id);
	
	@Select("SELECT * FROM usuario WHERE nome = #{nome}")
	Usuario selectByNome(String nome);
	
}
