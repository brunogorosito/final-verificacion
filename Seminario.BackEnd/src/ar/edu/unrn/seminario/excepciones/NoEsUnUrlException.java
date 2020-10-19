package ar.edu.unrn.seminario.excepciones;

public class NoEsUnUrlException extends RuntimeException{

	private static final long serialVersionUID = 3L;

	public NoEsUnUrlException () {
		
	}
	
	public NoEsUnUrlException (String message) {
		super(message);
	}


}
