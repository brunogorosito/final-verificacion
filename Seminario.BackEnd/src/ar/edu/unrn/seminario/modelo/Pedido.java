package ar.edu.unrn.seminario.modelo;
import java.util.ArrayList;
import java.util.Date;

import ar.edu.unrn.seminario.excepciones.*;

public class Pedido {
	
	ArrayList <Residuos> residuos = new ArrayList <Residuos>();
	
	private Vivienda vivienda;
	private int id;
	private Date fecha;
	private boolean cargaPesada;
	private String observacion;
	
	public Pedido(Vivienda v, Date fecha, boolean cargaPesada, String observacion) {
		this.vivienda = v;
		this.fecha = fecha;
		this.cargaPesada = cargaPesada;
		this.observacion = observacion;
		if(v == null || observacion == null) {
			throw new AtributoNullException ("Vivienda u observacion incorrectos al crear el pedido");
		}
		if(fecha == null) {
			throw new RangoFechaException("Fecha invalida en Membresia");
		}
	}
	
	public Pedido(int id, Vivienda v, Date fecha, boolean cargaPesada, String observacion) {
		this.id = id;
		this.vivienda = v;
		this.fecha = fecha;
		this.cargaPesada = cargaPesada;
		this.observacion = observacion;
		if(v == null || observacion == null) {
			throw new AtributoNullException ("Vivienda u observacion incorrectos al crear el pedido");
		}
		if(fecha == null) {
			throw new RangoFechaException("Fecha invalida en Membresia");
		}
	}
	
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = new Date();
	}
	public boolean isCargaPesada() {
		return cargaPesada;
	}
	public void setCargaPesada(boolean cargaPesada) {
		this.cargaPesada = cargaPesada;
	}
	public String getObservacion() {
		return observacion;
	}
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public Vivienda getVivienda() {
		return vivienda;
	}

	public void setV(Vivienda v) {
		this.vivienda = v;
	}

	@Override
	public String toString() {
		return "Pedido N°" + id + "\n"+ 
				"Vivienda: " + vivienda.getId() + "\n"+
				"Dueño: " +vivienda.getDueño()+"\n"+
				"DNI: " +vivienda.getDueño().getDNI()+"\n"+
				"Fecha: " + fecha + "\n"+
				"¿Se necesita carga pesada?: " + cargaPesada + "\n"+
				"Observaciones: " + observacion + "\n"+
				"-----Residuos------- " + "\n" +
				this.residuos.toString() + "\n" +
				"-----Fin Residuos------- ";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public void agregarResiduo(Residuos r) {
		this.residuos.add(r);
		r.setP(this);
	}
	
	public int calcularPuntos() {
		int puntos=0;
		for(Residuos item : this.residuos) {
			puntos = puntos + Math.multiplyExact(item.getKg(), item.getTipo().getPuntosXkg());
		}
		return puntos;
	}
	
	public int getDNIDueñoVivienda() {
		return this.getVivienda().getDNIDueño();
	}
}
