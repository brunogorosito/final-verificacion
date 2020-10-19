package ar.edu.unrn.seminario.modelo;
import ar.edu.unrn.seminario.excepciones.*;

public class Recolector extends Persona{
	
	private int id;
	private boolean ocupado=false;
	
	public Recolector(String nombre, String apellido, int dni, boolean ocupado) {
		super(nombre,apellido,dni);
		this.ocupado = ocupado;
	}
	
	public Recolector( int id, String nombre, String apellido, int dNI, boolean ocu) throws RuntimeException{
		super(nombre, apellido, dNI);
		this.id= id;
		this.ocupado = ocu;
		if(id <= 0) {
			throw new SoloNumerosMayoresException("Numero de recolector erroneo");
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int numRecolector) {
		this.id = numRecolector;
	}

	public boolean isOcupado() {
		return ocupado;
	}

	public void ocupar() {
		this.ocupado = true;
	}
	
	public void desocupar() {
		this.ocupado = false;
	}

	@Override
	public String toString() {
		return "Recolector N° " +this.id+ "\n"+
				" Nombre: "+this.nombre+ "\n"+
				" Apellido: " + this.apellido;
	}
	
}
