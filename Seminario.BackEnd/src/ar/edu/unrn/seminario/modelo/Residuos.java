package ar.edu.unrn.seminario.modelo;
import ar.edu.unrn.seminario.excepciones.AtributoNullException;
import ar.edu.unrn.seminario.excepciones.SoloNumerosMayoresException;

public class Residuos {

	private int id;
	private int kg;
	private TipoResiduo tipo;
	private Pedido p;
	private Visita v;
	
	public Residuos(int kg, TipoResiduo tipo, Pedido p){
		this.setP(p);
		this.kg = kg;
		this.tipo = tipo;
		if(tipo == null) {
			throw new AtributoNullException ("Tipo de Residuo erroneo al crear el tipo de residuo");
		}
		if(kg <= 0) {
			throw new SoloNumerosMayoresException ("Kilos invalidos al crear el tipo de residuo");
		}
	}
	
	public Residuos(int id, int kg, TipoResiduo tipo, Pedido p) {
		this.id = id;
		this.kg = kg;
		this.tipo = tipo;
		this.p = p;
		if(tipo == null) {
			throw new AtributoNullException ("Tipo de Residuo erroneo en RESIDUOS");
		}
		if(kg <= 0) {
			throw new SoloNumerosMayoresException ("Kilos invalidos en RESIDUOS");
		}
	}
	
	public Residuos(int kg, TipoResiduo tipo, Visita v){
		this.setV(v);
		this.kg = kg;
		this.tipo = tipo;
		if(tipo == null) {
			throw new AtributoNullException ("Tipo de Residuo erroneo en RESIDUOS");
		}
		if(kg <= 0) {
			throw new SoloNumerosMayoresException ("Kilos invalidos en RESIDUOS");
		}
	}
	
	public Residuos(int id, int kg, TipoResiduo tipo, Visita p) {
		this.id = id;
		this.kg = kg;
		this.tipo = tipo;
		this.setV(p);
		if(tipo == null) {
			throw new AtributoNullException ("Tipo de Residuo erroneo en RESIDUOS");
		}
		if(kg <= 0) {
			throw new SoloNumerosMayoresException ("Kilos invalidos en RESIDUOS");
		}
	}
	
	public int getKg() {
		return kg;
	}
	
	public void setKg(int kg) {
		this.kg = kg;
	}
	
	public TipoResiduo getTipo() {
		return tipo;
	}
	
	public void setTipo(TipoResiduo tipo) {
		this.tipo = tipo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Pedido getP() {
		return p;
	}
	
	@Override
	public String toString() {
		return tipo + " " + kg+"kg" ;
	}

	public void setP(Pedido p) {
		this.p = p;
	}

	public Visita getV() {
		return v;
	}

	public void setV(Visita v) {
		this.v = v;
	}
	
	
}
