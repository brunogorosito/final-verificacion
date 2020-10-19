package ar.edu.unrn.seminario.dao;

import java.util.List;

import ar.edu.unrn.seminario.modelo.MunicipioClub;

public interface MunicipioClubDAO {
	
	public void create(MunicipioClub n);
	public void update(MunicipioClub n);
	public void delete(int codigoMunicipioClub);
	public void delete(MunicipioClub n);
	public List<MunicipioClub> findAll();
	public MunicipioClub findById(int codigoMunicipioClub);

}
