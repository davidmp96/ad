package serpis.ad;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity(name="Pedido")
public class Pedido {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	private BigDecimal importe;
	private Calendar fecha = Calendar.getInstance(); //now
	
	@ManyToOne
	@JoinColumn(name="cliente")
	private Cliente cliente;
	
	@OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval=true)
	private List<PedidoLinea> pedidoLineas = new ArrayList<>();
	
	public PedidoLinea[] getPedidoLineas() {
		return pedidoLineas.toArray(new PedidoLinea[0]);
	}
	
	public void add(PedidoLinea pedidoLinea) {
		pedidoLineas.add(pedidoLinea);
		pedidoLinea.setPedido(this);
	}
	
	public void remove(PedidoLinea pedidoLinea) {
		pedidoLineas.remove(pedidoLinea);
		pedidoLinea.setPedido(null);
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public BigDecimal getImporte() {
		return importe;
	}
	
	public void setImporte(BigDecimal importe) {
		this.importe = importe;
	}
	
	public Calendar getFecha() {
		return fecha;
	}
	
	public void setFecha(Calendar fecha) {
		this.fecha = fecha;
	}
	
	public Cliente getCliente() {
		return cliente;
	}
	
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	@Override
	public String toString() {
		return String.format("[%s] %s %s %s", id, cliente.getNombre(), fecha.getTime(), importe);
	}
}
