/**
 * Class: DLLNode.java
 *
 * @author Daniel Geraghty
 *
 * Developed: August 2019
 * 
 * Version 1.0
 *
 * Purpose: Class to define a Node for the Doubly Linked List
 * 
 * Assessment 1 - ICTPRG523
 */
package trafficmonitoringapplication.DLL;

class DLLNode
{

    Object data;
    DLLNode next;
    DLLNode previous;

    public DLLNode(String data)
    {
        this.data = data;
        next = null;
        previous = null;
    }
}
