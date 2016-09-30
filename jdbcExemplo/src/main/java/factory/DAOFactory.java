package factory;

import conexao.ConexaoMysqlProducao;
import dao.CidadeDAO;
import dao.CidadeJDBC;
import dao.EnderecoDAO;
import dao.EnderecoJDBC;
import dao.UFDAO;
import dao.UFJDBC;

public class DAOFactory {

	private static DAOFactory factory;
	
	/**
	 * Pega a fabrica de DAO, instância única para todo mundo.
	 * Padrão Singleton simples.
	 * @return
	 */
	public static DAOFactory get(){
		if(factory == null){
			factory = new DAOFactory();
		}
		return factory;
	}
	
	/**
	 * Cria um DAO e retorna para quem chamou a interface DAO.
	 * @return EnderecoDAO
	 */
	public EnderecoDAO enderecoDAO(){
		return new EnderecoJDBC(new ConexaoMysqlProducao());
	}
	
	public CidadeDAO cidadeDAO(){
		return new CidadeJDBC(new ConexaoMysqlProducao());
	}
	
	public UFDAO ufDAO(){
		return new UFJDBC(new ConexaoMysqlProducao());
	}
	
}
