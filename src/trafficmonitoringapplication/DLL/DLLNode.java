/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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

    public DLLNode(int data)
    {
        this.data = data;
        next = null;
        previous = null;
    }
}
