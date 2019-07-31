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
    int size = 0;

    public BTNode getNode()
    {
        return root;
    }
    
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
        else if (value > current.value)
        {
            current.right = addRecursive(current.right, value, data);
        }
        else
        {
            // value already exists
            return current;
        }

        return current;
    }

    public void add(int value, String data)
    {
        root = addRecursive(root, value, data);
        size++;
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
    
    public int getSize()
    {
        return size;
    }
    
    public String saveBinaryTree(BTNode node, int index)
    {
        setMessage("");
        switch(index)
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
    
}
