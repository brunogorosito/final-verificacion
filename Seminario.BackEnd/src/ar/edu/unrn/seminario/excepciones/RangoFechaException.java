package ar.edu.unrn.seminario.excepciones;

public class RangoFechaException extends RuntimeException{

	private static final long serialVersionUID = 2L;
	
	public RangoFechaException() {
		
	}
	
	public RangoFechaException (String message) {
		super(message);
	}

}

