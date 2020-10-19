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
import ar.edu.unrn.seminario.modelo.OrdenDeRetiro;
import ar.edu.unrn.seminario.modelo.Pedido;
import ar.edu.unrn.seminario.modelo.Persona;
import ar.edu.unrn.seminario.modelo.Recolector;
import ar.edu.unrn.seminario.modelo.Residuos;
import ar.edu.unrn.seminario.modelo.TipoResiduo;
import ar.edu.unrn.seminario.modelo.Visita;
import ar.edu.unrn.seminario.modelo.Vivienda;

public class VisitaDAOjdbc implements VisitaDAO{

	private String urlConexion = "jdbc:mysql://localhost:3306/cooperativaviviendas";
	Connection miConexion = null;
	ResultSet rs = null;
	Statement sent = null;
	
	@Override
	public void create(Visita e) throws SQLErrorException{
		try {
			miConexion = DriverManager.getConnection(urlConexion,"root","");
			sent = miConexion.createStatement();
			PreparedStatement ps = (PreparedStatement) miConexion.prepareStatement("insert into visita (id_vivienda, fecha, observaciones, id_ordenderetiro ) values (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1,e.getVivienda().getId());
			ps.setDate(2, new java.sql.Date(e.getFecha().getTime()));
			ps.setString(3, e.getObservaciones());
			ps.setInt(4, e.getOrden().getId());
			ps.executeUpdate();
			rs = ps.getGeneratedKeys();
			if(rs != null && rs.next()) {
				System.out.println("Se agrego con exito");
			}
			rs.close();
			sent.close();
			miConexion.close();
		}catch(SQLException e1) {
			throw new SQLErrorException(""+e1.getMessage());
		}
	}

	@Override
	public void update(Visita e) throws SQLErrorException{
		try {
			miConexion = DriverManager.getConnection(urlConexion,"root","");
			Statement sent = miConexion.createStatement();
			PreparedStatement ps = (PreparedStatement) miConexion.prepareStatement("UPDATE visita "
					+ "SET id_vivienda = ? , fecha = ? , observaciones = ?, id_ordenderetiro = ? "
					+ "WHERE id_visita = ? ");
			ps.setInt(1,e.getVivienda().getId());
			ps.setDate(2,  new java.sql.Date(e.getFecha().getTime()));
			ps.setString(3,e.getObservaciones());
			ps.setInt(4,e.getOrden().getId());
			ps.setInt(5,e.getId());
			int cant = ps.executeUpdate();
			if(cant == 1) {
				System.out.println("Se modifico la visita con exito");
			}
			ps.close();
			sent.close();
			miConexion.close();
		}catch(SQLException e1) {
			throw new SQLErrorException(""+e1.getMessage());
		}
	}

	@Override
	public void delete(int codigoVisita) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Visita e) throws SQLErrorException{
		try {
			miConexion = DriverManager.getConnection(urlConexion,"root","");
			Statement sent = miConexion.createStatement();
			PreparedStatement ps = (PreparedStatement) miConexion.prepareStatement("DELETE FROM residuosrecolectados WHERE id_visita = ?");
			ps.setInt(1, e.getId());
			int cant = ps.executeUpdate();
			if(cant == 1) {
				System.out.println("Se eliminaron los residuos de la visita con exito");
			}
			ps = (PreparedStatement) miConexion.prepareStatement("DELETE FROM visita WHERE id_visita = ?");
			ps.setInt(1, e.getId());
			cant = ps.executeUpdate();
			if(cant == 1) {
				System.out.println("Se elimino la visita con exito");
			}
			rs.close();
			sent.close();
			miConexion.close();
		}catch(SQLException e1) {
			throw new SQLErrorException(""+e1.getMessage());
		}
		
	}

	@Override
	public List<Visita> findAll()throws SQLErrorException {
		List <Visita> visitas = new ArrayList<Visita>();
		try {
			miConexion = DriverManager.getConnection(urlConexion,"root","");
			sent = miConexion.createStatement();
			rs = sent.executeQuery("SELECT * FROM visita v "
					+ "JOIN ordenderetiro orden ON v.id_ordenderetiro = orden.id_ordenderetiro "
					+ "JOIN recolector rec ON orden.id_recolector = rec.id_recolector "
					+ "JOIN pedidos p ON orden.id_pedido = p.id_pedido "
					+ "JOIN vivienda v1 ON v.id_vivienda = v1.id_vivienda "
					+ "JOIN dueno n ON v1.id_dueño = n.id_dueño " );
			while(rs.next()) {
				Persona p1 = new Dueño(rs.getInt("id_dueño"),rs.getInt("DNI"), rs.getString("nombre"),rs.getString("apellido"));
				Vivienda v1 = new Vivienda(rs.getInt("id_vivienda"),rs.getDate("fecha_registro"),rs.getString("ciudad"),rs.getString("provincia"),rs.getLong("latitud"),rs.getLong("longitud"),rs.getString("calle"), rs.getInt("num_calle"),p1);
				Pedido pe = new Pedido(rs.getInt("id_pedido"),v1,rs.getDate("fecha"),rs.getBoolean("cargaPesada"),rs.getString("observaciones"));
				Persona rec = new Recolector(rs.getInt("id_recolector"),rs.getString("nombre_recolector"),rs.getString("apellido_recolector"),rs.getInt("dni_recolector"),rs.getBoolean("ocupado"));
				OrdenDeRetiro orden = new OrdenDeRetiro(rs.getInt("id_ordenderetiro"),pe,rec,rs.getDate("fecha"),rs.getString("estado"));
				Visita v = new Visita(rs.getInt("id_visita"),v1,rs.getDate("fecha"),rs.getString("observaciones"),orden );
				visitas.add(v);
			}
			rs.close();
			sent.close();
			miConexion.close();
		}catch(SQLException s){
			throw new SQLErrorException(""+s.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return visitas;
	}

	@Override
	public Visita findById(int codigoVisita)throws SQLErrorException {
		Visita v = null;
		List <Residuos> res = new ArrayList<Residuos>();
		try {
			miConexion = DriverManager.getConnection(urlConexion,"root","");
			PreparedStatement ps = (PreparedStatement) miConexion.prepareStatement("SELECT * FROM visita v "
					+ "JOIN ordenderetiro orden ON v.id_ordenderetiro = orden.id_ordenderetiro "
					+ "JOIN recolector rec ON orden.id_recolector = rec.id_recolector "
					+ "JOIN pedidos p ON orden.id_pedido = p.id_pedido "
					+ "JOIN vivienda v1 ON v.id_vivienda = v1.id_vivienda "
					+ "JOIN dueno n ON v1.id_dueño = n.id_dueño "
					+ "WHERE v.id_visita = ? " );
			ps.setInt(1, codigoVisita);
			rs = ps.executeQuery();
			while(rs.next()) {
				Persona p1 = new Dueño(rs.getInt("id_dueño"),rs.getInt("DNI"), rs.getString("nombre"),rs.getString("apellido"));
				Vivienda v1 = new Vivienda(rs.getInt("id_vivienda"),rs.getDate("fecha_registro"),rs.getString("ciudad"),rs.getString("provincia"),rs.getLong("latitud"),rs.getLong("longitud"),rs.getString("calle"), rs.getInt("num_calle"),p1);
				Pedido pe = new Pedido(rs.getInt("id_pedido"),v1,rs.getDate("fecha"),rs.getBoolean("cargaPesada"),rs.getString("observaciones"));
				Persona rec = new Recolector(rs.getInt("id_recolector"),rs.getString("nombre_recolector"),rs.getString("apellido_recolector"),rs.getInt("dni_recolector"),rs.getBoolean("ocupado"));
				OrdenDeRetiro orden = new OrdenDeRetiro(rs.getInt("id_ordenderetiro"),pe,rec,rs.getDate("fecha"),rs.getString("estado"));
				v = new Visita(rs.getInt("id_visita"),v1,rs.getDate("fecha"),rs.getString("observaciones"),orden );
			}
			PreparedStatement ps1 = (PreparedStatement) miConexion.prepareStatement("SELECT * FROM residuosrecolectados rr "
					+ "JOIN tiporesiduo tp ON rr.id_tiporesiduo = tp.id_tiporesiduo "
					+ "WHERE rr .id_visita = ? ");
			ps1.setInt(1, v.getId());
			rs = ps1.executeQuery();
			while(rs.next()) {
				TipoResiduo t = new TipoResiduo(rs.getInt("id_tiporesiduo"),rs.getString("nombre_tiporesiduo"), rs.getInt("puntoskg"));
				Residuos r = new Residuos(rs.getInt("id_residuo"), rs.getInt("kg"), t, v);
				res.add(r);
			}
			for(Residuos item : res) {
				v.agregarResiduos(item);
			}
			rs.close();
			ps.close();
			ps1.close();
			miConexion.close();
		}catch(SQLException s){
			throw new SQLErrorException(""+s.getMessage());
		}
		return v;
	}


	@Override
	public Visita findByFechayO(int id_orden, java.sql.Date d) throws SQLErrorException{
		Visita v = null;
		try {
			miConexion = DriverManager.getConnection(urlConexion,"root","");
			PreparedStatement ps = (PreparedStatement) miConexion.prepareStatement("SELECT * FROM visita v "
					+ "JOIN ordenderetiro orden ON v.id_ordenderetiro = orden.id_ordenderetiro "
					+ "JOIN recolector rec ON orden.id_recolector = rec.id_recolector "
					+ "JOIN pedidos p ON orden.id_pedido = p.id_pedido "
					+ "JOIN vivienda v1 ON v.id_vivienda = v1.id_vivienda "
					+ "JOIN dueno n ON v1.id_dueño = n.id_dueño "
					+ "WHERE orden.id_ordenderetiro = ? AND orden.fecha = ? " );
			ps.setInt(1, id_orden);
			ps.setDate(2, d);
			rs = ps.executeQuery();
			while(rs.next()) {
				Persona p1 = new Dueño(rs.getInt("id_dueño"),rs.getInt("DNI"), rs.getString("nombre"),rs.getString("apellido"));
				Vivienda v1 = new Vivienda(rs.getInt("id_vivienda"),rs.getDate("fecha_registro"),rs.getString("ciudad"),rs.getString("provincia"),rs.getLong("latitud"),rs.getLong("longitud"),rs.getString("calle"), rs.getInt("num_calle"),p1);
				Pedido pe = new Pedido(rs.getInt("id_pedido"),v1,rs.getDate("fecha"),rs.getBoolean("cargaPesada"),rs.getString("observaciones"));
				Persona rec = new Recolector(rs.getInt("id_recolector"),rs.getString("nombre_recolector"),rs.getString("apellido_recolector"),rs.getInt("dni_recolector"),rs.getBoolean("ocupado"));
				OrdenDeRetiro orden = new OrdenDeRetiro(rs.getInt("id_ordenderetiro"),pe,rec,rs.getDate("fecha"),rs.getString("estado"));
				v = new Visita(rs.getInt("id_visita"),v1,rs.getDate("fecha"),rs.getString("observaciones"),orden );
			}
			rs.close();
			ps.close();
			miConexion.close();
		}catch(SQLException s){
			throw new SQLErrorException(""+s.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return v;
	}

}
