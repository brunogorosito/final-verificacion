package ar.edu.unrn.seminario.modelo;
import java.util.ArrayList;
import java.util.Date;

import ar.edu.unrn.seminario.excepciones.*;

public class Vivienda {
	
	public ArrayList <Pedido> pedidos = new ArrayList <Pedido>();
	
	private int id;
	private Dueño dueño;
	private Date fechaRegistro;
	private String ciudad;
	private String provincia;
	private long latitud;
	private long longitud;
	private String calle;
	private int numero;
	
	public Vivienda(Dueño dueño, Date fecha, String ciudad, String provincia, long latitud, long longitud, String calle, int numero) throws RuntimeException{
		this.setDueño(dueño);
		this.setFechaRegistro(fecha);
		this.setCalle(calle);
		this.setCiudad(ciudad);
		this.setLatitud(latitud);
		this.setLongitud(longitud);
		this.setNumero(numero);
		this.setProvincia(provincia);
		if(dueño == null || fecha == null) {
			throw new AtributoNullException ("Dueño o fecha invalidas");
		}
		if(numero <= 0) {
			throw new SoloNumerosMayoresException("El numero de la calle invalido");
		}
		if(latitud < -100000000 || longitud >100000000) {
			throw new SoloNumerosMayoresException("Latitud o longitud erroneas");
		}
	}

	public Vivienda(int id, Date fecha, String ciudad, String provincia, long latitud, long longitud, String calle, int numero, Persona dueño) throws RuntimeException{
		this.setId(id);
		this.setDueño((Dueño) dueño);
		this.setFechaRegistro(fecha);
		this.setCalle(calle);
		this.setCiudad(ciudad);
		this.setLatitud(latitud);
		this.setLongitud(longitud);
		this.setNumero(numero);
		this.setProvincia(provincia);
		if(dueño == null || fecha == null) {
			throw new AtributoNullException ("Dueño o fecha invalidas");
		}
		if(numero <= 0) {
			throw new SoloNumerosMayoresException("El numero de la calle debe ser > 0");
		}
		if(latitud < -100000000 || longitud >100000000) {
			throw new SoloNumerosMayoresException("Latitud o longitud erroneas");
		}
	}
	
	public Vivienda(int id, Dueño dueño, String ciudad, String provincia, long latitud, long longitud, String calle, int numero){
		this.setId(id);
		this.setDueño(dueño);
		this.setCalle(calle);
		this.setCiudad(ciudad);
		this.setLatitud(latitud);
		this.setLongitud(longitud);
		this.setNumero(numero);
		this.setProvincia(provincia);
		if(dueño == null ){
			throw new AtributoNullException ("Dueño invalido");
		}
		if(numero <= 0) {
			throw new SoloNumerosMayoresException("El numero de la calle debe ser > 0");
		}
		if(latitud < -100000000 || longitud >100000000) {
			throw new SoloNumerosMayoresException("Latitud o longitud erroneas");
		}
	}
	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}
	
	@Override
	public String toString() {
		return "Vivienda N°" + id + "\n"+ 
				"Dueño: " + dueño + "\n"+
				"Fecha de registro: " + fechaRegistro + "\n"+
				"Ciudad: " + ciudad + "\n"+
				"Provincia: " + provincia + "\n"+
				"Latitud: " + latitud + "\n"+
				"Longitud: " + longitud + "\n"+
				"Calle: " + calle + "\n"+
				"Numero: " + numero + "\n" +
				"-----Pedidos------- " + "\n" +
				this.pedidos.toString() + "\n" +
				"-----Fin Pedidos------- ";
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public long getLatitud() {
		return latitud;
	}

	public void setLatitud(long latitud) {
		this.latitud = latitud;
	}

	public long getLongitud() {
		return longitud;
	}

	public void setLongitud(long longitud) {
		this.longitud = longitud;
	}

	public String getCalle() {
		return calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public void agregarPedido(Pedido p1) {
		pedidos.add(p1);
	}


	public Dueño getDueño() {
		return dueño;
	}


	public void setDueño(Dueño dueño) {
		this.dueño = dueño;
	}

	public int getId() {
		return this.id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public Date getFechaRegistro() {
		return fechaRegistro;
	}


	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vivienda other = (Vivienda) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	public int getDNIDueño() {
		return this.getDueño().getDNI();
	}


	
}
