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

public class DueñoDAOjdbc implements DueñoDAO{

	private String urlConexion = "jdbc:mysql://localhost:3306/cooperativaviviendas";
	Connection miConexion = null;
	ResultSet rs = null;
	Statement sent = null;
	
	@Override
	public void create(Dueño v) throws SQLErrorException{
		try {
			miConexion = DriverManager.getConnection(urlConexion,"root","");
			sent = miConexion.createStatement();
			PreparedStatement ps = (PreparedStatement) miConexion.prepareStatement("insert into dueno (DNI, nombre, apellido) values (?,?,?)", Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1,v.getDNI());
			ps.setString(2,v.getNombre());
			ps.setString(3,v.getApellido());
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
	public void update(Dueño v) throws SQLErrorException {
		try {
			miConexion = DriverManager.getConnection(urlConexion,"root","");
			Statement sent = miConexion.createStatement();
			PreparedStatement ps = (PreparedStatement) miConexion.prepareStatement("UPDATE dueno "
					+ "SET DNI = ? , nombre = ? , apellido = ?"
					+ "WHERE id_dueño = ?");
			ps.setInt(1,v.getDNI());
			ps.setString(2,v.getNombre());
			ps.setString(3,v.getApellido());
			ps.setInt(4, v.getId());
			int cant = ps.executeUpdate();
			if(cant == 1) {
				System.out.println("Se modifico el dueño con exito");
			}
			ps.close();
			sent.close();
			miConexion.close();
		}catch(SQLException e) {
			throw new SQLErrorException(""+e.getMessage());
		}
	}

	@Override
	public void delete(int codigoDueño)throws SQLErrorException {
		try {
			miConexion = DriverManager.getConnection(urlConexion,"root","");
			Statement sent = miConexion.createStatement();
			PreparedStatement ps = (PreparedStatement) miConexion.prepareStatement("DELETE FROM vivienda WHERE id_dueño = ?");
			ps.setInt(1, codigoDueño);
			int cant = ps.executeUpdate();
			if(cant == 1) {
				System.out.println("Se elimino la vivienda del dueño con exito");
			}
			ps = (PreparedStatement) miConexion.prepareStatement("DELETE FROM membresia WHERE id_dueño = ?");
			ps.setInt(1, codigoDueño);
			cant = ps.executeUpdate();
			if(cant == 1) {
				System.out.println("Se elimino la membresia del dueño con exito");
			}
			ps = (PreparedStatement) miConexion.prepareStatement("DELETE FROM dueno WHERE id_dueño = ?");
			ps.setInt(1, codigoDueño);
			cant = ps.executeUpdate();
			if(cant == 1) {
				System.out.println("Se elimino el dueño con exito");
			}
			rs.close();
			sent.close();
			miConexion.close();
		}catch(SQLException e) {
			throw new SQLErrorException(""+e.getMessage());
		}
	}

	@Override
	public void delete(Dueño v)throws SQLErrorException {
		try {
			miConexion = DriverManager.getConnection(urlConexion,"root","");
			Statement sent = miConexion.createStatement();
			PreparedStatement ps = (PreparedStatement) miConexion.prepareStatement("DELETE FROM vivienda WHERE id_dueño = ?");
			ps.setInt(1, v.getId());
			int cant = ps.executeUpdate();
			if(cant == 1) {
				System.out.println("Se elimino la vivienda del dueño con exito");
			}
			ps = (PreparedStatement) miConexion.prepareStatement("DELETE FROM membresia WHERE id_dueño = ?");
			ps.setInt(1, v.getId());
			cant = ps.executeUpdate();
			if(cant == 1) {
				System.out.println("Se elimino la membresia del dueño con exito");
			}
			ps = (PreparedStatement) miConexion.prepareStatement("DELETE FROM dueno WHERE id_dueño = ?");
			ps.setInt(1, v.getId());
			cant = ps.executeUpdate();
			if(cant == 1) {
				System.out.println("Se elimino el dueño con exito");
			}
			rs.close();
			sent.close();
			miConexion.close();
		}catch(SQLException e) {
			throw new SQLErrorException(""+e.getMessage());
		}
	}

	@Override
	public List<Dueño> findAll() throws SQLErrorException{
		List <Dueño> dueños = new ArrayList<Dueño>();
		try {
			miConexion = DriverManager.getConnection(urlConexion,"root","");
			sent = miConexion.createStatement();
			rs = sent.executeQuery("SELECT * FROM dueno");
			while(rs.next()) {
				Dueño d = new Dueño(rs.getInt("id_dueño"), rs.getInt("DNI"),rs.getString("apellido"), rs.getString("nombre"));
				dueños.add(d);
			}
			rs.close();
			sent.close();
			miConexion.close();
		}catch(SQLException s){
			throw new SQLErrorException(""+s.getMessage());
		}
		return dueños;
	}

	@Override
	public Dueño findById(int codigoDueño)throws SQLErrorException {
		Dueño p1 = null;
		try {
			miConexion = DriverManager.getConnection(urlConexion,"root","");
			PreparedStatement ps = (PreparedStatement) miConexion.prepareStatement("SELECT * FROM dueno WHERE id_dueño = ?");
			ps.setInt(1, codigoDueño);
			rs = ps.executeQuery();
			while(rs.next()) {
				p1 = new Dueño(rs.getInt("id_dueño"), rs.getInt("DNI"),rs.getString("apellido"), rs.getString("nombre"));
			}
			rs.close();
			ps.close();
			miConexion.close();
		}catch(SQLException s){
			throw new SQLErrorException(""+s.getMessage());
		}
		return p1;
	}

	@Override
	public Dueño findByDNI(int dni)throws SQLErrorException {
		Dueño p1 = null;
		try {
			miConexion = DriverManager.getConnection(urlConexion,"root","");
			PreparedStatement ps = (PreparedStatement) miConexion.prepareStatement("SELECT * FROM dueno WHERE DNI = ?");
			ps.setInt(1, dni);
			rs = ps.executeQuery();
			while(rs.next()) {
				p1 = new Dueño(rs.getInt("id_dueño"), rs.getInt("DNI"),rs.getString("apellido"), rs.getString("nombre"));
			}
			rs.close();
			ps.close();
			miConexion.close();
		}catch(SQLException s){
			throw new SQLErrorException(""+s.getMessage());
		}
		return p1;
	}

	@Override
	public List<Dueño> findAllSinMembresias() {
		List <Dueño> dueños = new ArrayList<Dueño>();
		try {
			miConexion = DriverManager.getConnection(urlConexion,"root","");
			sent = miConexion.createStatement();
			rs = sent.executeQuery("SELECT * FROM dueno a WHERE a.id_dueño NOT IN (SELECT m.id_dueño FROM membresia m)");
			while(rs.next()) {
				Dueño d = new Dueño(rs.getInt("id_dueño"), rs.getInt("DNI"),rs.getString("apellido"), rs.getString("nombre"));
				dueños.add(d);
			}
			rs.close();
			sent.close();
			miConexion.close();
		}catch(SQLException s){
			throw new SQLErrorException(""+s.getMessage());
		}
		return dueños;
	}

}
