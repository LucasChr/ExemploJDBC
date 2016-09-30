package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class PrincipalController {

	@FXML
	private BorderPane panelPrincipal;
	
    @FXML
    private MenuItem menuUF;

    @FXML
    private MenuItem menuCidade;

    @FXML
    void onMenuUF(ActionEvent event) {
    	AbreTela("UFForm.fxml");
    }

    @FXML
    void onMenuCidade(ActionEvent event) {
    	AbreTela("CidadeForm.fxml");
    }

    public void AbreTela(String tela) {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/telas/" +tela));
		try {
			AnchorPane telaView = (AnchorPane) loader.load();
			panelPrincipal.setCenter(telaView);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
