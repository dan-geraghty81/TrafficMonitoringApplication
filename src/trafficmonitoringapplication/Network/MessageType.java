/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trafficmonitoringapplication.Network;

import java.io.Serializable;

/**
 *
 * @author Dan
 */
public class MessageType implements Serializable
{
    protected static final long serialVersionUID = 1112122200L;
    public static final int LOGIN = 0, LOGOUT = 1, MESSAGE = 2, DATA = 3, STATIONS = 4;
    private int type;
    private String message;

    // constructor
    public MessageType(int type, String message)
    {
        this.type = type;
        this.message = message;
    }

    // getters
    int getType()
    {
        return type;
    }

    String getMessage()
    {
        return message;
    }
}
