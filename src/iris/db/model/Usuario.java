package iris.db.model;

public class Usuario extends NamedEntity {

	private String senha;
	
	public String getSenha() {
		return senha;
	}
	
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
}
