package ar.edu.unrn.seminario.excepciones;

public class SQLErrorException extends RuntimeException{

	private static final long serialVersionUID = 487483054086787064L;

	public SQLErrorException () {
		
	}
	
	public SQLErrorException (String message) {
		super(message);
	}
}
