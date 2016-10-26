package iris.service;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import iris.db.dao.UsuarioDB;
import iris.db.model.Usuario;

@Path("/user")
public class UserService {

	private static final UsuarioDB usuarioDB = new UsuarioDB();
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Usuario> getUsuarios() {
		return usuarioDB.getUsuarios();
	}
	
	@POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public Usuario login(Usuario usuario) {
		Usuario login = usuarioDB.login(usuario.getNome(), usuario.getSenha());
		if (login != null) {
			return login;
		}
		
		throw new WebApplicationException(Status.BAD_REQUEST);
	}
	
	@POST
    @Path("/create")
    @Produces(MediaType.APPLICATION_JSON)
    public Usuario criarUsuario(Usuario usuario) {
		try{
			usuarioDB.insert(usuario);
		} catch (Exception e){
			throw new WebApplicationException(Status.BAD_REQUEST);
		}
		return usuarioDB.get(usuario.getId());
	}

	@POST
	@Path("/id/{id}/alterarSenha/{senha}")
	@Produces(MediaType.APPLICATION_JSON)
	public void alterarSenha(@PathParam("id") final int id, @PathParam("senha") final String senha) {
		usuarioDB.alterarSenha(id, senha);
	}
	
	@GET
	@Path("/nome/{nome}")
	@Produces(MediaType.APPLICATION_JSON)
	public Usuario getUsuario(@PathParam("nome") final String nome) {
		Usuario usuario = usuarioDB.getUsuario(nome);
		if (usuario == null) {
			usuario = new Usuario();
			usuario.setNome(nome);
			usuarioDB.insert(usuario);
		}
		return usuario;
	}
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public void removerUsuario(@PathParam("id") final int id) {
		usuarioDB.deleteUsuario(id);
	}
}
