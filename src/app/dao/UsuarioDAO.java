package app.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import app.model.Usuario;
import app.utils.ConnectionFactory;
import totalcross.sql.ResultSet;
import totalcross.sql.Statement;

public class UsuarioDAO {

	private Statement st;

	public UsuarioDAO() {
	}

	public void salvar(Usuario usuario) {
		st = null;
		try {
			String query = "insert into usuario (nome, usuario, senha) " + "values ('" + usuario.getNome() + "', '"
					+ usuario.getUsuario() + "', '" + usuario.getSenha() + "')";

			st = (Statement) new ConnectionFactory().getConnection().createStatement();

			st.execute(query);

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	public void editar(Usuario usuario) {
		st = null;
		try {
			String query = "update usuario set nome = '" + usuario.getNome() + "', usuario = '" + usuario.getUsuario()
					+ "', senha = '" + usuario.getSenha() + "' where id_usuario = " + Integer.toString(usuario.getId());
			st = (Statement) new ConnectionFactory().getConnection().createStatement();

			st.execute(query);

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void excluir(String id) {
		st = null;
		try {
			String query = "delete from usuario where id_usuario = " + id;
			st = (Statement) new ConnectionFactory().getConnection().createStatement();

			st.execute(query);

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public List<Usuario> listar() {
		List<Usuario> usuarios = new ArrayList<>();
		Usuario usuario;
		st = null;
		try {
			String query = "select * from usuario";
			st = (Statement) new ConnectionFactory().getConnection().createStatement();

			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				usuario = new Usuario((int) rs.getObject("id_usuario"), (String) rs.getObject("nome"),
						(String) rs.getObject("usuario"), (String) rs.getObject("senha"));
				usuarios.add(usuario);
			}
			return usuarios;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	public Usuario selecionarPorId(String id) {
		Usuario usuario = null;
		st = null;
		try {
			String query = "select * from usuario where id_usuario = " + id;
			st = (Statement) new ConnectionFactory().getConnection().createStatement();

			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				usuario = new Usuario((int) rs.getObject("id_usuario"), (String) rs.getObject("nome"),
						(String) rs.getObject("usuario"), (String) rs.getObject("senha"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return usuario;
	}

	public boolean login(String usuario, String senha) {
		st = null;
		try {
			st = (Statement) new ConnectionFactory().getConnection().createStatement();

			String query = "select * from usuario where usuario = '" + usuario + "' and senha = '" + senha
					+ "' limit 1";
			ResultSet rs = st.executeQuery(query);
			int x = 0;
			while (rs.next()) {
				x++;
				break;
			}
			rs.close();
			if (x == 0) {
				return false;
			} else {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}
}
