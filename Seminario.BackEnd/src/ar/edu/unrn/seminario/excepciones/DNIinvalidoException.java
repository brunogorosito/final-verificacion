package ar.edu.unrn.seminario.excepciones;

public class DNIinvalidoException extends RuntimeException {

	private static final long serialVersionUID = 7L;

	public DNIinvalidoException () {
		
	}
	
	public DNIinvalidoException (String message) {
		super(message);
	}
}
