/**
 * Class: GUILibrary
 *
 * @author Daniel Geraghty
 *
 * Developed: May 2019
 *
 * Purpose: Java Class for instantiation and placement of GUI elements
 *
 * Assessment 2 - ICTPRG418
 */
package trafficmonitoringapplication.Resources;

import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.*;

public class GUILibrary
{

//<editor-fold defaultstate="collapsed" desc="VBox">
    public static VBox createAVBox(double padding, int dimX, int dimY)
    {
        VBox myVBox = new VBox(padding);
        myVBox.setPrefSize(dimX, dimY);
        return myVBox;
    }

    public static VBox addAVBox(BorderPane root, String location, double padding, int dimX, int dimY)
    {
        VBox myVBox = new VBox(padding);
        myVBox.setPrefSize(dimX, dimY);
        if (location.equals("Left"))
        {
            root.setLeft(myVBox);
        }
        if (location.equals("Right"))
        {
            root.setRight(myVBox);
        }
        if (location.equals("Top"))
        {
            root.setTop(myVBox);
        }
        if (location.equals("Bottom"))
        {
            root.setBottom(myVBox);
        }
        if (location.equals("Center"))
        {
            root.setCenter(myVBox);
        }
        return myVBox;
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="HBox">
    public static HBox createAHBox(double padding, int dimX, int dimY)
    {
        HBox myHBox = new HBox(padding);
        myHBox.setPrefSize(dimX, dimY);
        return myHBox;
    }

    public static HBox addAHBox(BorderPane root, String location, double padding, int dimX, int dimY)
    {
        HBox myHBox = new HBox(padding);
        myHBox.setPrefSize(dimX, dimY);
        if (location.equals("Left"))
        {
            root.setLeft(myHBox);
        }
        if (location.equals("Right"))
        {
            root.setRight(myHBox);
        }
        if (location.equals("Top"))
        {
            root.setTop(myHBox);
        }
        if (location.equals("Bottom"))
        {
            root.setBottom(myHBox);
        }
        if (location.equals("Center"))
        {
            root.setCenter(myHBox);
        }
        return myHBox;
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Buttons">
    public static Button addAButton(HBox root, String caption, int dimX, int dimY)
    {
        Button myButton = new Button(caption);
        root.getChildren().add(myButton);
        myButton.setPrefSize(dimX, dimY);
        return myButton;
    }

    public static Button addAButton(VBox root, String caption, int dimX, int dimY)
    {
        Button myButton = new Button(caption);
        root.getChildren().add(myButton);
        myButton.setPrefSize(dimX, dimY);
        return myButton;
    }

    public static Button addAButton(BorderPane root, String caption, int x, int y, int dimX, int dimY)
    {
        Button myButton = new Button(caption);
        root.getChildren().add(myButton);
        myButton.setMinWidth(dimX);
        myButton.setMinHeight(dimY);
        myButton.setLayoutX(x);
        myButton.setLayoutY(y);
        return myButton;
    }

    public static Button addAButton(AnchorPane root, String caption, int x, int y, int dimX, int dimY)
    {
        Button myButton = new Button(caption);
        root.getChildren().add(myButton);
        myButton.setMinWidth(dimX);
        myButton.setMinHeight(dimY);
        myButton.setLayoutX(x);
        myButton.setLayoutY(y);
        return myButton;
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Labels">
    public static Label addALabel(HBox root, String caption)
    {
        Label myLabel = new Label(caption);
        root.getChildren().add(myLabel);
        return myLabel;
    }

    public static Label addALabel(VBox root, String caption)
    {
        Label myLabel = new Label(caption);
        root.getChildren().add(myLabel);
        return myLabel;
    }

    public static Label addALabel(BorderPane root, String caption, int x, int y)
    {
        Label myLabel = new Label(caption);
        root.getChildren().add(myLabel);
        myLabel.setLayoutX(x);
        myLabel.setLayoutY(y);
        return myLabel;
    }

    public static Label addALabel(AnchorPane root, String caption, int x, int y)
    {
        Label myLabel = new Label(caption);
        root.getChildren().add(myLabel);
        myLabel.setLayoutX(x);
        myLabel.setLayoutY(y);
        return myLabel;
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="TextFields">
    public static TextField addATextField(HBox root, double width, boolean editable)
    {
        TextField myTextField = new TextField();
        myTextField.setMaxWidth(width);
        myTextField.setEditable(editable);
        root.getChildren().add(myTextField);
        return myTextField;
    }

    public static TextField addATextField(VBox root, double width, boolean editable)
    {
        TextField myTextField = new TextField();
        myTextField.setMaxWidth(width);
        myTextField.setEditable(editable);
        root.getChildren().add(myTextField);
        return myTextField;
    }

    public static TextField addATextField(AnchorPane root, int x, int y, double dimX, double dimY, boolean editable)
    {
        TextField myTextField = new TextField();
        myTextField.setMaxSize(dimX, dimY);
        myTextField.setEditable(editable);
        root.getChildren().add(myTextField);
        myTextField.setLayoutX(x);
        myTextField.setLayoutY(y);
        return myTextField;
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Other Components">
    public static RadioButton addARadioButton(VBox root, ToggleGroup group, String caption, double width, boolean disable)
    {
        RadioButton myRadioButton = new RadioButton(caption);
        root.getChildren().add(myRadioButton);
        myRadioButton.setDisable(disable);
        myRadioButton.setMaxWidth(width);
        myRadioButton.setToggleGroup(group);
        return myRadioButton;
    }

    public static TextArea addATextArea(AnchorPane root, int x, int y, int width, int height)
    {
        TextArea myTextArea = new TextArea();
        myTextArea.setPrefSize(width, height);
        myTextArea.setEditable(false);
        return myTextArea;
    }

    public static ComboBox addAComboBox(VBox root, ObservableList list, double width, boolean editable)
    {
        ComboBox myComboBox = new ComboBox(list);
        root.getChildren().add(myComboBox);
        myComboBox.setEditable(editable);
        myComboBox.setMaxWidth(width);
        return myComboBox;
    }

    public static DatePicker addADatePicker(VBox root, double width)
    {
        DatePicker myDatePicker = new DatePicker();
        myDatePicker.setMaxWidth(width);
        root.getChildren().add(myDatePicker);
        return myDatePicker;
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Menus">
    public static Menu addAMenu(String caption)
    {
        Menu myMenu = new Menu(caption);
        return myMenu;
    }

    public static MenuBar addAMenuBar(BorderPane root, Menu m1, Menu m2, Menu m3)
    {
        MenuBar myMenuBar = new MenuBar();
        myMenuBar.getMenus().addAll(m1, m2, m3);
        root.getChildren().add(myMenuBar);
        return myMenuBar;
    }

    public static MenuBar addAMenuBar(BorderPane root, Menu m1)
    {
        MenuBar myMenuBar = new MenuBar();
        myMenuBar.getMenus().addAll(m1);
        root.getChildren().add(myMenuBar);
        return myMenuBar;
    }

    public static MenuItem addAMenuItem(Menu myMenu, String caption)
    {
        MenuItem myMenuItem = new MenuItem();
        myMenuItem.setMnemonicParsing(true);
        //myMenuItem.setStyle("-fx-pref-width: 150; -fx-text-alignment: right; -fx-padding: 0 10px 0 0;");
        myMenuItem.setText(caption);
        myMenu.getItems().add(myMenuItem);
        return myMenuItem;
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="ScrollPane">
    public static ScrollPane addAScrollPane(AnchorPane root, TextArea textArea, int x, int y)
    {
        ScrollPane myScrollPane = new ScrollPane(textArea);
        root.getChildren().add(myScrollPane);
        myScrollPane.setLayoutX(x);
        myScrollPane.setLayoutY(y);
        myScrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
        myScrollPane.setVbarPolicy(ScrollBarPolicy.NEVER);
        return myScrollPane;
    }

    public static ScrollPane addAScrollPane(BorderPane pane, AnchorPane root)
    {
        ScrollPane myScrollPane = new ScrollPane(root);
        myScrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
        myScrollPane.setVbarPolicy(ScrollBarPolicy.NEVER);
        return myScrollPane;
    }
//</editor-fold>

    public static TabPane addATabPane(AnchorPane pane, Tab tabA, Tab tabB)
    {
        TabPane myTabPane = new TabPane();
        myTabPane.getTabs().addAll(tabA, tabB);
        pane.getChildren().add(myTabPane);
        return myTabPane;
    }
}
