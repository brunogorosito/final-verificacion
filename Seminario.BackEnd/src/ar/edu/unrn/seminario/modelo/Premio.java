package ar.edu.unrn.seminario.modelo;
import ar.edu.unrn.seminario.excepciones.*;

public class Premio {
	
	private int id;
	private String nombre;
	private int puntosNecesarios;
	private String descripcion;
	
	public Premio(String nombre, int puntosNecesarios, String descripcion) throws RuntimeException{
		this.nombre = nombre;
		this.puntosNecesarios = puntosNecesarios;
		this.setDescripcion(descripcion);
		if(nombre == null || descripcion == null) {
			throw new AtributoNullException("Nombre o descripcion erroneos");
		}
		if(puntosNecesarios <=0) {
			throw new SoloNumerosMayoresException ("Puntos Necesario erroneo");
		}
	}
	
	public Premio(int id, String nombre, int puntosNecesarios, String descripcion) throws RuntimeException{
		this.nombre = nombre;
		this.puntosNecesarios = puntosNecesarios;
		this.id=id;
		this.setDescripcion(descripcion);
		if(nombre == null || descripcion == null) {
			throw new AtributoNullException("Nombre o descripcion erroneos");
		}
		if(puntosNecesarios <=0 || id <=0) {
			throw new SoloNumerosMayoresException ("Puntos Necesario erroneo");
		}
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public int getPuntosNecesarios() {
		return puntosNecesarios;
	}

	public void setPuntosNecesarios(int puntosNecesarios) {
		this.puntosNecesarios = puntosNecesarios;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	@Override
	public String toString() {
		return "Premio N° " + id + "\n"+
				"Nombre: " + nombre + "\n"+
				"Puntos Necesarios para accederlo: " + puntosNecesarios +"\n"+
				"Descripcion: " + descripcion ;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	
}
