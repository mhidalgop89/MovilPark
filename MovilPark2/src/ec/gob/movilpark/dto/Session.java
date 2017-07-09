package ec.gob.movilpark.dto;

import java.util.List;

public class Session {
	
	private Usuario usuario;
	private Vehiculo vehiculo;
	private Provincia provincia;
	private Ciudad ciudad;
	private Areas area;
	private Tramite tramite;
	private Tramite tramitePrevio;
	private Perfil perfil;
	private Parquimetro parquimetro;
	private EspacioPorParquimetro espacioPorParquimetro;
	private Pago objPago;
	private Double latitud;
	private Double longitud;
	private String paypal;
	private String metodoPago;
	private EspacioPorParquimetro objEspacioPorParquimetroParam;
	private Double saldo;
	private List<Tramite> listaTramite;
	private boolean vehiculoPoseeTramitePrevio;
	
	
	public Areas getArea() {
		return area;
	}
	public void setArea(Areas area) {
		this.area = area;
	}
	public Pago getObjPago() {
		return objPago;
	}
	public void setObjPago(Pago objPago) {
		this.objPago = objPago;
	}
	public Parquimetro getParquimetro() {
		return parquimetro;
	}
	public void setParquimetro(Parquimetro parquimetro) {
		this.parquimetro = parquimetro;
	}
	public EspacioPorParquimetro getEspacioPorParquimetro() {
		return espacioPorParquimetro;
	}
	public void setEspacioPorParquimetro(EspacioPorParquimetro espacioPorParquimetro) {
		this.espacioPorParquimetro = espacioPorParquimetro;
	}
	public Perfil getPerfil() {
		return perfil;
	}
	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public Vehiculo getVehiculo() {
		return vehiculo;
	}
	public void setVehiculo(Vehiculo vehiculo) {
		this.vehiculo = vehiculo;
	}
	public Provincia getProvincia() {
		return provincia;
	}
	public void setProvincia(Provincia provincia) {
		this.provincia = provincia;
	}
	public Ciudad getCiudad() {
		return ciudad;
	}
	public void setCiudad(Ciudad ciudad) {
		this.ciudad = ciudad;
	}
	public Tramite getTramite() {
		return tramite;
	}
	public void setTramite(Tramite tramite) {
		this.tramite = tramite;
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
	public String getPaypal() {
		return paypal;
	}
	public void setPaypal(String paypal) {
		this.paypal = paypal;
	}
	public String getMetodoPago() {
		return metodoPago;
	}
	public EspacioPorParquimetro getObjEspacioPorParquimetroParam() {
		return objEspacioPorParquimetroParam;
	}
	public void setObjEspacioPorParquimetroParam(EspacioPorParquimetro objEspacioPorParquimetroParam) {
		this.objEspacioPorParquimetroParam = objEspacioPorParquimetroParam;
	}
	public Tramite getTramitePrevio() {
		return tramitePrevio;
	}
	public void setTramitePrevio(Tramite tramitePrevio) {
		this.tramitePrevio = tramitePrevio;
	}
	public void setMetodoPago(String metodoPago) {
		this.metodoPago = metodoPago;
	}
	public Double getSaldo() {
		return saldo;
	}
	public void setSaldo(Double saldo) {
		this.saldo = saldo;
	}
	public List<Tramite> getListaTramite() {
		return listaTramite;
	}
	public void setListaTramite(List<Tramite> listaTramite) {
		this.listaTramite = listaTramite;
	}
	public boolean isVehiculoPoseeTramitePrevio() {
		return vehiculoPoseeTramitePrevio;
	}
	public void setVehiculoPoseeTramitePrevio(boolean vehiculoPoseeTramitePrevio) {
		this.vehiculoPoseeTramitePrevio = vehiculoPoseeTramitePrevio;
	}
	

}
