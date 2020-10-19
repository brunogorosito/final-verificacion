package ar.edu.unrn.seminario.modelo;

import java.util.Date;

import ar.edu.unrn.seminario.excepciones.AtributoNullException;
import ar.edu.unrn.seminario.excepciones.RangoFechaException;
import ar.edu.unrn.seminario.excepciones.SoloNumerosMayoresException;

public class Membresia {

	private int id;
	private Due�o due�o;
	private int puntos;
	private Date fechaRegistro;
	
	public Membresia(int id, Due�o due�o, int puntos, Date fechaRegistro) {
		this.id = id;
		this.due�o = due�o;
		this.puntos = puntos;
		this.fechaRegistro = fechaRegistro;
		if(due�o == null) {
			throw new AtributoNullException("Debe seleccionar un due�o");
		}
		if(puntos < 0 || id <= 0){
			throw new SoloNumerosMayoresException("Puntos o id invalidos");
		}
		if(fechaRegistro == null) {
			throw new RangoFechaException("Fecha invalida en Membresia");
		}
	}

	public Membresia(Due�o due�o, int puntos, Date fechaRegistro) {
		this.due�o = due�o;
		this.puntos = puntos;
		this.fechaRegistro = fechaRegistro;
		if(due�o == null) {
			throw new AtributoNullException("Debe seleccionar un due�o");
		}
		if(puntos < 0){
			throw new SoloNumerosMayoresException("Puntos invalidos en Membresia");
		}
		if(fechaRegistro == null) {
			throw new RangoFechaException("Fecha invalida en Membresia");
		}
	}

	public Due�o getDue�o() {
		return due�o;
	}

	public void setDue�o(Due�o due�o) {
		this.due�o = due�o;
	}

	public int getPuntos() {
		return puntos;
	}

	public void setPuntos(int puntos) {
		this.puntos = puntos;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getNombreDue�o() {
		return due�o.getNombre() + ", " +due�o.getApellido();
	}

	@Override
	public String toString() {
		return "Membresia N�: " + id + "\n" + 
				"Due�o: " + due�o + "\n" +
				"Puntos: " + puntos + "\n" +
				"Fecha de registro: " + fechaRegistro;
	}

	public boolean equals(Membresia obj) {
		if (this.id == obj.id) {
			return true;
		}
		return false;
	}
	
	
}
