package application;

import java.util.Date;

import model.entities.Carro;
import model.entities.Cliente;

public class Program {

	public static void main(String[] args) {
		
		Carro obj = new Carro(1,"Corsa","LLL3444","Branca",1980,new Date(),15.0);

		Cliente obj1 = new Cliente("11111111111", "fulano de tal", "teste@gmail.com");
		
		System.out.println(obj);
		System.out.println(obj1);
		
	}
	
}
