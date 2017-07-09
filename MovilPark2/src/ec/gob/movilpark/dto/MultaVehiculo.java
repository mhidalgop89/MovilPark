package ec.gob.movilpark.dto;

import java.util.Date;

public class MultaVehiculo {
	
	private int idMultaVehiculo;
	private int vehiculoId;
	private String estado;
	private Date fechaIngresa;
	private Date fechaActualiza;
	private String usuarioIngresa;
	private String usuarioActualiza;
	public int getIdMultaVehiculo() {
		return idMultaVehiculo;
	}
	public void setIdMultaVehiculo(int idMultaVehiculo) {
		this.idMultaVehiculo = idMultaVehiculo;
	}
	public int getVehiculoId() {
		return vehiculoId;
	}
	public void setVehiculoId(int vehiculoId) {
		this.vehiculoId = vehiculoId;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public Date getFechaIngresa() {
		return fechaIngresa;
	}
	public void setFechaIngresa(Date fechaIngresa) {
		this.fechaIngresa = fechaIngresa;
	}
	public Date getFechaActualiza() {
		return fechaActualiza;
	}
	public void setFechaActualiza(Date fechaActualiza) {
		this.fechaActualiza = fechaActualiza;
	}
	public String getUsuarioIngresa() {
		return usuarioIngresa;
	}
	public void setUsuarioIngresa(String usuarioIngresa) {
		this.usuarioIngresa = usuarioIngresa;
	}
	public String getUsuarioActualiza() {
		return usuarioActualiza;
	}
	public void setUsuarioActualiza(String usuarioActualiza) {
		this.usuarioActualiza = usuarioActualiza;
	}
	
	
}
