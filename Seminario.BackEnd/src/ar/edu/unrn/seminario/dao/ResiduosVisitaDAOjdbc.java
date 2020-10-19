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

public class ResiduosVisitaDAOjdbc implements ResiduosDAO{

	private String urlConexion = "jdbc:mysql://localhost:3306/cooperativaviviendas";
	Connection miConexion = null;
	ResultSet rs = null;
	Statement sent = null;
	
	@Override
	public void create(Residuos r) throws SQLErrorException{
		try {
			miConexion = DriverManager.getConnection(urlConexion,"root","");
			sent = miConexion.createStatement();
			PreparedStatement ps = (PreparedStatement) miConexion.prepareStatement("insert into residuosrecolectados (id_tiporesiduo, kg, id_visita) values (?,?,?)", Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1,r.getTipo().getId());
			ps.setInt(2,r.getKg());
			ps.setInt(3,r.getV().getId());
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
	public void update(Residuos r) throws SQLErrorException{
		try {
			miConexion = DriverManager.getConnection(urlConexion,"root","");
			Statement sent = miConexion.createStatement();
			PreparedStatement ps = (PreparedStatement) miConexion.prepareStatement("UPDATE residuosrecolectados "
					+ "SET  id_tiporesiduo = ? , kg = ? , id_visita = ? "
					+ "WHERE id_residuo = ? ");
			ps.setInt(1,r.getTipo().getId());
			ps.setInt(2,r.getKg());
			ps.setInt(3,r.getV().getId());
			ps.setInt(4,r.getId());
			int cant = ps.executeUpdate();
			if(cant == 1) {
				System.out.println("Se modifico el residuos con exito");
			}
			ps.close();
			sent.close();
			miConexion.close();
		}catch(SQLException e) {
			throw new SQLErrorException(""+e.getMessage());
		}
	}

	@Override
	public void delete(int codigoResiduo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Residuos r) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Residuos> findAll() throws SQLErrorException{
		List <Residuos> residuos = new ArrayList<Residuos>();
		try {
			miConexion = DriverManager.getConnection(urlConexion,"root","");
			sent = miConexion.createStatement();
			rs = sent.executeQuery("SELECT * FROM residuosrecolectados rr "
					+ "JOIN visita v ON rr.id_visita = v.id_visita "
					+ "JOIN ordenderetiro or ON v.id_ordenderetiro = or.id_ordenderetiro "
					+ "JOIN recolector rec ON or.id_recolector = rec.id_recolector "
					+ "JOIN pedido p ON or.id_pedido = p.id_pedido "
					+ "JOIN vivienda v1 ON v.id_vivienda = v1.id_vivienda "
					+ "JOIN dueno n ON v.id_dueño = n.id_dueño "
					+ "JOIN tiporesiduo tp ON rr.id_tiporesiduo = tp.id_residuo");
			while(rs.next()) {
				Persona p1 = new Dueño(rs.getInt("id_dueño"),rs.getInt("DNI"), rs.getString("nombre"),rs.getString("apellido"));
				Vivienda v = new Vivienda(rs.getInt("id_vivienda"),rs.getDate("fecha_registro"),rs.getString("ciudad"),rs.getString("provincia"),rs.getLong("latitud"),rs.getLong("longitud"),rs.getString("calle"), rs.getInt("num_calle"),p1);
				Pedido pe = new Pedido(rs.getInt("id_pedido"),v,rs.getDate("fecha"),rs.getBoolean("cargaPesada"),rs.getString("observaciones"));
				Persona rec = new Recolector(rs.getInt("id_recolector"),rs.getString("nombre_recolector"),rs.getString("apellido_recolector"),rs.getInt("dni_recolector"),rs.getBoolean("ocupado"));
				OrdenDeRetiro orden = new OrdenDeRetiro(rs.getInt("id_ordenderetiro"),pe,rec,rs.getDate("fecha"),rs.getString("estado"));
				Visita vi = new Visita(rs.getInt("id_visita"),v,rs.getDate("fecha"),rs.getString("observaciones"),orden );
				TipoResiduo t = new TipoResiduo(rs.getInt("id_tiporesiduo"),rs.getString("nombre_tiporesiduo"), rs.getInt("puntoskg"));
				Residuos r = new Residuos(rs.getInt("id_residuo"), rs.getInt("kg"), t, vi);
				residuos.add(r);
			}
			rs.close();
			sent.close();
			miConexion.close();
		}catch(SQLException s){
			throw new SQLErrorException(""+s.getMessage());
		}
		return residuos;
	}

	@Override
	public Residuos findById(int codigoResiduo)throws SQLErrorException {
		Residuos res = null;
		try {
			miConexion = DriverManager.getConnection(urlConexion,"root","");
			PreparedStatement ps = (PreparedStatement) miConexion.prepareStatement("SELECT * FROM residuosrecolectados rr "
					+ "JOIN visita v ON rr.id_visita = v.id_visita "
					+ "JOIN ordenderetiro or ON v.id_ordenderetiro = or.id_ordenderetiro "
					+ "JOIN recolector rec ON or.id_recolector = rec.id_recolector "
					+ "JOIN pedido p ON or.id_pedido = p.id_pedido "
					+ "JOIN vivienda v1 ON v.id_vivienda = v1.id_vivienda "
					+ "JOIN dueno n ON v.id_dueño = n.id_dueño "
					+ "JOIN tiporesiduo tp ON rr.id_tiporesiduo = tp.id_residuo "
					+ "WHERE rr.id_residuo = ? ");
			ps.setInt(1, codigoResiduo);
			rs = ps.executeQuery();
			while(rs.next()) {
				Persona p1 = new Dueño(rs.getInt("id_dueño"),rs.getInt("DNI"), rs.getString("nombre"),rs.getString("apellido"));
				Vivienda v = new Vivienda(rs.getInt("id_vivienda"),rs.getDate("fecha_registro"),rs.getString("ciudad"),rs.getString("provincia"),rs.getLong("latitud"),rs.getLong("longitud"),rs.getString("calle"), rs.getInt("num_calle"),p1);
				Pedido pe = new Pedido(rs.getInt("id_pedido"),v,rs.getDate("fecha"),rs.getBoolean("cargaPesada"),rs.getString("observaciones"));
				Persona rec = new Recolector(rs.getInt("id_recolector"),rs.getString("nombre_recolector"),rs.getString("apellido_recolector"),rs.getInt("dni_recolector"),rs.getBoolean("ocupado"));
				OrdenDeRetiro orden = new OrdenDeRetiro(rs.getInt("id_ordenderetiro"),pe,rec,rs.getDate("fecha"),rs.getString("estado"));
				Visita vi = new Visita(rs.getInt("id_visita"),v,rs.getDate("fecha"),rs.getString("observaciones"),orden );
				TipoResiduo t = new TipoResiduo(rs.getInt("id_tiporesiduo"),rs.getString("nombre_tiporesiduo"), rs.getInt("puntoskg"));
				res = new Residuos(rs.getInt("id_residuo"), rs.getInt("kg"), t, vi); 
			}
			rs.close();
			ps.close();
			miConexion.close();
		}catch(SQLException s){
			throw new SQLErrorException(""+s.getMessage());
		} 
		return res;
	}

	@Override
	public List<Residuos> findResiduosXCodigoPedido(int codigoPedido) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Residuos> findResiduosXCodigoVisita(int codigoVisita) throws SQLErrorException{
		List <Residuos> res = new ArrayList<Residuos>();
		try {
			miConexion = DriverManager.getConnection(urlConexion,"root","");
			PreparedStatement ps = (PreparedStatement) miConexion.prepareStatement("SELECT * FROM residuosrecolectados rr "
					+ "JOIN visita v ON rr.id_visita = v.id_visita "
					+ "JOIN ordenderetiro orden ON v.id_ordenderetiro = orden.id_ordenderetiro "
					+ "JOIN recolector rec ON orden.id_recolector = rec.id_recolector "
					+ "JOIN pedidos p ON orden.id_pedido = p.id_pedido "
					+ "JOIN vivienda v1 ON v.id_vivienda = v1.id_vivienda "
					+ "JOIN dueno n ON v1.id_dueño = n.id_dueño "
					+ "JOIN tiporesiduo tp ON rr.id_tiporesiduo = tp.id_tiporesiduo "
					+ "WHERE v.id_visita = ? ");
			ps.setInt(1, codigoVisita);
			rs = ps.executeQuery();
			while(rs.next()) {
				Persona p1 = new Dueño(rs.getInt("id_dueño"),rs.getInt("DNI"), rs.getString("nombre"),rs.getString("apellido"));
				Vivienda v = new Vivienda(rs.getInt("id_vivienda"),rs.getDate("fecha_registro"),rs.getString("ciudad"),rs.getString("provincia"),rs.getLong("latitud"),rs.getLong("longitud"),rs.getString("calle"), rs.getInt("num_calle"),p1);
				Pedido pe = new Pedido(rs.getInt("id_pedido"),v,rs.getDate("fecha"),rs.getBoolean("cargaPesada"),rs.getString("observaciones"));
				Persona rec = new Recolector(rs.getInt("id_recolector"),rs.getString("nombre_recolector"),rs.getString("apellido_recolector"),rs.getInt("dni_recolector"),rs.getBoolean("ocupado"));
				OrdenDeRetiro orden = new OrdenDeRetiro(rs.getInt("id_ordenderetiro"),pe,rec,rs.getDate("fecha"),rs.getString("estado"));
				Visita vi = new Visita(rs.getInt("id_visita"),v,rs.getDate("fecha"),rs.getString("observaciones"),orden );
				TipoResiduo t = new TipoResiduo(rs.getInt("id_tiporesiduo"),rs.getString("nombre_tiporesiduo"), rs.getInt("puntoskg"));
				Residuos r = new Residuos(rs.getInt("id_residuo"), rs.getInt("kg"), t, vi);
				res.add(r);
			}
			rs.close();
			ps.close();
			miConexion.close();
		}catch(SQLException s){
			throw new SQLErrorException(""+s.getMessage());
		}
		return res;
	}

}
