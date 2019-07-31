package trafficmonitoringapplication.DLL;

public class DoublyLinkedList
{

    int size = 0;
    DLLNode head = null;
    DLLNode tail = null;

    public DLLNode getHeadNode()
    {
        return head;
    }

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

    public DLLNode addAtStart(int data)
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

    public DLLNode addAtEnd(int data)
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

    public DLLNode addAfter(int data, DLLNode prevNode)
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

    public String elementAt(int index)
    {
        if (index > size)
        {
            return "-1";
        }
        DLLNode n = head;
//        while (index - 1 != 0)
//        {
//            n = n.next;
//            index--;
//        }
        for (int i = 0; i < index; i++)
        {
            n = n.next;
        }
        return (String) n.data;
    }

    //get Size
    public int getSize()
    {
        return size;
    }

    public String printDLL()
    {
        String msg = "HEAD <-> ";
        DLLNode temp = head;
        System.out.println("Doubly Linked List:");
        while (temp != null)
        {
            String data = (String) temp.data;
            String[] tempData = data.split(", ");
            msg = msg + tempData[1] + " - " + tempData[5] + " - " + tempData[6] + " <--> ";
            System.out.println(" " + temp.data);
            temp = temp.next;
        }
        msg = msg.substring(0, msg.length() - 6);
        msg = msg + " <-> TAIL";
        System.out.println();
        return msg;
    }

    public int getLength(DLLNode a)
    {
        int count = 0;
        DLLNode h = a;
        while (h != null)
        {
            count++;
            h = h.next;
        }
        return count;
    }
}
