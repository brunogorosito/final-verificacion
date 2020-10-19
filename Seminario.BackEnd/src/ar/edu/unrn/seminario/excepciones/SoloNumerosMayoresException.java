package ar.edu.unrn.seminario.excepciones;

public class SoloNumerosMayoresException extends RuntimeException{

	private static final long serialVersionUID = 7L;

	public SoloNumerosMayoresException () {
		
	}
	
	public SoloNumerosMayoresException (String message) {
		super(message);
	}

}
