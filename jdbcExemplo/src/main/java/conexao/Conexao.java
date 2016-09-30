package conexao;

import java.sql.Connection;

public interface Conexao {

	Connection get();
	
	void close();
}
