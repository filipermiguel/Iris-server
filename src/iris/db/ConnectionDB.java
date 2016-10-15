package iris.db;

import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import iris.mapper.AlternativaMapper;
import iris.mapper.AlunoMapper;
import iris.mapper.PerguntaMapper;
import iris.mapper.ResultadoTesteMapper;
import iris.mapper.TesteMapper;
import iris.mapper.UsuarioMapper;

public class ConnectionDB {

	private static SqlSessionFactory sqlMapper = null;
	private static final String configurationsFile = "iris/db/configuration.xml";

	public static SqlSessionFactory getSqlMapper() {
		if (sqlMapper == null) {
			Reader reader = null;
			try {
				reader = Resources.getResourceAsReader(configurationsFile);
				sqlMapper = new SqlSessionFactoryBuilder().build(reader, "desenvolvimento");
				sqlMapper.getConfiguration().addMapper(UsuarioMapper.class);
				sqlMapper.getConfiguration().addMapper(TesteMapper.class);
				sqlMapper.getConfiguration().addMapper(PerguntaMapper.class);
				sqlMapper.getConfiguration().addMapper(AlternativaMapper.class);
				sqlMapper.getConfiguration().addMapper(AlunoMapper.class);
				sqlMapper.getConfiguration().addMapper(ResultadoTesteMapper.class);
			} catch (final Throwable t) {
				t.printStackTrace();
			}
		}

		return sqlMapper;
	}
}