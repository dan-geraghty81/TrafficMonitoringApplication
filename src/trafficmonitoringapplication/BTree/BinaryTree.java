/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trafficmonitoringapplication.BTree;

/**
 *
 * @author Daniel
 */
public class BinaryTree
{

    BTNode root;
    String msg = "";

    public BTNode getNode()
    {
        return root;
    }
    
    private BTNode addRecursive(BTNode current, int value)
    {
        if (current == null)
        {
            return new BTNode(value);
        }

        if (value < current.value)
        {
            current.left = addRecursive(current.left, value);
        }
        else if (value > current.value)
        {
            current.right = addRecursive(current.right, value);
        }
        else
        {
            // value already exists
            return current;
        }

        return current;
    }

    public void add(int value)
    {
        root = addRecursive(root, value);
    }

    private boolean containsNodeRecursive(BTNode current, int value)
    {
        if (current == null)
        {
            return false;
        }
        if (value == current.value)
        {
            return true;
        }
        return value < current.value
                ? containsNodeRecursive(current.left, value)
                : containsNodeRecursive(current.right, value);
    }

    public boolean containsNode(int value)
    {
        return containsNodeRecursive(root, value);
    }

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
    
    public void setMessage(String msg)
    {
        this.msg = msg;
    }
    
    
}
