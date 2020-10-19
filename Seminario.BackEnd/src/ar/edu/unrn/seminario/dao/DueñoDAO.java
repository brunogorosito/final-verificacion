package ar.edu.unrn.seminario.dao;

import java.util.List;

import ar.edu.unrn.seminario.modelo.*;

public interface Due�oDAO {

	public void create(Due�o v);
	public void update(Due�o v);
	public void delete(int codigoDue�o);
	public void delete(Due�o v);
	public List<Due�o> findAll();
	public List<Due�o> findAllSinMembresias();
	public Due�o findById(int codigoDue�o);
	public Due�o findByDNI(int dni);
	
}
