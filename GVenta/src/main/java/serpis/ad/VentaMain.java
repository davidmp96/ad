package serpis.ad;

import java.util.List;
import java.util.logging.Level;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class VentaMain {
	
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
		List<Categoria> categorias = entityManager.createQuery
							("from Categoria order by id", Categoria.class).getResultList();
		for(Categoria categoria : categorias)
			System.out.println(categoria);
		entityManager.getTransaction().commit();
	}
}
