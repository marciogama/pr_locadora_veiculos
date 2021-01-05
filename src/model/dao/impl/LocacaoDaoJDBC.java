package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO locacao" 
					+ "(instanteLocacao, instanteDevolucao, sede, valorTotal, carroId, cpfId)"
					+ "VALUES"
					+ "(?,?,?,?,?,?)",
					Statement.RETURN_GENERATED_KEYS);
			
			st.setDate(1, new java.sql.Date(obj.getInstanteLocacao().getTime()));
			st.setDate(2, new java.sql.Date(obj.getInstanteDevolucao().getTime()));
			st.setString(3, obj.getSede());
			st.setDouble(4, obj.getValorTotal());
			st.setInt(5, obj.getCarro().getId());
			st.setString(6, obj.getCliente().getCpf());
			
			int rowsAffected = st.executeUpdate();
			
			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				}
				DB.closeResultSet(rs);
			}
			else {
				throw new DbException("Erro inesperado! Nenhuma linha afetada!");
			}
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void update(Locacao obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE locacao " 
					+"SET instanteLocacao=?, instanteDevolucao=?, sede=?, valorTotal=?, carroId=?, cpfId=? "
					+ "WHERE Id = ?");
			
			st.setDate(1, new java.sql.Date(obj.getInstanteLocacao().getTime()));
			st.setDate(2, new java.sql.Date(obj.getInstanteDevolucao().getTime()));
			st.setString(3, obj.getSede());
			st.setDouble(4, obj.getValorTotal());
			st.setInt(5, obj.getCarro().getId());
			st.setString(6, obj.getCliente().getCpf());
			st.setInt(7, obj.getId());
			
			st.executeUpdate();
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
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
					+"cliente.cpf as CliCpf, "
					+"cliente.email as CliEmail, "
					+"cliente.nome as CliNome "
					+"FROM locacao " 
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
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT locacao.*,  "
					+ "carro.modelo as CarModelo, " 
					+ "carro.placa as CarPlaca, "
					+ "carro.cor as CarCor, "
					+ "carro.ano as CarAno, "
					+ "carro.dataAquisicao as CarDataAq, "
					+ "carro.valorDiaria as CarValDia, "
					+ "cliente.cpf as CliCpf, "
					+ "cliente.email as CliEmail, "
					+ "cliente.nome as CliNome "
					+ "FROM locacao "
					+ "INNER JOIN carro ON locacao.carroId = carro.id "
					+ "INNER JOIN cliente ON locacao.cpfId=cliente.cpf "
					+ "ORDER BY Modelo" ); 
				
			rs = st.executeQuery();
			
			List<Locacao> list = new ArrayList<>();
			Map<Integer, Carro> map = new HashMap<>();
			
			while (rs.next()) {
				
				Carro car = map.get(rs.getInt("carroId"));
				
				if (car == null) {
					car = instantiateCarro(rs);
					map.put(rs.getInt("carroId"),car);
				}
				
				Cliente cli = instantiateCliente(rs);
				Locacao obj = instantiateLocacao(rs, car, cli);
				list.add(obj);
			}
			return list;
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}

	}

	@Override
	public List<Locacao> findByCarro(Carro carro) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT locacao.*,  "
					+ "carro.modelo as CarModelo, " 
					+ "carro.placa as CarPlaca, "
					+ "carro.cor as CarCor, "
					+ "carro.ano as CarAno, "
					+ "carro.dataAquisicao as CarDataAq, "
					+ "carro.valorDiaria as CarValDia, "
					+ "cliente.cpf as CliCpf, "
					+ "cliente.email as CliEmail, "
					+ "cliente.nome as CliNome "
					+ "FROM locacao "
					+ "INNER JOIN carro ON locacao.carroId = carro.id "
					+ "INNER JOIN cliente ON locacao.cpfId=cliente.cpf "
					+ "WHERE carroId = ? "
					+ "ORDER BY Modelo" ); 
				
			st.setInt(1, carro.getId());	
			
			rs = st.executeQuery();
			
			List<Locacao> list = new ArrayList<>();
			Map<Integer, Carro> map = new HashMap<>();
			
			while (rs.next()) {
				
				Carro car = map.get(rs.getInt("carroId"));
				
				if (car == null) {
					car = instantiateCarro(rs);
					map.put(rs.getInt("carroId"),car);
				}
				
				Cliente cli = instantiateCliente(rs);
				Locacao obj = instantiateLocacao(rs, car, cli);
				list.add(obj);
			}
			return list;
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<Locacao> findByCliente(Cliente cliente) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT locacao.*,  "
					+ "carro.modelo as CarModelo, " 
					+ "carro.placa as CarPlaca, "
					+ "carro.cor as CarCor, "
					+ "carro.ano as CarAno, "
					+ "carro.dataAquisicao as CarDataAq, "
					+ "carro.valorDiaria as CarValDia, "
					+ "cliente.cpf as CliCpf, "
					+ "cliente.email as CliEmail, "
					+ "cliente.nome as CliNome "
					+ "FROM locacao "
					+ "INNER JOIN carro ON locacao.carroId = carro.id "
					+ "INNER JOIN cliente ON locacao.cpfId=cliente.cpf "
					+ "WHERE cpfId = ? "
					+ "ORDER BY Nome" ); 
				
			st.setString(1, cliente.getCpf());	
			
			rs = st.executeQuery();
			
			List<Locacao> list = new ArrayList<>();
			Map<Integer, Cliente> map = new HashMap<>();
			
			while (rs.next()) {
				
//				Cliente cli = map.get(rs.getInt("cpfId"));
				
				Cliente cli = map.get(rs.getInt("cpfId"));
							
				if (cli == null) {
					cli = instantiateCliente(rs);
					map.put(rs.getInt("cpfId"),cli);
				}
				
				Carro car = instantiateCarro(rs);
				Locacao obj = instantiateLocacao(rs, car, cli);
				list.add(obj);
			}
			return list;
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}
}
