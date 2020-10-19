package ar.edu.unrn.seminario.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.PreparedStatement;

import ar.edu.unrn.seminario.excepciones.SQLErrorException;
import ar.edu.unrn.seminario.modelo.Dueño;
import ar.edu.unrn.seminario.modelo.Membresia;

public class MembresiaDAOjdbc implements MembresiaDAO {

	private String urlConexion = "jdbc:mysql://localhost:3306/cooperativaviviendas";
	Connection miConexion = null;
	ResultSet rs = null;
	Statement sent = null;
	
	@Override
	public void create(Membresia m)throws SQLErrorException {
		try {
			miConexion = DriverManager.getConnection(urlConexion,"root","");
			sent = miConexion.createStatement();
			PreparedStatement ps = (PreparedStatement) miConexion.prepareStatement("insert into membresia (id_dueño, puntos, fecha, id_municipioclub) values (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1,m.getDueño().getId());
			ps.setInt(2,m.getPuntos());
			ps.setDate(3, new java.sql.Date(m.getFechaRegistro().getTime()));
			ps.setInt(4, 1);
			ps.executeUpdate();
			rs = ps.getGeneratedKeys();
			if(rs != null && rs.next()) {
				System.out.println("Se agrego con exito");
			}
			rs.close();
			sent.close();
			miConexion.close();
		}catch(SQLException s) {
			throw new SQLErrorException(""+s.getMessage());
		}
	}

	@Override
	public void update(Membresia m) throws SQLErrorException{
		try {
			miConexion = DriverManager.getConnection(urlConexion,"root","");
			Statement sent = miConexion.createStatement();
			PreparedStatement ps = (PreparedStatement) miConexion.prepareStatement("UPDATE membresia "
					+ "SET id_dueño = ? , puntos = ? , fecha = ? , id_municipioclub = ? "
					+ "WHERE id_membresia = ?");
			ps.setInt(1,m.getDueño().getId());
			ps.setInt(2,m.getPuntos());
			ps.setDate(3,(Date) m.getFechaRegistro());
			ps.setInt(4, 1);
			ps.setInt(5, m.getId());
			int cant = ps.executeUpdate();
			if(cant == 1) {
				System.out.println("Se modifico la membresia con exito");
			}
			ps.close();
			sent.close();
			miConexion.close();
		}catch(SQLException e)  {
			throw new SQLErrorException(""+e.getMessage());
		}
	}

	@Override
	public void delete(int codigoMembresia) throws SQLErrorException{
		try {
			miConexion = DriverManager.getConnection(urlConexion,"root","");
			Statement sent = miConexion.createStatement();
			PreparedStatement ps = (PreparedStatement) miConexion.prepareStatement("DELETE FROM membresia WHERE id_membresia = ?");
			ps.setInt(1, codigoMembresia);
			int cant = ps.executeUpdate();
			if(cant == 1) {
				System.out.println("Se elimino la membresia con exito con exito");
			}
			rs.close();
			sent.close();
			miConexion.close();
		}catch(SQLException e) {
			throw new SQLErrorException(""+e.getMessage());
		}
	}

	@Override
	public void delete(Membresia m)throws SQLErrorException {
		try {
			miConexion = DriverManager.getConnection(urlConexion,"root","");
			Statement sent = miConexion.createStatement();
			PreparedStatement ps = (PreparedStatement) miConexion.prepareStatement("DELETE FROM membresia WHERE id_membresia = ?");
			ps.setInt(1, m.getId());
			int cant = ps.executeUpdate();
			if(cant == 1) {
				System.out.println("Se elimino la membresia con exito con exito");
			}
			rs.close();
			sent.close();
			miConexion.close();
		}catch(SQLException e) {
			throw new SQLErrorException(""+e.getMessage());
		}
	}

	@Override
	public List<Membresia> findAll() throws SQLErrorException{
		List <Membresia> membresias = new ArrayList<Membresia>();
		try {
			miConexion = DriverManager.getConnection(urlConexion,"root","");
			sent = miConexion.createStatement();
			rs = sent.executeQuery("SELECT * FROM membresia m "
					+ "JOIN dueno d ON m.id_dueño = d.id_dueño");
			while(rs.next()) {
				Dueño du = new Dueño(rs.getInt("id_dueño"), rs.getInt("DNI"),rs.getString("apellido"), rs.getString("nombre"));
				Membresia m = new Membresia(rs.getInt("id_membresia"), du, rs.getInt("puntos"), rs.getDate("fecha"));
				membresias.add(m);
			}
			rs.close();
			sent.close();
			miConexion.close();
		}catch(SQLException s){
			throw new SQLErrorException(""+s.getMessage());
		}
		return membresias;
	}

	@Override
	public Membresia findById(int codigoMembresia) throws SQLErrorException{
		Membresia v = null;
		try {
			miConexion = DriverManager.getConnection(urlConexion,"root","");
			PreparedStatement ps = (PreparedStatement) miConexion.prepareStatement("SELECT * FROM membresia m "
					+ " JOIN dueno d ON m.id_dueño = d.id_dueño "
					+ " WHERE m.id_membresia = ? ");
			ps.setInt(1, codigoMembresia);
			rs = ps.executeQuery();
			while(rs.next()) {
				Dueño du = new Dueño(rs.getInt("id_dueño"), rs.getInt("DNI"),rs.getString("apellido"), rs.getString("nombre"));
				v = new Membresia(rs.getInt("id_membresia"),du, rs.getInt("puntos"), rs.getDate("fecha"));
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

	@Override
	public Membresia findByDniDueño(int dni) throws SQLErrorException{
		Membresia v = null;
		try {
			miConexion = DriverManager.getConnection(urlConexion,"root","");
			PreparedStatement ps = (PreparedStatement) miConexion.prepareStatement("SELECT * FROM membresia m JOIN dueno d ON m.id_dueño = d.id_dueño WHERE d.DNI = ?");
			ps.setInt(1, dni);
			rs = ps.executeQuery();
			while(rs.next()) {
				Dueño du = new Dueño(rs.getInt("id_dueño"), rs.getInt("DNI"),rs.getString("apellido"), rs.getString("nombre"));
				v = new Membresia(rs.getInt("id_membresia"), du, rs.getInt("puntos"), rs.getDate("fecha"));
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
