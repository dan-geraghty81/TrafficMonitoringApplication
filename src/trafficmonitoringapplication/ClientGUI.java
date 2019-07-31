/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trafficmonitoringapplication;

import trafficmonitoringapplication.Network.MessageType;
import trafficmonitoringapplication.Network.Client;
import trafficmonitoringapplication.Resources.GUILibrary;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class ClientGUI extends Application
{

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

    public ClientGUI(String server, int port, String station)
    {
        this.serverIP = server;
        this.portNo = port;
        this.stationNo = station;
        createScreen();

        client = new Client(server, portNo, stationNo, this);
        if (!client.start())
        {
            clientStage.close();
        }
    }

    private void createScreen()
    {
        System.out.println(serverIP);
        System.out.println(portNo);
        System.out.println(stationNo);

        clientScene = new Scene(clientPane, 400, 450);
        addTitleContainer();
        addDataContainer();
        addButtonContainer();
        addClickEvents();
        clientStage.setTitle("Monitoring Station - " + stationNo);
        clientScene.getStylesheets().add(getClass().getResource("Resources/ClientInterfaceCSS.css").toExternalForm());
        clientStage.setScene(clientScene);
        clientStage.show();
    }

    private void addTitleContainer()
    {
        boxTitle = GUILibrary.addAHBox(clientPane, "Top", 20, 400, 50);
        boxTitle.setAlignment(Pos.CENTER);
        lblTitle = GUILibrary.addALabel(boxTitle, "Monitoring Station " + stationNo);
        lblTitle.getStyleClass().add("label-client-heading");
    }

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

    private void addButtonContainer()
    {
        boxButtons = GUILibrary.addAHBox(clientPane, "Bottom", 20, 400, 100);
        boxButtons.setAlignment(Pos.CENTER);
        btnSubmit = GUILibrary.addAButton(boxButtons, "Submit", 150, 50);
        btnExit = GUILibrary.addAButton(boxButtons, "Exit", 150, 50);
    }

    private void addClickEvents()
    {
        btnExit.setOnAction((ActionEvent e) ->
        {
            client.sendMessage(new MessageType(MessageType.LOGOUT, stationNo));
            client.disconnect();
            System.exit(0);
        });

        btnSubmit.setOnAction((ActionEvent e) ->
        {
            if (txtTime.getText().equalsIgnoreCase("") || txtLanes.getText().equalsIgnoreCase("") ||
                    txtTotVehicles.getText().equalsIgnoreCase("") || txtVelocity.getText().equalsIgnoreCase(""))
            {
                return;
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
        });

        txtTotVehicles.focusedProperty().addListener(new ChangeListener<Boolean>()
        {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
            {
                if (!newPropertyValue)
                {
                    String average = calculateAvgVehicles();
                    txtAvgVehicles.setText(average);
                }
            }
        });
    }

    private String calculateAvgVehicles()
    {
        int average;
        try
        {
            int vehicles = Integer.parseInt(checkInteger(txtTotVehicles.getText()));
            int lanes = Integer.parseInt(checkInteger(txtLanes.getText()));
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

    @Override
    public void start(Stage primaryStage) throws Exception
    {
    }
}
