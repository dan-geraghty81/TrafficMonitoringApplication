/**
 * Class: ServerGUI.java
 *
 * @author Daniel Geraghty
 *
 * Developed: August 2019
 * 
 * Version: 1.0
 *
 * Purpose: GUI Setup for the server to display incoming information from
 * Monitoring Station Clients
 *
 * Assessment 2 - ICTPRG523
 */
package trafficmonitoringapplication;

//<editor-fold defaultstate="collapsed" desc="Imports">
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import trafficmonitoringapplication.Network.Server;
import trafficmonitoringapplication.Resources.GUILibrary;
import trafficmonitoringapplication.DLL.DoublyLinkedList;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import trafficmonitoringapplication.BTree.BinaryTree;
import trafficmonitoringapplication.BTree.DisplayBinaryTree;
//</editor-fold>

public class ServerGUI extends Application
{

//<editor-fold defaultstate="collapsed" desc="Global Variables">
    private static final long serialVersionUID = 1L;
    private String serverIP;
    private int portNo;
    private int dataID;

    ArrayList<String> sortList;
    LinkedHashMap<String, Integer> binaryOutput;
    DoublyLinkedList dll;
    BinaryTree bTree;

    private Stage officeStage = new Stage();
    private Scene officeScene;
    private BorderPane officePane = new BorderPane();
    private AnchorPane dataPane = new AnchorPane();
    private AnchorPane infoPane = new AnchorPane();
    private AnchorPane linkedPane = new AnchorPane();
    private AnchorPane binaryPane = new AnchorPane();
    private AnchorPane binaryButtons = new AnchorPane();
    private TabPane infoTabPane = new TabPane();
    private HBox boxDetails, boxTitle, boxExit, boxBinaryButtons;
    private VBox boxCenterData;

    private Label lblTitle, lblSort, lblLinkedList, lblBinaryTree, lblPreOrder, lblInOrder, lblPostOrder, lblSearch;
    private TextField txtSearch;
    private TextArea txtInformation, txtLinkedList, txtBinaryTree, txtConnected;
    private ScrollPane scrInformation, scrLinked, scrBinary, scrConnected;
    private TableView<MonitoringData> tblData = new TableView<MonitoringData>();
    private Button btnExit, btnCheckStatus, btnLocation, btnVehicle, btnVelocity, btnDisplay;
    private Button btnPreOrder, btnInOrder, btnPostOrder, btnPreSave, btnInSave, btnPostSave, btnSearch;

    ServerSocket serverSocket;
    Server server;
    Notifications notification = null;
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Constructor">
    /**
     * Main constructor for ServerGUI class
     *
     * @param serverIP Server IP Address
     * @param portNo Server Port Number
     */
    public ServerGUI(String serverIP, int portNo)
    {
        this.serverIP = serverIP;
        this.portNo = portNo;
        createScreen();
        server = new Server(serverIP, portNo, this);
        new ServerRunning().start();
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="GUI Setup">
    /**
     * Method that calls relevant methods to construct the GUI
     * 
     */
    private void createScreen()
    {
        System.out.println(serverIP);
        System.out.println(portNo);

        officeScene = new Scene(officePane, 780, 860);
        addTitleContainer();
        addDataContainer();
        addClickEvents();
        addData();
        officeStage.setTitle("Monitoring Office");
        officeScene.getStylesheets().add(getClass().getResource("Resources/ServerInterfaceCSS.css").toExternalForm());
        officeStage.setResizable(false);
        officeStage.setScene(officeScene);
        officeStage.setOnCloseRequest(e ->
        {
            exitServer();
        });
        officeStage.show();
    }

    /**
     * Method that adds testing data to the table
     *
     */
    private void addData()
    {
        int totVehicle = 450;
        int avgVehicle = 0;
        int time = 30;
        Random random = new Random();

        for (int i = 0; i < 10; i++)
        {

            totVehicle = random.nextInt(50) * 3;
            avgVehicle = totVehicle / 4;
            MonitoringData md = new MonitoringData(dataID, "" + time, "2", "4", "" + totVehicle, "" + avgVehicle, "" + random.nextInt(50));
            dataID++;
            time--;
            tblData.getItems().add(md);
        }
        time = 15;
        totVehicle = 150;
        for (int j = 0; j < 10; j++)
        {
            totVehicle = random.nextInt(50) * 3;
            avgVehicle = totVehicle / 8;
            MonitoringData md = new MonitoringData(dataID, "" + time, "1", "8", "" + totVehicle, "" + avgVehicle, "" + random.nextInt(50));
            dataID++;
            time--;
            tblData.getItems().add(md);
        }
    }

    /**
     * Method that creates the title area of the GUI
     *
     */
    private void addTitleContainer()
    {
        boxTitle = GUILibrary.addAHBox(officePane, "Top", 20, 770, 50);
        boxTitle.setAlignment(Pos.CENTER);
        lblTitle = GUILibrary.addALabel(boxTitle, "Monitoring Office");
        lblTitle.setPrefSize(750, 40);
        lblTitle.getStyleClass().add("officeHeading");
    }

    /**
     * Method that creates the data area of the GUI
     *
     */
    private void addDataContainer()
    {
        boxExit = GUILibrary.createAHBox(20, 770, 100);
        boxExit.setAlignment(Pos.CENTER);
        btnExit = GUILibrary.addAButton(boxExit, "Exit", 150, 50);

        officePane.setBottom(boxExit);

        boxCenterData = GUILibrary.createAVBox(10, 770, 400);
        //Create HBox for data
        boxDetails = GUILibrary.createAHBox(20, 770, 400);
        boxDetails.setAlignment(Pos.CENTER);

        //Add Data Pane
        createTable();
        dataPane.setPrefSize(450, 400);
        dataPane.getChildren().add(tblData);
        lblSort = GUILibrary.addALabel(dataPane, "Sort", 10, 350);
        lblSort.setPrefSize(430, 40);
        lblSort.getStyleClass().add("smallLabel-left");
        btnLocation = GUILibrary.addAButton(dataPane, "Location", 130, 350, 100, 40);
        btnVehicle = GUILibrary.addAButton(dataPane, "Vehicle#", 240, 350, 100, 40);
        btnVelocity = GUILibrary.addAButton(dataPane, "Velocity", 350, 350, 100, 40);

        btnLocation.getStyleClass().add("smallButton");
        btnVehicle.getStyleClass().add("smallButton");
        btnVelocity.getStyleClass().add("smallButton");
        //Add Communications Pane
        infoPane.setPrefSize(320, 400);
        txtInformation = GUILibrary.addATextArea(infoPane, 10, 10, 280, 325);
        scrInformation = GUILibrary.addAScrollPane(infoPane, txtInformation, 10, 10);
        btnCheckStatus = GUILibrary.addAButton(infoPane, "Check Status", 190, 350, 100, 40);
        btnCheckStatus.getStyleClass().add("smallButton");

        boxDetails.getChildren().addAll(dataPane, infoPane);

        //Add Linked List Section
        lblLinkedList = GUILibrary.addALabel(linkedPane, "Linked List", 10, 10);
        lblLinkedList.setPrefSize(750, 40);
        lblLinkedList.getStyleClass().add("smallLabel-left");
        txtLinkedList = GUILibrary.addATextArea(linkedPane, 10, 50, 750, 60);
        txtLinkedList.setWrapText(true);
        scrLinked = GUILibrary.addAScrollPane(linkedPane, txtLinkedList, 10, 50);

        //Add Binary Tree Section
        lblBinaryTree = GUILibrary.addALabel(binaryPane, "Binary Tree", 10, 10);
        lblBinaryTree.setPrefSize(750, 40);
        lblBinaryTree.getStyleClass().add("smallLabel-left");

        lblSearch = GUILibrary.addALabel(binaryPane, "Search", 400, 10);
        lblSearch.setPrefSize(50, 40);
        lblSearch.getStyleClass().add("smallLabel-left");

        txtSearch = GUILibrary.addATextField(binaryPane, 450, 15, 85, 40, true);

        btnSearch = GUILibrary.addAButton(binaryPane, "Search", 550, 10, 100, 40);
        btnSearch.getStyleClass().add("smallButton");
        btnDisplay = GUILibrary.addAButton(binaryPane, "Display", 660, 10, 100, 40);
        btnDisplay.getStyleClass().add("smallButton");

        txtBinaryTree = GUILibrary.addATextArea(binaryPane, 10, 50, 750, 60);
        scrBinary = GUILibrary.addAScrollPane(binaryPane, txtBinaryTree, 10, 50);

        lblPreOrder = GUILibrary.addALabel(binaryButtons, "Pre-Order", 10, 10);
        lblPreOrder.setPrefSize(205, 15);
        lblPreOrder.getStyleClass().add("smallLabel-center");
        btnPreOrder = GUILibrary.addAButton(binaryButtons, "Display", 10, 30, 100, 40);
        btnPreSave = GUILibrary.addAButton(binaryButtons, "Save", 115, 30, 100, 40);
        btnPreOrder.getStyleClass().add("smallButton");
        btnPreSave.getStyleClass().add("smallButton");

        lblInOrder = GUILibrary.addALabel(binaryButtons, "In-Order", 280, 10);
        lblInOrder.setPrefSize(205, 15);
        lblInOrder.getStyleClass().add("smallLabel-center");
        btnInOrder = GUILibrary.addAButton(binaryButtons, "Display", 280, 30, 100, 40);
        btnInSave = GUILibrary.addAButton(binaryButtons, "Save", 385, 30, 100, 40);
        btnInOrder.getStyleClass().add("smallButton");
        btnInSave.getStyleClass().add("smallButton");

        lblPostOrder = GUILibrary.addALabel(binaryButtons, "Post-Order", 555, 10);
        lblPostOrder.setPrefSize(205, 15);
        lblPostOrder.getStyleClass().add("smallLabel-center");
        btnPostOrder = GUILibrary.addAButton(binaryButtons, "Display", 555, 30, 100, 40);
        btnPostSave = GUILibrary.addAButton(binaryButtons, "Save", 660, 30, 100, 40);
        btnPostOrder.getStyleClass().add("smallButton");
        btnPostSave.getStyleClass().add("smallButton");

        boxBinaryButtons = GUILibrary.createAHBox(10, 750, 50);
        boxBinaryButtons.getChildren().add(binaryButtons);
        boxCenterData.getChildren().addAll(boxDetails, linkedPane, binaryPane, boxBinaryButtons);
        officePane.setCenter(boxCenterData);
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
            exitServer();
        });

        btnCheckStatus.setOnAction((ActionEvent e) ->
        {
            txtInformation.clear();
            server.displayStationList();

        });

        btnLocation.setOnAction((ActionEvent e) ->
        {
            createSortList();
            sortList = quickSortByLocation(sortList, 2);
            sortList = quickSortByLocation(sortList, 1);
            for (int i = 0; i < sortList.size(); i++)
            {
                System.out.println(sortList.get(i));
            }
            createLinkedList(sortList);
            resetTableData(sortList);
        });

        btnVehicle.setOnAction((ActionEvent e) ->
        {
            createBinaryTree();
            createSortList();
            sortList = inserstionSortByVehicle(sortList);
            resetTableData(sortList);
            System.out.println("Vehicle Sort:");
            for (int i = 0; i < sortList.size(); i++)
            {
                System.out.println(sortList.get(i));
            }
            resetTableData(sortList);
        });

        btnVelocity.setOnAction((ActionEvent e) ->
        {
            createSortList();
            sortList = mergeSortVelocity(sortList);
            resetTableData(sortList);
            System.out.println("Velocity Sort:");
            for (int i = 0; i < sortList.size(); i++)
            {
                System.out.println(sortList.get(i));
            }
        });

        btnSearch.setOnAction((ActionEvent e) ->
        {
            String found = bTree.containsNode(Integer.parseInt(txtSearch.getText()));
            String output = (found.equals("true"))
                    ? txtSearch.getText() + " has been found."
                    : txtSearch.getText() + " has not been found.";
            txtBinaryTree.setText(output);
            txtSearch.setText("");
        });

        btnDisplay.setOnAction((ActionEvent e) ->
        {
            DisplayBinaryTree dTree = new DisplayBinaryTree(bTree);
        });
        
        btnPreOrder.setOnAction((ActionEvent e) ->
        {
            bTree.setMessage("Pre-Order: ");
            txtBinaryTree.setText(bTree.traversePreOrder(bTree.getRootNode()));
        });

        btnInOrder.setOnAction((ActionEvent e) ->
        {
            bTree.setMessage("In-Order: ");
            txtBinaryTree.setText(bTree.traverseInOrder(bTree.getRootNode()));
        });

        btnPostOrder.setOnAction((ActionEvent e) ->
        {
            bTree.setMessage("Post-Order: ");
            txtBinaryTree.setText(bTree.traversePostOrder(bTree.getRootNode()));
        });

        btnPreSave.setOnAction((ActionEvent e) ->
        {
            saveBinaryTree(1);
        });

        btnInSave.setOnAction((ActionEvent e) ->
        {
            saveBinaryTree(2);
        });

        btnPostSave.setOnAction((ActionEvent e) ->
        {
            saveBinaryTree(3);
        });
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Sort Methods">
    /**
     * Method that creates an ArrayList from the table data
     *
     */
    private void createSortList()
    {
        sortList = new ArrayList();
        for (int i = 0; i < tblData.getItems().size(); i++)
        {
            sortList.add(tblData.getItems().get(i).getObject());
        }
    }

    /**
     * Method that uses an ArrayList and sorts the data using a quick sort.
     * 
     * @param list ArrayList of data
     * @param index Index of data to be sorted by
     * @return 
     */
    private ArrayList<String> quickSortByLocation(ArrayList<String> list, int index)
    {
        if (list.size() <= 1)
        {
            return list;
        }
        ArrayList<String> lesser = new ArrayList<>();
        ArrayList<String> greater = new ArrayList<>();
        String pivot = list.get(list.size() - 1);
        for (int i = 0; i < list.size() - 1; i++)
        {
            String[] temp1 = list.get(i).split(", ");
            String[] temp2 = pivot.split(", ");
            if (Integer.parseInt(temp1[index]) < Integer.parseInt(temp2[index]))
            {
                lesser.add(list.get(i));
            }
            else
            {
                greater.add(list.get(i));
            }
        }

        lesser = quickSortByLocation(lesser, index);
        greater = quickSortByLocation(greater, index);

        lesser.add(pivot);
        lesser.addAll(greater);

        return lesser;
    }

    /**
     * Method that uses an ArrayList and sorts the data using a insertion sort.
     * 
     * @param list ArrayList of data
     * @return 
     */
    private ArrayList<String> inserstionSortByVehicle(ArrayList<String> list)
    {
        if (list.size() <= 1)
        {
            return list;
        }
        String temp;
        for (int i = 0; i < list.size(); i++)
        {
            for (int j = i; j > 0; j--)
            {
                String[] temp1 = list.get(j).split(", ");
                String[] temp2 = list.get(j - 1).split(", ");
                if (Integer.parseInt(temp1[4]) < Integer.parseInt(temp2[4]))
                {
                    temp = list.get(j);
                    list.set(j, list.get(j - 1));
                    list.set(j - 1, temp);
                }
            }
        }
        return list;
    }

    /**
     * Method that uses an ArrayList and sorts the data using a merge sort.
     * 
     * @param list ArrayList of data
     * @return 
     */
    private ArrayList<String> mergeSortVelocity(ArrayList<String> list)
    {
        //Split list
        ArrayList<String> left = new ArrayList();
        ArrayList<String> right = new ArrayList();
        int center = list.size() / 2;

        if (list.size() <= 1)
        {
            return list;
        }
        else
        {
            center = list.size() / 2;

            for (int i = 0; i < center; i++)
            {
                left.add(list.get(i));
            }

            for (int i = center; i < list.size(); i++)
            {
                right.add(list.get(i));
            }

            left = mergeSortVelocity(left);
            right = mergeSortVelocity(right);

            merge(left, right, list);
        }
        return list;
    }

    private void merge(ArrayList<String> left, ArrayList<String> right, ArrayList<String> list)
    {
        int leftIndex = 0;
        int rightIndex = 0;
        int listIndex = 0;

        while (leftIndex < left.size() && rightIndex < right.size())
        {
            String data1 = left.get(leftIndex);
            String[] temp1 = data1.split(", ");
            String data2 = right.get(rightIndex);
            String[] temp2 = data2.split(", ");

            if (Integer.parseInt(temp1[6]) < Integer.parseInt(temp2[6]))
            {
                list.set(listIndex, left.get(leftIndex));
                leftIndex++;
            }
            else
            {
                list.set(listIndex, right.get(rightIndex));
                rightIndex++;
            }
            listIndex++;
        }

        ArrayList<String> rest;
        int restIndex;
        if (leftIndex >= left.size())
        {
            rest = right;
            restIndex = rightIndex;
        }
        else
        {
            rest = left;
            restIndex = leftIndex;
        }

        for (int i = restIndex; i < rest.size(); i++)
        {
            list.set(listIndex, rest.get(i));
            listIndex++;
        }
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Doubly Linked Lists">
    /**
     * Method that creates a Doubly Linked List from the sorted ArrayList.
     * 
     * @param list ArrayList of data
     */
    private void createLinkedList(ArrayList<String> list)
    {
        dll = new DoublyLinkedList();
        for (int i = 0; i < list.size(); i++)
        {
            if (dll.getSize() == 0)
            {
                dll.addAtStart(list.get(i));
            }
            else
            {
                dll.addAtEnd(list.get(i));
            }
        }
        txtLinkedList.setText(dll.printDLL() + "\n");
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Binary Tree">
    /**
     * Method that retrieves the data from the table and adds it to a Binary Tree.
     *
     */
    public void createBinaryTree()
    {
        String data;
        bTree = new BinaryTree();
        for (int row = 0; row < tblData.getItems().size(); row++)
        {
            data = tblData.getItems().get(row).getLocation() + " @ " + tblData.getItems().get(row).getTime();
            bTree.add(Integer.parseInt(tblData.getItems().get(row).getTotVehicles()), data);
            txtBinaryTree.setText("Vehicle numbers added to Binary Tree.\nSelect a button below to display data.");
        }
    }

    /**
     * Method that converts the output of the Binary Tree into a LinkedHashMap and
     * passes the data onto the writeFile method.
     * 
     * @param index Determines which button made the save call
     */
    private void saveBinaryTree(int index)
    {
        binaryOutput = new LinkedHashMap<>();
        String data = null, outputFileName = null;

        // Get BinaryTree data based on order selected.
        switch (index)
        {
            case 1:
                data = bTree.saveBinaryTree(bTree.getRootNode(), 1);
                outputFileName = "BT-PreOrder.txt";
                break;
            case 2:
                data = bTree.saveBinaryTree(bTree.getRootNode(), 2);
                outputFileName = "BT-InOrder.txt";
                break;
            case 3:
                data = bTree.saveBinaryTree(bTree.getRootNode(), 3);
                outputFileName = "BT-PostOrder.txt";
                break;
        }

        // Split the retreived BinaryTree data into key/value pairs and add
        // to LinkedHashMap.
        String[] output = data.split("\\|");
        for (int i = 0; i < output.length - 1; i = i + 2)
        {
            binaryOutput.put(output[i], Integer.parseInt(output[i + 1]));
        }
        writeFile(outputFileName);
    }

//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Override Methods - Unused">
    @Override
    public void init() throws Exception
    {
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
    }

    @Override
    public void stop() throws Exception
    {
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Messages">
    /**
     * Method to append data to the Information panel
     * @param msg String message to be displayed
     */
    public void displayMessage(String msg)
    {
        txtInformation.appendText(msg + "\n");
    }

    /**
     * Method to append data to the Information panel about a connected station
     * @param station Station number
     */
    private void loginNotification(String station)
    {
        String msg = ("Station " + station + " has connected");
        displayMessage(msg);
    }

    /**
     * Method to append data to the Information panel about a disconnected station
     * @param station Station number
     */
    private void logoutNotification(String station)
    {
        String msg = ("Station " + station + " has disconnected");
        displayMessage(msg);
    }

    /**
     * Method to display a pop up notification using the ControlsFX java library
     * depending on the id and message provided
     *
     * @param id Notification id
     * @param msg Message to be displayed
     */
    public void displayNotifications(int id, String msg)
    {
        Thread thread = null;
        switch (id)
        {
            case 1:
                thread = new Thread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        notification = Notifications.create()
                                .title("Server Started")
                                .text("Server started on IP: " + msg)
                                .position(Pos.TOP_RIGHT)
                                .darkStyle()
                                .hideAfter(Duration.seconds(5));
                    }
                });

                break;
            case 2:
                thread = new Thread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        notification = Notifications.create()
                                .title("Station Connected")
                                .text("Station " + msg + " has just connected.")
                                .position(Pos.TOP_RIGHT)
                                .darkStyle()
                                .hideAfter(Duration.seconds(5));
                    }
                });
                break;
            case 3:
                thread = new Thread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        notification = Notifications.create()
                                .title("Station Disconnected")
                                .text("Station " + msg + " has just disconnected.")
                                .position(Pos.TOP_RIGHT)
                                .darkStyle()
                                .hideAfter(Duration.seconds(5));
                    }
                });
                break;
        }
        thread.run();
        Platform.runLater(new Runnable()
        {
            @Override
            public void run()
            {
                notification.showConfirm();
            }
        });

    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Table Manipulation">
    /**
     * Method to add and format columns to the table
     */
    private void createTable()
    {
        TableColumn dataIDColumn = new TableColumn("ID");
        dataIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn timeColumn = new TableColumn("Time");
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));

        TableColumn locationColumn = new TableColumn("Location");
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));

        TableColumn lanesColumn = new TableColumn("Lanes");
        lanesColumn.setCellValueFactory(new PropertyValueFactory<>("lanes"));

        TableColumn totVehicleColumn = new TableColumn("Total Vehicle #");
        totVehicleColumn.setCellValueFactory(new PropertyValueFactory<>("totVehicles"));

        TableColumn avgVehicleColumn = new TableColumn("Avg Vehicle #");
        avgVehicleColumn.setCellValueFactory(new PropertyValueFactory<>("avgVehicles"));

        TableColumn velocityColumn = new TableColumn("Avg Velocity");
        velocityColumn.setCellValueFactory(new PropertyValueFactory<>("velocity"));

        locationColumn.setResizable(false);
        timeColumn.setResizable(false);
        avgVehicleColumn.setResizable(false);
        velocityColumn.setResizable(false);
        timeColumn.setSortable(false);
        locationColumn.setSortable(false);
        avgVehicleColumn.setSortable(false);
        velocityColumn.setSortable(false);
        timeColumn.setPrefWidth(110);
        locationColumn.setPrefWidth(110);
        avgVehicleColumn.setPrefWidth(110);
        velocityColumn.setPrefWidth(110);
        tblData.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tblData.getColumns().addAll(timeColumn, locationColumn, avgVehicleColumn, velocityColumn);
        tblData.setPlaceholder(new Label("No Data Received From Stations."));
        tblData.setPrefSize(440, 325);
        tblData.setLayoutX(10);
        tblData.setLayoutY(10);
    }
    
    /**
     * Method to add data to the table
     * 
     * @param data Message data from the client
     */
    public void receiveTableData(String data)
    {
        dataID++;
        System.out.println(data);
        String[] message = data.split("\\|");
        MonitoringData md = new MonitoringData(dataID, message[0], message[1], message[2], message[3], message[4], message[5]);
        tblData.getItems().add(md);
    }

    /**
     * Method to reset the current collection of table data after sorting
     * @param list ArrayList of table data
     */
    private void resetTableData(ArrayList<String> list)
    {
        tblData.getItems().clear();

        for (int i = 0; i < list.size(); i++)
        {
            String data = (String) list.get(i);
            String[] message = data.split(", ");
            int id = Integer.parseInt(message[0]);
            MonitoringData md = new MonitoringData(id, message[1], message[2], message[3], message[4], message[5], message[6]);
            tblData.getItems().add(md);
        }
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="File Operations">
    /**
     * Method to write data to a file
     * 
     * @param filename Name of output file
     */
    public void writeFile(String filename)
    {
        try
        {
            BufferedWriter out = new BufferedWriter(new FileWriter(filename));

            // Create Iterator to traverse through the LinkedHashMap to write
            // data to output file
            Iterator it = binaryOutput.values().iterator();
            for (Object key : binaryOutput.keySet())
            {
                Object value = it.next();
                out.write("Station " + key + " " + value + " vehicles.");
                out.newLine();
            }
            out.close();
            notification = Notifications.create()
                    .title("File Saved")
                    .text("File: " + filename + " has been saved.")
                    .position(Pos.TOP_RIGHT)
                    .darkStyle()
                    .hideAfter(Duration.seconds(5));
            notification.showConfirm();
        }
        catch (IOException ex)
        {
            Logger.getLogger(ServerGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Helper Methods">
    /**
     * Method to stop server connections on exit
     */
    private void exitServer()
    {
        if (server != null)
        {
            try
            {
                server.stop();
            }
            catch (Exception eClose)
            {
            }
            server = null;
        }
        System.exit(0);
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Helper Classes">
    /**
     * Class for the server thread
     */
    class ServerRunning extends Thread
    {

        @Override
        public void run()
        {
            server.start();         // should execute until if fails
            displayMessage("Server crashed\n");
            server = null;
        }
    }
//</editor-fold>
}
