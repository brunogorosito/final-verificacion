package ar.edu.unrn.seminario.modelo;
import java.util.ArrayList;
import java.util.Date;

import ar.edu.unrn.seminario.excepciones.AtributoNullException;
import ar.edu.unrn.seminario.excepciones.RangoFechaException;

public class Visita {

	ArrayList <Residuos> residuosRecolectados = new ArrayList <Residuos>();
	
	private int id;
	private Vivienda vivienda;
	private Date fecha;
	private String observaciones;
	private OrdenDeRetiro r;
	
	public Visita(Vivienda vivienda, Date date, String observaciones, OrdenDeRetiro r) {
		this.vivienda = vivienda;
		this.fecha = date;
		this.observaciones = observaciones;
		this.setOrden(r);
		if(vivienda == null || r == null) {
			throw new AtributoNullException("Vivienda u orden de retiro invalidas");
		}
		if(date == null) {
			throw new RangoFechaException("Fecha invalida");
		}
		
	}
	
	public Visita(int id, Vivienda vivienda, Date fecha, String observaciones,OrdenDeRetiro r) {
		this.id = id;
		this.vivienda = vivienda;
		this.fecha = fecha;
		this.observaciones = observaciones;
		this.setOrden(r);
		if(vivienda == null || r == null) {
			throw new AtributoNullException("Vivienda u orden de retiro invalidas");
		}
		if(fecha == null) {
			throw new RangoFechaException("Fecha invalida");
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
		this.fecha = fecha;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public int getId() {
		return id;
	}
	
	public String getNombreRecolector() {
		 return this.getOrden().getNombreApellidoRecolector();
	}

	public void setId(int id) {
		this.id = id;
	}

	public OrdenDeRetiro getOrden() {
		return r;
	}

	public void setOrden(OrdenDeRetiro r) {
		this.r = r;
	}
	
	public void agregarResiduos(Residuos r) {
		this.residuosRecolectados.add(r);
	}

	@Override
	public String toString() {
		return "Visita N°" + id + "\n"+ 
				"Vivienda: " + vivienda.getId() + "\n"+
				"Dueño: " +vivienda.getDueño()+"\n"+
				"DNI: " +vivienda.getDueño().getDNI()+"\n"+
				"Fecha: " + fecha + "\n"+
				"Observaciones: " + observaciones + "\n"+
				"-----Residuos------- " + "\n" +
				this.residuosRecolectados.toString() + "\n" +
				"-----Fin Residuos------- ";
	}
	
	
}
