/**
 * Class: Client.java
 *
 * @author Daniel Geraghty
 *
 * Developed: August 2019
 * 
 * Version: 1.0
 *
 * Purpose: Class to setup the client interactions to the server
 *
 * Assessment 2 - ICTPRG523
 */

package trafficmonitoringapplication.Network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import trafficmonitoringapplication.ClientGUI;

public class Client
{
    private ObjectInputStream sInput;		
    private ObjectOutputStream sOutput;		
    private Socket socket;
    private ClientGUI cg;
    private final String server, stationNo;
    private final int port;

    /**
     * Constructor to set the necessary server details
     * @param server Server IP address
     * @param port Server port number
     * @param station Client station number
     * @param cg Client UI
     */
    public Client(String server, int port, String station, ClientGUI cg)
    {
        this.server = server;
        this.port = port;
        this.stationNo = station;
        this.cg = cg;
    }

    /**
     * Method to start the connection to the server and open input/output streams
     * @return True/False if connection is made
     */
    public boolean start()
    {
        try
        {
            socket = new Socket(server, port);
        } 
        catch (IOException ec)
        {
            displayMessage("Error connectiong to server:" + ec);
            return false;
        }

        String msg = "Connection accepted " + socket.getInetAddress() + ":" + socket.getPort();
        displayMessage(msg);

        // Creating Input & Output Streams
        try
        {
            sInput = new ObjectInputStream(socket.getInputStream());
            sOutput = new ObjectOutputStream(socket.getOutputStream());
        }
        catch (IOException eIO)
        {
            displayMessage("Exception creating new Input/output Streams: " + eIO);
            return false;
        }

        // Creates the Thread to listen from the server 
        new ListenFromServer().start();
        
        // Send Login Message
        try
        {
            sOutput.writeObject(new MessageType(0,stationNo));
        }
        catch (IOException eIO)
        {
            displayMessage("Exception doing login : " + eIO);
            disconnect();
            return false;
        }
        // Return Successful Connection status
        return true;
    }

    /**
     * Method to display incoming messages from the server to the console
     * @param msg received message
     */
    private void displayMessage(String msg)
    {
        System.out.println(msg);      // println in console mode
    }

    /**
     * Method to send a message of the type MessageType to the server
     * @param msg data to be sent to the server
     */
    public void sendMessage(MessageType msg)
    {
        try
        {
            sOutput.writeObject(msg);
        }
        catch (IOException e)
        {
            displayMessage("Exception writing to server: " + e);
        }
    }

    /**
     * Method to close input/output streams and disconnect server connection
     */
    public void disconnect()
    {
        try
        {
            if (sInput != null)
            {
                sInput.close();
            }
        }
        catch (IOException e)
        {
        } // not much else I can do
        try
        {
            if (sOutput != null)
            {
                sOutput.close();
            }
        }
        catch (IOException e)
        {
        } // not much else I can do
        try
        {
            if (socket != null)
            {
                socket.close();
            }
        }
        catch (IOException e)
        {
        } // not much else I can do

    }

    /**
     * Class to listen for messages from the server and display them in the console
     */
    class ListenFromServer extends Thread
    {
        @Override
        public void run()
        {
            while (true)
            {
                try
                {
                    MessageType msg = (MessageType)sInput.readObject();
                    if (msg.getType() == 4){
                        System.out.println("Station Check");
                    }
                    System.out.println(msg.getMessage());
                }
                catch (IOException e)
                {
                    displayMessage("Server has close the connection: " + e);
                    break;
                }
                catch (ClassNotFoundException ex)
                {
                    System.out.println("Error: " + ex);
                } 
            }
        }
    }
}
