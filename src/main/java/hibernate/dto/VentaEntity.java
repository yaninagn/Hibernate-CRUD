package hibernate.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "venta", uniqueConstraints = { @UniqueConstraint(columnNames = "ID"), })

public class VentaEntity implements Serializable {

	private static final long serialVersionUID = -1798070786993154676L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private Integer ventaId;

	@Column(name = "FECHA", unique = true, nullable = false, length = 50)
	private String fcVenta;

	@Column(name = "IMPORTE", unique = true, nullable = false, length = 50)
	private double importe;

	@ManyToOne
	@JoinColumn(name = "ID_PERSONA", nullable = true)
	private PersonaEntity perVtaId;

	public Integer getVentaId() {
		return ventaId;
	}

	public void setVentaId(Integer ventaId) {
		this.ventaId = ventaId;
	}

	public String getFcVenta() {
		return fcVenta;
	}

	public void setFcVenta(String fcVenta) {
		this.fcVenta = fcVenta;
	}

	public double getImporte() {
		return importe;
	}

	public void setImporte(double importe) {
		this.importe = importe;
	}

	public PersonaEntity getPerVtaId() {
		return perVtaId;
	}

	public void setPerVtaId(PersonaEntity perVtaId) {
		this.perVtaId = perVtaId;
	}

	
}
