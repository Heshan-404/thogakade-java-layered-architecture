package edu.iuhs.crm.controllers.item;

import com.jfoenix.controls.JFXTextField;
import edu.iuhs.crm.DTO.Item;
import edu.iuhs.crm.service.ServiceFactory;
import edu.iuhs.crm.service.custom.ItemService;
import edu.iuhs.crm.service.custom.impl.ItemSerivceImpl;
import edu.iuhs.crm.utils.ServiceType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;


public class ItemController implements Initializable {

    private ItemService itemService;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colItemCode;

    @FXML
    private TableColumn<?, ?> colPacSize;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private TableView<Item> tblItems;

    @FXML
    private JFXTextField txtDescription;

    @FXML
    private JFXTextField txtItemCode;

    @FXML
    private JFXTextField txtPackSize;

    @FXML
    private JFXTextField txtQty;

    @FXML
    private JFXTextField txtUnitPrice;

    @FXML
    void btnAddOnAction(ActionEvent event) {
        if (itemService.addItem(
                new Item(
                        txtItemCode.getText(),
                        txtDescription.getText(),
                        txtPackSize.getText(),
                        Double.parseDouble(txtUnitPrice.getText()),
                        Integer.parseInt(txtQty.getText())
                ))) {
            new Alert(Alert.AlertType.INFORMATION, "Item Added!!").show();
            loadTable();
        } else {
            new Alert(Alert.AlertType.ERROR, "Item Not Added!!").show();
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        if (itemService.deleteItem(txtItemCode.getText())) {
            new Alert(Alert.AlertType.INFORMATION, txtItemCode.getText() + " Item Deleted").show();
            loadTable();
        } else {
            new Alert(Alert.AlertType.ERROR, txtItemCode.getText() + " Item Not Deleted").show();
        }
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {
        Item item = itemService.searchItem(txtItemCode.getText());
        if (item != null) {
            setValueToText(item);
        } else {
            new Alert(Alert.AlertType.ERROR, txtItemCode.getText() + " Item Not Found").show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

        if (itemService.updateItem(new Item(
                txtItemCode.getText(),
                txtDescription.getText(),
                txtPackSize.getText(),
                Double.parseDouble(txtUnitPrice.getText()),
                Integer.parseInt(txtQty.getText())
        ))) {
            new Alert(Alert.AlertType.INFORMATION, "Item Updated!").show();
            loadTable();
        } else {
            new Alert(Alert.AlertType.INFORMATION, "Item not Updated!").show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colPacSize.setCellValueFactory(new PropertyValueFactory<>("packSize"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));

        itemService = ServiceFactory.getInstance().getService(ServiceType.ITEM);

        loadTable();

        tblItems.getSelectionModel().selectedItemProperty().addListener((observableValue, item, newValue) -> {
            if (newValue != null) {
                setValueToText(newValue);
            }
        });
    }

    private void loadTable() {
        tblItems.setItems(itemService.getAllItem());
    }

    private void setValueToText(Item newValue) {
        txtItemCode.setText(newValue.getItemCode());
        txtDescription.setText(newValue.getDescription());
        txtPackSize.setText(newValue.getPackSize());
        txtUnitPrice.setText(newValue.getUnitPrice().toString());
        txtQty.setText(newValue.getQtyOnHand().toString());
    }
}
