package serpis.ad;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO {
	private static Connection connection;
	public static void init(Connection connection) {
		PedidoDAO.connection = connection;
	}
	
	public static void close(Connection connection) throws SQLException{
		PedidoDAO.connection.close();
	}
	public static List<Pedido> getPedidoList(){
		return null;
	}
	public static List<Cliente> getClienteList(){
		return null;
	}
	public static List<Articulo> getArticuloList(){
		return null;
	}
}