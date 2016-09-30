package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Endereco implements Entidade {

	private Long codigo;
	private String rua;
	private String bairro;
	private Cidade cidade;
	
}
