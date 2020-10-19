package ar.edu.unrn.seminario.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.mysql.jdbc.PreparedStatement;

import ar.edu.unrn.seminario.excepciones.SQLErrorException;
import ar.edu.unrn.seminario.modelo.Due�o;
import ar.edu.unrn.seminario.modelo.Pedido;
import ar.edu.unrn.seminario.modelo.Residuos;
import ar.edu.unrn.seminario.modelo.TipoResiduo;
import ar.edu.unrn.seminario.modelo.Vivienda;

public class ViviendaDAOjdbc implements ViviendaDAO {

	private String urlConexion = "jdbc:mysql://localhost:3306/cooperativaviviendas";
	Connection miConexion = null;
	ResultSet rs = null;
	Statement sent = null;

	@Override
	public void create(Vivienda v) throws SQLErrorException {
		try {
			if (v.getDue�o().getId() > 0) {
				miConexion = DriverManager.getConnection(urlConexion, "root", "");
				Statement sent = miConexion.createStatement();
				PreparedStatement ps = (PreparedStatement) miConexion.prepareStatement(
						"insert into vivienda (fecha_registro,ciudad,provincia,latitud,longitud,calle,num_calle,id_due�o) values (?,?,?,?,?,?,?,?)");
				ps.setDate(1, new java.sql.Date(v.getFechaRegistro().getTime()));
				ps.setString(2, v.getCiudad());
				ps.setString(3, v.getProvincia());
				ps.setLong(4, v.getLatitud());
				ps.setLong(5, v.getLongitud());
				ps.setString(6, v.getCalle());
				ps.setInt(7, v.getNumero());
				ps.setInt(8, v.getDue�o().getId());
				int cant = ps.executeUpdate();
				if (cant == 1) {
					System.out.println("Se agrego la vivienda con exito");
				}
				sent.close();
				miConexion.close();
			} else {
				miConexion = DriverManager.getConnection(urlConexion, "root", "");
				Statement sent = miConexion.createStatement();
				PreparedStatement ps = (PreparedStatement) miConexion.prepareStatement(
						"insert into dueno (DNI, nombre, apellido) values (?,?,?)", Statement.RETURN_GENERATED_KEYS);
				ps.setInt(1, v.getDue�o().getDNI());
				ps.setString(2, v.getDue�o().getNombre());
				ps.setString(3, v.getDue�o().getApellido());
				ps.executeUpdate();
				rs = ps.getGeneratedKeys();
				if (rs != null && rs.next()) {
					System.out.println("Se agrego el due�o con exito");
				}
				ps = (PreparedStatement) miConexion.prepareStatement(
						"insert into vivienda (fecha_registro,ciudad,provincia,latitud,longitud,calle,num_calle,id_due�o) values (?,?,?,?,?,?,?,?)");
				ps.setDate(1, new java.sql.Date(v.getFechaRegistro().getTime()));
				ps.setString(2, v.getCiudad());
				ps.setString(3, v.getProvincia());
				ps.setLong(4, v.getLatitud());
				ps.setLong(5, v.getLongitud());
				ps.setString(6, v.getCalle());
				ps.setInt(7, v.getNumero());
				ps.setInt(8, rs.getInt(1));
				int cant = ps.executeUpdate();
				if (cant == 1) {
					System.out.println("Se agrego la vivienda con exito");
				}
				rs.close();
				sent.close();
				miConexion.close();
			}
		} catch (SQLException e) {
			throw new SQLErrorException("" + e.getMessage());
		}
	}

	@Override
	public void update(Vivienda v) throws SQLErrorException {
		try {
			miConexion = DriverManager.getConnection(urlConexion, "root", "");
			Statement sent = miConexion.createStatement();
			PreparedStatement ps = (PreparedStatement) miConexion.prepareStatement("UPDATE vivienda "
					+ "SET ciudad = ? , provincia = ? , latitud = ? , longitud = ? , calle = ? , num_calle = ? "
					+ "WHERE id_vivienda = ?");
			ps.setString(1, v.getCiudad());
			ps.setString(2, v.getProvincia());
			ps.setLong(3, v.getLatitud());
			ps.setLong(4, v.getLongitud());
			ps.setString(5, v.getCalle());
			ps.setInt(6, v.getNumero());
			ps.setInt(7, v.getId());
			int cant = ps.executeUpdate();
			if (cant == 1) {
				System.out.println("Se modifico la vivienda con exito");
			}
			ps.close();
			sent.close();
			miConexion.close();
		} catch (SQLException e) {
			throw new SQLErrorException("" + e.getMessage());
		}

	}

	@Override
	public void delete(int codigoVivienda) throws SQLErrorException {
		try {
			miConexion = DriverManager.getConnection(urlConexion, "root", "");
			Statement sent = miConexion.createStatement();
			PreparedStatement ps = (PreparedStatement) miConexion
					.prepareStatement("DELETE FROM vivienda WHERE id_vivienda = ?");
			ps.setInt(1, codigoVivienda);
			int cant = ps.executeUpdate();
			if (cant == 1) {
				System.out.println("Se elimino la vivienda con exito");
			}
			ps = (PreparedStatement) miConexion.prepareStatement("DELETE FROM pedidos WHERE id_vivienda = ?");
			ps.setInt(1, codigoVivienda);
			cant = ps.executeUpdate();
			if (cant == 1) {
				System.out.println("Se eliminaron los pedidos asociados a esa vivienda con exito");
			}
			rs.close();
			sent.close();
			miConexion.close();
		} catch (SQLException e) {
			throw new SQLErrorException("" + e.getMessage());
		}
	}

	@Override
	public void delete(Vivienda v) throws SQLErrorException {
		try {
			miConexion = DriverManager.getConnection(urlConexion, "root", "");
			Statement sent = miConexion.createStatement();
			PreparedStatement ps = (PreparedStatement) miConexion
					.prepareStatement("DELETE FROM vivienda WHERE id_vivienda = ?");
			ps.setInt(1, v.getId());
			int cant = ps.executeUpdate();
			if (cant == 1) {
				System.out.println("Se elimino la vivienda con exito");
			}
			ps = (PreparedStatement) miConexion.prepareStatement("DELETE FROM pedidos WHERE id_vivienda = ?");
			ps.setInt(1, v.getId());
			cant = ps.executeUpdate();
			if (cant == 1) {
				System.out.println("Se eliminaron los pedidos asociados a esa vivienda con exito");
			}
			rs.close();
			sent.close();
			miConexion.close();
		} catch (SQLException e) {
			throw new SQLErrorException("" + e.getMessage());
		}
	}

	@Override
	public List<Vivienda> findAll() throws SQLErrorException {
		List<Vivienda> viviendas = new LinkedList<Vivienda>();
		try {
			miConexion = DriverManager.getConnection(urlConexion, "root", "");
			sent = miConexion.createStatement();
			rs = sent.executeQuery("SELECT * FROM vivienda v JOIN dueno d ON v.id_due�o = d.id_due�o ");
			while (rs.next()) {
				Due�o d = new Due�o(rs.getInt("id_due�o"), rs.getInt("DNI"), rs.getString("apellido"),
						rs.getString("nombre"));
				Vivienda v = new Vivienda(rs.getInt("id_vivienda"), rs.getDate("fecha_registro"),
						rs.getString("ciudad"), rs.getString("provincia"), rs.getLong("latitud"),
						rs.getLong("longitud"), rs.getString("calle"), rs.getInt("num_calle"), d);
				viviendas.add(v);
			}
			rs.close();
			sent.close();
			miConexion.close();
		} catch (SQLException s) {
			throw new SQLErrorException("" + s.getMessage());
		}
		return viviendas;
	}

	@Override
	public Vivienda findById(int codigoVivienda) throws SQLErrorException {
		Vivienda v = null;
		List <Pedido> pedidos = new ArrayList <Pedido>();
		try {
			miConexion = DriverManager.getConnection(urlConexion, "root", "");
			PreparedStatement ps = (PreparedStatement) miConexion.prepareStatement("SELECT * FROM vivienda v "
					+ "JOIN dueno d ON v.id_due�o = d.id_due�o " + "WHERE v.id_vivienda=? ");
			ps.setInt(1, codigoVivienda);
			rs = ps.executeQuery();
			while (rs.next()) {
				Due�o d = new Due�o(rs.getInt("id_due�o"), rs.getInt("DNI"), rs.getString("nombre"),
						rs.getString("apellido"));
				v = new Vivienda(rs.getInt("id_vivienda"), rs.getDate("fecha_registro"), rs.getString("ciudad"),
						rs.getString("provincia"), rs.getLong("latitud"), rs.getLong("longitud"), rs.getString("calle"),
						rs.getInt("num_calle"), d);
			}
			PreparedStatement ps2 = (PreparedStatement) miConexion
					.prepareStatement("SELECT * FROM pedidos p " + "JOIN vivienda v ON p.id_vivienda = v.id_vivienda "
							+ "JOIN dueno n ON v.id_due�o = n.id_due�o " + "WHERE p.id_vivienda = ? ");
			ps2.setInt(1, codigoVivienda);
			rs = ps2.executeQuery();
			while (rs.next()) {
				Pedido p = new Pedido(rs.getInt("id_pedido"), v, rs.getDate("fecha"), rs.getBoolean("cargaPesada"),
						rs.getString("observaciones"));
				pedidos.add(p);
			}
			for (Pedido item : pedidos) {
				PreparedStatement ps1 = (PreparedStatement) miConexion.prepareStatement(
						"SELECT * FROM residuosarecolectar ra " + "JOIN pedidos p ON ra.id_pedido = p.id_pedido "
								+ "JOIN tiporesiduo tp ON ra.id_tiporesiduo = tp.id_tiporesiduo "
								+ "WHERE ra.id_pedido = ? ");
				ps1.setInt(1, item.getId());
				rs = ps1.executeQuery();
				while (rs.next()) {
					TipoResiduo t = new TipoResiduo(rs.getInt("id_tiporesiduo"), rs.getString("nombre_tiporesiduo"),
							rs.getInt("puntoskg"));
					Residuos r = new Residuos(rs.getInt("id_residuo"), rs.getInt("kg"), t, item);
					item.agregarResiduo(r);
				}
				ps1.close();
			}
			for(Pedido ped : pedidos) {
				v.agregarPedido(ped);
			}
			rs.close();
			ps.close();
			miConexion.close();
		} catch (SQLException s) {
			throw new SQLErrorException("" + s.getMessage());
		}
		return v;
	}

	@Override
	public List<Vivienda> findByDNIDue�o(int dni) throws SQLErrorException {
		List<Vivienda> viviendas = new LinkedList<Vivienda>();
		try {
			miConexion = DriverManager.getConnection(urlConexion, "root", "");
			sent = miConexion.createStatement();
			PreparedStatement ps = (PreparedStatement) miConexion.prepareStatement(
					"SELECT * FROM vivienda v JOIN dueno d ON v.id_due�o = d.id_due�o WHERE d.dni = ?");
			ps.setInt(1, dni);
			rs = ps.executeQuery();
			while (rs.next()) {
				Due�o d = new Due�o(rs.getInt("id_due�o"), rs.getInt("DNI"), rs.getString("apellido"),
						rs.getString("nombre"));
				Vivienda v = new Vivienda(rs.getInt("id_vivienda"), rs.getDate("fecha_registro"),
						rs.getString("ciudad"), rs.getString("provincia"), rs.getLong("latitud"),
						rs.getLong("longitud"), rs.getString("calle"), rs.getInt("num_calle"), d);
				viviendas.add(v);
			}
			rs.close();
			sent.close();
			miConexion.close();
		} catch (SQLException s) {
			throw new SQLErrorException("" + s.getMessage());
		}
		return viviendas;
	}

	@Override
	public Vivienda findByIdVisita(int codigoVisita) throws SQLErrorException {
		Vivienda v = null;
		try {
			miConexion = DriverManager.getConnection(urlConexion, "root", "");
			PreparedStatement ps = (PreparedStatement) miConexion
					.prepareStatement("SELECT * FROM vivienda v " + "JOIN dueno d ON v.id_due�o = d.id_due�o "
							+ "JOIN visita vi ON vi.id_vivienda = v.id_vivienda " + "WHERE vi.id_visita=? ");
			ps.setInt(1, codigoVisita);
			rs = ps.executeQuery();
			while (rs.next()) {
				Due�o d = new Due�o(rs.getInt("id_due�o"), rs.getInt("DNI"), rs.getString("nombre"),
						rs.getString("apellido"));
				v = new Vivienda(rs.getInt("id_vivienda"), rs.getDate("fecha_registro"), rs.getString("ciudad"),
						rs.getString("provincia"), rs.getLong("latitud"), rs.getLong("longitud"), rs.getString("calle"),
						rs.getInt("num_calle"), d);
			}
			rs.close();
			ps.close();
			miConexion.close();
		} catch (SQLException s) {
			throw new SQLErrorException("" + s.getMessage());
		}
		return v;
	}

}
