/**
 * Class: DoublyLinkedList.java
 *
 * @author Daniel Geraghty
 *
 * Developed: August 2019
 *
 * Version 1.0
 *
 * Purpose: Class to define the DoublyLinkedList, perform additions to the list,
 * traverse the tree and display the results and export the tree data to CSV files.
 *
 * Assessment 1 - ICTPRG523
 */
package trafficmonitoringapplication.DLL;

public class DoublyLinkedList
{

    int size = 0;
    DLLNode head = null;
    DLLNode tail = null;

    /**
     * Method to return the head node in the list
     *
     * @return head node
     */
    public DLLNode getHeadNode()
    {
        return head;
    }

    /**
     * Method to return the last node in the list
     *
     * @return last node
     */
    public DLLNode getLastNode()
    {
        DLLNode temp = head;
        if (head.next == null)
        {
            return head;
        }
        else
        {
            while (temp.next != null)
            {
                temp = temp.next;
            }
        }
        return temp;
    }

    /**
     * Method to add a node at the start of the list
     *
     * @param data data to be added
     * @return node that was added
     */
    public DLLNode addAtStart(String data)
    {
        System.out.println("Adding Node " + data + " at the start");
        DLLNode n = new DLLNode(data);
        if (size == 0)
        {
            head = n;
            tail = n;
        }
        else
        {
            n.next = head;
            head.previous = n;
            head = n;
        }
        size++;
        return n;
    }

    /**
     * Method to add a node at the end of the list
     *
     * @param data data to be added
     * @return node that was added
     */
    public DLLNode addAtEnd(String data)
    {
        System.out.println("Adding Node " + data + " at the End");
        DLLNode n = new DLLNode(data);
        if (size == 0)
        {
            head = n;
            tail = n;
        }
        else
        {
            tail.next = n;
            n.previous = tail;
            tail = n;
        }
        size++;
        return n;
    }

    /**
     * Method to add a node after a specified node
     *
     * @param data data to be added
     * @param prevNode node before where data is to be added
     * @return node that was added
     */
    public DLLNode addAfter(String data, DLLNode prevNode)
    {
        if (prevNode == null)
        {
            System.out.println("Node after which new node to be added cannot be null");
            return null;
        }
        else if (prevNode == tail)
        {//check if it a last node
            return addAtEnd(data);
        }
        else
        {
            System.out.println("Adding node after " + prevNode.data);
            //create a new node
            DLLNode n = new DLLNode(data);

            //store the next node of prevNode
            DLLNode nextNode = prevNode.next;

            //make new node next points to prevNode
            n.next = nextNode;

            //make prevNode next points to new Node
            prevNode.next = n;

            //make nextNode previous points to new node
            nextNode.previous = n;

            //make  new Node previous points to prevNode
            n.previous = prevNode;
            size++;
            return n;
        }
    }

    /**
     * Method to delete a node from the start of the list
     */
    public void deleteFromStart()
    {
        if (size == 0)
        {
            System.out.println("\nList is Empty");
        }
        else
        {
            System.out.println("\ndeleting node " + head.data + " from start");
            head = head.next;
            size--;
        }
    }

    /**
     * Method to delete a node from the end of the list
     */
    public void deleteFromEnd()
    {
        switch (size)
        {
            case 0:
                System.out.println("\nList is Empty");
                break;
            case 1:
                deleteFromStart();
                break;
            default:
                //store the 2nd last node
                String x = (String) tail.data;
                DLLNode prevTail = tail.previous;
                //detach the last node
                tail = prevTail;
                tail.next = null;
                System.out.println("\ndeleting node " + x + " from end");
                size--;
                break;
        }
    }

    /**
     * Method to return the element at a particular index
     *
     * @param index
     * @return node at specified index
     */
    public String elementAt(int index)
    {
        if (index > size)
        {
            return "-1";
        }
        DLLNode n = head;
        for (int i = 0; i < index; i++)
        {
            n = n.next;
        }
        return (String) n.data;
    }

    /**
     *Method to return the number of nodes in the list
     * @return number of nodes
     */
    public int getSize()
    {
        return size;
    }

    /**
     * Method to create output string to be displayed
     * @return String data of the list
     */
    public String printDLL()
    {
        String msg = "HEAD <-> ";
        DLLNode temp = head;
        while (temp != null)
        {
            String data = (String) temp.data;
            String[] tempData = data.split(", ");
            msg = msg + tempData[1] + " - " + tempData[5] + " - " + tempData[6] + " <--> ";
            temp = temp.next;
        }
        msg = msg.substring(0, msg.length() - 6);
        msg = msg + " <-> TAIL";
        return msg;
    }
}
