package ar.edu.unrn.seminario.excepciones;

public class TipoIncompatibleException extends RuntimeException{

	private static final long serialVersionUID = 4L;
	
	public TipoIncompatibleException () {
		
	}
	
	public TipoIncompatibleException (String message) {
		super(message);
	}

}
