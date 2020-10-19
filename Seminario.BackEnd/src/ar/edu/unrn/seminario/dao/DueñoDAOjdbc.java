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
import ar.edu.unrn.seminario.modelo.Due�o;

public class Due�oDAOjdbc implements Due�oDAO{

	private String urlConexion = "jdbc:mysql://localhost:3306/cooperativaviviendas";
	Connection miConexion = null;
	ResultSet rs = null;
	Statement sent = null;
	
	@Override
	public void create(Due�o v) throws SQLErrorException{
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
	public void update(Due�o v) throws SQLErrorException {
		try {
			miConexion = DriverManager.getConnection(urlConexion,"root","");
			Statement sent = miConexion.createStatement();
			PreparedStatement ps = (PreparedStatement) miConexion.prepareStatement("UPDATE dueno "
					+ "SET DNI = ? , nombre = ? , apellido = ?"
					+ "WHERE id_due�o = ?");
			ps.setInt(1,v.getDNI());
			ps.setString(2,v.getNombre());
			ps.setString(3,v.getApellido());
			ps.setInt(4, v.getId());
			int cant = ps.executeUpdate();
			if(cant == 1) {
				System.out.println("Se modifico el due�o con exito");
			}
			ps.close();
			sent.close();
			miConexion.close();
		}catch(SQLException e) {
			throw new SQLErrorException(""+e.getMessage());
		}
	}

	@Override
	public void delete(int codigoDue�o)throws SQLErrorException {
		try {
			miConexion = DriverManager.getConnection(urlConexion,"root","");
			Statement sent = miConexion.createStatement();
			PreparedStatement ps = (PreparedStatement) miConexion.prepareStatement("DELETE FROM vivienda WHERE id_due�o = ?");
			ps.setInt(1, codigoDue�o);
			int cant = ps.executeUpdate();
			if(cant == 1) {
				System.out.println("Se elimino la vivienda del due�o con exito");
			}
			ps = (PreparedStatement) miConexion.prepareStatement("DELETE FROM membresia WHERE id_due�o = ?");
			ps.setInt(1, codigoDue�o);
			cant = ps.executeUpdate();
			if(cant == 1) {
				System.out.println("Se elimino la membresia del due�o con exito");
			}
			ps = (PreparedStatement) miConexion.prepareStatement("DELETE FROM dueno WHERE id_due�o = ?");
			ps.setInt(1, codigoDue�o);
			cant = ps.executeUpdate();
			if(cant == 1) {
				System.out.println("Se elimino el due�o con exito");
			}
			rs.close();
			sent.close();
			miConexion.close();
		}catch(SQLException e) {
			throw new SQLErrorException(""+e.getMessage());
		}
	}

	@Override
	public void delete(Due�o v)throws SQLErrorException {
		try {
			miConexion = DriverManager.getConnection(urlConexion,"root","");
			Statement sent = miConexion.createStatement();
			PreparedStatement ps = (PreparedStatement) miConexion.prepareStatement("DELETE FROM vivienda WHERE id_due�o = ?");
			ps.setInt(1, v.getId());
			int cant = ps.executeUpdate();
			if(cant == 1) {
				System.out.println("Se elimino la vivienda del due�o con exito");
			}
			ps = (PreparedStatement) miConexion.prepareStatement("DELETE FROM membresia WHERE id_due�o = ?");
			ps.setInt(1, v.getId());
			cant = ps.executeUpdate();
			if(cant == 1) {
				System.out.println("Se elimino la membresia del due�o con exito");
			}
			ps = (PreparedStatement) miConexion.prepareStatement("DELETE FROM dueno WHERE id_due�o = ?");
			ps.setInt(1, v.getId());
			cant = ps.executeUpdate();
			if(cant == 1) {
				System.out.println("Se elimino el due�o con exito");
			}
			rs.close();
			sent.close();
			miConexion.close();
		}catch(SQLException e) {
			throw new SQLErrorException(""+e.getMessage());
		}
	}

	@Override
	public List<Due�o> findAll() throws SQLErrorException{
		List <Due�o> due�os = new ArrayList<Due�o>();
		try {
			miConexion = DriverManager.getConnection(urlConexion,"root","");
			sent = miConexion.createStatement();
			rs = sent.executeQuery("SELECT * FROM dueno");
			while(rs.next()) {
				Due�o d = new Due�o(rs.getInt("id_due�o"), rs.getInt("DNI"),rs.getString("apellido"), rs.getString("nombre"));
				due�os.add(d);
			}
			rs.close();
			sent.close();
			miConexion.close();
		}catch(SQLException s){
			throw new SQLErrorException(""+s.getMessage());
		}
		return due�os;
	}

	@Override
	public Due�o findById(int codigoDue�o)throws SQLErrorException {
		Due�o p1 = null;
		try {
			miConexion = DriverManager.getConnection(urlConexion,"root","");
			PreparedStatement ps = (PreparedStatement) miConexion.prepareStatement("SELECT * FROM dueno WHERE id_due�o = ?");
			ps.setInt(1, codigoDue�o);
			rs = ps.executeQuery();
			while(rs.next()) {
				p1 = new Due�o(rs.getInt("id_due�o"), rs.getInt("DNI"),rs.getString("apellido"), rs.getString("nombre"));
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
	public Due�o findByDNI(int dni)throws SQLErrorException {
		Due�o p1 = null;
		try {
			miConexion = DriverManager.getConnection(urlConexion,"root","");
			PreparedStatement ps = (PreparedStatement) miConexion.prepareStatement("SELECT * FROM dueno WHERE DNI = ?");
			ps.setInt(1, dni);
			rs = ps.executeQuery();
			while(rs.next()) {
				p1 = new Due�o(rs.getInt("id_due�o"), rs.getInt("DNI"),rs.getString("apellido"), rs.getString("nombre"));
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
	public List<Due�o> findAllSinMembresias() {
		List <Due�o> due�os = new ArrayList<Due�o>();
		try {
			miConexion = DriverManager.getConnection(urlConexion,"root","");
			sent = miConexion.createStatement();
			rs = sent.executeQuery("SELECT * FROM dueno a WHERE a.id_due�o NOT IN (SELECT m.id_due�o FROM membresia m)");
			while(rs.next()) {
				Due�o d = new Due�o(rs.getInt("id_due�o"), rs.getInt("DNI"),rs.getString("apellido"), rs.getString("nombre"));
				due�os.add(d);
			}
			rs.close();
			sent.close();
			miConexion.close();
		}catch(SQLException s){
			throw new SQLErrorException(""+s.getMessage());
		}
		return due�os;
	}

}
