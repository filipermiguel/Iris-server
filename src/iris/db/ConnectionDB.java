package iris.db;

import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import iris.mapper.AlternativeMapper;
import iris.mapper.StudentMapper;
import iris.mapper.QuestionMapper;
import iris.mapper.TestResultMapper;
import iris.mapper.TestMapper;
import iris.mapper.UserMapper;

public class ConnectionDB {

	private static SqlSessionFactory sqlMapper = null;
	private static final String configurationsFile = "iris/db/configuration.xml";

	public static SqlSessionFactory getSqlMapper() {
		if (sqlMapper == null) {
			Reader reader = null;
			try {
				reader = Resources.getResourceAsReader(configurationsFile);
				sqlMapper = new SqlSessionFactoryBuilder().build(reader, "desenvolvimento");
				sqlMapper.getConfiguration().addMapper(UserMapper.class);
				sqlMapper.getConfiguration().addMapper(TestMapper.class);
				sqlMapper.getConfiguration().addMapper(QuestionMapper.class);
				sqlMapper.getConfiguration().addMapper(AlternativeMapper.class);
				sqlMapper.getConfiguration().addMapper(StudentMapper.class);
				sqlMapper.getConfiguration().addMapper(TestResultMapper.class);
			} catch (final Throwable t) {
				t.printStackTrace();
			}
		}

		return sqlMapper;
	}
}