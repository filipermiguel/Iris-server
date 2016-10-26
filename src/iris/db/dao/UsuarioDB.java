package iris.db.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import iris.db.ConnectionDB;
import iris.db.model.Usuario;
import iris.mapper.UsuarioMapper;

public class UsuarioDB {

	private final SqlSessionFactory sqlMapper;

	public UsuarioDB() {
		this.sqlMapper = ConnectionDB.getSqlMapper();
	}
	
	public List<Usuario> getUsuarios() {
		final SqlSession session = this.sqlMapper.openSession();
		try {
			UsuarioMapper usuarioMapper = session.getMapper(UsuarioMapper.class);
			return usuarioMapper.selectAll();
		} finally {
			session.close();
		}
	}
	
	public Usuario get(int id) {
		final SqlSession session = this.sqlMapper.openSession();
		try {
			UsuarioMapper userMapper = session.getMapper(UsuarioMapper.class);
			return userMapper.select(id);
		} finally {
			session.close();
		}
	}
	

	public Usuario login(String nome, String senha) {
		final SqlSession session = this.sqlMapper.openSession();
		try {
			UsuarioMapper userMapper = session.getMapper(UsuarioMapper.class);
			return userMapper.login(nome, senha);
		} finally {
			session.close();
		}
	}
	
	public void insert(Usuario usuario) {
		final SqlSession session = this.sqlMapper.openSession();
		try {
			UsuarioMapper userMapper = session.getMapper(UsuarioMapper.class);
			userMapper.insert(usuario);
			session.commit();
		} finally {
			session.close();
		}
	}

	public void alterarSenha(int id, String senha) {
		final SqlSession session = this.sqlMapper.openSession();
		try {
			UsuarioMapper userMapper = session.getMapper(UsuarioMapper.class);
			userMapper.update(id, senha);
			session.commit();
		} finally {
			session.close();
		}
	}

	public Usuario getUsuario(String nome) {
		final SqlSession session = this.sqlMapper.openSession();
		try {
			UsuarioMapper userMapper = session.getMapper(UsuarioMapper.class);
			return userMapper.selectByNome(nome);
		} finally {
			session.close();
		}
	}
	
	public void deleteUsuario(int id) {
		final SqlSession session = this.sqlMapper.openSession();
		try {
			UsuarioMapper userMapper = session.getMapper(UsuarioMapper.class);
			userMapper.delete(id);
			session.commit();
		} finally {
			session.close();
		}
	}

}


