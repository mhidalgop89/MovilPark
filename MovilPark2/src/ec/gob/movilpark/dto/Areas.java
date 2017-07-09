package ec.gob.movilpark.dto;

public class Areas {
	
	//private String codigoAreas;
	private int idAreas;
	private String codigoProvincia;
	private String codigoCiudad;
	private String nombre;
	private String estado;
	private String nombreCiudad;
	private String nombreProvincia;
	private String nombreArea;
	private Double valorMinimo;
	private Double valorMaximo;
	private Double valorPorHora;
	private String rutaImagen;

	public String getRutaImagen() {
		return rutaImagen;
	}
	public void setRutaImagen(String rutaImagen) {
		this.rutaImagen = rutaImagen;
	}
	public String getCodigoProvincia() {
		return codigoProvincia;
	}
	public void setCodigoProvincia(String codigoProvincia) {
		this.codigoProvincia = codigoProvincia;
	}
	public String getCodigoCiudad() {
		return codigoCiudad;
	}
	public void setCodigoCiudad(String codigoCiudad) {
		this.codigoCiudad = codigoCiudad;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
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
	public int getIdAreas() {
		return idAreas;
	}
	public void setIdAreas(int idAreas) {
		this.idAreas = idAreas;
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
	public Double getValorPorHora() {
		return valorPorHora;
	}
	public void setValorPorHora(Double valorPorHora) {
		this.valorPorHora = valorPorHora;
	}
	

}
