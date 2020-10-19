package ar.edu.unrn.seminario.dao;

import java.util.List;
import ar.edu.unrn.seminario.modelo.Recolector;

public interface RecolectorDAO {

	public void create(Recolector r);
	public void update(Recolector r);
	public void delete(int codigoRecolector);
	public void delete(Recolector r);
	public List<Recolector> findAll();
	public Recolector findById(int codigoRecolector);
	public Recolector findByDNI(int dni);
}
