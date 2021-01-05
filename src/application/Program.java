package application;

import java.util.Date;
import java.util.List;

import model.dao.DaoFactory;
import model.dao.LocacaoDao;
import model.entities.Carro;
import model.entities.Cliente;
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
		
		System.out.println("\n=== Teste 3: Locacao findByCliente ====");
		Cliente cliente = new Cliente("44444444444",null,null);
//		list = locacaoDao.findByCliente(cliente);
//		for (Locacao obj : list) {
//			System.out.println(obj);
//		}
		
		System.out.println("\n=== Teste 4: Locacao findAll ====");
		list = locacaoDao.findAll();
		for (Locacao obj : list) {
			System.out.println(obj);
		}
		
		System.out.println("\n=== Teste 5: Locacao insert ====");
		Locacao novaLocacao = new Locacao(null, new Date(), new Date(), "Fortaleza", 3000.00, carro, cliente );
		locacaoDao.insert(novaLocacao);
		System.out.println("Inserido! Novo id = "+novaLocacao.getId());
	}
}
