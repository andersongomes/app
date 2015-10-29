package app.dao;

import java.sql.SQLException;

import app.model.Local;
import app.utils.ConnectionFactory;
import totalcross.sql.ResultSet;
import totalcross.sql.Statement;

public class LocalDao {
	
	Statement st;
	
	public LocalDao() {}

	public void salvar(Local local) throws SQLException {
		st = null;
		try {
			String query = "insert into local (nome, endereco, numero, ponto_referencia, cidade, estado) "
					+ "values ('" + local.getNome() + "', '" + local.getEndereco() + "', '" 
					+ local.getNumero() + "', '" + local.getPontoReferencia() + "',  '" 
					+ local.getCidade() + "', '" + local.getEstado() + "')";
			
			st = (Statement) ConnectionFactory.getConnection().createStatement();
			
			st.executeQuery(query);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			st.close();
		}
		
	}

	public void editar(Local local) {

	}

	public void excluir(Local local) {

	}

	public ResultSet listar() throws SQLException {
		st = null;
		try {
			String query = "select * from local";
			st = (Statement) ConnectionFactory.getConnection().createStatement();
			
			ResultSet rs = st.executeQuery(query);
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			st.close();
		}
		return null;
	}
}
