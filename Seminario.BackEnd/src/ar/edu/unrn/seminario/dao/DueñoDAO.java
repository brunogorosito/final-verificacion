package ar.edu.unrn.seminario.dao;

import java.util.List;

import ar.edu.unrn.seminario.modelo.*;

public interface DueñoDAO {

	public void create(Dueño v);
	public void update(Dueño v);
	public void delete(int codigoDueño);
	public void delete(Dueño v);
	public List<Dueño> findAll();
	public List<Dueño> findAllSinMembresias();
	public Dueño findById(int codigoDueño);
	public Dueño findByDNI(int dni);
	
}
