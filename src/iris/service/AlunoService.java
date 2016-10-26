package iris.service;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import iris.db.dao.AlunoDB;
import iris.db.model.Aluno;
import iris.db.model.ResultadoTeste;
import iris.db.model.Teste;

@Path("/aluno")
public class AlunoService {

	private static final AlunoDB alunoDB = new AlunoDB();

	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Aluno> getAlunos() {
		return alunoDB.getAlunos();
	}

	@POST
	@Path("/create")
	@Produces(MediaType.APPLICATION_JSON)
	public void criarAluno(Aluno aluno) {
		alunoDB.insert(aluno);
	}

	@GET
	@Path("/rg/{rg}")
	@Produces(MediaType.APPLICATION_JSON)
	public Aluno getAluno(@PathParam("rg") final long rg) {
		return alunoDB.getAluno(rg);
	}
	
	@GET
	@Path("/rg/{rg}/test")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Teste> getStudentTestsDone(@PathParam("rg") final long rg) {
		return alunoDB.getStudentTestsDone(rg);
	}
	
	@GET
	@Path("/rg/{rg}/test/{testeId}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<ResultadoTeste> getStudentTestDoneListDates(@PathParam("rg") final long rg, @PathParam("testeId") final int testeId) {
		return alunoDB.getStudentTestDoneListDates(rg, testeId);
	}
}
