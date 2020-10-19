package ar.edu.unrn.seminario.api;

import java.sql.Date;
import java.util.List;

import ar.edu.unrn.seminario.dao.DueñoDAO;
import ar.edu.unrn.seminario.dao.DueñoDAOjdbc;
import ar.edu.unrn.seminario.dao.MembresiaDAO;
import ar.edu.unrn.seminario.dao.MembresiaDAOjdbc;
import ar.edu.unrn.seminario.dao.MunicipioClubDAO;
import ar.edu.unrn.seminario.dao.MunicipioClubDAOjdbc;
import ar.edu.unrn.seminario.dao.OrdenDeRetiroDAO;
import ar.edu.unrn.seminario.dao.OrdenDeRetiroDAOjdbc;
import ar.edu.unrn.seminario.dao.PedidoDAO;
import ar.edu.unrn.seminario.dao.PedidoDAOjdbc;
import ar.edu.unrn.seminario.dao.PremioDAO;
import ar.edu.unrn.seminario.dao.PremioDAOjdbc;
import ar.edu.unrn.seminario.dao.RecolectorDAO;
import ar.edu.unrn.seminario.dao.RecolectorDAOjdbc;
import ar.edu.unrn.seminario.dao.ResiduosDAO;
import ar.edu.unrn.seminario.dao.ResiduosDAOjdbc;
import ar.edu.unrn.seminario.dao.ResiduosVisitaDAOjdbc;
import ar.edu.unrn.seminario.dao.TipoResiduoDAO;
import ar.edu.unrn.seminario.dao.TipoResiduoDAOjdbc;
import ar.edu.unrn.seminario.dao.VisitaDAO;
import ar.edu.unrn.seminario.dao.VisitaDAOjdbc;
import ar.edu.unrn.seminario.dao.ViviendaDAO;
import ar.edu.unrn.seminario.dao.ViviendaDAOjdbc;
import ar.edu.unrn.seminario.modelo.Dueño;
import ar.edu.unrn.seminario.modelo.Membresia;
import ar.edu.unrn.seminario.modelo.MunicipioClub;
import ar.edu.unrn.seminario.modelo.OrdenDeRetiro;
import ar.edu.unrn.seminario.modelo.Pedido;
import ar.edu.unrn.seminario.modelo.Persona;
import ar.edu.unrn.seminario.modelo.Premio;
import ar.edu.unrn.seminario.modelo.Recolector;
import ar.edu.unrn.seminario.modelo.Residuos;
import ar.edu.unrn.seminario.modelo.TipoResiduo;
import ar.edu.unrn.seminario.modelo.Visita;
import ar.edu.unrn.seminario.modelo.Vivienda;

public class Api {

	private ViviendaDAO viviendaDAO = new ViviendaDAOjdbc();
	private DueñoDAO dueñoDAO = new DueñoDAOjdbc();
	private TipoResiduoDAO tiporesiduoDAO = new TipoResiduoDAOjdbc();
	private ResiduosDAO residuoDAO = new ResiduosDAOjdbc();
	private PedidoDAO pedidoDAO = new PedidoDAOjdbc();
	private RecolectorDAO recolectorDAO = new RecolectorDAOjdbc();
	private OrdenDeRetiroDAO ordenDAO = new OrdenDeRetiroDAOjdbc();
	private VisitaDAO visitaDAO = new VisitaDAOjdbc();
	private ResiduosDAO residuovisitaDAO = new ResiduosVisitaDAOjdbc();
	private PremioDAO premiosDAO = new PremioDAOjdbc();
	private MembresiaDAO membresiaDAO = new MembresiaDAOjdbc();
	private MunicipioClubDAO municipioDAO = new MunicipioClubDAOjdbc();
	
	
	//TODOS LOS "CREATE"
	
	public void crearRegistroVivienda(Date fecha, String nombre, String apellido, int DNI, int numCalle, String calle, long latitud, long longitud, String provincia, String ciudad) {
		Persona p1 = new Dueño (apellido, nombre, DNI);
		Vivienda v1 = new Vivienda ((Dueño)p1, fecha, ciudad, provincia, latitud, longitud, calle, numCalle);
		viviendaDAO.create(v1);
	}
	
	
	public void crearDueño(int DNI, String nombre, String apellido){
		Persona d = new Dueño(apellido, nombre, DNI);
		dueñoDAO.create((Dueño) d);
	}
	
	public void crearRegistroViviendaDesdeDueño(int id_dueño, Date fecha, int numCalle, String calle, long latitud, long longitud, String provincia, String ciudad) {
		Persona dueño = buscarDueñoXId(id_dueño);
		Vivienda vivienda = new Vivienda ((Dueño) dueño, fecha, ciudad, provincia, latitud, longitud, calle, numCalle);
		viviendaDAO.create(vivienda);

	}
	
	public void crearResiduo(String nombre, int kg){
		TipoResiduo d = new TipoResiduo(nombre,kg);
		tiporesiduoDAO.create(d);

	}
	
	public void crearPedido(int id_vivienda, Date fecha, boolean cargaP, String observaciones){
		Vivienda vivienda = this.buscarViviendaXId(id_vivienda);
		Pedido p = new Pedido(vivienda ,fecha,cargaP,observaciones);
		vivienda.agregarPedido(p);
		pedidoDAO.create(p);
	}
	
	public void crearResiduos(int id_tipoRes, int kg, int id_pedido){
		Pedido pedido = this.buscarPedidoXId(id_pedido);
		Residuos r = new Residuos(kg, this.buscarTipoResiduoXId(id_tipoRes),pedido );
		pedido.agregarResiduo(r);
		residuoDAO.create(r);
	}
	
	public void crearRecolector(String nombre, String apellido, int dni, boolean ocupado){
		Persona rec1 = new Recolector(nombre,apellido,dni,ocupado);
		recolectorDAO.create((Recolector) rec1);

	}
	
	public void crearOrdenDeRetiro(int id_pedido, int id_recolector, Date fecha) {
		Pedido pedido = buscarPedidoXId(id_pedido);
		Recolector recolector = buscarRecolectorXId(id_recolector);
		OrdenDeRetiro orden = new OrdenDeRetiro(pedido,recolector,fecha);
		ordenDAO.create(orden);

	}
	
	public void crearVisita(Date fecha, String observaciones, int id_orden){
		OrdenDeRetiro orden = this.buscarOrdenXId(id_orden);
		Vivienda vivienda = this.buscarViviendaXId(orden.getIdViviendaDePedido());
		Visita visita = new Visita(vivienda, fecha, observaciones, orden);
		orden.agregarVisita(visita);
		ordenDAO.updateOyV(orden,visita);
	}
	
	public void crearResiduosVisita(int id_tipoRes, int kg, int id_visita){
		Visita visita =  this.buscarVisitaXId(id_visita);
		TipoResiduo tipo = this.buscarTipoResiduoXId(id_tipoRes);
		Residuos r = new Residuos(kg, tipo ,visita);
		visita.agregarResiduos(r);
		residuovisitaDAO.create(r);
	}
	
	public void crearPremio(String nombre, int puntos_necesarios, String descripcion) {
		Premio p = new Premio(nombre,puntos_necesarios,descripcion);
		premiosDAO.create(p);
	}
	
	public void crearMembresia(int id_dueño, int puntos, Date fechaReg){
		Dueño dueño = this.buscarDueñoXId(id_dueño);
		Membresia m = new Membresia(dueño,puntos,fechaReg);
		membresiaDAO.create(m);

	}
	
	//-------------------------------------------------------------------------------------//
	
	//TODOS LOS "LIST"
	
	public List <Dueño> getDueños(){
		return dueñoDAO.findAll();
	}
	
	public List <Vivienda> getViviendas(){
		return viviendaDAO.findAll();
	}
	
	public List <TipoResiduo> getTipoResiduo(){
		return tiporesiduoDAO.findAll();
	}
	
	public List <Pedido> getPedidos(){
		return pedidoDAO.findAll() ;
	}
	
	public List <Recolector> getRecolectores(){
		return recolectorDAO.findAll();
	}
	
	public List <Residuos> getResiduos(int codigoPedido){
		return residuoDAO.findResiduosXCodigoPedido(codigoPedido);
	}
	
	public List <Residuos> getResiduosV(int codigoVisita){
		return residuovisitaDAO.findResiduosXCodigoVisita(codigoVisita);
	}
	
	public List<Pedido> getPedidosXVivienda(int id){
		return pedidoDAO.findByVivienda(id);
	}
	
	public List<OrdenDeRetiro> getOrdenesDeRetiro() {
		return ordenDAO.findAll();
	}
	
	public List<Visita> getVisitas(){
		return visitaDAO.findAll();
	}
	
	public List<Membresia> getMembresias() {
		return membresiaDAO.findAll();
	}
	
	public List<Dueño> getDueñosSinMembresias() {
		return dueñoDAO.findAllSinMembresias();
	}
	
	public List <Vivienda> buscarViviendaXDNIDueño(int dni) {
		return viviendaDAO.findByDNIDueño (dni);
	}
	
	public List<Premio> getPremios() {
		return premiosDAO.findAll();
	}
	
	public List<Premio> getPremiosDisponiblesParaMembresia(int id_membresia){
		Membresia me = this.buscarMembresiaXId(id_membresia);
		MunicipioClub m1 = this.buscarMunicipioClubXId(1);
		return m1.getPremiosDisponibles(me);
	}
	
	//-------------------------------------------------------------------------------------//
	
	//TODOS LOS "DELETE"
	
	public void eliminarVivienda(int id_vivienda) {
		viviendaDAO.delete(id_vivienda);
	}
	
	public void eliminarDueño(int id_dueño) {
		dueñoDAO.delete(id_dueño);
	}
	
	public void eliminarTipoResiduo(int id_tipores) {
		tiporesiduoDAO.delete(id_tipores);
	}
	
	public void eliminarRecolector(int id_recolector) {
		recolectorDAO.delete(id_recolector);
	}
	
	public void eliminarPedido(int id_pedido) {
		pedidoDAO.delete(id_pedido);
	}
	
	public void eliminarVisita(int id_visita) {
		Visita visita = this.buscarVisitaXId(id_visita);
		visitaDAO.delete(visita);
	}
	
	public void eliminarPremio(int id_premio) {
		premiosDAO.delete(id_premio);
	}
	
	public void eliminarMembresia(int i) {
		membresiaDAO.delete(i);
	}
	
	//-------------------------------------------------------------------------------------//
	
	//TODOS LOS "UPDATE"
	
	public void modificarVivienda(int dni, int id_vivienda, int numCalle, String calle, long latitud, long longitud, String provincia, String ciudad) {
		Dueño p1 = this.buscarDueñoXDNI(dni);
		Vivienda v1 = new Vivienda (id_vivienda, p1, ciudad, provincia, latitud, longitud, calle, numCalle);
		dueñoDAO.update((Dueño)p1);
		viviendaDAO.update(v1);

	}
	
	public void modificarDueño(int id_dueño,int DNI, String nombre, String apellido) {
		Persona d = new Dueño(id_dueño,DNI, nombre, apellido);
		dueñoDAO.update((Dueño) d);
	}
	
	public void modificarTipoResiduo(int id_tiporesiduo, String nombre, int puntosxkg) {
		TipoResiduo t = new TipoResiduo(id_tiporesiduo, nombre, puntosxkg);
		tiporesiduoDAO.update(t);
	}
	
	public void modificarRecolector(int id, String nombre, String apellido, int dni, boolean ocupado){
		Persona recolector = new Recolector(id, nombre, apellido, dni, ocupado);
		recolectorDAO.update((Recolector) recolector);
	}
	
	private void modificarRecolector(Recolector recolector) {
		recolectorDAO.update(recolector);
	}
	
	public void modificarPedido(int id, int id_vivienda, Date fecha, boolean cargaPesada, String observacion) {
		Vivienda vivienda = this.buscarViviendaXId(id_vivienda);
		Pedido p = new Pedido(id, vivienda, fecha, cargaPesada, observacion);
		pedidoDAO.update(p);
	}
	
	public void modificarVisita(int id, Date fecha, String observaciones){
		Vivienda vivienda = this.buscarViviendaXIdVisita(id);
		OrdenDeRetiro orden = this.buscarOrdenXIdVisita(id);
		Visita visita = new Visita(id,vivienda, (java.sql.Date) fecha ,observaciones,orden);
		visitaDAO.update(visita);
	}

	public void modificarResiduos(int id, int kg, int tipoRes, int id_pedido) {
		TipoResiduo tipo = this.buscarTipoResiduoXId(tipoRes);
		Pedido pedido = this.buscarPedidoXId(id_pedido);
		Residuos r = new Residuos(id,kg,tipo,pedido);
		residuoDAO.update(r);
	}
	
	public void modificarResiduosV(int id, int kg, int tipoRes, int id_visita){
		Visita visita = this.buscarVisitaXId(id_visita);
		TipoResiduo tipo = this.buscarTipoResiduoXId(tipoRes);
		Residuos r = new Residuos(id,kg,tipo,visita);
		residuovisitaDAO.update(r);
	}
	
	public void modificarPremio(int id, String nombre, int puntos, String observaciones){
		Premio p = new Premio(id,nombre,puntos,observaciones);
		premiosDAO.update(p);
	}
	
	public void modificarMembresia(int id_membresia, java.sql.Date fecha, int id_dueño, int puntos){
		Membresia m = new Membresia(id_membresia,this.buscarDueñoXId(id_dueño), puntos, fecha);
		membresiaDAO.update(m);
	}
	
	public void cancelarOrden(int id_orden) { //CANCELA UNA ORDEN DE RETIRO
		OrdenDeRetiro orden = this.buscarOrdenXId(id_orden);
		orden.cancelarOrden();
		ordenDAO.update(orden);
		this.modificarRecolector(orden.getRecolector());
	}

	public void actualizarOrden(int id_orden) { //ACTUALIZA UNA ORDEN DE RETIRO (PONE EN EJECUCION)
		OrdenDeRetiro orden = this.buscarOrdenXId(id_orden);
		orden.actualizarOrden();
		ordenDAO.update(orden);
	}
	
	public void concretarOrden(int id_orden){ //CONCRETA UNA ORDEN DE RETIRO
		OrdenDeRetiro orden = this.buscarOrdenXId(id_orden);
		orden.concretarOrden();
		this.actualizarMembresia(orden);
		this.modificarRecolector(orden.getRecolector());
		ordenDAO.update(orden);
	}
	
	private void actualizarMembresia(OrdenDeRetiro orden){ //ACTUALIZA LA MEMEBRESIA DEL DUEÑO CON LOS PUNTOS POR CONCRETADO
		Membresia mem = this.buscarMembresiaXDNIDueño(orden.getDNIDueñoDeVivienda());
		if(mem!=null) {
			MunicipioClub m1 = this.buscarMunicipioClubXId(1);
			m1.agregarPuntosAMembresia(mem, orden.getPuntosPedido());
			membresiaDAO.update(mem);
		}

	}
	
	public void verificarOrden(int id){ //verifica el estado de la orden para agregarle visitas
		OrdenDeRetiro orden = this.buscarOrdenXId(id);
		orden.verificarOrden();
	}
	

	
	//-------------------------------------------------------------------------------------//
	
	//TODOS LOS "FIND BY"
	
	public Vivienda buscarViviendaXId(int id){
		Vivienda v = viviendaDAO.findById(id);
		return v;
	}
	
	public Vivienda buscarViviendaXIdVisita(int id) {
		Vivienda v = viviendaDAO.findByIdVisita(id);
		return v;
	}
	
	private OrdenDeRetiro buscarOrdenXIdVisita(int id){
		OrdenDeRetiro orden = ordenDAO.findByIdVisita(id);
		return orden;
	}
	
	public Pedido buscarPedidoXId(int id){
		Pedido p = pedidoDAO.findById(id);
		return p;
	}
	
	public MunicipioClub buscarMunicipioClubXId(int valueAt){
		MunicipioClub m1 = municipioDAO.findById(valueAt);
		return m1;
	}
	
	public Dueño buscarDueñoXId(int id) {
		return dueñoDAO.findById(id);
	}
	
	public Visita buscarVisitaXId(int id) {
		Visita v = visitaDAO.findById(id);
		return v;
	}
	
	public TipoResiduo buscarTipoResiduoXId(int id){
		return tiporesiduoDAO.findById(id);
	}
	
	public OrdenDeRetiro buscarOrdenXId(int id){
		return ordenDAO.findById(id);
	}
	
	public Recolector buscarRecolectorXId(int id_recolector) {
		return recolectorDAO.findById(id_recolector);
	}

	public Recolector buscarRecolectorXDNI(int dni){
		return recolectorDAO.findByDNI(dni);
	}
	
	public OrdenDeRetiro buscarOrdenPorIdPedido(int id_pedido){
		return ordenDAO.findByPedidoId(id_pedido);
	}
	
	public Dueño buscarDueñoXDNI(int dni){
		return dueñoDAO.findByDNI(dni);
	}

	public Premio buscarPremioXId(int id){
		return premiosDAO.findById(id);
	}
	
	public Membresia buscarMembresiaXDNIDueño(int parseInt){
		return membresiaDAO.findByDniDueño(parseInt);
	}

	public Membresia buscarMembresiaXId(int valueAt){
		return membresiaDAO.findById(valueAt);
	}
	
	public int buscarIdPedidoXFechayVivienda(int id_vivienda, java.sql.Date d) {
		Vivienda vivienda = buscarViviendaXId(id_vivienda);
		return pedidoDAO.findByFechayV(vivienda, (java.sql.Date) d).getId();
	}
	
	public int buscarIdVisitaXFechayOrden(int id_orden, java.sql.Date d){
		return visitaDAO.findByFechayO(id_orden,(java.sql.Date) d).getId();
	}
	
	public void canjearPremio(int id_membresia, int id_premio){ //CANJEA UN PREMIO POR LOS PUNTOS DE UNA MEMBRESIA
		MunicipioClub m1 = this.buscarMunicipioClubXId(1);
		Membresia me = this.buscarMembresiaXId(id_membresia);
		Premio pe = this.buscarPremioXId(id_premio);
		m1.relizarCanje(me, pe);
		membresiaDAO.update(me);
	}
	
}
