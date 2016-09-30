package controller;

import java.util.Collection;
import java.util.List;

import dao.UFDAO;
import factory.DAOFactory;
import model.UF;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;

public class UFController {

	@FXML
	private BorderPane panelPrincipal;
	
	@FXML
	private TextField tfCodigo;

	@FXML
	private TextField tfNome;
	
	@FXML
	private TextField tfPesquisa;

	@FXML
	private TableView<UF> tblUf;

	@FXML
	private TableColumn<UF, Number> tcCodigo;

	@FXML
	private TableColumn<UF, String> tcNome;

	private UFDAO ufDAO;

	public UFController() {
		ufDAO = DAOFactory.get().ufDAO();
	}

	@FXML
	public void initialize() {
		// tcCodigo.setCellValueFactory(uf ->
		// uf.getValue().getCodigoProperty());
		// tcNome.setCellValueFactory(uf -> uf.getValue().getNomeProperty());
		tcCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
		tcNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		atualizaTabela();
		ouvirSelecaoTabela();
	}

	private void ouvirSelecaoTabela() {
		tblUf.getSelectionModel().selectedItemProperty().addListener((observable, ufVelha, ufSelecionada) -> {
			if (ufSelecionada != null) {
				populaTela(ufSelecionada);
			}
		});
	}

	private void atualizaTabela() {
		Collection<UF> ufs = ufDAO.todos();
		tblUf.setItems(FXCollections.observableArrayList(ufs));
	}

	private void populaTela(UF ufSelecionada) {
		tfCodigo.setText(ufSelecionada.getCodigo().toString());
		tfNome.setText(ufSelecionada.getNome());
	}

	@FXML
	void onSalvar(ActionEvent event) {
		UF uf = criaUf();
		ufDAO.salvar(uf);
		atualizaTabela();
		tfCodigo.setText(uf.getCodigo().toString());
	}

	@FXML
	void onNovo(ActionEvent event) {
		limparCampos();
	}

	@FXML
	void onExcluir(ActionEvent event) {
		ufDAO.excluir(Long.valueOf(tfCodigo.getText()));
		atualizaTabela();
		limparCampos();
	}

	@FXML
	void onPesquisar(KeyEvent event) {
		if(tfPesquisa.getText().length() > 3){
			List<UF> ufs = ufDAO.getPorNome(tfPesquisa.getText());
			tblUf.setItems(FXCollections.observableArrayList(ufs));
		}
		if(tfPesquisa.getText().isEmpty()){
			atualizaTabela();
		}
	}

	private void limparCampos() {
		tfCodigo.setText("");
		tfNome.setText("");
	}

	private UF criaUf() {
		String codigo = tfCodigo.getText();
		UF uf = new UF();
		uf.setNome(tfNome.getText());
		if (!codigo.isEmpty()) {
			uf.setCodigo(Long.valueOf(codigo));
		}
		return uf;
	}
}
