package serpis.ad;

import java.math.BigDecimal;
import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name="Pedido")
public class Pedido {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	private BigDecimal importe;
	private Date fecha;
	@ManyToOne
	@JoinColumn(name="cliente_id")
	private Cliente cliente;
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id=id;
	}
	
	public BigDecimal getImporte() {
		return importe;
	}
	
	public void setImporte(BigDecimal importe) {
		this.importe=importe;
	}
	
	public Date getFecha() {
		return fecha;
	}
	
	public void setFecha(Date fecha) {
		this.fecha=fecha;
	}
	
	public Cliente getCliente() {
		return cliente;
	}
	
	public void setCliente(Cliente cliente) {
		this.cliente=cliente;
	}
}
