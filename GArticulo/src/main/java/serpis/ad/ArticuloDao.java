package serpis.ad;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ArticuloDao {
  private static Connection connection;
  public static void init(Connection connection) {
	  ArticuloDao.connection = connection;
  }
  
  private static PreparedStatement selectIdPreparedStatement;
  private static String selectIdSql = "select * from articulo where id=?";
  
  public static Articulo get(long id) throws SQLException {
    if (selectIdPreparedStatement == null)
    	selectIdPreparedStatement = connection.prepareStatement(selectIdSql);
    	selectIdPreparedStatement.setObject(1, id);
    	
    ResultSet resultSet=selectIdPreparedStatement.executeQuery();
    resultSet.next();
    Articulo articulo = new Articulo();
    articulo.setId(resultSet.getLong("id"));
    articulo.setNombre(resultSet.getString("nombre"));
    articulo.setPrecio(resultSet.getBigDecimal("precio"));
    articulo.setCategoria(resultSet.getLong("categoria"));
    return articulo;
  }
  
  private static String insertSql = "insert into articulo (nombre, precio, categoria) values (?, ?, ?)";
  
    private static PreparedStatement insertPreparedStatement;
    private static void insert(Articulo articulo) throws SQLException {
      if (insertPreparedStatement == null)
	      insertPreparedStatement = connection.prepareStatement(insertSql);
	      insertPreparedStatement.setObject(1, articulo.getNombre());
	      insertPreparedStatement.setObject(2, articulo.getPrecio());
	      insertPreparedStatement.setObject(3, articulo.getCategoria());
      int affected=insertPreparedStatement.executeUpdate();
      System.out.println("\n Número de líneas afectadas: "+affected+"\n");
    }
   
  private static String updateSql = 
		  "update articulo set nombre = ?, precio = ?, categoria = ? where id = ?";
  private static PreparedStatement updatePreparedStatement;
  private static void update(Articulo articulo) throws SQLException {
	  if (updatePreparedStatement == null)
	      updatePreparedStatement = connection.prepareStatement(updateSql);
	      updatePreparedStatement.setObject(1, articulo.getNombre());
	      updatePreparedStatement.setObject(2, articulo.getPrecio());
	      updatePreparedStatement.setObject(3, articulo.getCategoria());
	      updatePreparedStatement.setObject(4, articulo.getId());
	      int affected= updatePreparedStatement.executeUpdate();
	      System.out.println("\n Número de líneas afectadas: "+affected+"\n");
  }
}
