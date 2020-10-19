package ar.edu.unrn.seminario.modelo;
import java.util.ArrayList;
import java.util.Date;

import ar.edu.unrn.seminario.excepciones.AtributoNullException;
import ar.edu.unrn.seminario.excepciones.WrongStateException;

public class OrdenDeRetiro {
	
	private static String ESTADO_PENDIENTE = "PENDIENTE";
	private static String ESTADO_EN_EJECUCION = "EN EJECUCION";
	private static String ESTADO_CONCRETADO = "CONCRETADO";
	private static String ESTADO_CANCELADO = "CANCELADO";
	
	ArrayList <Visita> visitas = new ArrayList <Visita>();
	
	private int id;
	private Pedido pedido;
	private Recolector recolector;
	private Date fecha;
	private String estado;
	

	
	public OrdenDeRetiro(Pedido pedido, Recolector recolector, Date fecha) throws RuntimeException{
		this.pedido = pedido;
		this.recolector = recolector;
		this.recolector.ocupar();
		this.fecha = fecha;
		this.estado = ESTADO_PENDIENTE;
		if(pedido == null || recolector == null) {
			throw new AtributoNullException("Pedido o Recolector invalido al crear la orden de retiro");
		}
	}
	
	public OrdenDeRetiro(int id, Pedido pedido, Persona r1, Date fecha, String estado) throws RuntimeException{
		this.setId(id);
		this.pedido = pedido;
		this.recolector = (Recolector) r1;
		this.fecha = fecha;
		this.estado = estado;
        if(pedido == null || recolector == null) {
			throw new AtributoNullException("Pedido o Recolector invalido al crear la orden de retiro");
		}
	}
	
	public int getDNIDueñoDeVivienda(){
		return this.pedido.getDNIDueñoVivienda();
	}
	
	public int getPuntosPedido() {
		return this.pedido.calcularPuntos();
	}
	
	public Pedido getPedido() {
		return pedido;
	}
	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}
	public Recolector getRecolector() {
		return recolector;
	}
	public void setRecolector(Recolector recolector) {
		this.recolector = recolector;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	public String getEstado() {
		return this.estado;
	}
	public String getNombreApellidoRecolector() {
		return recolector.nombre + " " + recolector.apellido;
	}
	@Override
	public String toString() {
		return "del "+this.getPedido();
	}
	
	public void agregarVisita(Visita visita) {
		if (this.isConcretado() || this.isCancelado()) {
		        throw new WrongStateException("No se pueden agregar visita a ordenes de retiro ya concretadas o canceladas");
		} else {
			this.actualizarOrden();
			visita.setOrden(this); 
		    this.visitas.add(visita);
		}
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public Vivienda getViviendaDePedido() {
		return this.pedido.getVivienda();
	}
	
	public int getIdPedido() {
		return this.getPedido().getId();
	}
	
	public boolean isPendiente() {
		return this.estado.equals(ESTADO_PENDIENTE);
	}
	
	public boolean isEnEjecucion() {
		return this.estado.equals(ESTADO_EN_EJECUCION);
	}
	
	public boolean isCancelado() {
		return this.estado.equals(ESTADO_CANCELADO);
	}
	
	public boolean isConcretado() {
		return this.estado.equals(ESTADO_CONCRETADO);
	}
	
	public void concretarOrden() throws WrongStateException {
        if (this.isConcretado() || this.isCancelado()) {
        	throw new WrongStateException("No se pueden concretar ordenes de retiro ya concretadas o canceladas");
        } else {
        	this.recolector.desocupar();
        	this.estado = ESTADO_CONCRETADO;
        }
    }
	
	public void cancelarOrden() throws WrongStateException {
        if (this.isConcretado() || this.isCancelado()) {
        	throw new WrongStateException("No se pueden cancelar ordenes de retiro ya concretadas o canceladas");
        } else {
        	this.recolector.desocupar();
            this.estado = ESTADO_CANCELADO;
        }
    }
	
	public void actualizarOrden() throws WrongStateException { //pasa estado a EN_EJECUCION
		 if (this.isConcretado() || this.isCancelado()) {
			 throw new WrongStateException("No se pueden actualizar ordenes de retiro ya concretadas o canceladas");
		 }else {
			 if (visitas.isEmpty()) {
				 this.estado = ESTADO_EN_EJECUCION;
		     }
		 }
	}
	
	public void verificarOrden() throws WrongStateException { //verifica la orden para ver si se pueden crear o no visitas nuevas
		 if (this.isConcretado() || this.isCancelado()) {
			 throw new WrongStateException("No se pueden actualizar ordenes de retiro ya concretadas o canceladas");
		 }
	}

	public int getIdViviendaDePedido() {
		return this.getViviendaDePedido().getId();
	}


}

