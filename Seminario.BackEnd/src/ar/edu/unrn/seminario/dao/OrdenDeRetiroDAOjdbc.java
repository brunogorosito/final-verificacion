package ar.edu.unrn.seminario.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.PreparedStatement;

import ar.edu.unrn.seminario.excepciones.SQLErrorException;
import ar.edu.unrn.seminario.modelo.Dueño;
import ar.edu.unrn.seminario.modelo.OrdenDeRetiro;
import ar.edu.unrn.seminario.modelo.Pedido;
import ar.edu.unrn.seminario.modelo.Persona;
import ar.edu.unrn.seminario.modelo.Recolector;
import ar.edu.unrn.seminario.modelo.Residuos;
import ar.edu.unrn.seminario.modelo.TipoResiduo;
import ar.edu.unrn.seminario.modelo.Visita;
import ar.edu.unrn.seminario.modelo.Vivienda;

public class OrdenDeRetiroDAOjdbc implements OrdenDeRetiroDAO{

	private String urlConexion = "jdbc:mysql://localhost:3306/cooperativaviviendas";
	Connection miConexion = null;
	ResultSet rs = null;
	Statement sent = null;

	@Override
	public void create(OrdenDeRetiro e)throws SQLErrorException {
		try {
			miConexion = DriverManager.getConnection(urlConexion,"root","");
			sent = miConexion.createStatement();
			PreparedStatement ps = (PreparedStatement) miConexion.prepareStatement("insert into ordenderetiro (fecha, id_pedido, id_recolector, estado) values (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
			ps.setDate(1, Date.valueOf(LocalDate.now()));
			ps.setInt(2,e.getPedido().getId());
			ps.setInt(3,e.getRecolector().getId());
			ps.setString(4,e.getEstado());
			ps.executeUpdate();
			rs = ps.getGeneratedKeys();
			if(rs != null && rs.next()) {
				System.out.println("Se agrego con exito");
			}
			rs.close();
			sent.close();
			miConexion.close();
		}catch(SQLException l) {
			throw new SQLErrorException(""+l.getMessage());
		}
	}

	@Override
	public void update(OrdenDeRetiro e) throws SQLErrorException{
		try {
			miConexion = DriverManager.getConnection(urlConexion,"root","");
			Statement sent = miConexion.createStatement();
			PreparedStatement ps = (PreparedStatement) miConexion.prepareStatement(" UPDATE ordenderetiro "
					+ " SET estado = ? WHERE id_ordenderetiro = ? " );
			ps.setString(1, e.getEstado());
			ps.setInt(2, e.getId());
			int cant = ps.executeUpdate();
			if(cant == 1) {
				System.out.println("Se modifico la orden con exito");
			}
			ps.close();
			sent.close();
			miConexion.close();
		}catch(SQLException e1) {
			throw new SQLErrorException(""+e1.getMessage());
		}
		
	}

	@Override
	public void delete(int codigoOrdenDeRetiro) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(OrdenDeRetiro e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<OrdenDeRetiro> findAll() throws SQLErrorException{
		List <OrdenDeRetiro> ordenes = new ArrayList<OrdenDeRetiro>();
		try {
			miConexion = DriverManager.getConnection(urlConexion,"root","");
			sent = miConexion.createStatement();
			rs = sent.executeQuery("SELECT * FROM ordenderetiro o "
					+ "JOIN pedidos p ON o.id_pedido = p.id_pedido "
					+ "JOIN recolector r ON o.id_recolector = r.id_recolector "
					+ "JOIN vivienda v ON p.id_vivienda = v.id_vivienda "
					+ "JOIN dueno n ON v.id_dueño = n.id_dueño ");
			while(rs.next()) {
				Persona p1 = new Dueño(rs.getInt("id_dueño"),rs.getInt("DNI"), rs.getString("nombre"),rs.getString("apellido"));
				Vivienda v = new Vivienda(rs.getInt("id_vivienda"),rs.getDate("fecha_registro"),rs.getString("ciudad"),rs.getString("provincia"),rs.getLong("latitud"),rs.getLong("longitud"),rs.getString("calle"), rs.getInt("num_calle"),p1);
				Pedido p = new Pedido(rs.getInt("id_pedido"), v ,rs.getDate("fecha"), rs.getBoolean("cargaPesada"), rs.getString("observaciones"));
				Persona r1 = new Recolector(rs.getInt("id_recolector"),rs.getString("nombre_recolector"),rs.getString("apellido_recolector"),rs.getInt("dni_recolector"),rs.getBoolean("ocupado"));
				OrdenDeRetiro d = new OrdenDeRetiro(rs.getInt("id_ordenderetiro"),p, r1, rs.getDate("fecha"), rs.getString("estado"));
				ordenes.add(d);
			}
			rs.close();
			sent.close();
			miConexion.close();
		}catch(SQLException s){
			throw new SQLErrorException(""+s.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
		}
		return ordenes;
	}

	@Override
	public OrdenDeRetiro findByPedidoId(int codigoOrdenDeRetiro)throws SQLErrorException{
		OrdenDeRetiro d1 = null;
		try {
			miConexion = DriverManager.getConnection(urlConexion,"root","");
			PreparedStatement ps = (PreparedStatement) miConexion.prepareStatement("SELECT * FROM ordenderetiro o "
											+ "JOIN pedidos p ON o.id_pedido = p.id_pedido "
											+ "JOIN recolector r ON o.id_recolector = r.id_recolector "
											+ "JOIN vivienda v ON p.id_vivienda = v.id_vivienda "
											+ "JOIN dueno n ON v.id_dueño = n.id_dueño "
											+ "WHERE o.id_pedido = ? ");
			ps.setInt(1, codigoOrdenDeRetiro);
			rs = ps.executeQuery();
			while(rs.next()) {
				Persona p1 = new Dueño(rs.getInt("id_dueño"),rs.getInt("DNI"), rs.getString("nombre"),rs.getString("apellido"));
				Vivienda v = new Vivienda(rs.getInt("id_vivienda"),rs.getDate("fecha_registro"),rs.getString("ciudad"),rs.getString("provincia"),rs.getLong("latitud"),rs.getLong("longitud"),rs.getString("calle"), rs.getInt("num_calle"),p1);
				Pedido p = new Pedido(rs.getInt("id_pedido"), v ,rs.getDate("fecha"), rs.getBoolean("cargaPesada"), rs.getString("observaciones"));
				Persona r1 = new Recolector(rs.getInt("id_recolector"),rs.getString("nombre_recolector"),rs.getString("apellido_recolector"),rs.getInt("dni_recolector"),rs.getBoolean("ocupado"));
				d1 = new OrdenDeRetiro(rs.getInt("id_ordenderetiro"),p, r1, rs.getDate("fecha"), rs.getString("estado"));
			}
			rs.close();
			ps.close();
			miConexion.close();
		}catch(SQLException s){
			throw new SQLErrorException(""+s.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return d1;
	}

	@Override
	public OrdenDeRetiro findById(int codigoOrdenDeRetiro)throws SQLErrorException{
		OrdenDeRetiro d1 = null;
		try {
			miConexion = DriverManager.getConnection(urlConexion,"root","");
			PreparedStatement ps = (PreparedStatement) miConexion.prepareStatement("SELECT * FROM ordenderetiro o "
					+ "JOIN pedidos p ON o.id_pedido = p.id_pedido "
					+ "JOIN recolector r ON o.id_recolector = r.id_recolector "
					+ "JOIN vivienda v ON p.id_vivienda = v.id_vivienda "
					+ "JOIN dueno n ON v.id_dueño = n.id_dueño "
					+ "WHERE o.id_ordenderetiro = ? ");
			ps.setInt(1, codigoOrdenDeRetiro);
			rs = ps.executeQuery();
			while(rs.next()) {
				Persona p1 = new Dueño(rs.getInt("id_dueño"),rs.getInt("DNI"), rs.getString("nombre"),rs.getString("apellido"));
				Vivienda v = new Vivienda(rs.getInt("id_vivienda"),rs.getDate("fecha_registro"),rs.getString("ciudad"),rs.getString("provincia"),rs.getLong("latitud"),rs.getLong("longitud"),rs.getString("calle"), rs.getInt("num_calle"),p1);
				Pedido p = new Pedido(rs.getInt("id_pedido"), v ,rs.getDate("fecha"), rs.getBoolean("cargaPesada"), rs.getString("observaciones"));
				Persona r1 = new Recolector(rs.getInt("id_recolector"),rs.getString("nombre_recolector"),rs.getString("apellido_recolector"),rs.getInt("dni_recolector"),rs.getBoolean("ocupado"));
				d1 = new OrdenDeRetiro(rs.getInt("id_ordenderetiro"),p, r1, rs.getDate("fecha"),rs.getString("estado"));
			}
			PreparedStatement ps1 = (PreparedStatement) miConexion.prepareStatement("SELECT * FROM residuosarecolectar ra " + 
					"JOIN tiporesiduo tp ON ra.id_tiporesiduo = tp.id_tiporesiduo " + 
					"WHERE ra.id_pedido = ? ");
			ps1.setInt(1,d1.getIdPedido());
			List <Residuos> residuos = new ArrayList<Residuos>();
			Pedido ped = d1.getPedido();
			rs = ps1.executeQuery();
			while(rs.next()) {
				TipoResiduo t = new TipoResiduo(rs.getInt("id_tiporesiduo"),rs.getString("nombre_tiporesiduo"), rs.getInt("puntoskg"));
				Residuos r = new Residuos(rs.getInt("id_residuo"), rs.getInt("kg"), t, ped);
				residuos.add(r);
			}
			for(Residuos item : residuos) {
				ped.agregarResiduo(item);
			}
			rs.close();
			ps.close();
			miConexion.close();
		}catch(SQLException s){
			throw new SQLErrorException(""+s.getMessage());
		}
		return d1;
	}

	@Override
	public OrdenDeRetiro findByIdVisita(int codigoVisita)throws SQLErrorException {
		OrdenDeRetiro d1 = null;
		try {
			miConexion = DriverManager.getConnection(urlConexion,"root","");
			PreparedStatement ps = (PreparedStatement) miConexion.prepareStatement("SELECT * FROM ordenderetiro o "
											+ "JOIN visita vi ON vi.id_ordenderetiro = o.id_ordenderetiro "
											+ "JOIN pedidos p ON o.id_pedido = p.id_pedido "
											+ "JOIN recolector r ON o.id_recolector = r.id_recolector "
											+ "JOIN vivienda v ON p.id_vivienda = v.id_vivienda "
											+ "JOIN dueno n ON v.id_dueño = n.id_dueño "
											+ "WHERE vi.id_visita = ? ");
			ps.setInt(1, codigoVisita);
			rs = ps.executeQuery();
			while(rs.next()) {
				Persona p1 = new Dueño(rs.getInt("id_dueño"),rs.getInt("DNI"), rs.getString("nombre"),rs.getString("apellido"));
				Vivienda v = new Vivienda(rs.getInt("id_vivienda"),rs.getDate("fecha_registro"),rs.getString("ciudad"),rs.getString("provincia"),rs.getLong("latitud"),rs.getLong("longitud"),rs.getString("calle"), rs.getInt("num_calle"),p1);
				Pedido p = new Pedido(rs.getInt("id_pedido"), v ,rs.getDate("fecha"), rs.getBoolean("cargaPesada"), rs.getString("observaciones"));
				Persona r1 = new Recolector(rs.getInt("id_recolector"),rs.getString("nombre_recolector"),rs.getString("apellido_recolector"),rs.getInt("dni_recolector"),rs.getBoolean("ocupado"));
				d1 = new OrdenDeRetiro(rs.getInt("id_ordenderetiro"),p, r1, rs.getDate("fecha"), rs.getString("estado"));
			}
			rs.close();
			ps.close();
			miConexion.close();
		}catch(SQLException s){
			throw new SQLErrorException(""+s.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return d1;
	}

	@Override
	public void updateOyV(OrdenDeRetiro e, Visita v) {
		try {
			miConexion = DriverManager.getConnection(urlConexion,"root","");
			Statement sent = miConexion.createStatement();
			PreparedStatement ps = (PreparedStatement) miConexion.prepareStatement(" UPDATE ordenderetiro "
					+ " SET estado = ? WHERE id_ordenderetiro = ? " );
			ps.setString(1, e.getEstado());
			ps.setInt(2, e.getId());
			int cant = ps.executeUpdate();
			if(cant == 1) {
				System.out.println("Se modifico la orden con exito");
			}
			PreparedStatement ps1 = (PreparedStatement) miConexion.prepareStatement("insert into visita (id_vivienda, fecha, observaciones, id_ordenderetiro ) values (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
			ps1.setInt(1,v.getVivienda().getId());
			ps1.setDate(2, new java.sql.Date(v.getFecha().getTime()));
			ps1.setString(3, v.getObservaciones());
			ps1.setInt(4, e.getId());
			ps1.executeUpdate();
			rs = ps1.getGeneratedKeys();
			if(rs != null && rs.next()) {
				System.out.println("Se agrego con exito la visita a la orden");
			}
			ps.close();
			sent.close();
			miConexion.close();
		}catch(SQLException e1) {
			throw new SQLErrorException(""+e1.getMessage());
		}
	}
	
}
