/**
 * Class: DisplayBinaryTree.java
 *
 * @author Daniel Geraghty
 *
 * Developed: August 2019
 *
 * Version 1.0
 *
 * Purpose: Class to display a 2D Graphical representation of the Binary Tree.
 *
 * Assessment 1 - ICTPRG523
 */
package trafficmonitoringapplication.BTree;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public final class DisplayBinaryTree extends Canvas
{
    private final BinaryTree bTree;
    private final double radius = 20; // Tree node radius
    private final double verticalGap = 50; // Gap between two levels in a tree

    public DisplayBinaryTree(BinaryTree tree)
    {
        this.bTree = tree;
        BTView view = new BTView(bTree);
        Stage stage = new Stage();
        Scene scene = new Scene(view, 900, 700);
        stage.setTitle("2D Binary Tree");
        stage.setScene(scene);
        view.displayTree();
        stage.show();
    }
    
    public class BTView extends Pane
    {
        BinaryTree bTree;
        
        public BTView(BinaryTree tree)
        {
            this.bTree = tree;
        }
        public void displayTree()
        {
            this.getChildren().clear(); // Clear the pane
            if (bTree.getRootNode() != null)
            {
                displayTree(bTree.getRootNode(), getWidth() / 2, verticalGap, getWidth() / 4);
            }
        }

        /**
         * Method to display a node at x & y position
         * @param root Current node
         * @param x Screen X position
         * @param y Screen Y position
         * @param horizontalGap Gap between nodes
         */
        private void displayTree(BTNode root, double x, double y, double horizontalGap)
        {
            if (root.left != null)
            {
                getChildren().add(new Line(x - horizontalGap, y + verticalGap, x, y));
                displayTree(root.left, x - horizontalGap, y + verticalGap, horizontalGap / 2);
            }

            if (root.right != null)
            {
                getChildren().add(new Line(x + horizontalGap, y + verticalGap, x, y));
                displayTree(root.right, x + horizontalGap, y + verticalGap, horizontalGap / 2);
            }
            Circle circle = new Circle(x, y, radius);
            circle.setFill(Color.WHITE);
            circle.setStroke(Color.BLACK);
            this.getChildren().addAll(circle, new Text(x - 4, y + 4, "" + root.value));
        }
    }
}
