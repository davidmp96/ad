package serpis.ad;

import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name =  "PedidoLinea")
public class PedidoLinea {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne
	@JoinColumn (name = "pedido")
	private Pedido pedido;
	
	@ManyToOne
	@JoinColumn (name = "articulo")
	private Articulo articulo;
	private BigDecimal precio;
	private BigDecimal unidades;
	private BigDecimal total;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Pedido getPedido() {
		return pedido;
	}

	//Visibilidad unica para el package
	void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public Articulo getArticulo() {
		return articulo;
	}

	public void setArticulo(Articulo articulo) {
		this.articulo = articulo;
		precio = articulo.getPrecio();
		unidades = new BigDecimal(1);
		total = unidades.multiply(precio);
	}

	public BigDecimal getPrecio() {
		return precio;
	}

	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}

	public BigDecimal getUnidades() {
		return unidades;
	}

	public void setUnidades(BigDecimal unidades) {
		this.unidades = unidades;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	
	@Override
	public String toString() {
		return String.format("[%s] %s (%s)", id, articulo.getNombre(), pedido);
	}
}
