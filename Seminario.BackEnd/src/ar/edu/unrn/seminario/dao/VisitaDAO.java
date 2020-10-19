package ar.edu.unrn.seminario.dao;

import java.util.List;

import ar.edu.unrn.seminario.modelo.Visita;

public interface VisitaDAO {

	public void create(Visita e);
	public void update(Visita e);
	public void delete(int codigoVisita);
	public void delete(Visita e);
	public List<Visita> findAll();
	public Visita findById(int codigoVisita);
	public Visita findByFechayO(int id_orden, java.sql.Date d);
}
