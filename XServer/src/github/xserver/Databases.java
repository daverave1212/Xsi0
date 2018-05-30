package github.xserver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Databases {

	private static Connection getConnection() throws SQLException {
		final String DB_URL = "jdbc:mysql://sql7.freemysqlhosting.net:3306/sql7240559";
		final String DB_USER = "sql7240559";
		final String DB_PASS = "lIFiguElyI";
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw new SQLException();
		}
		Connection myConn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
		return myConn;
	}

	private static void closeConnection(Connection myConn){
        try {
            myConn.close();
        } catch (SQLException e) {
            return;
        }
    }

    private static void closePS(java.sql.PreparedStatement ps){
        try {
            ps.close();
        } catch (SQLException e) {
            return;
        }
    }

    private static void closeResult(ResultSet rs){
        try {
            rs.close();
        } catch (SQLException e) {
            return;
        }
    }

	public static boolean validateLogin(String username, String password) throws SQLException {

		/*
		 * Cauta in baza de date sa vada daca exista userul asta
		 * Daca exista, returneaza true
		 * Daca nu exista, returneaza false
		 *
		 */
		Connection database=null;
		try {
			database = getConnection();
		} catch (SQLException e) {
			System.out.println("Eroare de conexiune cu DB sql\n");
			return true;
		}

		java.sql.PreparedStatement ps;
		try {
			ps = database.prepareStatement
					("SELECT count(user) FROM users WHERE user=? and pass=? ");
			ps.setString(1, username);
			ps.setString(2, password);
		} catch (SQLException e) {
			System.out.println("PreparedStatement did not work\n");
			closeConnection(database);
			return true;
		}
		ResultSet rs = null;
		try {
			rs = ps.executeQuery();
		} catch (SQLException e) {
			System.out.println("Result set did not work");
			System.out.println("(to be expected if PreparedStatement also did not work)\n");
			closePS(ps);
			closeConnection(database);
			return true;
		}
		while (rs.next()){
            String one= null;
            try {
                one = rs.getString(1);
            } catch (SQLException e) {
                return true;
            }
            if(Integer.parseInt(one)==1)
				return true;
			else
				return false;
		}

		closeResult(rs);
		closePS(ps);
		closeConnection(database);
		return true;
	}
	
}
