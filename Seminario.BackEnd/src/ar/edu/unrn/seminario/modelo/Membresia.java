package ar.edu.unrn.seminario.modelo;

import java.util.Date;

import ar.edu.unrn.seminario.excepciones.AtributoNullException;
import ar.edu.unrn.seminario.excepciones.RangoFechaException;
import ar.edu.unrn.seminario.excepciones.SoloNumerosMayoresException;

public class Membresia {

	private int id;
	private Dueño dueño;
	private int puntos;
	private Date fechaRegistro;
	
	public Membresia(int id, Dueño dueño, int puntos, Date fechaRegistro) {
		this.id = id;
		this.dueño = dueño;
		this.puntos = puntos;
		this.fechaRegistro = fechaRegistro;
		if(dueño == null) {
			throw new AtributoNullException("Debe seleccionar un dueño");
		}
		if(puntos < 0 || id <= 0){
			throw new SoloNumerosMayoresException("Puntos o id invalidos");
		}
		if(fechaRegistro == null) {
			throw new RangoFechaException("Fecha invalida en Membresia");
		}
	}

	public Membresia(Dueño dueño, int puntos, Date fechaRegistro) {
		this.dueño = dueño;
		this.puntos = puntos;
		this.fechaRegistro = fechaRegistro;
		if(dueño == null) {
			throw new AtributoNullException("Debe seleccionar un dueño");
		}
		if(puntos < 0){
			throw new SoloNumerosMayoresException("Puntos invalidos en Membresia");
		}
		if(fechaRegistro == null) {
			throw new RangoFechaException("Fecha invalida en Membresia");
		}
	}

	public Dueño getDueño() {
		return dueño;
	}

	public void setDueño(Dueño dueño) {
		this.dueño = dueño;
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
	
	public String getNombreDueño() {
		return dueño.getNombre() + ", " +dueño.getApellido();
	}

	@Override
	public String toString() {
		return "Membresia N°: " + id + "\n" + 
				"Dueño: " + dueño + "\n" +
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
