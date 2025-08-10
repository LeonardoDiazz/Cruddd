package com.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;

public class HelloController {
    @FXML private TableView<Crud> tablaTareas;
    @FXML private TableColumn<Crud, Integer> colId;
    @FXML private TableColumn<Crud, String> colTitulo;
    @FXML private TableColumn<Crud, String> colDescripcion;
    @FXML private TableColumn<Crud, String> colPrioridad;
    @FXML private TableColumn<Crud, LocalDate> colFechaLimite;

    @FXML private TextField txtId;
    @FXML private TextField txtTitulo;
    @FXML private TextArea txtDescripcion;
    @FXML private ComboBox<String> cmbPrioridad;
    @FXML private DatePicker dateFechaLimite;

    private final ObservableList<Crud> listaTareas = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colId.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getId()).asObject());
        colTitulo.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getTitulo()));
        colDescripcion.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getDescripcion()));
        colPrioridad.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getPrioridad()));
        colFechaLimite.setCellValueFactory(data -> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getFechaLimite()));

        cmbPrioridad.getItems().addAll("Alta", "Media", "Baja");

        tablaTareas.setItems(listaTareas);

        tablaTareas.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                txtId.setText(String.valueOf(newSel.getId()));
                txtTitulo.setText(newSel.getTitulo());
                txtDescripcion.setText(newSel.getDescripcion());
                cmbPrioridad.setValue(newSel.getPrioridad());
                dateFechaLimite.setValue(newSel.getFechaLimite());
            }
        });
    }

    @FXML
    private void limpiarCampos() {
        txtId.clear();
        txtTitulo.clear();
        txtDescripcion.clear();
        cmbPrioridad.setValue(null);
        dateFechaLimite.setValue(null);
        tablaTareas.getSelectionModel().clearSelection();
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    @FXML
    private void agregarTarea() {
        try {
            int id = Integer.parseInt(txtId.getText());
            String titulo = txtTitulo.getText();
            String descripcion = txtDescripcion.getText();
            String prioridad = cmbPrioridad.getValue();
            LocalDate fecha = dateFechaLimite.getValue();

            listaTareas.add(new Crud(id, titulo, descripcion, prioridad, fecha));
            limpiarCampos();
        } catch (Exception e) {
            mostrarAlerta("Error", "Datos inválidos", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void editarTarea() {
        Crud seleccionada = tablaTareas.getSelectionModel().getSelectedItem();
        if (seleccionada != null) {
            seleccionada.setId(Integer.parseInt(txtId.getText()));
            seleccionada.setTitulo(txtTitulo.getText());
            seleccionada.setDescripcion(txtDescripcion.getText());
            seleccionada.setPrioridad(cmbPrioridad.getValue());
            seleccionada.setFechaLimite(dateFechaLimite.getValue());
            tablaTareas.refresh();
            limpiarCampos();
        } else {
            mostrarAlerta("Advertencia", "Seleccione una tarea para editar", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void eliminarTarea() {
        Crud seleccionada = tablaTareas.getSelectionModel().getSelectedItem();
        if (seleccionada != null) {
            listaTareas.remove(seleccionada);
            limpiarCampos();
        } else {
            mostrarAlerta("Advertencia", "Seleccione una tarea para eliminar", Alert.AlertType.WARNING);
        }
    }

}