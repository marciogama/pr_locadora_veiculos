package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.LocacaoDao;
import model.entities.Carro;
import model.entities.Cliente;
import model.entities.Locacao;

public class LocacaoDaoJDBC implements LocacaoDao {
	
	private Connection conn;
	
	public LocacaoDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Locacao obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Locacao obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Integer id) {
		
		// TODO Auto-generated method stub
		
	}

	@Override
	public Locacao findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT locacao.*, " 
					+"carro.modelo as CarModelo, " 
					+"carro.placa as CarPlaca, "
					+"carro.cor as CarCor, "
					+"carro.ano as CarAno, "
					+"carro.dataAquisicao as CarDataAq, "
					+"carro.valorDiaria as CarValDia, "
					+"cliente.cpf as CliCPf, "
					+"cliente.email as CliEmail, "
					+"cliente.nome as CliNome FROM locacao " 
					+"INNER JOIN carro On locacao.carroId=carro.id " 
					+"INNER JOIN cliente ON locacao.cpfId=cliente.cpf "
					+"where locacao.carroId = ?"); 
				
			st.setInt(1, id);			
			rs = st.executeQuery();
			if (rs.next()) {
				Carro car = instantiateCarro(rs);
				Cliente cli = instantiateCliente(rs);
				Locacao obj = instantiateLocacao(rs, car, cli);
				return obj;
			}
			return null;
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	private Locacao instantiateLocacao(ResultSet rs, Carro car, Cliente cli) throws SQLException {
		
		Locacao obj = new Locacao();
		obj.setId(rs.getInt("id"));
		obj.setInstanteLocacao(rs.getDate("instanteLocacao"));
		obj.setInstanteDevolucao(rs.getDate("instanteDevolucao"));
		obj.setSede(rs.getString("sede"));
		obj.setValorTotal(rs.getDouble("valorTotal"));
		obj.setCarro(car);
		obj.setCliente(cli);
		
		return obj;
	}

	private Cliente instantiateCliente(ResultSet rs) throws SQLException {
		Cliente cli = new Cliente();
		cli.setCpf(rs.getString("CliCpf"));
		cli.setNome(rs.getString("CliNome"));
		cli.setEmail(rs.getString("CliEmail")); 
		return cli;
	}

	private Carro instantiateCarro(ResultSet rs) throws SQLException {
		Carro car = new Carro();
		car.setId(rs.getInt("carroId"));
		car.setModelo(rs.getString("CarModelo"));
		car.setPlaca(rs.getString("CarPlaca"));
		car.setCor(rs.getString("CarCor"));
		car.setAno(rs.getInt("CarAno"));
		car.setDataAquisicao(rs.getDate("CarDataAq"));
		car.setValorDiaria(rs.getDouble("CarValDia"));
		return car;
	}

	@Override
	public List<Locacao> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
