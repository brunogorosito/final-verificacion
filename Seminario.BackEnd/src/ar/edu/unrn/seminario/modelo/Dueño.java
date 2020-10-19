package ar.edu.unrn.seminario.modelo;

public class Dueño extends Persona{
	
	public Dueño(String nombre, String apellido, int dNI){
		super(nombre, apellido, dNI);

	}
	
	public Dueño(int id, int dNI, String apellido, String nombre){
		super(id, nombre, apellido, dNI);
	}
	
	@Override
	public String toString() {
		return " "+this.nombre+", " + this.apellido;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
}
