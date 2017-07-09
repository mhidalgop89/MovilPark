package ec.gob.movilpark.dto;

import java.util.Date;

public class NotaCredito {

	private int idNotaCredito;
	private Double valor;
	private Double descuento;
	private Double saldo;
	private String estado;
	private int usuarioId;
	private String observacion;
	private String usuarioIngresa;
	private Date fechaIngresa;
	private String usuarioActualiza;
	private Date fechaActualiza;
	private String nombreUsuario;
	private String usuario;
	private int idTramite;
	private int idPago;
	private String numeroFactura;
	private Date fhasta;
	
	
	
	public Double getDescuento() {
		return descuento;
	}
	public void setDescuento(Double descuento) {
		this.descuento = descuento;
	}
	public Double getSaldo() {
		return saldo;
	}
	public void setSaldo(Double saldo) {
		this.saldo = saldo;
	}
	public int getIdNotaCredito() {
		return idNotaCredito;
	}
	public void setIdNotaCredito(int idNotaCredito) {
		this.idNotaCredito = idNotaCredito;
	}
	public Double getValor() {
		return valor;
	}
	public void setValor(Double valor) {
		this.valor = valor;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public int getUsuarioId() {
		return usuarioId;
	}
	public void setUsuarioId(int usuarioId) {
		this.usuarioId = usuarioId;
	}
	public String getObservacion() {
		return observacion;
	}
	public void setObservacion(String observacion) {
		this.observacion = observacion;
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
	public String getUsuarioActualiza() {
		return usuarioActualiza;
	}
	public void setUsuarioActualiza(String usuarioActualiza) {
		this.usuarioActualiza = usuarioActualiza;
	}
	public Date getFechaActualiza() {
		return fechaActualiza;
	}
	public void setFechaActualiza(Date fechaActualiza) {
		this.fechaActualiza = fechaActualiza;
	}
	public String getNombreUsuario() {
		return nombreUsuario;
	}
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public int getIdTramite() {
		return idTramite;
	}
	public void setIdTramite(int idTramite) {
		this.idTramite = idTramite;
	}
	public int getIdPago() {
		return idPago;
	}
	public void setIdPago(int idPago) {
		this.idPago = idPago;
	}
	public String getNumeroFactura() {
		return numeroFactura;
	}
	public void setNumeroFactura(String numeroFactura) {
		this.numeroFactura = numeroFactura;
	}
	public Date getFhasta() {
		return fhasta;
	}
	public void setFhasta(Date fhasta) {
		this.fhasta = fhasta;
	}
	
	
}
