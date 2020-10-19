package ar.edu.unrn.seminario.dao;

import java.util.List;

import ar.edu.unrn.seminario.modelo.*;

public interface ViviendaDAO {

	public void create(Vivienda v);
	public void update(Vivienda v);
	public void delete(int codigoVivienda);
	public void delete(Vivienda v);
	public List<Vivienda> findAll();
	public Vivienda findById(int codigoVivienda);
	public List<Vivienda> findByDNIDueño(int dni);
	public Vivienda findByIdVisita(int codigoVisita);
	
}
