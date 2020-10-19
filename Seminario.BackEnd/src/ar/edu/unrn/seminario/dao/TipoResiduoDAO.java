package ar.edu.unrn.seminario.dao;

import java.util.List;

import ar.edu.unrn.seminario.modelo.*;

public interface TipoResiduoDAO {

	public void create(TipoResiduo v);
	public void update(TipoResiduo v);
	public void delete(int codigoTipoResiduo);
	public void delete(TipoResiduo v);
	public List<TipoResiduo> findAll();
	public TipoResiduo findById(int codigoTipoResiduo);
}
