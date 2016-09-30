package dao;

import java.util.List;

import model.UF;

public interface UFDAO extends CrudDAO<UF> {

	List<UF> getPorNome(String nome);
}
