package model.dao;

import java.util.List;

import model.entities.Carro;

public interface CarroDao {
	
	void insert(Carro obj);
	void update(Carro obj);
	void deleteById(Integer id);
	Carro findById(Integer id);
	List<Carro> findAll();
}
