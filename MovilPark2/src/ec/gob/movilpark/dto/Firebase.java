package ec.gob.movilpark.dto;

import java.util.Date;

public class Firebase {
	
	private int codigoCompania;
	private int cfirebase;
	private String cproductoFirebase;
	private String productoFirebase;
	private String rutaClavePrivada;
	private String observacion;
	private Date fdesde;
	private Date fhasta;
	public int getCodigoCompania() {
		return codigoCompania;
	}
	
	public String getCproductoFirebase() {
		return cproductoFirebase;
	}

	public void setCproductoFirebase(String cproductoFirebase) {
		this.cproductoFirebase = cproductoFirebase;
	}

	public void setCodigoCompania(int codigoCompania) {
		this.codigoCompania = codigoCompania;
	}

	public int getCfirebase() {
		return cfirebase;
	}

	public void setCfirebase(int cfirebase) {
		this.cfirebase = cfirebase;
	}

	public String getProductoFirebase() {
		return productoFirebase;
	}
	public void setProductoFirebase(String productoFirebase) {
		this.productoFirebase = productoFirebase;
	}
	public String getRutaClavePrivada() {
		return rutaClavePrivada;
	}
	public void setRutaClavePrivada(String rutaClavePrivada) {
		this.rutaClavePrivada = rutaClavePrivada;
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

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
	


}
