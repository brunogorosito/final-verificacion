package ar.edu.unrn.seminario.dao;

import java.util.List;

import ar.edu.unrn.seminario.modelo.Pedido;
import ar.edu.unrn.seminario.modelo.Vivienda;

public interface PedidoDAO {
	
	public void create(Pedido p);
	public void update(Pedido p);
	public void delete(int codigoPedido);
	public void delete(Pedido p);
	public List<Pedido> findAll();
	public Pedido findById(int codigoPedido);
	public Pedido findByFechayV(Vivienda v, java.sql.Date d);
	public List<Pedido> findByVivienda(int codigoVivienda);

}
