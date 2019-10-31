/**
 * Class: BinaryTree.java
 *
 * @author Daniel Geraghty
 *
 * Developed: August 2019
 *
 * Version 1.0
 *
 * Purpose: Class to define the Binary Tree, perform additions to the tree,
 * traverse the tree and display the results and export the tree data to CSV files.
 *
 * Assessment 1 - ICTPRG523
 */
package trafficmonitoringapplication.BTree;

public class BinaryTree
{

    BTNode root;
    String msg = "";
    int size = 0;

//<editor-fold defaultstate="collapsed" desc="Add Node Methods">
    /**
     * Method to add a node to the binary tree
     *
     * @param current Current node
     * @param value Value of node
     * @param data Data contained in node
     * @return BTNode
     */
    private BTNode addRecursive(BTNode current, int value, String data)
    {
        if (current == null)
        {
            return new BTNode(value, data);
        }

        if (value < current.value)
        {
            current.left = addRecursive(current.left, value, data);
        }
        else
        {
            current.right = addRecursive(current.right, value, data);
        }

        return current;
    }

    public void add(int value, String data)
    {
        root = addRecursive(root, value, data);
        size++;
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Traversal Methods">
    /**
     * Method to traverse the binary tree InOrder
     *
     * @param node current node
     * @return String to be display
     */
    public String traverseInOrder(BTNode node)
    {
        if (node != null)
        {
            traverseInOrder(node.left);
            msg = msg + (" " + node.value);
            traverseInOrder(node.right);
        }
        return msg;
    }

    /**
     * Method to traverse the binary tree PreOrder
     *
     * @param node current node
     * @return String to be display
     */
    public String traversePreOrder(BTNode node)
    {
        if (node != null)
        {
            msg = msg + (" " + node.value);
            traversePreOrder(node.left);
            traversePreOrder(node.right);
        }
        return msg;
    }

    /**
     * Method to traverse the binary tree PostOrder
     *
     * @param node current node
     * @return String to be display
     */
    public String traversePostOrder(BTNode node)
    {
        if (node != null)
        {
            traversePostOrder(node.left);
            traversePostOrder(node.right);
            msg = msg + (" " + node.value);
        }
        return msg;
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Save Methods">
    /**
     * Method to determine the order in which the tree needs to be traversed in
     * order to save the correct file
     *
     * @param node current node
     * @param index index of which button called the method
     * @return String to be display
     */
    public String saveBinaryTree(BTNode node, int index)
    {
        setMessage("");
        switch (index)
        {
            case 1:
                msg = savePreOrder(root);
                break;
            case 2:
                msg = saveInOrder(root);
                break;
            case 3:
                msg = savePostOrder(root);
                break;
        }
        return msg;
    }

    /**
     * Method to traverse the tree and format the retrieved data for file output
     *
     * @param node current node
     * @return String to be saved
     */
    public String saveInOrder(BTNode node)
    {
        if (node != null)
        {
            saveInOrder(node.left);
            msg = msg + (node.data + "|" + node.value + "|");
            saveInOrder(node.right);
        }
        return msg;
    }

    /**
     * Method to traverse the tree and format the retrieved data for file output
     *
     * @param node current node
     * @return String to be saved
     */
    public String savePreOrder(BTNode node)
    {
        if (node != null)
        {
            msg = msg + (node.data + "|" + node.value + "|");
            savePreOrder(node.left);
            savePreOrder(node.right);
        }
        return msg;
    }

    /**
     * Method to traverse the tree and format the retrieved data for file output
     *
     * @param node current node
     * @return String to be saved
     */
    public String savePostOrder(BTNode node)
    {
        if (node != null)
        {
            savePostOrder(node.left);
            savePostOrder(node.right);
            msg = msg + (node.data + "|" + node.value + "|");
        }
        return msg;
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Helper Methods">
    /**
     * Method to return the root node
     *
     * @return root node
     */
    public BTNode getRootNode()
    {
        return root;
    }

    /**
     * Method to set the message to be displayed in the text area
     * @param msg Message to be displayed
     */
    public void setMessage(String msg)
    {
        this.msg = msg;
    }

    /**
     * Method to determine the number of levels in the binary tree
     * @param node Current node
     * @return maxDepth of tree
     */
    public int maxDepth(BTNode node)
    {
        if (node == null)
        {
            return 0;
        }
        else
        {
            /* compute the depth of each subtree */
            int lDepth = maxDepth(node.left);
            int rDepth = maxDepth(node.right);

            /* use the larger one */
            if (lDepth > rDepth)
            {
                return (lDepth + 1);
            }
            else
            {
                return (rDepth + 1);
            }
        }
    }

    /**
     * Method to return the total number of nodes in the binary tree
     * @return number of nodes in tree
     */
    public int getSize()
    {
        return size;
    }

    /**
     * Methods to traverse the tree to determine if a selected value is contained
     * in the tree
     * 
     * @param current Current node
     * @param value Value to be checked
     * @return String if found
     */
    private String containsNodeRecursive(BTNode current, int value)
    {
        if (current == null)
        {
            return "false";
        }
        if (value == current.value)
        {
            return "true";
        }
        return value < current.value
                ? containsNodeRecursive(current.left, value)
                : containsNodeRecursive(current.right, value);
    }

    public String containsNode(int value)
    {
        return containsNodeRecursive(root, value);
    }

//</editor-fold>
}
