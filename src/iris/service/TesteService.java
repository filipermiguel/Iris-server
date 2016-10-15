package iris.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.activation.MimetypesFileTypeMap;
import javax.imageio.ImageIO;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sun.javafx.iio.ImageStorage.ImageType;
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
	public Response getImagemPergunta(@PathParam("testeId") final int testeId, @PathParam("perguntaId") final int perguntaId) throws FileNotFoundException {
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

	@PUT
	@Path("/update")
	@Produces(MediaType.APPLICATION_JSON)
	public void atualizarTeste(Teste teste) {
		testeDB.update(teste);
	}

	@DELETE
	@Path("/{testeId}")
	@Produces(MediaType.APPLICATION_JSON)
	public void removerTeste(@PathParam("testeId") final int testeId) {
		testeDB.delete(testeId);
	}

	@POST
	@Path("/salvarResultado")
	@Produces(MediaType.APPLICATION_JSON)
	public void salvarResultado(ResultadoTeste resultadoTeste) throws IOException {
		testeDB.insertTestResult(resultadoTeste);
	}
}
