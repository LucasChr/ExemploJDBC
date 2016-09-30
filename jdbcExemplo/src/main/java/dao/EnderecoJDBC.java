package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import conexao.Conexao;
import model.Cidade;
import model.Endereco;

public class EnderecoJDBC implements EnderecoDAO {

	private Conexao conexao;
	
	/**
	 * Conexão com o banco de dados.
	 * @param conexao.get()
	 */
	public EnderecoJDBC(Conexao conexao){
		this.conexao = conexao;
	}
	
	@Override
	public void inserir(Endereco objeto) {
		String insert = "insert into endereco (rua,bairro,codcidade) "
				+ "values(?,?,?)";
		try {
			PreparedStatement ps = conexao.get().prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, objeto.getRua());
			ps.setString(2, objeto.getBairro());
			ps.setLong(3, objeto.getCidade().getCodigo());
			ps.executeUpdate();
			// Popular o objeto com o código gerado.
			ResultSet rs = ps.getGeneratedKeys();
			rs.next();
			objeto.setCodigo(rs.getLong(1));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conexao.close();
		}
	}

	@Override
	public void alterar(Endereco objeto) {
		String update = "update endereco set rua=?, bairro=?, "
				+ "codcidade = ? where codendereco = ?";
		try {
			PreparedStatement ps = conexao.get().prepareStatement(update);
			ps.setString(1, objeto.getRua());
			ps.setString(2, objeto.getBairro());
			ps.setLong(3, objeto.getCidade().getCodigo());
			ps.setLong(4, objeto.getCodigo());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conexao.close();
		}

	}

	@Override
	public void excluir(Long codigo) {
		String delete = "delete from endereco " + "where codendereco = ?";
		try {
			PreparedStatement ps = conexao.get().prepareStatement(delete);
			ps.setLong(1, codigo);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conexao.close();
		}
	}

	@Override
	public Collection<Endereco> todos() {
		String sql = "select * from Endereco";
		List<Endereco> enderecos = new ArrayList<>();
		try {
			PreparedStatement ps = conexao.get().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			enderecos = getLista(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conexao.close();
		}
		return enderecos;
	}

	@Override
	public Endereco get(Long codigo) {
		String sql = "select * from Endereco where codendereco = ?";
		Endereco endereco = null;
		try {
			PreparedStatement ps = conexao.get().prepareStatement(sql);
			ps.setLong(1, codigo);
			ResultSet rs = ps.executeQuery();
			// Passa por todos os registros que vieram do banco.
			while (rs.next()) {
				endereco = getEndereco(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conexao.close();
		}
		return endereco;
	}

	@Override
	public Collection<Endereco> getPorRua(String rua) {
		String sql = "select * from Endereco where rua = ?";
		List<Endereco> enderecos = new ArrayList<>();
		try {
			PreparedStatement ps = conexao.get().prepareStatement(sql);
			ps.setString(1, rua);
			ResultSet rs = ps.executeQuery();
			enderecos = getLista(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conexao.close();
		}
		return enderecos;
	}

	private List<Endereco> getLista(ResultSet rs) throws SQLException {
		List<Endereco> enderecos = new ArrayList<>();
		while (rs.next()) {
			enderecos.add(getEndereco(rs));
		}
		return enderecos;
	}

	private Endereco getEndereco(ResultSet rs) throws SQLException {
		Endereco endereco = new Endereco(rs.getLong("codendereco"), 
				rs.getString("rua"),
				rs.getString("bairro"),
				new Cidade(rs.getLong("codcidade")));
		return endereco;
	}

}
