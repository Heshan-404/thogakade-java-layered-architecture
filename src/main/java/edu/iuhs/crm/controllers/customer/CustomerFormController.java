package edu.iuhs.crm.controllers.customer;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import edu.iuhs.crm.DTO.Customer;
import edu.iuhs.crm.service.ServiceFactory;
import edu.iuhs.crm.service.custom.CustomerService;
import edu.iuhs.crm.utils.ServiceType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CustomerFormController implements Initializable {

    @FXML
    private JFXComboBox<String> cmbTitle;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colCity;

    @FXML
    private TableColumn<?, ?> colDob;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colPostalCode;

    @FXML
    private TableColumn<?, ?> colProvince;

    @FXML
    private TableColumn<?, ?> colSalary;

    @FXML
    private TableColumn<?, ?> colTitle;

    @FXML
    private DatePicker dateDob;

    @FXML
    private TableView<Customer> tblCustomers;

    @FXML
    private JFXTextField txtAddress;

    @FXML
    private JFXTextField txtCity;

    @FXML
    private JFXTextField txtCustomerId;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtPostalCode;

    @FXML
    private JFXTextField txtProvince;

    @FXML
    private JFXTextField txtSalary;

    private CustomerService customerService;

    @FXML
    void btnAddOnAction(ActionEvent event) throws SQLException {
        Customer customer = new Customer(txtCustomerId.getText(), cmbTitle.getValue(), txtName.getText(), dateDob.getValue(), Double.parseDouble(txtSalary.getText()), txtAddress.getText(), txtCity.getText(), txtProvince.getText(), txtPostalCode.getText());

        if (customerService.addCustomer(customer)) {
            new Alert(Alert.AlertType.INFORMATION, "Customer Added :)").show();
            loadTable();
        } else {
            new Alert(Alert.AlertType.INFORMATION, "Customer Not Added!").show();
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        if (customerService.deleteCustomer(txtCustomerId.getText())) {
            new Alert(Alert.AlertType.INFORMATION, "Customer Deleted :)").show();
            loadTable();
        } else {
            new Alert(Alert.AlertType.INFORMATION, "Customer Not Deleted!").show();
        }
    }

    @FXML
    void btnReloadOnAction(ActionEvent event) {
        loadTable();
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {
        Customer customer = customerService.searchCustomer(txtCustomerId.getText());
        if (customer != null) {
            setValue(customer);
        } else {
            new Alert(Alert.AlertType.INFORMATION, "Customer Not Found").show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        if (customerService.updateCustomer(new Customer(
                txtCustomerId.getText(),
                cmbTitle.getValue(),
                txtName.getText(),
                dateDob.getValue(),
                Double.parseDouble(txtSalary.getText()),
                txtAddress.getText(),
                txtCity.getText(),
                txtProvince.getText(),
                txtPostalCode.getText()))) {
            new Alert(Alert.AlertType.INFORMATION, "Customer Updated :)").show();
            loadTable();
        } else {
            new Alert(Alert.AlertType.INFORMATION, "Customer Not Updated!").show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> customerTittleList = FXCollections.observableArrayList();
        customerTittleList.add("Mr");
        customerTittleList.add("Mrs");
        customerTittleList.add("Miss");
        customerTittleList.add("Ms");
        cmbTitle.setItems(customerTittleList);
        customerService = ServiceFactory.getInstance().getService(ServiceType.CUSTOMER);
        loadTable();


        tblCustomers.getSelectionModel().selectedItemProperty().addListener(((observableValue, customer, newValue) -> {
            if (newValue != null) {
                setValue(newValue);
            }
        }));

    }

    private void setValue(Customer customerValues) {
        txtCustomerId.setText(customerValues.getId());
        txtName.setText(customerValues.getName());
        txtAddress.setText(customerValues.getAddress());
        txtSalary.setText(customerValues.getSalary() + "");
        txtCity.setText(customerValues.getCity());
        txtPostalCode.setText(customerValues.getPostalCode());
        txtProvince.setText(customerValues.getProvince());
        dateDob.setValue(customerValues.getDob());
    }

    private void loadTable() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDob.setCellValueFactory(new PropertyValueFactory<>("dob"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colCity.setCellValueFactory(new PropertyValueFactory<>("city"));
        colPostalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        colProvince.setCellValueFactory(new PropertyValueFactory<>("province"));
        tblCustomers.setItems(customerService.getAllCustomers());
    }
}
