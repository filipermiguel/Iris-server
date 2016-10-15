package iris.db.model;

import java.util.ArrayList;
import java.util.List;

public class Pergunta extends NamedEntity {

	private List<Alternativa> alternativas;
	private int alternativaCorreta;
	private String imagem;
	
	public List<Alternativa> getAlternativas() {
		if(alternativas == null){
			alternativas = new ArrayList<Alternativa>();
		}
		return alternativas;
	}

	public void setAlternativas(List<Alternativa> alternativas) {
		this.alternativas = alternativas;
	}

	public int getAlternativaCorreta() {
		return alternativaCorreta;
	}

	public void setAlternativaCorreta(int alternativaCorreta) {
		this.alternativaCorreta = alternativaCorreta;
	}

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}

}
