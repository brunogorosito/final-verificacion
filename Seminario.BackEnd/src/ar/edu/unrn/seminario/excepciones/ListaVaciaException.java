package ar.edu.unrn.seminario.excepciones;

public class ListaVaciaException extends RuntimeException{

	private static final long serialVersionUID = 6L;

	public ListaVaciaException () {
		
	}
	
	public ListaVaciaException (String message) {
		super(message);
	}
}
