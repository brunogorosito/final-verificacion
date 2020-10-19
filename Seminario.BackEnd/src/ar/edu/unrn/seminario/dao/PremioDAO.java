package ar.edu.unrn.seminario.dao;

import java.util.List;

import ar.edu.unrn.seminario.modelo.Premio;

public interface PremioDAO {

	public void create(Premio p);
	public void update(Premio p);
	public void delete(int codigoPremio);
	public void delete(Premio p);
	public List<Premio> findAll();
	public Premio findById(int codigoPremio);
}
