package ar.edu.unrn.seminario.modelo;
import java.util.Date;

import ar.edu.unrn.seminario.excepciones.AtributoNullException;

public class RegistroViviendas {
	
	private Date fecha;
	private Vivienda vivienda;
	
	public RegistroViviendas(Date fecha, Vivienda vivienda) {
		this.fecha = fecha;
		this.vivienda = vivienda;
		if ( vivienda == null) {
			throw new AtributoNullException("Vivienda erronea");
		}
		
	}
	public Vivienda getVivienda() {
		return vivienda;
	}
	public void setVivienda(Vivienda vivienda) {
		this.vivienda = vivienda;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = new Date();
	}
	@Override
	public String toString() {
		return " " + vivienda;
	}

}
