/**
 * Class: MessageType.java
 *
 * @author Daniel Geraghty
 *
 * Developed: August 2019
 * 
 * Version: 1.0
 *
 * Purpose: Class to define the properties of the message data being sent
 *
 * Assessment 2 - ICTPRG523
 */
package trafficmonitoringapplication.Network;

import java.io.Serializable;

public class MessageType implements Serializable
{
    protected static final long serialVersionUID = 1112122200L;
    public static final int LOGIN = 0, LOGOUT = 1, MESSAGE = 2, DATA = 3, STATIONS = 4;
    private int type;
    private String message;

    public MessageType(int type, String message)
    {
        this.type = type;
        this.message = message;
    }

    int getType()
    {
        return type;
    }

    String getMessage()
    {
        return message;
    }
}
