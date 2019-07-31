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
