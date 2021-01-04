package model.dao;

import model.dao.impl.LocacaoDaoJDBC;

public class DaoFactory {
	
	public static LocacaoDao createLocacaoDao() {
		return new LocacaoDaoJDBC();
	}

}
