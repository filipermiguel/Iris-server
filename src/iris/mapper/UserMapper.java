package iris.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import iris.db.model.User;

public interface UserMapper {

	@Select("SELECT * FROM user")
	List<User> selectAll();
	
	@Select("SELECT * FROM user WHERE id = #{id}")
	User select(int id);
	
	@Select("SELECT * FROM user WHERE name = #{name} AND password = #{password}")
	User login(@Param("name") String name, @Param("password") String password);

	@Insert("INSERT INTO user (name, password) VALUES (#{name}, #{password})")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	void insert(User usuario);
	
	@Update("UPDATE user SET password=#{password} WHERE id = #{id}")
	int update(@Param("id") int id, @Param("password") String password);
	
	@Delete("DELETE FROM user WHERE id = #{id}")
	void delete(@Param("id") int id);
	
	@Select("SELECT * FROM user WHERE name = #{name}")
	User selectByName(String name);
	
}
