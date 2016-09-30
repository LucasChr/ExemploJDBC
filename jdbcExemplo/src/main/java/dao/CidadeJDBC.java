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
import model.UF;

public class CidadeJDBC implements CidadeDAO {

	private Conexao conexao;

	/**
	 * Conexão com o banco de dados.
	 * 
	 * @param conexao.get()
	 */
	public CidadeJDBC(Conexao conexao) {
		this.conexao = conexao;
	}

	@Override
	public void inserir(Cidade objeto) {
		String insert = "insert into Cidade (nome,codUF) "
				+ "values(?,?)";
		try {
			PreparedStatement ps = conexao.get().prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, objeto.getNome());
			ps.setLong(2, objeto.getUf().getCodigo());
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
	public void alterar(Cidade objeto) {
		String update = "update Cidade set nome=?, codUF = ?"
				+ " where codCidade = ?";
		try {
			PreparedStatement ps = conexao.get().prepareStatement(update);
			ps.setString(1, objeto.getNome());
			ps.setLong(2, objeto.getUf().getCodigo());
			ps.setLong(3, objeto.getCodigo());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conexao.close();
		}
	}

	@Override
	public void excluir(Long codigo) {
		String delete = "delete from Cidade where codCidade = ?";
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
	public Collection<Cidade> todos() {
		String sql = "select * from Cidade";
		List<Cidade> cidades = new ArrayList<>();
		try {
			PreparedStatement ps = conexao.get().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			cidades = getLista(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conexao.close();
		}
		return cidades;
	}

	@Override
	public Cidade get(Long codigo) {
		String sql = "select * from Cidade where codCidade = ?";
		Cidade cidade = null;
		try {
			PreparedStatement ps = conexao.get().prepareStatement(sql);
			ps.setLong(1, codigo);
			ResultSet rs = ps.executeQuery();
			// Passa por todos os registros que vieram do banco.
			while (rs.next()) {
				cidade = getCidade(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conexao.close();
		}
		return cidade;
	}
	
	private List<Cidade> getLista(ResultSet rs) throws SQLException {
		List<Cidade> cidades = new ArrayList<>();
		while (rs.next()) {
			cidades.add(getCidade(rs));
		}
		return cidades;
	}

	private Cidade getCidade(ResultSet rs) throws SQLException {
		Cidade cidade = new Cidade(rs.getLong("codCidade"), 
				rs.getString("nome"), new UF(rs.getLong("codUF")));
		return cidade;
	}

	@Override
	public List<Cidade> todosComUf() {
		String sql = "select c.codCidade, c.nome nomeCidade, c.codUF, u.nome nomeUf"
				+ " from Cidade c join UF u on c.codUF = u.codUF ";
		List<Cidade> cidades = new ArrayList<>();
		try {
			PreparedStatement ps = conexao.get().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				Cidade cidade = new Cidade(rs.getLong("codCidade"), 
						rs.getString("nomeCidade"), 
						new UF(rs.getLong("codUF"), rs.getString("nomeUf")));
				cidades.add(cidade);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conexao.close();
		}
		return cidades;
	}

}
