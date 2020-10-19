package ar.edu.unrn.seminario.modelo;
import ar.edu.unrn.seminario.excepciones.*;


public class Persona {
	
	protected int id;
	protected String nombre;
	protected String apellido;
	protected int DNI;
	
	public Persona(String nombre, String apellido, int dNI){
		this.nombre = nombre;
		this.apellido = apellido;
		DNI = dNI;
		if(nombre == null || apellido == null) {
			throw new AtributoNullException("Nombre o apellidos invalidos al crear la persona");
		}
		if(dNI <1000000 || dNI >99999999) {
			throw new DNIinvalidoException("DNI ingresado invalido al crear la persona");
		}
		
	}
	
	public Persona(int codigo, String nombre, String apellido, int dNI){
		this.id = codigo;
		this.nombre = nombre;
		this.apellido = apellido;
		DNI = dNI;
		if(nombre == null || apellido == null) {
			throw new AtributoNullException("Nombre o apellidos invalidos en PERSONA");
		}
		if(dNI <1000000 || dNI >99999999) {
			throw new DNIinvalidoException("DNI ingresado invalido en PERSONA");
		}
		if(codigo <= 0) {
			throw new SoloNumerosMayoresException("Codigo debe ser mayor a 0");
		}
		
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getApellido() {
		return apellido;
	}
	
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	
	public int getDNI() {
		return DNI;
	}
	
	public void setDNI(int dNI) {
		DNI = dNI;
	}
	
	
}
