package model.dao;

import db.DB;
import model.dao.impl.LocacaoDaoJDBC;

public class DaoFactory {
	
	public static LocacaoDao createLocacaoDao() {
		return new LocacaoDaoJDBC(DB.getConnection());
	}
}