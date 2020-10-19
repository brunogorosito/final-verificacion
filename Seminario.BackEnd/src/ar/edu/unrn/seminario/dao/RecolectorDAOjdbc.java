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
import ar.edu.unrn.seminario.modelo.Recolector;

public class RecolectorDAOjdbc implements RecolectorDAO{
	
	private String urlConexion = "jdbc:mysql://localhost:3306/cooperativaviviendas";
	Connection miConexion = null;
	ResultSet rs = null;
	Statement sent = null;

	@Override
	public void create(Recolector r)throws SQLErrorException {
		try {
			miConexion = DriverManager.getConnection(urlConexion,"root","");
			sent = miConexion.createStatement();
			PreparedStatement ps = (PreparedStatement) miConexion.prepareStatement("insert into recolector (nombre_recolector, apellido_recolector, dni_recolector, ocupado) values (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
			ps.setString(1,r.getNombre());
			ps.setString(2,r.getApellido());
			ps.setInt(3,r.getDNI());
			ps.setBoolean(4, r.isOcupado());
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
	public void update(Recolector r) throws SQLErrorException{
		try {
			miConexion = DriverManager.getConnection(urlConexion,"root","");
			Statement sent = miConexion.createStatement();
			PreparedStatement ps = (PreparedStatement) miConexion.prepareStatement(" UPDATE recolector "
					+ " SET nombre_recolector = ? , apellido_recolector = ? , dni_recolector = ? , ocupado = ? WHERE id_recolector = ? " );
			ps.setString(1,r.getNombre());
			ps.setString(2,r.getApellido());
			ps.setInt(3,r.getDNI());
			ps.setBoolean(4,r.isOcupado());
			ps.setInt(5,r.getId());
			int cant = ps.executeUpdate();
			if(cant == 1) {
				System.out.println("Se modifico el recolector con exito");
			}
			ps.close();
			sent.close();
			miConexion.close();
		}catch(SQLException e) {
			throw new SQLErrorException(""+e.getMessage());
		}
	}

	@Override
	public void delete(int codigoRecolector) throws SQLErrorException{
		try {
			miConexion = DriverManager.getConnection(urlConexion,"root","");
			Statement sent = miConexion.createStatement();
			PreparedStatement ps = (PreparedStatement) miConexion.prepareStatement("DELETE FROM recolector WHERE id_recolector = ?");
			ps.setInt(1, codigoRecolector);
			int cant = ps.executeUpdate();
			if(cant == 1) {
				System.out.println("Se elimino el recolector con exito");
			}
			ps = (PreparedStatement) miConexion.prepareStatement("UPDATE ordenderetiro SET id_estado = ? WHERE id_recolector = ?");
			ps.setInt(1, 5);
			ps.setInt(2, codigoRecolector);
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
	public void delete(Recolector r) throws SQLErrorException{
		try {
			miConexion = DriverManager.getConnection(urlConexion,"root","");
			Statement sent = miConexion.createStatement();
			PreparedStatement ps = (PreparedStatement) miConexion.prepareStatement("DELETE FROM recolector WHERE id_recolector = ?");
			ps.setInt(1, r.getId());
			int cant = ps.executeUpdate();
			if(cant == 1) {
				System.out.println("Se elimino el recolector con exito");
			}
			ps = (PreparedStatement) miConexion.prepareStatement("UPDATE ordenderetiro SET id_estado = ? WHERE id_recolector = ?");
			ps.setInt(1, 5);
			ps.setInt(2, r.getId());
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
	public List<Recolector> findAll() throws SQLErrorException{
		List <Recolector> recolectores = new ArrayList<Recolector>();
		try {
			miConexion = DriverManager.getConnection(urlConexion,"root","");
			sent = miConexion.createStatement();
			rs = sent.executeQuery("SELECT * FROM recolector");
			while(rs.next()) {
				Recolector d = new Recolector(rs.getInt("id_recolector"), rs.getString("nombre_recolector"),rs.getString("apellido_recolector"), rs.getInt("dni_recolector"), rs.getBoolean("ocupado"));
				recolectores.add(d);
			}
			rs.close();
			sent.close();
			miConexion.close();
		}catch(SQLException s){
			throw new SQLErrorException(""+s.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
		}
		return recolectores;
	}

	@Override
	public Recolector findById(int id) throws SQLErrorException{
		Recolector p1 = null;
		try {
			miConexion = DriverManager.getConnection(urlConexion,"root","");
			PreparedStatement ps = (PreparedStatement) miConexion.prepareStatement("SELECT * FROM recolector WHERE id_recolector = ?");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			while(rs.next()) {
				p1 = new Recolector(rs.getInt("id_recolector"), rs.getString("nombre_recolector"),rs.getString("apellido_recolector"), rs.getInt("dni_recolector"), rs.getBoolean("ocupado"));
			}
			rs.close();
			ps.close();
			miConexion.close();
		}catch(SQLException s){
			throw new SQLErrorException(""+s.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return p1;
	}

	@Override
	public Recolector findByDNI(int dni)throws SQLErrorException{
		Recolector p1 = null;
		try {
			miConexion = DriverManager.getConnection(urlConexion,"root","");
			PreparedStatement ps = (PreparedStatement) miConexion.prepareStatement("SELECT * FROM recolector WHERE dni_recolector = ?");
			ps.setInt(1, dni);
			rs = ps.executeQuery();
			while(rs.next()) {
				p1 = new Recolector(rs.getInt("id_recolector"), rs.getString("nombre_recolector"),rs.getString("apellido_recolector"), rs.getInt("dni_recolector"), rs.getBoolean("ocupado"));
			}
			rs.close();
			ps.close();
			miConexion.close();
		}catch(SQLException s){
			throw new SQLErrorException(""+s.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return p1;
	}

	
}
