package ar.edu.unrn.seminario.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.PreparedStatement;

import ar.edu.unrn.seminario.excepciones.SQLErrorException;
import ar.edu.unrn.seminario.modelo.Dueño;
import ar.edu.unrn.seminario.modelo.Pedido;
import ar.edu.unrn.seminario.modelo.Persona;
import ar.edu.unrn.seminario.modelo.Residuos;
import ar.edu.unrn.seminario.modelo.TipoResiduo;
import ar.edu.unrn.seminario.modelo.Vivienda;

public class PedidoDAOjdbc implements PedidoDAO{

	private String urlConexion = "jdbc:mysql://localhost:3306/cooperativaviviendas";
	Connection miConexion = null;
	ResultSet rs = null;
	Statement sent = null;
	
	@Override
	public void create(Pedido p) throws SQLErrorException{
		try {
			miConexion = DriverManager.getConnection(urlConexion,"root","");
			sent = miConexion.createStatement();
			PreparedStatement ps = (PreparedStatement) miConexion.prepareStatement("insert into pedidos (id_vivienda, fecha, cargaPesada, observaciones) values (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1,p.getVivienda().getId());
			ps.setDate(2, new java.sql.Date(p.getFecha().getTime()));
			ps.setBoolean(3,p.isCargaPesada());
			ps.setString(4,p.getObservacion());
			ps.executeUpdate();
			rs = ps.getGeneratedKeys();
			if(rs != null && rs.next()) {
				System.out.println("Se agrego con exito");
			}
			rs.close();
			sent.close();
			miConexion.close();
		}catch(SQLException e) {
			throw new SQLErrorException(""+e.getMessage());
		}
	}

	@Override
	public void update(Pedido p) throws SQLErrorException{
		try {
			miConexion = DriverManager.getConnection(urlConexion,"root","");
			Statement sent = miConexion.createStatement();
			PreparedStatement ps = (PreparedStatement) miConexion.prepareStatement("UPDATE pedidos "
					+ "SET id_vivienda = ? , fecha = ? , cargaPesada = ? , observaciones = ? "
					+ "WHERE id_pedido = ? ");
			ps.setInt(1,p.getVivienda().getId());
			ps.setDate(2,(java.sql.Date) p.getFecha());
			ps.setBoolean(3,p.isCargaPesada());
			ps.setString(4,p.getObservacion());
			ps.setInt(5,p.getId());
			int cant = ps.executeUpdate();
			if(cant == 1) {
				System.out.println("Se modifico el pedido con exito");
			}
			ps.close();
			sent.close();
			miConexion.close();
		}catch(SQLException e) {
			throw new SQLErrorException(""+e.getMessage());
		}
		
	}

	@Override
	public void delete(int codigoPedido)throws SQLErrorException {
		try {
			miConexion = DriverManager.getConnection(urlConexion,"root","");
			Statement sent = miConexion.createStatement();
			PreparedStatement ps = (PreparedStatement) miConexion.prepareStatement("DELETE FROM residuosarecolectar WHERE id_pedido = ?");
			ps.setInt(1, codigoPedido);
			int cant = ps.executeUpdate();
			if(cant == 1) {
				System.out.println("Se eliminaron los residuos del pedido con exito");
			}
			ps = (PreparedStatement) miConexion.prepareStatement("DELETE FROM pedidos WHERE id_pedido = ?");
			ps.setInt(1, codigoPedido);
			cant = ps.executeUpdate();
			if(cant == 1) {
				System.out.println("Se elimino el pedido con exito");
			}
			ps = (PreparedStatement) miConexion.prepareStatement("UPDATE ordenderetiro SET estado = ? WHERE id_pedido = ?");
			ps.setString(1, "CANCELADO");
			ps.setInt(2, codigoPedido);
			cant = ps.executeUpdate();
			if(cant == 1) {
				System.out.println("Se cancelo la orden de retiro con exito");
			}
			rs.close();
			sent.close();
			miConexion.close();
		}catch(SQLException e) {
			throw new SQLErrorException(""+e.getMessage());
		}
	}

	@Override
	public void delete(Pedido p)throws SQLErrorException {
		try {
			miConexion = DriverManager.getConnection(urlConexion,"root","");
			Statement sent = miConexion.createStatement();
			PreparedStatement ps = (PreparedStatement) miConexion.prepareStatement("DELETE FROM residuosarecolectar WHERE id_pedido = ?");
			ps.setInt(1, p.getId());
			int cant = ps.executeUpdate();
			if(cant == 1) {
				System.out.println("Se eliminaron los residuos del pedido con exito");
			}
			ps = (PreparedStatement) miConexion.prepareStatement("DELETE FROM pedidos WHERE id_pedido = ?");
			ps.setInt(1, p.getId());
			cant = ps.executeUpdate();
			if(cant == 1) {
				System.out.println("Se elimino el pedido con exito");
			}
			ps = (PreparedStatement) miConexion.prepareStatement("UPDATE ordenderetiro SET estado = ? WHERE id_pedido = ?");
			ps.setString(1, "CANCELADO");
			ps.setInt(1, p.getId());
			cant = ps.executeUpdate();
			if(cant == 1) {
				System.out.println("Se cancelo la orden de retiro con exito");
			}
			rs.close();
			sent.close();
			miConexion.close();
		}catch(SQLException e) {
			throw new SQLErrorException(""+e.getMessage());
		}
	}

	@Override
	public List<Pedido> findAll()throws SQLErrorException {
		List <Pedido> pedidos = new ArrayList<Pedido>();
		try {
			miConexion = DriverManager.getConnection(urlConexion,"root","");
			sent = miConexion.createStatement();
			rs = sent.executeQuery("SELECT * FROM pedidos p "
					+ "JOIN vivienda v ON p.id_vivienda = v.id_vivienda "
					+ "JOIN dueno n ON v.id_dueño = n.id_dueño ");
			while(rs.next()) {
				Persona p1 = new Dueño(rs.getInt("id_dueño"),rs.getInt("DNI"), rs.getString("nombre"),rs.getString("apellido"));
				Vivienda v = new Vivienda(rs.getInt("id_vivienda"),rs.getDate("fecha_registro"),rs.getString("ciudad"),rs.getString("provincia"),rs.getLong("latitud"),rs.getLong("longitud"),rs.getString("calle"), rs.getInt("num_calle"),p1);
				Pedido p = new Pedido(rs.getInt("id_pedido"), v ,rs.getDate("fecha"), rs.getBoolean("cargaPesada"), rs.getString("observaciones"));
				pedidos.add(p);
			}
			rs.close();
			sent.close();
			miConexion.close();
		}catch(SQLException s){
;
		}
		return pedidos;
	}

	@Override
	public Pedido findById(int codigoPedido)throws SQLErrorException {
		Pedido p = null;
		List <Residuos> residuos = new ArrayList<Residuos>();
		try {
			miConexion = DriverManager.getConnection(urlConexion,"root","");
			PreparedStatement ps = (PreparedStatement) miConexion.prepareStatement("SELECT * FROM pedidos p " + 
															"JOIN vivienda v ON p.id_vivienda = v.id_vivienda " + 
															"JOIN dueno n ON v.id_dueño = n.id_dueño " +
															"WHERE p.id_pedido = ? ");
			ps.setInt(1, codigoPedido);
			rs = ps.executeQuery();
			while(rs.next()) {
				Persona p1 = new Dueño(rs.getInt("id_dueño"),rs.getInt("DNI"), rs.getString("nombre"),rs.getString("apellido"));
				Vivienda v = new Vivienda(rs.getInt("id_vivienda"),rs.getDate("fecha_registro"),rs.getString("ciudad"),rs.getString("provincia"),rs.getLong("latitud"),rs.getLong("longitud"),rs.getString("calle"), rs.getInt("num_calle"),p1);
				p = new Pedido(rs.getInt("id_pedido"), v ,rs.getDate("fecha"), rs.getBoolean("cargaPesada"), rs.getString("observaciones"));
			}
			PreparedStatement ps1 = (PreparedStatement) miConexion.prepareStatement("SELECT * FROM residuosarecolectar ra " + 
					"JOIN tiporesiduo tp ON ra.id_tiporesiduo = tp.id_tiporesiduo " + 
					"WHERE ra.id_pedido = ? ");
			ps1.setInt(1, p.getId());
			rs = ps1.executeQuery();
			while(rs.next()) {
				TipoResiduo t = new TipoResiduo(rs.getInt("id_tiporesiduo"),rs.getString("nombre_tiporesiduo"), rs.getInt("puntoskg"));
				Residuos r = new Residuos(rs.getInt("id_residuo"), rs.getInt("kg"), t, p);
				residuos.add(r);
			}
			for(Residuos item : residuos) {
				p.agregarResiduo(item);
			}
			rs.close();
			ps1.close();
			ps.close();
			miConexion.close();
		}catch(SQLException s){
			throw new SQLErrorException(""+s.getMessage());
		}
		return p;
	}

	@Override
	public Pedido findByFechayV(Vivienda v, java.sql.Date d) throws SQLErrorException{
		Pedido p = null;
		try {
			miConexion = DriverManager.getConnection(urlConexion,"root","");
			PreparedStatement ps = (PreparedStatement) miConexion.prepareStatement("SELECT * FROM pedidos WHERE id_vivienda=? AND fecha=?");
			ps.setInt(1, v.getId());
			ps.setDate(2, d);
			rs = ps.executeQuery();
			while(rs.next()) {
				p = new Pedido(rs.getInt("id_pedido"),v,rs.getDate("fecha"),rs.getBoolean("cargaPesada"),rs.getString("observaciones"));
			}
			rs.close();
			ps.close();
			miConexion.close();
		}catch(SQLException s){
			throw new SQLErrorException(""+s.getMessage());
		}
		return p;
	}

	@Override
	public List<Pedido> findByVivienda(int codigoVivienda) throws SQLErrorException{
		List <Pedido> pedidos = new ArrayList <Pedido>();
		try {
			miConexion = DriverManager.getConnection(urlConexion,"root","");
			PreparedStatement ps = (PreparedStatement) miConexion.prepareStatement("SELECT * FROM pedidos p " + 
															"JOIN vivienda v ON p.id_vivienda = v.id_vivienda " + 
															"JOIN dueno n ON v.id_dueño = n.id_dueño " +
															"WHERE p.id_vivienda = ? ");
			ps.setInt(1, codigoVivienda);
			rs = ps.executeQuery();
			while(rs.next()) {
				Persona p1 = new Dueño(rs.getInt("id_dueño"),rs.getInt("DNI"), rs.getString("nombre"),rs.getString("apellido"));
				Vivienda v = new Vivienda(rs.getInt("id_vivienda"),rs.getDate("fecha_registro"),rs.getString("ciudad"),rs.getString("provincia"),rs.getLong("latitud"),rs.getLong("longitud"),rs.getString("calle"), rs.getInt("num_calle"),p1);
				Pedido p = new Pedido(rs.getInt("id_pedido"), v ,rs.getDate("fecha"), rs.getBoolean("cargaPesada"), rs.getString("observaciones"));
				pedidos.add(p);
			}
			for(Pedido item : pedidos) {
				PreparedStatement ps1 = (PreparedStatement) miConexion.prepareStatement("SELECT * FROM residuosarecolectar ra " + 
						"JOIN pedidos p ON ra.id_pedido = p.id_pedido " + 
						"JOIN tiporesiduo tp ON ra.id_tiporesiduo = tp.id_tiporesiduo " + 
						"WHERE ra.id_pedido = ? ");
				ps1.setInt(1, item.getId());
				rs = ps1.executeQuery();
				while(rs.next()) {
					TipoResiduo t = new TipoResiduo(rs.getInt("id_tiporesiduo"),rs.getString("nombre_tiporesiduo"), rs.getInt("puntoskg"));
					Residuos r = new Residuos(rs.getInt("id_residuo"), rs.getInt("kg"), t, item);
					item.agregarResiduo(r);
				}
				ps1.close();
			}
			rs.close();
			ps.close();
			miConexion.close();
		}catch(SQLException s){
			throw new SQLErrorException(""+s.getMessage());
		}
		return pedidos;
	}
}
