package iris.db.dao;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.json.JSONException;

import iris.db.ConnectionDB;
import iris.db.model.Alternativa;
import iris.db.model.Pergunta;
import iris.db.model.ResultadoTeste;
import iris.db.model.Teste;
import iris.mapper.AlternativaMapper;
import iris.mapper.PerguntaMapper;
import iris.mapper.ResultadoTesteMapper;
import iris.mapper.TesteMapper;
import iris.utils.IrisUtils;

public class TesteDB {

	private final SqlSessionFactory sqlMapper;

	public TesteDB() {
		this.sqlMapper = ConnectionDB.getSqlMapper();
	}

	public Teste getTeste(int id) {
		final SqlSession session = this.sqlMapper.openSession();
		try {
			TesteMapper testeMapper = session.getMapper(TesteMapper.class);
			return testeMapper.select(id);
		} finally {
			session.close();
		}
	}

	public List<Teste> getTestes() {
		final SqlSession session = this.sqlMapper.openSession();
		try {
			TesteMapper testeMapper = session.getMapper(TesteMapper.class);
			return testeMapper.selectAll();
		} finally {
			session.close();
		}
	}

	public List<Pergunta> getPerguntas(int id) {
		final SqlSession session = this.sqlMapper.openSession();
		try {
			PerguntaMapper perguntaMapper = session.getMapper(PerguntaMapper.class);
			return perguntaMapper.selectByTeste(id);
		} finally {
			session.close();
		}
	}

	public void insert(Teste teste) throws IOException {
		final SqlSession session = this.sqlMapper.openSession();
		try {
			TesteMapper testeMapper = session.getMapper(TesteMapper.class);
			testeMapper.insert(teste);

			// Insere as perguntas
			if (teste.getPerguntas() != null) {
				PerguntaMapper perguntaMapper = session.getMapper(PerguntaMapper.class);
				for (Pergunta pergunta : teste.getPerguntas()) {
					if (pergunta.getImagem() != null && !pergunta.getImagem().equals("")) {
						byte[] imagedata = DatatypeConverter.parseBase64Binary(pergunta.getImagem());
						BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imagedata));
						BufferedImage scaledImage = IrisUtils.getScaledImage(bufferedImage, 400, 400);
						File tempDirectory = new File("C:/Temp");
						File file = File.createTempFile("iris-", ".png", tempDirectory);
						ImageIO.write(scaledImage, "png", file);
						pergunta.setImagem(file.getAbsolutePath());
					}

					perguntaMapper.insert(pergunta, teste.getId());

					// Insere as alternativas
					if (pergunta.getAlternativas() != null) {
						AlternativaMapper alternativaMapper = session.getMapper(AlternativaMapper.class);
						for (Alternativa alternativa : pergunta.getAlternativas()) {
							alternativaMapper.insert(alternativa, pergunta.getId());
						}
					}
				}
			}

			session.commit();
		} finally {
			session.close();
		}
	}
	
	public void delete(int id) {
		final SqlSession session = this.sqlMapper.openSession();
		try {
			TesteMapper testeMapper = session.getMapper(TesteMapper.class);
			testeMapper.delete(id);
			session.commit();
		} finally {
			session.close();
		}
	}

	public String getImagemPerguntaByTest(int teste, int id) {
		final SqlSession session = this.sqlMapper.openSession();
		try {
			PerguntaMapper perguntaMapper = session.getMapper(PerguntaMapper.class);
			return perguntaMapper.selectImagemPerguntaByTest(teste, id);
		} finally {
			session.close();
		}
	}

	public void insertTestResult(ResultadoTeste resultadoTeste) {
		final SqlSession session = this.sqlMapper.openSession();
		try {
			ResultadoTesteMapper resultadoTesteMapper = session.getMapper(ResultadoTesteMapper.class);
			resultadoTesteMapper.insert(resultadoTeste);
			session.commit();
		} finally {
			session.close();
		}
	}

	public List<ResultadoTeste> getResults(int testeId, float aproveitamentoMinimo, float aproveitamentoMaximo,
			String dataInicial, String dataFinal) throws JSONException {
		final SqlSession session = this.sqlMapper.openSession();
		try {
			ResultadoTesteMapper resultadoTesteMapper = session.getMapper(ResultadoTesteMapper.class);
			List<ResultadoTeste> select = resultadoTesteMapper.selectResultsByTest(testeId, dataInicial, dataFinal);
			List<ResultadoTeste> listaResultados = new ArrayList<>();

			for (ResultadoTeste resultadoTeste : select) {
				int size = resultadoTeste.getTeste().getPerguntas().size();
				float aproveitamento = (resultadoTeste.getQtdAcertos() / (float) size) * 100;

				if (aproveitamento >= aproveitamentoMinimo && aproveitamento <= aproveitamentoMaximo) {
					listaResultados.add(resultadoTeste);
				}
			}

			return listaResultados;
		} finally {
			session.close();
		}
	}
	
	public boolean hasResultsByTest(int testeId){
		final SqlSession session = this.sqlMapper.openSession();
		try {
			ResultadoTesteMapper resultadoTesteMapper = session.getMapper(ResultadoTesteMapper.class);
			return resultadoTesteMapper.existsByTest(testeId) == 1;
		} finally {
			session.close();
		}
	}
	
	public boolean hasResults(int testeId, long rg, String data){
		final SqlSession session = this.sqlMapper.openSession();
		try {
			ResultadoTesteMapper resultadoTesteMapper = session.getMapper(ResultadoTesteMapper.class);
			return resultadoTesteMapper.existsResult(testeId, rg, data) == 1;
		} finally {
			session.close();
		}
	}
}
