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

import iris.db.dao.UserDB;
import iris.db.model.User;

@Path("/user")
public class UserService {

	private static final UserDB usuarioDB = new UserDB();
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getUsers() {
		return usuarioDB.getUsers();
	}
	
	@POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public User login(User user) {
		User login = usuarioDB.login(user.getName(), user.getPassword());
		if (login != null) {
			return login;
		}
		
		throw new WebApplicationException(Status.BAD_REQUEST);
	}
	
	@POST
    @Path("/create")
    @Produces(MediaType.APPLICATION_JSON)
    public User createUser(User user) {
		try{
			usuarioDB.insert(user);
		} catch (Exception e){
			throw new WebApplicationException(Status.BAD_REQUEST);
		}
		return usuarioDB.getUser(user.getId());
	}

	@POST
	@Path("/id/{id}/changePassword/{password}")
	@Produces(MediaType.APPLICATION_JSON)
	public void changePassword(@PathParam("id") final int id, @PathParam("password") final String password) {
		usuarioDB.changePassword(id, password);
	}
	
	@GET
	@Path("/name/{nome}")
	@Produces(MediaType.APPLICATION_JSON)
	public User getUser(@PathParam("name") final String name) {
		User user = usuarioDB.getUser(name);
		if (user == null) {
			user = new User();
			user.setName(name);
			usuarioDB.insert(user);
		}
		return user;
	}
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public void deleteUser(@PathParam("id") final int id) {
		usuarioDB.deleteUser(id);
	}
}
