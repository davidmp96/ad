package serpis.ad;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class VentaMain {
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
				
				System.out.print("\n \nIntroduce opción: ");
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
	
	private static EntityManagerFactory entityManagerFactory;
	
	private static <TEntity> void showAll(Class<TEntity> entityType) {
	    EntityManager entityManager = entityManagerFactory.createEntityManager();
	    entityManager.getTransaction().begin();
	    String queryString = String.format("from %s order by id", entityType.getSimpleName());
	    List<TEntity>  entities = entityManager
	        .createQuery(queryString, entityType)
	        .getResultList();
	    for (TEntity entity : entities)
	      System.out.println(entity);
	    entityManager.getTransaction().commit();
	  }

	private static void salir() {
		System.out.println("Saliendo del programa......\n\n");
	}

	private static void nuevoPedido() {
	    EntityManager entityManager = entityManagerFactory.createEntityManager();
	    entityManager.getTransaction().begin();
	    Pedido pedido = new Pedido();
	    Cliente cliente = entityManager.getReference(Cliente.class, 1L);
	    pedido.setCliente(cliente);
	    PedidoLinea pedidoLinea1 = new PedidoLinea();
//	    //Ojo las dos sentencias siguientes mantienen sincronizada la asociación.
//	    pedido.getPedidoLineas().add(pedidoLinea1);
//	    pedidoLinea1.setPedido(pedido);
	    pedido.add(pedidoLinea1);
	    Articulo articulo = entityManager.getReference(Articulo.class, 1L);
	    pedidoLinea1.setArticulo(articulo);
	    entityManager.persist(pedido);
	    entityManager.getTransaction().commit();
	    
	    for(PedidoLinea pedidoLinea : pedido.getPedidoLineas())
	      System.out.println(pedidoLinea);
	  }
	
	private static void nuevoArticulo() {
	    EntityManager entityManager = entityManagerFactory.createEntityManager();
	    entityManager.getTransaction().begin();
	    Categoria categoria = entityManager.getReference(Categoria.class, 1L);
	    Articulo articulo = new Articulo();
	    articulo.setNombre("nuevo " + new Date());
	    articulo.setPrecio(new BigDecimal(6));
	    articulo.setCategoria(categoria);
	    entityManager.persist(articulo);
	    entityManager.getTransaction().commit();
	  }
	
	private static void nuevoCliente(){
		
	}
	
	private static void borrarPedido(){
		
	}
	private static void borrarCliente(){
		
	}
	private static void borrarArticulo(){
		
	}	

	private static void gestionPedidos() {
		Menu menuPedidos = new Menu()
				.add(new Action("Salir", ()->salir()))
				.add(new Action("Mostrar Pedidos", ()->showAll(Pedido.class)))
				.add(new Action("Crear Pedido", ()->nuevoPedido()))
				.add(new Action("Borrar Pedido", ()->borrarPedido()))
			;
		menuPedidos.show();
	}
	
	private static void gestionClientes() {
		Menu menuClientes = new Menu()
				.add(new Action("Salir", ()->salir()))
				.add(new Action("Mostrar Clientes", ()->showAll(Cliente.class)))
				.add(new Action("Registrar Cliente", ()->nuevoCliente()))
				.add(new Action("Borrar Cliente", ()->borrarCliente()))
			;
		menuClientes.show();
	}

	private static void gestionArticulos() {
		Menu menuProductos = new Menu()
				.add(new Action("Salir", ()->salir()))
				.add(new Action("Mostrar Articulos", ()->showAll(Articulo.class)))
				.add(new Action("Registrar Articulos", ()->nuevoArticulo()))
				.add(new Action("Borrar Articulos", ()->borrarArticulo()))
			;
		menuProductos.show();
	}

	private static Connection connection;
	private static void init() throws SQLException {
		connection = DriverManager.getConnection(
				"jdbc:mysql://localhost/dbprueba", "root", "sistemas");
		PedidoDAO.init(connection);
	}
	
	private static void close() throws SQLException {
		PedidoDAO.close(connection);
		connection.close();
	}

	public static void main(String[] args) throws SQLException {
		init();
		Menu menu = new Menu()
			.add(new Action("Salir", ()->salir()))
			.add(new Action("Gestionar Pedidos", ()->gestionPedidos()))
			.add(new Action("Gestionar Clientes", ()->gestionClientes()))
			.add(new Action("Gestionar Articulos", ()->gestionArticulos()))
		;
		menu.show();
		close();
	}
}