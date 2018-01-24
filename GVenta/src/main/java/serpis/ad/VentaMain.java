package serpis.ad;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

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
	
	public static void main(String[] args) {
		java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF);
		
		entityManagerFactory = 
				Persistence.createEntityManagerFactory("serpis.ad.gventa");
		
		showAll();
		
		entityManagerFactory.close();
	}
	
	private static void showAll() {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		List<Articulo> articulos = entityManager.createQuery
							("from Articulo order by id", Articulo.class).getResultList();
		for(Articulo articulo : articulos)
			System.out.println(articulo);
		entityManager.getTransaction().commit();
	}
}
