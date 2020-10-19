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
import ar.edu.unrn.seminario.modelo.Pedido;
import ar.edu.unrn.seminario.modelo.Persona;
import ar.edu.unrn.seminario.modelo.Residuos;
import ar.edu.unrn.seminario.modelo.TipoResiduo;
import ar.edu.unrn.seminario.modelo.Vivienda;

public class ResiduosDAOjdbc implements ResiduosDAO{

	private String urlConexion = "jdbc:mysql://localhost:3306/cooperativaviviendas";
	private Connection miConexion = null;
	private ResultSet rs = null;
	private Statement sent = null;
	
	@Override
	public void create(Residuos r)throws SQLErrorException {
		try {
			miConexion = DriverManager.getConnection(urlConexion,"root","");
			sent = miConexion.createStatement();
			PreparedStatement ps = (PreparedStatement) miConexion.prepareStatement("insert into residuosarecolectar (id_tiporesiduo, kg, id_pedido) values (?,?,?)", Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1,r.getTipo().getId());
			ps.setInt(2,r.getKg());
			ps.setInt(3,r.getP().getId());
			ps.executeUpdate();
			rs = ps.getGeneratedKeys();
			if(rs != null && rs.next()) {
				System.out.println("Se agrego con exito el residuo");
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
			PreparedStatement ps = (PreparedStatement) miConexion.prepareStatement("UPDATE residuosarecolectar"
					+ " SET  id_tiporesiduo = ? , kg = ? , id_pedido = ? "
					+ "WHERE id_residuo = ?");
			ps.setInt(1,r.getTipo().getId());
			ps.setInt(2,r.getKg());
			ps.setInt(3,r.getP().getId());
			ps.setInt(4,r.getId());
			int cant = ps.executeUpdate();
			if(cant == 1) {
				System.out.println("Se modificaron los residuos con exito");
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
			rs = sent.executeQuery("SELECT * FROM residuosarecolectar ra "
					+ "JOIN pedido p ON ra.id_pedido = p.id_pedido "
					+ "JOIN vivienda v ON p.id_vivienda = v.id_vivienda "
					+ "JOIN dueno n ON v.id_dueño = n.id_dueño "
					+ "JOIN tiporesiduo tp ON ra.id_tiporesiduo = tp.id_residuo");
			while(rs.next()) {
				Persona p1 = new Dueño(rs.getInt("id_dueño"),rs.getInt("DNI"), rs.getString("nombre"),rs.getString("apellido"));
				Vivienda v = new Vivienda(rs.getInt("id_vivienda"),rs.getDate("fecha_registro"),rs.getString("ciudad"),rs.getString("provincia"),rs.getLong("latitud"),rs.getLong("longitud"),rs.getString("calle"), rs.getInt("num_calle"),p1);
				Pedido p = new Pedido(rs.getInt("id_pedido"), v ,rs.getDate("fecha"), rs.getBoolean("cargaPesada"), rs.getString("observaciones"));
				TipoResiduo t = new TipoResiduo(rs.getInt("id_tiporesiduo"),rs.getString("nombre_tiporesiduo"), rs.getInt("puntoskg"));
				Residuos r = new Residuos(rs.getInt("id_residuo"), rs.getInt("kg"), t, p);
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
		Residuos p1 = null;
		try {
			miConexion = DriverManager.getConnection(urlConexion,"root","");
			PreparedStatement ps = (PreparedStatement) miConexion.prepareStatement("SELECT * FROM residuosarecolectar ra " + 
					"JOIN pedido p ON ra.id_pedido = p.id_pedido " + 
					"JOIN vivienda v ON p.id_vivienda = v.id_vivienda " + 
					"JOIN dueno n ON v.id_dueño = n.id_dueño " + 
					"JOIN tiporesiduo tp ON ra.id_tiporesiduo = tp.id_residuo " + 
					"WHERE ra.id_residuo = ?");
			ps.setInt(1, codigoResiduo);
			rs = ps.executeQuery();
			while(rs.next()) {
				Persona pe1 = new Dueño(rs.getInt("id_dueño"),rs.getInt("DNI"), rs.getString("nombre"),rs.getString("apellido"));
				Vivienda v = new Vivienda(rs.getInt("id_vivienda"),rs.getDate("fecha_registro"),rs.getString("ciudad"),rs.getString("provincia"),rs.getLong("latitud"),rs.getLong("longitud"),rs.getString("calle"), rs.getInt("num_calle"),pe1);
				Pedido p = new Pedido(rs.getInt("id_pedido"), v ,rs.getDate("fecha"), rs.getBoolean("cargaPesada"), rs.getString("observaciones"));
				TipoResiduo t = new TipoResiduo(rs.getInt("id_tiporesiduo"),rs.getString("nombre_tiporesiduo"), rs.getInt("puntoskg"));
				p1 = new Residuos(rs.getInt("id_residuo"), rs.getInt("kg"), t, p); 
			}
			rs.close();
			ps.close();
			miConexion.close();
		}catch(SQLException s){
			throw new SQLErrorException(""+s.getMessage());
		}
		return p1;
	}
	
	public List<Residuos> findResiduosXCodigoPedido(int codigoPedido)throws SQLErrorException{
		List <Residuos> p1 = new ArrayList<Residuos>();
		try {
			miConexion = DriverManager.getConnection(urlConexion,"root","");
			PreparedStatement ps = (PreparedStatement) miConexion.prepareStatement("SELECT * FROM residuosarecolectar ra " + 
					"JOIN pedidos p ON ra.id_pedido = p.id_pedido " + 
					"JOIN vivienda v ON p.id_vivienda = v.id_vivienda " + 
					"JOIN dueno n ON v.id_dueño = n.id_dueño " + 
					"JOIN tiporesiduo tp ON ra.id_tiporesiduo = tp.id_tiporesiduo " + 
					"WHERE ra.id_pedido = ? ");
			ps.setInt(1, codigoPedido);
			rs = ps.executeQuery();
			while(rs.next()) {
				Persona pe1 = new Dueño(rs.getInt("id_dueño"),rs.getInt("DNI"), rs.getString("nombre"),rs.getString("apellido"));
				Vivienda v = new Vivienda(rs.getInt("id_vivienda"),rs.getDate("fecha_registro"),rs.getString("ciudad"),rs.getString("provincia"),rs.getLong("latitud"),rs.getLong("longitud"),rs.getString("calle"), rs.getInt("num_calle"),pe1);
				Pedido p = new Pedido(rs.getInt("id_pedido"), v ,rs.getDate("fecha"), rs.getBoolean("cargaPesada"), rs.getString("observaciones"));
				TipoResiduo t = new TipoResiduo(rs.getInt("id_tiporesiduo"),rs.getString("nombre_tiporesiduo"), rs.getInt("puntoskg"));
				Residuos r = new Residuos(rs.getInt("id_residuo"), rs.getInt("kg"), t, p);
				p1.add(r);
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
	public List<Residuos> findResiduosXCodigoVisita(int codigoVisita) {
		// TODO Auto-generated method stub
		return null;
	}

}
