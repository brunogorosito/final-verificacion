package ar.edu.unrn.seminario.excepciones;

public class WrongStateException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public WrongStateException () {
	
	}
	
	public WrongStateException (String message) {
		super(message);
	}

}
