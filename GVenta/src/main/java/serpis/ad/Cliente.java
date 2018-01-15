package serpis.ad;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="Cliente")
public class Cliente {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	private String nombre;

	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}	
}
