package ec.gob.movilpark.dto;

public class Vehiculo {
	
	private int vehiculoId;
	private int usuarioId;
	private String placa;
	private String estado;
	
	public int getUsuarioId() {
		return usuarioId;
	}
	public void setUsuarioId(int usuarioId) {
		this.usuarioId = usuarioId;
	}
	public int getVehiculoId() {
		return vehiculoId;
	}
	public void setVehiculoId(int vehiculoId) {
		this.vehiculoId = vehiculoId;
	}
	public String getPlaca() {
		return placa;
	}
	public void setPlaca(String placa) {
		this.placa = placa;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}

}
