/**
 * Class: TrafficMonitoringApplication.java
 *
 * @author Daniel Geraghty
 *
 * Developed: August 2019
 * 
 * Version: 1.0
 *
 * Purpose: Main class to setup the program to determine if application is to run
 * as the server or client and identify network settings
 *
 * Assessment 2 - ICTPRG523
 */
package trafficmonitoringapplication;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import trafficmonitoringapplication.Resources.GUILibrary;
import javafx.application.Application;
import static javafx.collections.FXCollections.observableArrayList;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class TrafficMonitoringApplication extends Application
{
//<editor-fold defaultstate="collapsed" desc="GUI Global Variables">

    Stage appStage;
    BorderPane root = new BorderPane();
    Scene configScene, serverScene, clientScene;
    Button btnExit, btnBack, btnStartServer, btnStartClient, btnClient, btnServer, btnHelp;
    TextField txtServer, txtPort, txtStationNo;
    ComboBox cmbServer;
    Label lblServer, lblPort, lblTitle, lblStationNo, lblHeading;
    HBox boxTitle, boxButtons, boxSetup;
    VBox boxServerConfig, boxClientConfig, boxAppConfig;
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Global Variables">
    String hostServer, stationNo;
    int portNo;
    private ObservableList<String> ipAddress = observableArrayList();
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Run Methods">
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage)
    {
        try
        {
            hostServer = getIPAddress();
        }
        catch (UnknownHostException ex)
        {
            System.out.println(ex);
        }
        catch (Exception ex)
        {
            Logger.getLogger(TrafficMonitoringApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
        ipAddress.add(hostServer);
        createScene(primaryStage);
        appStage.show();
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Create Scene Methods">
    private void createScene(Stage primaryStage)
    {
        appStage = primaryStage;
        configScene = new Scene(root, 400, 400);
        addTitleContainer();
        createAppConfigScene();
        appStage.setTitle("Traffic Monitoring System");
        appStage.setResizable(false);
        appStage.setOnCloseRequest(e -> System.exit(0));
        appStage.setScene(configScene);
    }

    private void createAppConfigScene()
    {
        root.setCenter(null);
        addAppConfigContainer();
        addAppClickEvents();
    }

    private void createServerScene()
    {
        root.setCenter(null);
        addServerContainer();
        addAppClickEvents();
    }

    private void createClientScene()
    {
        root.setCenter(null);
        addClientContainer();
        addAppClickEvents();
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Main Scene Setup">
    /**
     * Method to add components to the title area of the GUI
     */
    private void addTitleContainer()
    {
        boxTitle = GUILibrary.addAHBox(root, "Top", 20, 400, 50);
        boxTitle.setAlignment(Pos.CENTER);
        lblTitle = GUILibrary.addALabel(boxTitle, "Traffic Monitoring System\nApplication Setup");
        lblTitle.setAlignment(Pos.CENTER);
        lblTitle.getStyleClass().add("label-heading");
    }

    /**
     * Method to add components for the main screen
     */
    private void addAppConfigContainer()
    {
        configScene.getStylesheets().clear();
        configScene.getStylesheets().add(TrafficMonitoringApplication.class.getResource("Resources/ServerInterfaceCSS.css").toExternalForm());
        lblTitle.setText("Traffic Monitoring System\nApplication Setup");
        boxAppConfig = GUILibrary.addAVBox(root, "Center", 20, 400, 150);
        boxAppConfig.setAlignment(Pos.CENTER);
        lblHeading = GUILibrary.addALabel(boxAppConfig, "Select which application to setup");
        boxSetup = GUILibrary.createAHBox(20, 400, 100);
        boxSetup.setAlignment(Pos.CENTER);
        btnServer = GUILibrary.addAButton(boxSetup, "Server Setup", 100, 50);
        btnClient = GUILibrary.addAButton(boxSetup, "Client Setup", 100, 50);
        boxAppConfig.getChildren().add(boxSetup);
        boxButtons = GUILibrary.addAHBox(root, "Bottom", 20, 400, 100);
        boxButtons.setAlignment(Pos.CENTER);
        btnExit = GUILibrary.addAButton(boxButtons, "Exit", 100, 50);
        btnHelp = GUILibrary.addAButton(boxButtons, "Help", 100, 50);
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Server Scene Setup">
    /**
     * Method to add components for the Server Setup screen
     */
    private void addServerContainer()
    {
        configScene.getStylesheets().clear();
        configScene.getStylesheets().add(TrafficMonitoringApplication.class.getResource("Resources/ServerInterfaceCSS.css").toExternalForm());
        lblTitle.setText("Traffic Monitoring System\nServer Setup");
        boxServerConfig = GUILibrary.addAVBox(root, "Center", 20, 400, 150);
        boxServerConfig.setAlignment(Pos.CENTER);
        lblServer = GUILibrary.addALabel(boxServerConfig, "Enter Server Address:");
        cmbServer = GUILibrary.addAComboBox(boxServerConfig, ipAddress, 200, true);
        lblPort = GUILibrary.addALabel(boxServerConfig, "Enter Port Number:");
        txtPort = GUILibrary.addATextField(boxServerConfig, 200, true);
        txtPort.setText("1234");
        boxButtons = GUILibrary.addAHBox(root, "Bottom", 20, 400, 100);
        boxButtons.setAlignment(Pos.CENTER);
        btnStartServer = GUILibrary.addAButton(boxButtons, "Start Server", 100, 50);
        btnBack = GUILibrary.addAButton(boxButtons, "Back", 100, 50);
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Client Scene Setup">
    /**
     * Method to add components for the Client Setup screen
     */
    private void addClientContainer()
    {
        configScene.getStylesheets().clear();
        configScene.getStylesheets().add(TrafficMonitoringApplication.class.getResource("Resources/ClientInterfaceCSS.css").toExternalForm());
        lblTitle.setText("Traffic Monitoring System\nClient Setup");
        boxClientConfig = GUILibrary.addAVBox(root, "Center", 20, 400, 150);
        boxClientConfig.setAlignment(Pos.CENTER);
        lblServer = GUILibrary.addALabel(boxClientConfig, "Select Server Address:");
        cmbServer = GUILibrary.addAComboBox(boxClientConfig, ipAddress, 200, true);
        lblPort = GUILibrary.addALabel(boxClientConfig, "Enter Port Number:");
        txtPort = GUILibrary.addATextField(boxClientConfig, 200, true);
        txtPort.setText("1234");
        lblStationNo = GUILibrary.addALabel(boxClientConfig, "Enter Station Number:");
        txtStationNo = GUILibrary.addATextField(boxClientConfig, 200, true);
        boxButtons = GUILibrary.addAHBox(root, "Bottom", 20, 400, 100);
        boxButtons.setAlignment(Pos.CENTER);
        btnStartClient = GUILibrary.addAButton(boxButtons, "Start Client", 100, 50);
        btnBack = GUILibrary.addAButton(boxButtons, "Back", 100, 50);
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Click Events">
    /**
     * Method to add events to the buttons
     */
    private void addAppClickEvents()
    {
        if (btnExit != null)
        {
            btnExit.setOnAction((ActionEvent e) ->
            {
                System.exit(0);
            });
        }

        if (btnBack != null)
        {
            btnBack.setOnAction((ActionEvent e) ->
            {
                createAppConfigScene();
            });
        }

        if (btnServer != null)
        {
            btnServer.setOnAction((ActionEvent e) ->
            {
                createServerScene();
            });
        }

        if (btnClient != null)
        {
            btnClient.setOnAction((ActionEvent e) ->
            {
                createClientScene();
            });
        }

        if (btnStartServer != null)
        {
            btnStartServer.setOnAction((ActionEvent e) ->
            {
                hostServer = cmbServer.getValue().toString();
                portNo = Integer.parseInt(txtPort.getText());
                ServerGUI server = new ServerGUI(hostServer, portNo);
                appStage.close();

                System.out.println("Server: " + hostServer + " Port No: " + portNo);
            });
        }

        if (btnStartClient != null)
        {
            btnStartClient.setOnAction((ActionEvent e) ->
            {
                hostServer = cmbServer.getValue().toString();
                portNo = Integer.parseInt(txtPort.getText());
                stationNo = txtStationNo.getText();
                ClientGUI client = new ClientGUI(hostServer, portNo, stationNo);
                appStage.close();

                System.out.println("Server: " + hostServer + " Port No: " + portNo + " Station No: " + stationNo);
            });
        }

        if (btnHelp != null)
        {
            btnHelp.setOnAction((ActionEvent e) ->
            {
                WebView webView = new WebView();
                webView.getEngine().load(getClass().getResource("/UserManual/UserGuide.html").toExternalForm());
                Stage helpStage = new Stage();
                helpStage.setTitle("Room Mapping User Guide");
                VBox vBox = new VBox(webView);
                Scene scene = new Scene(vBox, 960, 600);
                helpStage.setScene(scene);
                helpStage.show();
            });

        }

    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Helper Methods">
    /**
     * Purpose: Get the IPAddress of the system the application is running on
     *
     * @return String value of host IP address
     */
    private String getIPAddress() throws UnknownHostException
    {
        InetAddress address = InetAddress.getLocalHost();
        String hostIP = address.getHostAddress();
        return hostIP;
    }
//</editor-fold>
}
