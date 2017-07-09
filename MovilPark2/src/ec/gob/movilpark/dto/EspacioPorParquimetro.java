package ec.gob.movilpark.dto;

import java.util.Date;

public class EspacioPorParquimetro {
private int idEspacioPorParquimetro;
private int idParquimetro;
private int numeroEspacio;
private String enUso;
private String estado;
private String codigoProvincia;
private int idAreas;
private String codigoCiudad;
private String provincia;
private String ciudad;
private String areas;
private String estilo;
private String nombreParquimetro;
private String espacio;
private long minutos;
private long segundos;
private Date fechaFinal;
private String placa;
private int idMultaVehiculo;
private boolean existeMulta;
private int vehiculoId;

public String getPlaca() {
	return placa;
}
public void setPlaca(String placa) {
	this.placa = placa;
}
public String getCodigoProvincia() {
	return codigoProvincia;
}
public void setCodigoProvincia(String codigoProvincia) {
	this.codigoProvincia = codigoProvincia;
}

public int getIdAreas() {
	return idAreas;
}
public void setIdAreas(int idAreas) {
	this.idAreas = idAreas;
}
public String getCodigoCiudad() {
	return codigoCiudad;
}
public void setCodigoCiudad(String codigoCiudad) {
	this.codigoCiudad = codigoCiudad;
}
public String getProvincia() {
	return provincia;
}
public void setProvincia(String provincia) {
	this.provincia = provincia;
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
public int getIdEspacioPorParquimetro() {
	return idEspacioPorParquimetro;
}
public void setIdEspacioPorParquimetro(int idEspacioPorParquimetro) {
	this.idEspacioPorParquimetro = idEspacioPorParquimetro;
}
public int getIdParquimetro() {
	return idParquimetro;
}
public void setIdParquimetro(int idParquimetro) {
	this.idParquimetro = idParquimetro;
}
public int getNumeroEspacio() {
	return numeroEspacio;
}
public void setNumeroEspacio(int numeroEspacio) {
	this.numeroEspacio = numeroEspacio;
}
public String getEnUso() {
	return enUso;
}
public void setEnUso(String enUso) {
	this.enUso = enUso;
}
public String getEstado() {
	return estado;
}
public String getEstilo() {
	return estilo;
}
public void setEstilo(String estilo) {
	this.estilo = estilo;
}
public void setEstado(String estado) {
	this.estado = estado;
}
public String getNombreParquimetro() {
	return nombreParquimetro;
}
public void setNombreParquimetro(String nombreParquimetro) {
	this.nombreParquimetro = nombreParquimetro;
}
public String getEspacio() {
	return espacio;
}
public void setEspacio(String espacio) {
	this.espacio = espacio;
}

public long getMinutos() {
	return minutos;
}
public void setMinutos(long minutos) {
	this.minutos = minutos;
}
public Date getFechaFinal() {
	return fechaFinal;
}
public void setFechaFinal(Date fechaFinal) {
	this.fechaFinal = fechaFinal;
}
public int getIdMultaVehiculo() {
	return idMultaVehiculo;
}
public void setIdMultaVehiculo(int idMultaVehiculo) {
	this.idMultaVehiculo = idMultaVehiculo;
}
public boolean isExisteMulta() {
	return existeMulta;
}
public void setExisteMulta(boolean existeMulta) {
	this.existeMulta = existeMulta;
}
public long getSegundos() {
	return segundos;
}
public void setSegundos(long segundos) {
	this.segundos = segundos;
}
public int getVehiculoId() {
	return vehiculoId;
}
public void setVehiculoId(int vehiculoId) {
	this.vehiculoId = vehiculoId;
}

	
}
