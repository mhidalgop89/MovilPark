package ec.gob.movilpark.dto;

import java.util.Date;

public class PushMessage {
	
	private int codigoCompania;
	private int cpush;
	private Date fdesde;
	private Date fhasta;
	private String request;
	private String response;
	private String mensaje;
	private String cuerpo;
	private String metodo;
	private String token;
	private String estado;
	private String rutaClavePrivada;
	private String usuarioIngresa;
	private String usuarioActualiza;
	private Date fechaIngresa;
	private Date fechaActualiza;
	public int getCodigoCompania() {
		return codigoCompania;
	}
	public void setCodigoCompania(int codigoCompania) {
		this.codigoCompania = codigoCompania;
	}
	public int getCpush() {
		return cpush;
	}
	public void setCpush(int cpush) {
		this.cpush = cpush;
	}
	public Date getFdesde() {
		return fdesde;
	}
	public void setFdesde(Date fdesde) {
		this.fdesde = fdesde;
	}
	public Date getFhasta() {
		return fhasta;
	}
	public void setFhasta(Date fhasta) {
		this.fhasta = fhasta;
	}
	public String getRequest() {
		return request;
	}
	public void setRequest(String request) {
		this.request = request;
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
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
	public String getUsuarioActualiza() {
		return usuarioActualiza;
	}
	public void setUsuarioActualiza(String usuarioActualiza) {
		this.usuarioActualiza = usuarioActualiza;
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
	public String getRutaClavePrivada() {
		return rutaClavePrivada;
	}
	public void setRutaClavePrivada(String rutaClavePrivada) {
		this.rutaClavePrivada = rutaClavePrivada;
	}
	public String getMetodo() {
		return metodo;
	}
	public void setMetodo(String metodo) {
		this.metodo = metodo;
	}
	public String getCuerpo() {
		return cuerpo;
	}
	public void setCuerpo(String cuerpo) {
		this.cuerpo = cuerpo;
	}

	

}
