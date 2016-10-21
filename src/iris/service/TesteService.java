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

import iris.db.dao.TesteDB;
import iris.db.model.Pergunta;
import iris.db.model.ResultadoTeste;
import iris.db.model.Teste;

@Path("/testes")
public class TesteService {

	private static final TesteDB testeDB = new TesteDB();

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Teste getTeste(@PathParam("id") final int id) {
		return testeDB.getTeste(id);
	}

	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Teste> getTestes() {
		return testeDB.getTestes();
	}

	@GET
	@Path("/{id}/perguntas")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Pergunta> getPerguntas(@PathParam("id") final int id) {
		return testeDB.getPerguntas(id);
	}

	@GET
	@Path("/{id}/primeiraPergunta")
	@Produces(MediaType.APPLICATION_JSON)
	public Pergunta getPrimeiraPergunta(@PathParam("id") final int id) {
		return testeDB.getPerguntas(id).get(0);
	}

	@GET
	@Path("/{id}/primeiraImagem")
	@Produces("image/jpg")
	public Response getPrimeiraImagem(@PathParam("id") final int id) throws FileNotFoundException {
		String imagePath = testeDB.getPerguntas(id).get(0).getImagem();
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

	@GET
	@Path("/{testeId}/imagemPergunta/{perguntaId}")
	@Produces("image/jpg")
	public Response getImagemPergunta(@PathParam("testeId") final int testeId,
			@PathParam("perguntaId") final int perguntaId) throws FileNotFoundException {
		String imagePath = testeDB.getImagemPerguntaByTest(testeId, perguntaId);
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

	@POST
	@Path("/create")
	@Produces(MediaType.APPLICATION_JSON)
	public void criarTeste(Teste teste) throws IOException {
		testeDB.insert(teste);
	}

	@DELETE
	@Path("/{testeId}")
	@Produces(MediaType.APPLICATION_JSON)
	public void removerTeste(@PathParam("testeId") final int testeId) {
		if(testeDB.hasResultsByTest(testeId)){
			throw new WebApplicationException(Status.BAD_REQUEST);
		}
		testeDB.delete(testeId);
	}

	@POST
	@Path("/salvarResultado")
	@Produces(MediaType.APPLICATION_JSON)
	public void salvarResultado(ResultadoTeste resultadoTeste) throws IOException {
		testeDB.insertTestResult(resultadoTeste);
	}

	@GET
	@Path("/{testeId}/resultados")
	@Produces(MediaType.APPLICATION_JSON)
	public List<ResultadoTeste> resultados(@PathParam("testeId") final int testeId, //
			@QueryParam("aproveitamentoMinimo") final float aproveitamentoMinimo, //
			@QueryParam("aproveitamentoMaximo") final float aproveitamentoMaximo, //
			@QueryParam("inicio") final String inicio, //
			@QueryParam("fim") final String fim) throws JSONException, ParseException {
		
		return testeDB.getResults(testeId, aproveitamentoMinimo, aproveitamentoMaximo, inicio, fim);
	}
	
	@GET
	@Path("/{testeId}/resultado")
	@Produces(MediaType.APPLICATION_JSON)
	public boolean hasResultToday(@PathParam("testeId") final int testeId, //
			@QueryParam("rg") final long rg, @QueryParam("data") final String data) throws JSONException, ParseException {
		
		return testeDB.hasResults(testeId, rg, data);
	}
}
