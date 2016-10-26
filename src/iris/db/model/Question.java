package iris.db.model;

import java.util.ArrayList;
import java.util.List;

public class Question extends NamedEntity {

	private List<Alternative> alternatives;
	private int correctAlternative;
	private String image;
	
	public List<Alternative> getAlternatives() {
		if(alternatives == null){
			alternatives = new ArrayList<Alternative>();
		}
		return alternatives;
	}

	public void setAlternatives(List<Alternative> alternatives) {
		this.alternatives = alternatives;
	}

	public int getCorrectAlternative() {
		return correctAlternative;
	}

	public void setCorrectAlternative(int correctAlternative) {
		this.correctAlternative = correctAlternative;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

}
