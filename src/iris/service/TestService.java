package iris.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.json.JSONException;

import com.sun.org.apache.xml.internal.security.utils.Base64;

import iris.db.dao.TestDB;
import iris.db.model.TestResult;
import iris.db.model.Test;

@Path("/test")
public class TestService {

	private static final TestDB testeDB = new TestDB();

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Test getTeste(@PathParam("id") final int id) {
		return testeDB.getTest(id);
	}

	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Test> getTestes() {
		return testeDB.getTests();
	}

	@GET
	@Path("/{testId}/questionImage/{questionId}")
	@Produces("image/jpg")
	public Response getQuestionImage(@PathParam("testId") final int testId,
			@PathParam("questionId") final int questionId) throws FileNotFoundException {
		String imagePath = testeDB.getQuestionImageByTest(testId, questionId);
		if(imagePath != null && !imagePath.equals("")){
			BufferedImage image;
			ByteArrayOutputStream baos = null;
			try {
				image = ImageIO.read(new File(imagePath));
				baos = new ByteArrayOutputStream();
				ImageIO.write(image, "jpg", baos);
			} catch (IOException e) {
				e.printStackTrace();
			}
			byte[] imageData = baos.toByteArray();
			String encode = Base64.encode(imageData);
			return Response.ok(encode).build();
		}
		return Response.ok().build();
	}

	@POST
	@Path("/create")
	@Produces(MediaType.APPLICATION_JSON)
	public Test createTest(Test test) throws IOException {
		return testeDB.insertTest(test);
	}

	@DELETE
	@Path("/{testId}")
	@Produces(MediaType.APPLICATION_JSON)
	public void deleteTest(@PathParam("testId") final int testId) {
		if(testeDB.hasResultsByTest(testId)){
			throw new WebApplicationException(Status.BAD_REQUEST);
		}
		testeDB.deleteTest(testId);
	}

	@POST
	@Path("/saveResult")
	@Produces(MediaType.APPLICATION_JSON)
	public void saveResult(TestResult testResult) throws IOException {
		testeDB.insertTestResult(testResult);
	}

	@GET
	@Path("/{testId}/results")
	@Produces(MediaType.APPLICATION_JSON)
	public List<TestResult> results(@PathParam("testId") final int testeId, //
			@QueryParam("minimumEfficiency") final float minimumEfficiency, //
			@QueryParam("maximumEfficiency") final float maximumEfficiency, //
			@QueryParam("initialDate") final String initialDate, //
			@QueryParam("endDate") final String endDate) throws JSONException, ParseException {
		
		return testeDB.getResults(testeId, minimumEfficiency, maximumEfficiency, initialDate, endDate);
	}
	
	@GET
	@Path("/{testId}/result")
	@Produces(MediaType.APPLICATION_JSON)
	public boolean hasResultToday(@PathParam("testId") final int testeId, //
			@QueryParam("rg") final long rg, @QueryParam("date") final String date) throws JSONException, ParseException {
		
		return testeDB.hasResults(testeId, rg, date);
	}
}
