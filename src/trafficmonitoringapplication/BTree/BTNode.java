/**
 * Class: BTNode.java
 *
 * @author Daniel Geraghty
 *
 * Developed: August 2019
 * 
 * Version 1.0
 *
 * Purpose: Class to define a Node for the Binary Tree
 * 
 * Assessment 1 - ICTPRG523
 */
package trafficmonitoringapplication.BTree;

public class BTNode
{
    int value;
    String data;
    BTNode left;
    BTNode right;
    
    BTNode(int value, String data)
    {
        this.value = value;
        this.data = data;
        right = null;
        left = null;
    }
}
