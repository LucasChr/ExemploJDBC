package dao;

import java.util.Collection;
import model.Endereco;

public interface EnderecoDAO extends CrudDAO<Endereco> {

	Collection<Endereco> getPorRua(String rua);
}
