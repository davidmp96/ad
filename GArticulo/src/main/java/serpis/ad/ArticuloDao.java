package serpis.ad;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ArticuloDao {
	private static Connection connection;
	public static void init(Connection connection) {
		ArticuloDao.connection = connection;
	}
  
	private static PreparedStatement selectIdPS;
	private static String selectIdSql = "select * from articulo where id=?";
	public static Articulo get(long id) throws SQLException {
		if (selectIdPS == null)
			selectIdPS = connection.prepareStatement(selectIdSql);
			selectIdPS.setObject(1, id);
	ResultSet resultSet=selectIdPS.executeQuery();
	resultSet.next();
	Articulo articulo = new Articulo();
	articulo.setId(resultSet.getLong("id"));
	articulo.setNombre(resultSet.getString("nombre"));
	articulo.setPrecio(resultSet.getBigDecimal("precio"));
	articulo.setCategoria(resultSet.getLong("categoria"));
	return articulo;
	}
  
	private static PreparedStatement insertPS;
	private static String insertSql = "insert into articulo (nombre, precio, categoria) values (?, ?, ?)";
	private static void insert(Articulo articulo) throws SQLException {
		if (insertPS == null)
			insertPS = connection.prepareStatement(insertSql);
    		insertPS.setObject(1, articulo.getNombre());
    		insertPS.setObject(2, articulo.getPrecio());
    		insertPS.setObject(3, articulo.getCategoria());
    int affected=insertPS.executeUpdate();
    System.out.println("\n Número de líneas afectadas: "+affected+"\n");
	}
  
	private static PreparedStatement updatePS;
	private static String updateSql = "update articulo set nombre= ?, precio= ?, categoria= ? where id= ?";
	private static void update(Articulo articulo) throws SQLException {
		if (updatePS == null)
			updatePS = connection.prepareStatement(updateSql);
    		updatePS.setObject(1, articulo.getNombre());
    		updatePS.setObject(2, articulo.getPrecio());
    		updatePS.setObject(3, articulo.getCategoria());
    		updatePS.setObject(4, articulo.getId());
    int affected= updatePS.executeUpdate();
    System.out.println("\n Número de líneas afectadas: "+affected+"\n");
	}
  
	public static void save(Articulo articulo) throws SQLException {
		if (articulo.getId() == 0)
			insert(articulo);
		else
			update(articulo);
	}
  
	private static PreparedStatement deletePS;
	private static String deleteSql = "delete from articulo where id= ?";
	public static void delete(long id) throws SQLException {
		if (deletePS == null)
			deletePS = connection.prepareStatement(deleteSql);
    		deletePS.setObject(1, id);
    int affected= deletePS.executeUpdate();
    System.out.println("\n Número de líneas afectadas: "+affected+"\n");
  }
  
	private static String selectSql = "select * from articulo";
	public static List<Articulo> getList() throws SQLException {
	    Statement statement = connection.createStatement();
	    ResultSet resultSet = statement.executeQuery(selectSql);
	    List<Articulo> articulos = new ArrayList<>();
	    
	    while (resultSet.next()){
    	Articulo articulo = new Articulo();
    	articulo.setId(resultSet.getLong("id"));
    	articulo.setNombre(resultSet.getString("nombre"));
    	articulo.setPrecio(resultSet.getBigDecimal("precio"));
    	articulo.setCategoria(resultSet.getLong("categoria"));
    	articulos.add(articulo);
	    }
    return articulos;
	}
  
	public static void close() throws SQLException {
		if (insertPS != null)
			insertPS.close();
	}
}