package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import conexao.Conexao;
import model.UF;

public class UFJDBC implements UFDAO {

	private Conexao conexao;

	/**
	 * Conexão com o banco de dados.
	 * 
	 * @param conexao.get()
	 */
	public UFJDBC(Conexao conexao) {
		this.conexao = conexao;
	}

	@Override
	public void inserir(UF objeto) {
		String insert = "insert into UF (nome) "
				+ "values(?)";
		try {
			PreparedStatement ps = conexao.get().prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, objeto.getNome());
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
	public void alterar(UF objeto) {
		String update = "update UF set nome=?"
				+ " where codUF = ?";
		try {
			PreparedStatement ps = conexao.get().prepareStatement(update);
			ps.setString(1, objeto.getNome());
			ps.setLong(2, objeto.getCodigo());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conexao.close();
		}
	}

	@Override
	public void excluir(Long codigo) {
		String delete = "delete from UF where codUF = ?";
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
	public Collection<UF> todos() {
		String sql = "select * from UF";
		List<UF> UFs = new ArrayList<>();
		try {
			PreparedStatement ps = conexao.get().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			UFs = getLista(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conexao.close();
		}
		return UFs;
	}

	@Override
	public UF get(Long codigo) {
		String sql = "select * from UF where codUF = ?";
		UF UF = null;
		try {
			PreparedStatement ps = conexao.get().prepareStatement(sql);
			ps.setLong(1, codigo);
			ResultSet rs = ps.executeQuery();
			// Passa por todos os registros que vieram do banco.
			while (rs.next()) {
				UF = getUF(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conexao.close();
		}
		return UF;
	}
	
	private List<UF> getLista(ResultSet rs) throws SQLException {
		List<UF> ufs = new ArrayList<>();
		while (rs.next()) {
			ufs.add(getUF(rs));
		}
		return ufs;
	}

	private UF getUF(ResultSet rs) throws SQLException {
		UF uf = new UF(rs.getLong("codUF"), 
				rs.getString("nome"));
		return uf;
	}

	@Override
	public List<UF> getPorNome(String nome) {
		String sql = "select * from UF where nome like ?";
		nome = "%"+nome+"%";
		List<UF> UFs = new ArrayList<>();
		try {
			PreparedStatement ps = conexao.get().prepareStatement(sql);
			ps.setString(1, nome);
			ResultSet rs = ps.executeQuery();
			UFs = getLista(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conexao.close();
		}
		return UFs;
	}

}
