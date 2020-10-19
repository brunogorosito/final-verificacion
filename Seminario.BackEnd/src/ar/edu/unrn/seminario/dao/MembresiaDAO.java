package ar.edu.unrn.seminario.dao;

import java.util.List;

import ar.edu.unrn.seminario.modelo.Membresia;

public interface MembresiaDAO {

	public void create(Membresia m);
	public void update(Membresia m);
	public void delete(int codigoMembresia);
	public void delete(Membresia m);
	public List<Membresia> findAll();
	public Membresia findById(int codigoMembresia);
	public Membresia findByDniDueño(int DNI);
}
