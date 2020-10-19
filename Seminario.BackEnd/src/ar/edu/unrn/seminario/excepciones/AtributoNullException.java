package ar.edu.unrn.seminario.excepciones;

public class AtributoNullException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public AtributoNullException () {
	
	}
	
	public AtributoNullException (String message) {
		super(message);
	}

}
