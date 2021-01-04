package application;

import model.dao.DaoFactory;
import model.dao.LocacaoDao;
import model.entities.Locacao;

public class Program {

	public static void main(String[] args) {
		
//		Carro obj = new Carro(1,"Corsa","LLL3444","Branca",1980,new Date(),15.0);
//
//		Cliente obj1 = new Cliente("11111111111", "fulano de tal", "teste@gmail.com");
//		
//		Locacao obj2 = new Locacao(21, new Date(), new Date(), "Rio de Janeiro", 10.5,obj,obj1);
//		
//		LocacaoDao locacaoDao = DaoFactory.createLocacaoDao();
//		
//		System.out.println(obj);
//		System.out.println(obj1);
//		System.out.println(obj2);
		
		LocacaoDao locacaoDao = DaoFactory.createLocacaoDao();
		
		Locacao locacao = locacaoDao.findById(3);
		
		System.out.println(locacao);
		
	}
	
}
