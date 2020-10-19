package ar.edu.unrn.seminario.dao;

import java.util.List;

import ar.edu.unrn.seminario.modelo.*;

public interface ResiduosDAO {

	public void create(Residuos r);
	public void update(Residuos r);
	public void delete(int codigoResiduo);
	public void delete(Residuos r);
	public List<Residuos> findAll();
	public Residuos findById(int codigoResiduo);
	public List<Residuos> findResiduosXCodigoPedido(int codigoPedido);
	public List<Residuos> findResiduosXCodigoVisita(int codigoVisita);
}
