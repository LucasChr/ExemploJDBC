package dao;

import java.util.List;

import model.Cidade;

public interface CidadeDAO extends CrudDAO<Cidade> {

	List<Cidade> todosComUf();
}
