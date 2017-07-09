package ec.gob.movilpark.dto;

import java.util.Date;

public class Parametros {
	
	private int parametrosID;
	private Double valorPorMinuto		;
	private int tiempoMaxEnMinutos		;
	private Double maximoDolares		;
	private double valorMinimoDolares;//parametrizable--
	private String estado;
	private String usuarioIngresa;
	private Date fechaIngresa;
	private String usuarioModifica;
	private Date fechaModifica;
	
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public int getParametrosID() {
		return parametrosID;
	}
	public void setParametrosID(int parametrosID) {
		this.parametrosID = parametrosID;
	}

	public Double getValorPorMinuto() {
		return valorPorMinuto;
	}
	public void setValorPorMinuto(Double valorPorMinuto) {
		this.valorPorMinuto = valorPorMinuto;
	}
	public int getTiempoMaxEnMinutos() {
		return tiempoMaxEnMinutos;
	}
	public void setTiempoMaxEnMinutos(int tiempoMaxEnMinutos) {
		this.tiempoMaxEnMinutos = tiempoMaxEnMinutos;
	}
	public Double getMaximoDolares() {
		return maximoDolares;
	}
	public void setMaximoDolares(Double maximoDolares) {
		this.maximoDolares = maximoDolares;
	}
	public double getValorMinimoDolares() {
		return valorMinimoDolares;
	}
	public void setValorMinimoDolares(double valorMinimoDolares) {
		this.valorMinimoDolares = valorMinimoDolares;
	}
	public String getUsuarioIngresa() {
		return usuarioIngresa;
	}
	public void setUsuarioIngresa(String usuarioIngresa) {
		this.usuarioIngresa = usuarioIngresa;
	}

	public String getUsuarioModifica() {
		return usuarioModifica;
	}
	public void setUsuarioModifica(String usuarioModifica) {
		this.usuarioModifica = usuarioModifica;
	}
	public Date getFechaIngresa() {
		return fechaIngresa;
	}
	public void setFechaIngresa(Date fechaIngresa) {
		this.fechaIngresa = fechaIngresa;
	}
	public Date getFechaModifica() {
		return fechaModifica;
	}
	public void setFechaModifica(Date fechaModifica) {
		this.fechaModifica = fechaModifica;
	}

	

	

}
