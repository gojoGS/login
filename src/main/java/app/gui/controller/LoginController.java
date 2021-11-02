package app.gui.controller;

import app.service.Status;
import app.service.WebAuthenticationService;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class LoginController {
    @FXML
    public TextField usernameField;

    @FXML
    public TextField passwordField;

    @FXML
    public Button submitButton;

    @FXML
    public void initialize() {
        submitButton.setOnAction(this::onSubmit);
    }

    private void alert(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR, msg);
        alert.show();
    }

    private void onSubmit(ActionEvent actionEvent) {
        WebAuthenticationService service = new WebAuthenticationService();
        if(usernameField.getText().length() == 0) {
            alert("Username too short");
            return;
        }

        if(passwordField.getText().length() == 0) {
            alert("password too short");
            return;
        }

        Status status = service.authenticate(usernameField.getText(), passwordField.getText());
        switch (status) {
            case INVALID_REQUEST -> alert("internal error");
            case SERVER_UNAVAILABLE -> alert("server unreachable");
            case USER_NOT_FOUND -> alert("user not found");
            case FAILED -> alert("invalid password");
            case SUCCESSFUL -> Platform.exit();
        }
    }
}
