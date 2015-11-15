package app.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import app.model.Local;
import app.utils.ConnectionFactory;
import totalcross.sql.ResultSet;
import totalcross.sql.Statement;

public class LocalDAO {
	
	private Statement st;
	
	public LocalDAO() {}

	public void salvar(Local local) {
		st = null;
		try {
			String query = "insert into local (nome, endereco, numero, ponto_referencia, cidade, estado) "
					+ "values ('" + local.getNome() + "', '" + local.getEndereco() + "', '" 
					+ local.getNumero() + "', '" + local.getPontoReferencia() + "',  '" 
					+ local.getCidade() + "', '" + local.getEstado() + "')";
			
			st = (Statement) new ConnectionFactory().getConnection().createStatement();
			
			st.execute(query);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}

	public void editar(Local local) {
		st = null;
		try {
			String query = "update local set nome = '" + local.getNome() + "', endereco = '"
				+ local.getEndereco() + "', numero = '" + local.getNumero() + "', ponto_referencia = '"
				+ local.getPontoReferencia() + "', cidade = '" + local.getCidade() + "', estado = '"
				+ local.getEstado() + "', latitude = '" + local.getLatitude() + "', longitude = '" + local.getLongitude()
				+ "' where codigo = " + Integer.toString(local.getCodigo());
			st = (Statement) new ConnectionFactory().getConnection().createStatement();
			
			st.execute(query);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
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
			String query = "delete from local where codigo = " + id;
			st = (Statement) new ConnectionFactory().getConnection().createStatement();
			
			st.execute(query);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public List<Local> listar() {
		List<Local> locais = new ArrayList<>();
		Local local;
		st = null;
		try {
			String query = "select * from local";
			st = (Statement) new ConnectionFactory().getConnection().createStatement();
			
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				local = new Local((int) rs.getObject("codigo"), (String) rs.getObject("nome"),
						(String) rs.getObject("endereco"), (String) rs.getObject("numero"),
						(String) rs.getObject("ponto_referencia"), (String) rs.getObject("cidade"),
						(String) rs.getObject("estado"), (String) rs.getObject("latitude"),
						(String) rs.getObject("longitude"));
				locais.add(local);
			}
			return locais;
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
	
	public Local selecionarPorId(String id) {
		Local local = null;
		st = null;
		try {
			String query = "select * from local where codigo = " + id;
			st = (Statement) new ConnectionFactory().getConnection().createStatement();
			
			ResultSet rs = st.executeQuery(query);
			while(rs.next()){
				local = new Local((int) rs.getObject("codigo"),
						(String) rs.getObject("nome"),
						(String) rs.getObject("endereco"),
						(String) rs.getObject("numero"),
						(String) rs.getObject("ponto_referencia"),
						(String) rs.getObject("cidade"),
						(String) rs.getObject("estado"),
						(String) rs.getObject("latitude"),
						(String) rs.getObject("longitude"));
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
		
		return local;
	}
}
