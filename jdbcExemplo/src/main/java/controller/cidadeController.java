package controller;

import java.util.Collection;
import dao.CidadeDAO;
import dao.UFDAO;
import factory.DAOFactory;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.util.StringConverter;
import model.Cidade;
import model.UF;

public class cidadeController {

	@FXML
	private BorderPane panelPrincipal;

	@FXML
	private TextField tfCodigo;

	@FXML
	private TextField tfNome;

	@FXML
	private ComboBox<UF> cbUF;

	@FXML
	private TableView<Cidade> tblCidade;

	@FXML
	private TableColumn<Cidade, Number> tcCodigo;

	@FXML
	private TableColumn<Cidade, String> tcNome;

	@FXML
	private TableColumn<Cidade, String> tcUF;

	private UFDAO ufDAO;
	private CidadeDAO cidadeDAO;

	public cidadeController() {
		ufDAO = DAOFactory.get().ufDAO();
		cidadeDAO = DAOFactory.get().cidadeDAO();
	}

	private void ouvirSelecaoTabela() {
		tblCidade.getSelectionModel().selectedItemProperty()
				.addListener((observable, cidadeVelha, cidadeSelecionada) -> {
					if (cidadeSelecionada != null) {
						populaTela(cidadeSelecionada);
					}
				});
	}

	@FXML
	public void initialize() {
		tcCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
		tcNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		tcUF.setCellValueFactory(new PropertyValueFactory<>("nomeUF"));
		atualizaTabela();
		ouvirSelecaoTabela();
		montaComboUF();
	}

	private void atualizaTabela() {
		Collection<Cidade> cidades = cidadeDAO.todosComUf();
		tblCidade.setItems(FXCollections.observableArrayList(cidades));
	}

	private void populaTela(Cidade cidade) {
		tfCodigo.setText(cidade.getCodigo().toString());
		tfNome.setText(cidade.getNome());
		cbUF.setValue(cidade.getUf());
	}

	private void montaComboUF() {
		cbUF.getItems().addAll(ufDAO.todos());
		
		cbUF.setCellFactory((comboBox) -> {
			return new ListCell<UF>() {
				@Override
				protected void updateItem(UF item, boolean empty) {
					super.updateItem(item, empty);
					if (item == null || empty) {
						setText(null);
					} else {
						setText(item.getNome());
					}
				}
			};
		});

		// define um valor que renderiza quando o item é selecionado
		cbUF.setConverter(new StringConverter<UF>() {
			@Override
			public String toString(UF uf) {
				if (uf == null) {
					return null;
				} else {
					return uf.getCodigo() + " - " + uf.getNome();
				}
			}

			@Override
			public UF fromString(String personString) {

				return null;
			}
		});
	}

	@FXML
	void onSalvar(ActionEvent event) {
		Cidade cidade = criaCidade();
		cidadeDAO.salvar(cidade);
		atualizaTabela();
		tfCodigo.setText(cidade.getCodigo().toString());
	}

	@FXML
	void onNovo(ActionEvent event) {
		limparCampos();
	}

	@FXML
	void onExcluir(ActionEvent event) {
		cidadeDAO.excluir(Long.valueOf(tfCodigo.getText()));
		atualizaTabela();
		limparCampos();
	}

	private void limparCampos() {
		tfCodigo.setText("");
		tfNome.setText("");
	}

	private Cidade criaCidade() {
		String codigo = tfCodigo.getText();
		Cidade cidade = new Cidade();
		cidade.setNome(tfNome.getText());
		cidade.setUf(cbUF.getValue());
		if (!codigo.isEmpty()) {
			cidade.setCodigo(Long.valueOf(codigo));
		}
		cidade.setUf(cbUF.getValue());
		return cidade;
	}

}
