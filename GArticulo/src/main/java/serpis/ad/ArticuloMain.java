package serpis.ad;


import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.sql.SQLException;


public class ArticuloMain {
  
	private static Scanner scanner = new Scanner(System.in);
  
	private static class Action {
		private String label;
		private Runnable runnable;
    
    public Action(String label, Runnable runnable) {
    	this.label = label;
    	this.runnable = runnable;
    }
    
    public String getLabel() {
    	return label;
    }
    
    public void execute() {
    	runnable.run();
    }
    }
  
	private static class Menu {
    private List<Action> actions = new ArrayList<>();
    
    public Menu add(Action action) {
      actions.add(action);
      return this;
    }
    
    private String getOptions() {
      String options = "";
      for (int index = 0; index < actions.size(); index ++) 
        options = options + index;
      return "[" + options + "]";
    }
    
    private int getOption() {
      String options = getOptions();
      while (true) {
        for (int index = 0; index < actions.size(); index ++) 
          System.out.printf("%d %s\n", index, actions.get(index).label);
        
        System.out.print("Introduce opción: ");
        String option = scanner.nextLine();
        if (option.matches(options))
          return Integer.parseInt(option);
      }
    }
    
    public void show() {
      while (true) {
        int option = getOption();
        actions.get(option).execute();
        if (option == 0)
          return;
      }
    }
  }
  

	private static void salir() {
		System.out.println("Saliendo del Programa........");
	}

	private static void nuevo() {
		Articulo articulo = new Articulo();
		ArticuloView.get(articulo);
		try  {
			ArticuloDao.save(articulo);
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
		}
	}
  
	private static void modificar() {
	    Articulo articulo = new Articulo();
	    long id = ArticuloView.getId();
	    articulo.setId(id);
	    ArticuloView.get(articulo);
	    try {
	    	ArticuloDao.save(articulo);
	    } catch (SQLException ex) {
	    	System.out.println("SQLException: " + ex.getMessage());
	    }
	}

	private static void eliminar() {
	    long id = ArticuloView.getId();
	    try{
	    ArticuloDao.delete(id);
	    }catch (SQLException ex) {
	    	System.out.println("SQLException: " + ex.getMessage());
	    }
	}

	private static void consultar() {
		long id = ArticuloView.getId();
		Articulo articulo;
		try {
			articulo = ArticuloDao.get(id);
			ArticuloView.show(articulo);
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
		}
	}
  
	private static void listarTodos() {
		try {
			List<Articulo> articulos = ArticuloDao.getList(); 
			ArticuloView.show(articulos);
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
		}
	}

	private static Connection connection;
	private static void init() throws SQLException {
		connection = DriverManager.getConnection(
					 "jdbc:mysql://localhost/dbprueba", "root", "sistemas");
		ArticuloDao.init(connection);
	}
  
	private static void close() throws SQLException {
		ArticuloDao.close();
		connection.close();
	}

	public static void main(String[] args) throws SQLException {
		init();
		Menu menu = new Menu()
				.add(new Action("Salir", ()->salir()))
				.add(new Action("Nuevo", ()->nuevo()))
				.add(new Action("Modificar", ()->modificar()))
				.add(new Action("Eliminar", ()->eliminar()))
				.add(new Action("Consultar", ()->consultar()))
				.add(new Action("Listar todos", ()->listarTodos()));
		menu.show();
		close();
	}
}


