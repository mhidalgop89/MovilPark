package ec.gob.movilpark.filtros;

import ec.gob.movilpark.dto.Areas;
import ec.gob.movilpark.dto.Ciudad;
import ec.gob.movilpark.dto.Parquimetro;
import ec.gob.movilpark.dto.Provincia;
import ec.gob.movilpark.dto.Tramite;
import ec.gob.movilpark.dto.Usuario;
import ec.gob.movilpark.dto.Vehiculo;

public class Filtros {
	
	private Areas objArea;
	private Ciudad objCiudad;
	private Parquimetro objParquimetro;
	private Provincia objProvincia;
	private Tramite objTramite;
	private Usuario objUsuario;
	private Vehiculo objVehiculo;
	public Filtros(){
		objVehiculo=new Vehiculo();
		objProvincia = new Provincia();
		objCiudad = new Ciudad();
		objArea = new Areas();
		objParquimetro = new Parquimetro();
		objTramite = new Tramite();
		objUsuario = new Usuario();
	}
	public Areas getObjArea() {
		return objArea;
	}
	public void setObjArea(Areas objArea) {
		this.objArea = objArea;
	}
	public Ciudad getObjCiudad() {
		return objCiudad;
	}
	public void setObjCiudad(Ciudad objCiudad) {
		this.objCiudad = objCiudad;
	}
	public Parquimetro getObjParquimetro() {
		return objParquimetro;
	}
	public void setObjParquimetro(Parquimetro objParquimetro) {
		this.objParquimetro = objParquimetro;
	}
	public Provincia getObjProvincia() {
		return objProvincia;
	}
	public void setObjProvincia(Provincia objProvincia) {
		this.objProvincia = objProvincia;
	}
	public Tramite getObjTramite() {
		return objTramite;
	}
	public void setObjTramite(Tramite objTramite) {
		this.objTramite = objTramite;
	}
	public Usuario getObjUsuario() {
		return objUsuario;
	}
	public void setObjUsuario(Usuario objUsuario) {
		this.objUsuario = objUsuario;
	}
	public Vehiculo getObjVehiculo() {
		return objVehiculo;
	}
	public void setObjVehiculo(Vehiculo objVehiculo) {
		this.objVehiculo = objVehiculo;
	}
	
	
	

}
