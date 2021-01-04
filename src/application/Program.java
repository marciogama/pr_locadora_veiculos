package application;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.LocacaoDao;
import model.entities.Carro;
import model.entities.Locacao;

public class Program {

	public static void main(String[] args) {
		
		LocacaoDao locacaoDao = DaoFactory.createLocacaoDao();

		System.out.println("=== Teste 1: Locacao findById ====");
		Locacao locacao = locacaoDao.findById(3);
		System.out.println(locacao);
		
		System.out.println("\n=== Teste 2: Locacao findByCarro ====");
		Carro carro = new Carro(2,null,null,null,null,null, null);
		List<Locacao> list = locacaoDao.findByCarro(carro);
		for (Locacao obj : list) {
			System.out.println(obj);
		}
		
		
		
	}
}
