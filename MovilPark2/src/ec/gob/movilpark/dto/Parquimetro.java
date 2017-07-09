package ec.gob.movilpark.dto;

public class Parquimetro {
	
	private int idParquimetro;
	private String provinciaCodigo;
	private String ciudadCodigo;
	private int idAreas;
	private String calle;
	private String poste;
	private int numeroEspacios;
	private Double latitud;
	private Double longitud;
	private String estado;
	private String nombre;
	private String nombreCiudad;
	private String nombreProvincia;
	private String nombreArea;
	private Double valorMinimo;
	private Double valorMaximo;
	private Double valorPorHora;
	
	
	public Double getValorPorHora() {
		return valorPorHora;
	}
	public void setValorPorHora(Double valorPorHora) {
		this.valorPorHora = valorPorHora;
	}
	public Double getValorMinimo() {
		return valorMinimo;
	}
	public void setValorMinimo(Double valorMinimo) {
		this.valorMinimo = valorMinimo;
	}
	public Double getValorMaximo() {
		return valorMaximo;
	}
	public void setValorMaximo(Double valorMaximo) {
		this.valorMaximo = valorMaximo;
	}
	public int getIdParquimetro() {
		return idParquimetro;
	}
	public void setIdParquimetro(int idParquimetro) {
		this.idParquimetro = idParquimetro;
	}
	public String getProvinciaCodigo() {
		return provinciaCodigo;
	}
	public void setProvinciaCodigo(String provinciaCodigo) {
		this.provinciaCodigo = provinciaCodigo;
	}
	public String getCiudadCodigo() {
		return ciudadCodigo;
	}
	public void setCiudadCodigo(String ciudadCodigo) {
		this.ciudadCodigo = ciudadCodigo;
	}

	public int getIdAreas() {
		return idAreas;
	}
	public void setIdAreas(int idAreas) {
		this.idAreas = idAreas;
	}
	public String getCalle() {
		return calle;
	}
	public void setCalle(String calle) {
		this.calle = calle;
	}
	public String getPoste() {
		return poste;
	}
	public void setPoste(String poste) {
		this.poste = poste;
	}
	public int getNumeroEspacios() {
		return numeroEspacios;
	}
	public void setNumeroEspacios(int numeroEspacios) {
		this.numeroEspacios = numeroEspacios;
	}
	public Double getLatitud() {
		return latitud;
	}
	public void setLatitud(Double latitud) {
		this.latitud = latitud;
	}
	public Double getLongitud() {
		return longitud;
	}
	public void setLongitud(Double longitud) {
		this.longitud = longitud;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getNombreCiudad() {
		return nombreCiudad;
	}
	public void setNombreCiudad(String nombreCiudad) {
		this.nombreCiudad = nombreCiudad;
	}
	public String getNombreProvincia() {
		return nombreProvincia;
	}
	public void setNombreProvincia(String nombreProvincia) {
		this.nombreProvincia = nombreProvincia;
	}
	public String getNombreArea() {
		return nombreArea;
	}
	public void setNombreArea(String nombreArea) {
		this.nombreArea = nombreArea;
	}
	

}
