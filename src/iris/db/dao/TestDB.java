package iris.db.dao;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.ws.rs.core.Response;
import javax.xml.bind.DatatypeConverter;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.json.JSONException;

import com.sun.org.apache.xml.internal.security.utils.Base64;

import iris.db.ConnectionDB;
import iris.db.model.Alternative;
import iris.db.model.Question;
import iris.db.model.TestResult;
import iris.db.model.Test;
import iris.mapper.AlternativeMapper;
import iris.mapper.QuestionMapper;
import iris.mapper.TestResultMapper;
import iris.mapper.TestMapper;
import iris.utils.IrisUtils;

public class TestDB {

	private final SqlSessionFactory sqlMapper;

	public TestDB() {
		this.sqlMapper = ConnectionDB.getSqlMapper();
	}

	public Test getTest(int id) {
		final SqlSession session = this.sqlMapper.openSession();
		try {
			TestMapper testMapper = session.getMapper(TestMapper.class);
			Test test = testMapper.select(id);

			for (Question question : test.getQuestions()) {
//				if (question.getImage() != null) {
//					BufferedImage image;
//					ByteArrayOutputStream baos = null;
//					try {
//						image = ImageIO.read(new File(question.getImage()));
//						baos = new ByteArrayOutputStream();
//						ImageIO.write(image, "jpg", baos);
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
//					question.setImage(Base64.encode(baos.toByteArray()));
//				}
				question.setImage(null);
			}
			return test;
		} finally {
			session.close();
		}
	}

	public List<Test> getTests() {
		final SqlSession session = this.sqlMapper.openSession();
		try {
			TestMapper testMapper = session.getMapper(TestMapper.class);
			return testMapper.selectAll();
		} finally {
			session.close();
		}
	}

	public List<Question> getQuestions(int id) {
		final SqlSession session = this.sqlMapper.openSession();
		try {
			QuestionMapper questionMapper = session.getMapper(QuestionMapper.class);
			return questionMapper.selectByTest(id);
		} finally {
			session.close();
		}
	}

	public Test insertTest(Test test) throws IOException {
		final SqlSession session = this.sqlMapper.openSession();
		try {
			TestMapper testMapper = session.getMapper(TestMapper.class);
			testMapper.insert(test);

			if (test.getQuestions() != null) {
				QuestionMapper questionMapper = session.getMapper(QuestionMapper.class);
				for (Question question : test.getQuestions()) {

					if (question.getImage() != null && !question.getImage().equals("")) {
						byte[] imagedata = DatatypeConverter.parseBase64Binary(question.getImage());
						BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imagedata));
						BufferedImage scaledImage = IrisUtils.getScaledImage(bufferedImage, 400, 400);
						File tempDirectory = new File("C:/Temp");
						File file = File.createTempFile("iris-", ".png", tempDirectory);
						ImageIO.write(scaledImage, "png", file);
						question.setImage(file.getAbsolutePath());
					}

					questionMapper.insert(question, test.getId());

					if (question.getAlternatives() != null) {
						AlternativeMapper alternativeMapper = session.getMapper(AlternativeMapper.class);
						for (Alternative alternative : question.getAlternatives()) {
							alternativeMapper.insert(alternative, question.getId());
						}
					}
				}
			}

			session.commit();
			return test;
		} finally {
			session.close();
		}
	}

	public void deleteTest(int id) {
		final SqlSession session = this.sqlMapper.openSession();
		try {
			TestMapper testeMapper = session.getMapper(TestMapper.class);
			testeMapper.delete(id);
			session.commit();
		} finally {
			session.close();
		}
	}

	public String getQuestionImageByTest(int test, int id) {
		final SqlSession session = this.sqlMapper.openSession();
		try {
			QuestionMapper questionMapper = session.getMapper(QuestionMapper.class);
			return questionMapper.selectQuestionImageByTest(test, id);
		} finally {
			session.close();
		}
	}

	public void insertTestResult(TestResult testResult) {
		final SqlSession session = this.sqlMapper.openSession();
		try {
			TestResultMapper resultadoTesteMapper = session.getMapper(TestResultMapper.class);
			resultadoTesteMapper.insert(testResult);
			session.commit();
		} finally {
			session.close();
		}
	}

	public List<TestResult> getResults(int testId, float minimumEfficiency, float maximumEfficiency, String initialDate,
			String endDate) throws JSONException {
		final SqlSession session = this.sqlMapper.openSession();
		try {
			TestResultMapper testResultMapper = session.getMapper(TestResultMapper.class);
			List<TestResult> selectResultsByTest = testResultMapper.selectResultsByTest(testId, initialDate, endDate);
			List<TestResult> resultList = new ArrayList<>();

			for (TestResult testResult : selectResultsByTest) {
				int size = testResult.getTest().getQuestions().size();
				float aproveitamento = (testResult.getScore() / (float) size) * 100;

				if (aproveitamento >= minimumEfficiency && aproveitamento <= maximumEfficiency) {
					resultList.add(testResult);
				}
			}

			return resultList;
		} finally {
			session.close();
		}
	}

	public boolean hasResultsByTest(int testId) {
		final SqlSession session = this.sqlMapper.openSession();
		try {
			TestResultMapper testResultMapper = session.getMapper(TestResultMapper.class);
			return testResultMapper.existsByTest(testId) == 1;
		} finally {
			session.close();
		}
	}

	public boolean hasResults(int testId, long rg, String date) {
		final SqlSession session = this.sqlMapper.openSession();
		try {
			TestResultMapper testResultMapper = session.getMapper(TestResultMapper.class);
			return testResultMapper.existsResult(testId, rg, date) == 1;
		} finally {
			session.close();
		}
	}
}
