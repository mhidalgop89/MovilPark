package ec.gob.movilpark.dto;

public class NotaCreditoParam {
	private int idNcParam;
private int tiempoMaximoVigencia;
private Double valorMinimo;
private Double valormaximo;
private String estado;
public int getTiempoMaximoVigencia() {
	return tiempoMaximoVigencia;
}
public void setTiempoMaximoVigencia(int tiempoMaximoVigencia) {
	this.tiempoMaximoVigencia = tiempoMaximoVigencia;
}
public Double getValorMinimo() {
	return valorMinimo;
}
public void setValorMinimo(Double valorMinimo) {
	this.valorMinimo = valorMinimo;
}
public Double getValormaximo() {
	return valormaximo;
}
public void setValormaximo(Double valormaximo) {
	this.valormaximo = valormaximo;
}
public String getEstado() {
	return estado;
}
public void setEstado(String estado) {
	this.estado = estado;
}
public int getIdNcParam() {
	return idNcParam;
}
public void setIdNcParam(int idNcParam) {
	this.idNcParam = idNcParam;
}

}
