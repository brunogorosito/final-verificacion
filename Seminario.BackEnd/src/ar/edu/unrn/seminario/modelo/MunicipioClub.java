package ar.edu.unrn.seminario.modelo;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unrn.seminario.excepciones.NoEsUnUrlException;


public class MunicipioClub {
	
	private int id;
	private String url;
	
	ArrayList <Premio> premios = new ArrayList <Premio>();
	ArrayList <Membresia> membresias  = new ArrayList<Membresia>();
	
	public MunicipioClub(String url) throws Exception {
		this.url = url;
		if(!url.contains("www")) {
			throw new  NoEsUnUrlException("Se debe ingresar una url valida");
		}
	}
	
	public MunicipioClub(int id, String url) throws RuntimeException {
		this.setId(id);
		this.url = url;
		if(!url.contains("www")) {
			throw new  NoEsUnUrlException("Se debe ingresar una url valida");
		}
	}

	public void agregarMebresia(Membresia d1) {
		membresias.add(d1);
	}
	
	public void agregarPuntosAMembresia(Membresia m1, int puntos) {
		int puntosAct = m1.getPuntos();
		for(Membresia membresia : this.membresias) {
			if(membresia.equals(m1)) {
				m1.setPuntos(puntosAct+puntos);
				System.out.println("Se han agregado " +puntos +" a la membresia N° "+m1.getId());
			}
		}
	}
	
	public List<Premio> getPremiosDisponibles(Membresia m) {
		int puntos = m.getPuntos();
		List<Premio> premios = new ArrayList<Premio>();
		for(Premio item: this.premios) {
			if(item.getPuntosNecesarios()<puntos) {
				premios.add(item);
			}
		}
		return premios;
	}
	
	public void relizarCanje(Membresia m, Premio p) {
		int puntosAct = m.getPuntos();
		int puntosPre = p.getPuntosNecesarios();
		for(Membresia membresia : this.membresias) {
			if(membresia.equals(m)) {
				m.setPuntos(puntosAct-puntosPre);
				System.out.println("El dueño "+m.getNombreDueño()+" elijio el premio: "+p.getNombre()+" y se le restaron "+p.getPuntosNecesarios()+" puntos");
			}
		}
	}

	public void agregarPremio(Premio p) {
		premios.add(p);
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
}
