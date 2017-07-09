package ec.gob.movilpark.dto;

import java.util.Date;

public class Pago {

	private int idPago;
	private int idTramite;
	private Double valorTotal;
	private Double valorSubTotal;
	private Double valorPagado;
	private Double saldo;
	private Date fechaPago;
	private String estado;
	private String usuarioIngresa;
	private Date fechaIngresa;
	private String usuarioModifica;
	private Date fechaModifica;
	private int minutos;
	private String ciudad;
	private String areas;
	private String placa;
	public int getIdPago() {
		return idPago;
	}
	public void setIdPago(int idPago) {
		this.idPago = idPago;
	}
	public int getIdTramite() {
		return idTramite;
	}
	public void setIdTramite(int idTramite) {
		this.idTramite = idTramite;
	}
	public Double getValorTotal() {
		return valorTotal;
	}
	public void setValorTotal(Double valorTotal) {
		this.valorTotal = valorTotal;
	}
	public Double getValorPagado() {
		return valorPagado;
	}
	public void setValorPagado(Double valorPagado) {
		this.valorPagado = valorPagado;
	}
	public Double getSaldo() {
		return saldo;
	}
	public void setSaldo(Double saldo) {
		this.saldo = saldo;
	}
	public Date getFechaPago() {
		return fechaPago;
	}
	public void setFechaPago(Date fechaPago) {
		this.fechaPago = fechaPago;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getUsuarioIngresa() {
		return usuarioIngresa;
	}
	public void setUsuarioIngresa(String usuarioIngresa) {
		this.usuarioIngresa = usuarioIngresa;
	}
	public Date getFechaIngresa() {
		return fechaIngresa;
	}
	public void setFechaIngresa(Date fechaIngresa) {
		this.fechaIngresa = fechaIngresa;
	}
	public String getUsuarioModifica() {
		return usuarioModifica;
	}
	public void setUsuarioModifica(String usuarioModifica) {
		this.usuarioModifica = usuarioModifica;
	}
	public Date getFechaModifica() {
		return fechaModifica;
	}
	public void setFechaModifica(Date fechaModifica) {
		this.fechaModifica = fechaModifica;
	}
	public Double getValorSubTotal() {
		return valorSubTotal;
	}
	public void setValorSubTotal(Double valorSubTotal) {
		this.valorSubTotal = valorSubTotal;
	}
	public int getMinutos() {
		return minutos;
	}
	public void setMinutos(int minutos) {
		this.minutos = minutos;
	}
	public String getCiudad() {
		return ciudad;
	}
	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}
	public String getAreas() {
		return areas;
	}
	public void setAreas(String areas) {
		this.areas = areas;
	}
	public String getPlaca() {
		return placa;
	}
	public void setPlaca(String placa) {
		this.placa = placa;
	}
	
	
	
}
