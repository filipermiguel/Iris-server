package iris.db.model;

import java.util.ArrayList;
import java.util.List;

public class Test extends NamedEntity {

	private List<Question> questions;

	public List<Question> getQuestions() {
		if (questions == null) {
			questions = new ArrayList<Question>();
		}
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}
}
