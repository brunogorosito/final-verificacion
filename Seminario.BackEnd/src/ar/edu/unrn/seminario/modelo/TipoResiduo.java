package ar.edu.unrn.seminario.modelo;
import ar.edu.unrn.seminario.excepciones.*;

public class TipoResiduo {

	private int id;
	private String nombre;
	private int puntosXkg;

	public TipoResiduo(String nombre, int puntosXkg){
		this.nombre = nombre;
		this.puntosXkg = puntosXkg;
		if(nombre == null) {
			throw new AtributoNullException ("Nombre erroneo");
		}
		if(puntosXkg <=0) {
			throw new SoloNumerosMayoresException("Puntos por Kilogramo invalido");
		}
	}

	public TipoResiduo(int id, String nombre, int puntosXkg) {
		this.id = id;
		this.nombre = nombre;
		this.puntosXkg = puntosXkg;
		if(nombre == null) {
			throw new AtributoNullException ("Nombre erroneo en TIPORESIDUO");
		}
		if(puntosXkg <=0) {
			throw new SoloNumerosMayoresException("Puntos por Kilogramo invalid");
		}
		if(id <= 0) {
			throw new SoloNumerosMayoresException("Id invalido  en TIPORESIDUO");
		}
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getPuntosXkg() {
		return puntosXkg;
	}

	public void setPuntosXkg(int puntosXkg) {
		this.puntosXkg = puntosXkg;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return this.nombre;
	}
	
	
}
