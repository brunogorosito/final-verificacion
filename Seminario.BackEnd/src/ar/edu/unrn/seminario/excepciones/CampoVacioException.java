package ar.edu.unrn.seminario.excepciones;

public class CampoVacioException extends RuntimeException {
	
	private static final long serialVersionUID = -2448749445563322988L;

	public CampoVacioException () {
		
	}
	
	public CampoVacioException (String message) {
		super(message);
	}

}
