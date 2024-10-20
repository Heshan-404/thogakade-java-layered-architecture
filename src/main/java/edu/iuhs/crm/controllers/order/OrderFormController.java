package edu.iuhs.crm.controllers.order;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import edu.iuhs.crm.DTO.*;
import edu.iuhs.crm.service.ServiceFactory;
import edu.iuhs.crm.service.custom.CustomerService;
import edu.iuhs.crm.service.custom.ItemService;
import edu.iuhs.crm.utils.ServiceType;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;

import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class OrderFormController implements Initializable {

    private final ItemService itemService = ServiceFactory.getInstance().getService(ServiceType.ITEM);
    private final CustomerService customerService = ServiceFactory.getInstance().getService(ServiceType.CUSTOMER);

    public JFXComboBox cmbCustId;
    public JFXComboBox cmbItems;
    public TableView tblCart;
    private ObservableList<CartItem> cartItems = FXCollections.observableArrayList();
    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colItemCode;

    @FXML
    private TableColumn<?, ?> colItemQTY;

    @FXML
    private TableColumn<?, ?> colTotal;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private JFXTextField txtCustomerCity;

    @FXML
    private JFXTextField txtCustomerName;

    @FXML
    private JFXTextField txtCustomerSalary;

    @FXML
    private JFXTextField txtItemDesc;

    @FXML
    private JFXTextField txtItemPrice;

    @FXML
    private JFXTextField txtItemQTY;

    @FXML
    private JFXTextField txtItemQTYOnHand;

    @FXML
    private Label txtOrderDate;

    @FXML
    private Label txtOrderID;

    @FXML
    private Label txtOrderTime;


    @FXML
    void btnAddToCartOnAction(ActionEvent event) {

        if (cmbCustId.getValue() == null) {
            new Alert(Alert.AlertType.ERROR, "First Select a Customer").show();
        } else if (cmbItems.getValue() == null) {
            new Alert(Alert.AlertType.ERROR, "First Select an Item").show();
        } else {
            cartItems.add(new CartItem(String.valueOf(cmbItems.getValue()), txtItemDesc.getText(), Integer.parseInt(txtItemQTY.getText()), Double.parseDouble(txtItemPrice.getText()), Double.parseDouble(txtItemQTY.getText()) * Double.parseDouble(txtItemPrice.getText())));
            tblCart.setItems(cartItems);
        }
    }

    @FXML
    void btnClearCartOnAction(ActionEvent event) {
        clearAll();
    }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) {
        String orderID = txtOrderID.getText();
        String customerID = (String) cmbCustId.getValue();
        List<OrderDetail> orderDetailList = new ArrayList<>();

        for (CartItem cartItem : cartItems) {
            String itemCode = cartItem.getItemCode();
            Integer qty = cartItem.getQty();
            orderDetailList.add(new OrderDetail(orderID, itemCode, qty, 0.0));
        }

        try {

            if (new OrderController().placeOrder(new Order(orderID, LocalDate.now(), customerID, orderDetailList))) {
                new Alert(Alert.AlertType.INFORMATION, "Order Placed!!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Order Not Placed!!").show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colItemQTY.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));


        loadDateTime();
        loadCustomersID();
        loadItemsID();

        try {
            txtOrderID.setText(new OrderController().getNextOrderID());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        cmbItems.getSelectionModel().selectedItemProperty().addListener((observableValue, o, newValue) -> {
            Item item = itemService.searchItem((String) newValue);
            setItemValues(item);
        });

        cmbCustId.getSelectionModel().selectedItemProperty().addListener((observableValue, o, newValue) -> {
            setCustomerValues(customerService.searchCustomer((String) newValue));
        });
    }

    private void loadDateTime() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        txtOrderDate.setText(dateFormat.format(date));

        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime now = LocalTime.now();

            txtOrderTime.setText(String.format("%02d", now.getHour()) + ":" + String.format("%02d", now.getMinute()) + ":" + String.format("%02d", now.getSecond()));
        }),
                new KeyFrame(Duration.seconds(1))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void loadCustomersID() {
        cmbCustId.setItems(customerService.getAllCustomerIDs());
    }

    private void loadItemsID() {
        cmbItems.setItems(itemService.getAllItemIds());
    }

    private void setItemValues(Item item) {
        txtItemDesc.setText(item.getDescription());
        txtItemQTYOnHand.setText(String.valueOf(item.getQtyOnHand()));
        txtItemPrice.setText(String.valueOf(item.getUnitPrice()));
    }

    private void setCustomerValues(Customer customer) {
        txtCustomerName.setText(customer.getName());
        txtCustomerCity.setText(customer.getCity());
        txtCustomerSalary.setText(String.valueOf(customer.getSalary()));
    }

    private void clearAll() {
        loadDateTime();
        loadCustomersID();
        loadItemsID();
        txtItemQTY.setText("");
        txtCustomerName.setText("");
        txtCustomerSalary.setText("");
        txtCustomerCity.setText("");
        txtItemPrice.setText("");
        txtItemDesc.setText("");
        txtItemQTYOnHand.setText("");
        cartItems.clear();
    }
}
