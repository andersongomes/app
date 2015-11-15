package app.dao;

import java.sql.SQLException;

import app.utils.ConnectionFactory;
import totalcross.sql.ResultSet;
import totalcross.sql.Statement;

public class BaseDAO {
	private Statement st;

	public BaseDAO() {
	}

	public void criaBaseDeDados() {
		st = null;
		String query = "";
		try {
			st = (Statement) new ConnectionFactory().getConnection().createStatement();

			query = "create table if not exists person (name varchar, born datetime, number varchar)";
			st.execute(query);
			query = "create table if not exists usuario (id_usuario integer primary key autoincrement, nome varchar, usuario varchar, senha varchar)";
			st.execute(query);
			query = "create table if not exists local (codigo integer primary key autoincrement, nome varchar, endereco varchar, numero varchar, ponto_referencia varchar, cidade varchar, estado varchar, latitude varchar, longitude varchar)";
			st.execute(query);
			query = "select * from usuario limit 1";

			int x = 0;
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				x++;
				break;
			}
			rs.close();
			if (x == 0) {
				query = "insert into usuario (nome, usuario, senha) values ('Administrador', 'admin', '12345')";
				st.execute(query);
			}	
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
}
