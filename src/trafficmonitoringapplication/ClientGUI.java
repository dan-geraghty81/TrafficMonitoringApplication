/**
 * Class: ClientGUI.java
 *
 * @author Daniel Geraghty
 *
 * Developed: August 2019
 * 
 * Version: 1.0
 *
 * Purpose: Class to setup the GUI for a Road Monitoring Station
 *
 * Assessment 2 - ICTPRG523
 */
package trafficmonitoringapplication;

//<editor-fold defaultstate="collapsed" desc="Imports">
import trafficmonitoringapplication.Network.MessageType;
import trafficmonitoringapplication.Network.Client;
import trafficmonitoringapplication.Resources.GUILibrary;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
//</editor-fold>

public class ClientGUI extends Application
{
//<editor-fold defaultstate="collapsed" desc="Global Variables">

    private static final long serialVersionUID = 1L;
    private String serverIP, stationNo;
    private int portNo;

    private Stage clientStage = new Stage();
    private Scene clientScene;
    private BorderPane clientPane = new BorderPane();
    private AnchorPane clientData = new AnchorPane();
    private HBox boxButtons, boxTitle;

    Label lblTitle, lblHeading, lblTime, lblLocation, lblLanes, lblTotVehicles, lblAvgVehicles, lblVelocity;
    TextField txtTime, txtLocation, txtLanes, txtTotVehicles, txtAvgVehicles, txtVelocity;
    Button btnSubmit, btnExit, btnLogin;

    private Client client;
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Constructor">
    /**
     * Main constructor for ClientGUI class
     *
     * @param server Server IP address
     * @param port Server port number
     * @param station Monitoring Station Number
     */
    public ClientGUI(String server, int port, String station)
    {
        this.serverIP = server;
        this.portNo = port;
        this.stationNo = station;
        createScreen();

        client = new Client(serverIP, portNo, stationNo, this);
        if (!client.start())
        {
            showAlert(1);
            clientStage.close();
        }
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="GUI Setup">
    /**
     * Method that calls relevant methods to construct the GUI
     *
     */
    private void createScreen()
    {
        clientScene = new Scene(clientPane, 400, 450);
        addTitleContainer();
        addDataContainer();
        addButtonContainer();
        addClickEvents();
        clientStage.setTitle("Monitoring Station - " + stationNo);
        clientScene.getStylesheets().add(getClass().getResource("Resources/ClientInterfaceCSS.css").toExternalForm());
        clientStage.setResizable(false);
        clientStage.setScene(clientScene);
        clientStage.setOnCloseRequest(e ->
        {
            exitClient();
        });
        clientStage.show();
    }

    /**
     * Method that creates the title area of the GUI
     *
     */
    private void addTitleContainer()
    {
        boxTitle = GUILibrary.addAHBox(clientPane, "Top", 20, 400, 50);
        boxTitle.setAlignment(Pos.CENTER);
        lblTitle = GUILibrary.addALabel(boxTitle, "Monitoring Station " + stationNo);
        lblTitle.getStyleClass().add("label-client-heading");
    }

    /**
     * Method that creates the data area of the GUI
     *
     */
    private void addDataContainer()
    {
        lblHeading = GUILibrary.addALabel(clientData, "Enter your readings and click submit", 0, 20);
        lblHeading.setPrefWidth(400);
        lblTime = GUILibrary.addALabel(clientData, "Time:", 20, 65);
        lblLocation = GUILibrary.addALabel(clientData, "Location No:", 20, 105);
        lblLanes = GUILibrary.addALabel(clientData, "No of Lanes:", 20, 145);
        lblTotVehicles = GUILibrary.addALabel(clientData, "Total No of Vehicles:", 20, 185);
        lblAvgVehicles = GUILibrary.addALabel(clientData, "Average No of Vehicles:", 20, 225);
        lblVelocity = GUILibrary.addALabel(clientData, "Average Velocity:", 20, 265);

        txtTime = GUILibrary.addATextField(clientData, 200, 60, 200, 20, true);
        txtLocation = GUILibrary.addATextField(clientData, 200, 100, 200, 20, false);
        txtLocation.setText(stationNo);
        txtLanes = GUILibrary.addATextField(clientData, 200, 140, 200, 20, true);
        txtTotVehicles = GUILibrary.addATextField(clientData, 200, 180, 200, 20, true);
        txtAvgVehicles = GUILibrary.addATextField(clientData, 200, 220, 200, 20, false);
        txtVelocity = GUILibrary.addATextField(clientData, 200, 260, 200, 20, true);
        clientPane.setCenter(clientData);
    }

    /**
     * Method that creates the button area of the GUI
     *
     */
    private void addButtonContainer()
    {
        boxButtons = GUILibrary.addAHBox(clientPane, "Bottom", 20, 400, 100);
        boxButtons.setAlignment(Pos.CENTER);
        btnSubmit = GUILibrary.addAButton(boxButtons, "Submit", 150, 50);
        btnExit = GUILibrary.addAButton(boxButtons, "Exit", 150, 50);
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Click Events">
    /**
     * Method that adds the events for when the buttons are clicked
     *
     */
    private void addClickEvents()
    {
        btnExit.setOnAction((ActionEvent e) ->
        {
            exitClient();
        });

        btnSubmit.setOnAction((ActionEvent e) ->
        {
            submitData();
        });

        txtLanes.focusedProperty().addListener(new ChangeListener<Boolean>()
        {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
            {
                if (!newPropertyValue)
                {
                    String check = checkInteger(txtLanes.getText());
                    if (check.equals("0"))
                    {
                        showAlert(3);
                        txtLanes.setText("");
                    }
                }
            }
        });

        txtTotVehicles.focusedProperty().addListener(new ChangeListener<Boolean>()
        {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
            {
                if (!newPropertyValue)
                {
                    String check = checkInteger(txtTotVehicles.getText());
                    if (check.equals("0"))
                    {
                        showAlert(3);
                        txtTotVehicles.setText("");
                    }
                    else
                    {
                        String average = calculateAvgVehicles();
                        txtAvgVehicles.setText(average);
                    }
                }
            }
        });
        
        txtVelocity.focusedProperty().addListener(new ChangeListener<Boolean>()
        {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
            {
                if (!newPropertyValue)
                {
                    String check = checkInteger(txtVelocity.getText());
                    if (check.equals("0"))
                    {
                        showAlert(3);
                        txtVelocity.setText("");
                    }
                }
            }
        });
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Helper Methods">
    /**
     * Method to collate the user input into a MessageType format and sends the
     * data through the Client class to the server.
     */
    private void submitData()
    {
        if (txtTime.getText().equalsIgnoreCase("") || txtLanes.getText().equalsIgnoreCase("")
                || txtTotVehicles.getText().equalsIgnoreCase("") || txtVelocity.getText().equalsIgnoreCase(""))
        {
            showAlert(2);
        }
        else
        {
            String msg = (txtTime.getText()
                    + "|" + txtLocation.getText()
                    + "|" + txtLanes.getText()
                    + "|" + txtTotVehicles.getText()
                    + "|" + txtAvgVehicles.getText()
                    + "|" + txtVelocity.getText());
            try
            {
                client.sendMessage(new MessageType(MessageType.DATA, msg));
            }
            catch (Exception ex)
            {
                System.out.println("Message sending failed");
            }
            txtTime.clear();
            txtLanes.clear();
            txtTotVehicles.clear();
            txtAvgVehicles.clear();
            txtVelocity.clear();
        }
    }

    /**
     * Method to calculate the Average no of vehicles depending on the input
     * from the user
     *
     * @return will return either the calculated average or null if there is an
     * error
     */
    private String calculateAvgVehicles()
    {
        int average;
        try
        {
            int vehicles = Integer.parseInt((txtTotVehicles.getText()));
            int lanes = Integer.parseInt((txtLanes.getText()));
            if (vehicles != 0 && lanes != 0)
            {
                average = vehicles / lanes;
            }
            else
            {
                return "null";
            }

        }
        catch (Exception e)
        {
            return "null";
        }
        return Integer.toString(average);
    }

    /**
     * Checks user input to see if it is an integer value
     *
     * @param strValue string value to check
     * @return
     */
    private String checkInteger(String strValue)
    {
        try
        {
            Integer.parseInt(strValue);
            return strValue;
        }
        catch (Exception e)
        {
            return "0";
        }
    }

    /**
     * Method to handle different Alert message boxes depending on where it is
     * called from
     *
     * @param index index of message to be displayed
     */
    private void showAlert(int index)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        switch (index)
        {
            case 1:
                alert.setTitle("Server Not Found");
                alert.setHeaderText("The server address: " + serverIP + " does not exist");
                alert.setContentText("Please check the address and try again.");
                break;
            case 2:
                alert.setTitle("Invalid Inputs");
                alert.setHeaderText("All fields require input");
                alert.setContentText("Please check the values and try again.");
                break;
            case 3:
                alert.setTitle("Invalid Inputs");
                alert.setHeaderText("Please enter a valid number");
                alert.setContentText("Please check the values and try again.");
                break;
        }
        alert.showAndWait();
    }

    /**
     * Method that sends the logout message and disconnects the client
     * from the server.
     *
     */
    private void exitClient()
    {
        client.sendMessage(new MessageType(MessageType.LOGOUT, stationNo));
        client.disconnect();
        System.exit(0);
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
    }
//</editor-fold>
}
