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
import ar.edu.unrn.seminario.modelo.Premio;

public class PremioDAOjdbc implements PremioDAO {

	private String urlConexion = "jdbc:mysql://localhost:3306/cooperativaviviendas";
	Connection miConexion = null;
	ResultSet rs = null;
	Statement sent = null;
	
	@Override
	public void create(Premio p) throws SQLErrorException{
		try {
			miConexion = DriverManager.getConnection(urlConexion,"root","");
			sent = miConexion.createStatement();
			PreparedStatement ps = (PreparedStatement) miConexion.prepareStatement("insert into premios (nombre,puntos_necesarios,descripcion,id_municipioclub) values (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
			ps.setString(1,p.getNombre());
			ps.setInt(2,p.getPuntosNecesarios());
			ps.setString(3, p.getDescripcion());
			ps.setInt(4,1);
			ps.executeUpdate();
			rs = ps.getGeneratedKeys();
			if(rs != null && rs.next()) {
				System.out.println("Se agrego con exito el premio");
			}
			rs.close();
			sent.close();
			miConexion.close();
		}catch(SQLException e) {
			throw new SQLErrorException(""+e.getMessage());
		}
	}

	@Override
	public void update(Premio p)throws SQLErrorException {
		try {
			miConexion = DriverManager.getConnection(urlConexion,"root","");
			Statement sent = miConexion.createStatement();
			PreparedStatement ps = (PreparedStatement) miConexion.prepareStatement("UPDATE premios "
					+ "SET id_premio = ? , nombre = ? , puntos_necesarios = ? , descripcion = ? , id_municipioclub= ? "
					+ "WHERE id_premio = ? ");
			ps.setInt(1,p.getId());
			ps.setString(2, p.getNombre());
			ps.setInt(3,p.getPuntosNecesarios());
			ps.setString(4,p.getDescripcion());
			ps.setInt(5,1);
			ps.setInt(6,p.getId());
			int cant = ps.executeUpdate();
			if(cant == 1) {
				System.out.println("Se modifico el premio con exito");
			}
			ps.close();
			sent.close();
			miConexion.close();
		}catch(SQLException e) {
			throw new SQLErrorException(""+e.getMessage());
		}
	}

	@Override
	public void delete(int codigoPremio) throws SQLErrorException{
		try {
			miConexion = DriverManager.getConnection(urlConexion,"root","");
			Statement sent = miConexion.createStatement();
			PreparedStatement ps = (PreparedStatement) miConexion.prepareStatement("DELETE FROM premios WHERE id_premio = ?");
			ps.setInt(1, codigoPremio);
			int cant = ps.executeUpdate();
			if(cant == 1) {
				System.out.println("Se elimino el premio con exito");
			}
			rs.close();
			sent.close();
			miConexion.close();
		}catch(SQLException e1) {
			throw new SQLErrorException(""+e1.getMessage());
		}
	}

	@Override
	public void delete(Premio p)throws SQLErrorException {
		try {
			miConexion = DriverManager.getConnection(urlConexion,"root","");
			Statement sent = miConexion.createStatement();
			PreparedStatement ps = (PreparedStatement) miConexion.prepareStatement("DELETE FROM premios WHERE id_premio = ?");
			ps.setInt(1, p.getId());
			int cant = ps.executeUpdate();
			if(cant == 1) {
				System.out.println("Se elimino el premio con exito");
			}
			rs.close();
			sent.close();
			miConexion.close();
		}catch(SQLException e1) {
			throw new SQLErrorException(""+e1.getMessage());
		}
		
	}

	@Override
	public List<Premio> findAll() throws SQLErrorException{
		List <Premio> premios = new ArrayList<Premio>();
		try {
			miConexion = DriverManager.getConnection(urlConexion,"root","");
			sent = miConexion.createStatement();
			rs = sent.executeQuery("SELECT * FROM premios");
			while(rs.next()) {
				Premio d = new Premio(rs.getInt("id_premio"), rs.getString("nombre"), rs.getInt("puntos_necesarios"), rs.getString("descripcion"));
				premios.add(d);
			}
			rs.close();
			sent.close();
			miConexion.close();
		}catch(SQLException s){
			throw new SQLErrorException(""+s.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
		}
		return premios;
	}

	@Override
	public Premio findById(int codigoPremio) throws SQLErrorException{
		Premio v = null;
		try {
			miConexion = DriverManager.getConnection(urlConexion,"root","");
			PreparedStatement ps = (PreparedStatement) miConexion.prepareStatement("SELECT * FROM premios WHERE id_premio=?");
			ps.setInt(1, codigoPremio);
			rs = ps.executeQuery();
			while(rs.next()) {
				v = new Premio(rs.getInt("id_premio"), rs.getString("nombre"), rs.getInt("puntos_necesarios"), rs.getString("descripcion"));
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
