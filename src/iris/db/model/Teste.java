package iris.db.model;

import java.util.ArrayList;
import java.util.List;

public class Teste extends NamedEntity {

	private List<Pergunta> perguntas;

	public List<Pergunta> getPerguntas() {
		if (perguntas == null) {
			perguntas = new ArrayList<Pergunta>();
		}
		return perguntas;
	}

	public void setPerguntas(List<Pergunta> perguntas) {
		this.perguntas = perguntas;
	}
}
