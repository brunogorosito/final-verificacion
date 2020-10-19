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
import ar.edu.unrn.seminario.modelo.*;

public class TipoResiduoDAOjdbc implements TipoResiduoDAO{
	
	private String urlConexion = "jdbc:mysql://localhost:3306/cooperativaviviendas";
	Connection miConexion = null;
	ResultSet rs = null;
	Statement sent = null;

	@Override
	public void create(TipoResiduo v) throws SQLErrorException{
		try {
			miConexion = DriverManager.getConnection(urlConexion,"root","");
			sent = miConexion.createStatement();
			PreparedStatement ps = (PreparedStatement) miConexion.prepareStatement("insert into tiporesiduo (nombre_tiporesiduo,puntoskg) values (?,?)", Statement.RETURN_GENERATED_KEYS);
			ps.setString(1,v.getNombre());
			ps.setInt(2,v.getPuntosXkg());
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
	public void update(TipoResiduo v) throws SQLErrorException{
		try {
			miConexion = DriverManager.getConnection(urlConexion,"root","");
			Statement sent = miConexion.createStatement();
			PreparedStatement ps = (PreparedStatement) miConexion.prepareStatement("UPDATE tiporesiduo "
					+ "SET nombre_tiporesiduo = ? , puntoskg = ? "
					+ "WHERE id_tiporesiduo = ? ");
			ps.setString(1,v.getNombre());
			ps.setInt(2,v.getPuntosXkg());
			ps.setInt(3,v.getId());
			int cant = ps.executeUpdate();
			if(cant == 1) {
				System.out.println("Se modifico el residuo con exito");
			}
			ps.close();
			sent.close();
			miConexion.close();
		}catch(SQLException e) {
			throw new SQLErrorException(""+e.getMessage());
		}
	}

	@Override
	public void delete(int codigoTipoResiduo) throws SQLErrorException{
		try {
			miConexion = DriverManager.getConnection(urlConexion,"root","");
			Statement sent = miConexion.createStatement();
			PreparedStatement ps = (PreparedStatement) miConexion.prepareStatement("DELETE FROM tiporesiduo WHERE id_tiporesiduo = ?");
			ps.setInt(1, codigoTipoResiduo);
			int cant = ps.executeUpdate();
			if(cant == 1) {
				System.out.println("Se elimino el residuo con exito");
			}
			rs.close();
			sent.close();
			miConexion.close();
		}catch(SQLException e) {
			throw new SQLErrorException(""+e.getMessage());
		}
	}

	@Override
	public void delete(TipoResiduo v) throws SQLErrorException{
		try {
			miConexion = DriverManager.getConnection(urlConexion,"root","");
			Statement sent = miConexion.createStatement();
			PreparedStatement ps = (PreparedStatement) miConexion.prepareStatement("DELETE FROM tiporesiduo WHERE id_tiporesiduo = ?");
			ps.setInt(1,v.getId());
			int cant = ps.executeUpdate();
			if(cant == 1) {
				System.out.println("Se elimino el residuo con exito");
			}
			rs.close();
			sent.close();
			miConexion.close();
		}catch(SQLException e) {
			throw new SQLErrorException(""+e.getMessage());
		}
	}

	@Override
	public List<TipoResiduo> findAll()throws SQLErrorException {
		List <TipoResiduo> tiporesiduo = new ArrayList<TipoResiduo>();
		try {
			miConexion = DriverManager.getConnection(urlConexion,"root","");
			sent = miConexion.createStatement();
			rs = sent.executeQuery("SELECT * FROM tiporesiduo");
			while(rs.next()) {
				TipoResiduo t = new TipoResiduo(rs.getInt("id_tiporesiduo"),rs.getString("nombre_tiporesiduo"), rs.getInt("puntoskg"));
				tiporesiduo.add(t);
			}
			rs.close();
			sent.close();
			miConexion.close();
		}catch(SQLException s){
			throw new SQLErrorException(""+s.getMessage());
		}
		return tiporesiduo;
	}

	@Override
	public TipoResiduo findById(int codigoTipoResiduo) throws SQLErrorException{
		TipoResiduo v = null;
		try {
			miConexion = DriverManager.getConnection(urlConexion,"root","");
			PreparedStatement ps = (PreparedStatement) miConexion.prepareStatement("SELECT * FROM tiporesiduo WHERE id_tiporesiduo=?");
			ps.setInt(1, codigoTipoResiduo);
			rs = ps.executeQuery();
			while(rs.next()) {
				v = new TipoResiduo(rs.getInt("id_tiporesiduo"),rs.getString("nombre_tiporesiduo"),rs.getInt("puntoskg"));
			}
			rs.close();
			ps.close();
			miConexion.close();
		}catch(SQLException s){
			throw new SQLErrorException(""+s.getMessage());
		}
		return v;
	}
}
