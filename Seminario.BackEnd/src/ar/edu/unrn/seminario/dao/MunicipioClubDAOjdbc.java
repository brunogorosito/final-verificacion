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
import ar.edu.unrn.seminario.modelo.Membresia;
import ar.edu.unrn.seminario.modelo.MunicipioClub;
import ar.edu.unrn.seminario.modelo.Premio;

public class MunicipioClubDAOjdbc implements MunicipioClubDAO{
	
	private String urlConexion = "jdbc:mysql://localhost:3306/cooperativaviviendas";
	Connection miConexion = null;
	ResultSet rs = null;
	Statement sent = null;

	@Override
	public void create(MunicipioClub n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(MunicipioClub n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(int codigoMunicipioClub) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(MunicipioClub n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<MunicipioClub> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MunicipioClub findById(int codigoMunicipioClub) throws SQLErrorException{
		MunicipioClub m = null;
		List <Membresia> membresias = new ArrayList<Membresia>();
		List <Premio> premios = new ArrayList<Premio>();
		try {
			miConexion = DriverManager.getConnection(urlConexion,"root","");
			PreparedStatement ps = (PreparedStatement) miConexion.prepareStatement("SELECT * FROM municipioclub WHERE id_municipioclub=?");
			ps.setInt(1, codigoMunicipioClub);
			rs = ps.executeQuery();
			while(rs.next()) {
				m = new MunicipioClub(rs.getInt("id_municipioclub"), rs.getString("url"));
			}
			sent = miConexion.createStatement();
			rs = sent.executeQuery("SELECT * FROM membresia m "
					+ "JOIN dueno d ON m.id_dueño = d.id_dueño");
			while(rs.next()) {
				Dueño du = new Dueño(rs.getInt("id_dueño"), rs.getInt("DNI"),rs.getString("apellido"), rs.getString("nombre"));
				Membresia mem = new Membresia(rs.getInt("id_membresia"), du, rs.getInt("puntos"), rs.getDate("fecha"));
				membresias.add(mem);
			}
			for(Membresia item : membresias) {
				m.agregarMebresia(item);
			}
			sent = miConexion.createStatement();
			rs = sent.executeQuery("SELECT * FROM premios");
			while(rs.next()) {
				Premio d = new Premio(rs.getInt("id_premio"), rs.getString("nombre"), rs.getInt("puntos_necesarios"), rs.getString("descripcion"));
				premios.add(d);
			}
			for(Premio items : premios){
				m.agregarPremio(items);
			}
			rs.close();
			sent.close();
			ps.close();
			miConexion.close();
		}catch(SQLException s){
			throw new SQLErrorException(""+s.getMessage());
		}
		return m;
	}

}
