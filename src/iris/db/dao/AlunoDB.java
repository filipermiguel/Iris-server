package iris.db.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import iris.db.ConnectionDB;
import iris.db.model.Aluno;
import iris.db.model.ResultadoTeste;
import iris.db.model.Teste;
import iris.mapper.AlunoMapper;
import iris.mapper.ResultadoTesteMapper;

public class AlunoDB {

	private final SqlSessionFactory sqlMapper;

	public AlunoDB() {
		this.sqlMapper = ConnectionDB.getSqlMapper();
	}
	
	public List<Aluno> getAlunos() {
		final SqlSession session = this.sqlMapper.openSession();
		try {
			AlunoMapper alunoMapper = session.getMapper(AlunoMapper.class);
			return alunoMapper.selectAll();
		} finally {
			session.close();
		}
	}
	
	public Aluno get(long rg) {
		final SqlSession session = this.sqlMapper.openSession();
		try {
			AlunoMapper alunoMapper = session.getMapper(AlunoMapper.class);
			return alunoMapper.select(rg);
		} finally {
			session.close();
		}
	}
	
	public void insert(Aluno aluno) {
		final SqlSession session = this.sqlMapper.openSession();
		try {
			AlunoMapper alunoMapper = session.getMapper(AlunoMapper.class);
			alunoMapper.insert(aluno);
			session.commit();
		} finally {
			session.close();
		}
	}

	public void update(Aluno aluno) {
		final SqlSession session = this.sqlMapper.openSession();
		try {
			AlunoMapper alunoMapper = session.getMapper(AlunoMapper.class);
			alunoMapper.update(aluno);
			session.commit();
		} finally {
			session.close();
		}
	}

	public Aluno getAluno(String nome) {
		final SqlSession session = this.sqlMapper.openSession();
		try {
			AlunoMapper alunoMapper = session.getMapper(AlunoMapper.class);
			return alunoMapper.selectByNome(nome);
		} finally {
			session.close();
		}
	}
	
	public Aluno getAluno(long rg) {
		final SqlSession session = this.sqlMapper.openSession();
		try {
			AlunoMapper alunoMapper = session.getMapper(AlunoMapper.class);
			return alunoMapper.select(rg);
		} finally {
			session.close();
		}
	}

	public List<Teste> getStudentTestsDone(long rg){
		final SqlSession session = this.sqlMapper.openSession();
		try {
			ResultadoTesteMapper resultadoTesteMapper = session.getMapper(ResultadoTesteMapper.class);
			return resultadoTesteMapper.selectStudentTestsDone(rg);
		} finally {
			session.close();
		}
	}
	
	public List<ResultadoTeste> getStudentTestDoneListDates(long rg, int testeId){
		final SqlSession session = this.sqlMapper.openSession();
		try {
			ResultadoTesteMapper resultadoTesteMapper = session.getMapper(ResultadoTesteMapper.class);
			return resultadoTesteMapper.selectStudentTestDoneListDates(rg, testeId);
		} finally {
			session.close();
		}
	}
}


