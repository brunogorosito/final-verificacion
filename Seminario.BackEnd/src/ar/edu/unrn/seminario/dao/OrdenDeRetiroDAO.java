package ar.edu.unrn.seminario.dao;

import java.util.List;

import ar.edu.unrn.seminario.modelo.*;

public interface OrdenDeRetiroDAO {

	public void create(OrdenDeRetiro e);
	public void update(OrdenDeRetiro e);
	public void updateOyV(OrdenDeRetiro e, Visita v);
	public void delete(int codigoOrdenDeRetiro);
	public void delete(OrdenDeRetiro e);
	public List<OrdenDeRetiro> findAll();
	public OrdenDeRetiro findByPedidoId(int codigoPedido);
	public OrdenDeRetiro findById(int codigoOrdenDeRetiro);
	public OrdenDeRetiro findByIdVisita(int codigoVisita);
}
