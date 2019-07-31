package trafficmonitoringapplication.Network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import trafficmonitoringapplication.ClientGUI;

public class Client
{

    private ObjectInputStream sInput;		// to read from the socket
    private ObjectOutputStream sOutput;		// to write on the socket
    private Socket socket;

    // if I use a GUI or not
    private ClientGUI cg;

    // the server, the port and the username
    private String server, stationNo;
    private int port;

    public Client(String server, int port, String station, ClientGUI cg)
    {
        this.server = server;
        this.port = port;
        this.stationNo = station;
        this.cg = cg;
    }

    public boolean start()
    {
        try
        {
            socket = new Socket(server, port);
        } 
        catch (Exception ec)
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

    private void displayMessage(String msg)
    {
        System.out.println(msg);      // println in console mode
    }

    /*
	 * To send a message to the server
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

    /*
	 * When something goes wrong
	 * Close the Input/Output streams and disconnect not much to do in the catch clause
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
        catch (Exception e)
        {
        } // not much else I can do
        try
        {
            if (sOutput != null)
            {
                sOutput.close();
            }
        }
        catch (Exception e)
        {
        } // not much else I can do
        try
        {
            if (socket != null)
            {
                socket.close();
            }
        }
        catch (Exception e)
        {
        } // not much else I can do

    }

    /*
	 * a class that waits for the message from the server and append them to the JTextArea
	 * if we have a GUI or simply System.out.println() it in console mode
     */
    class ListenFromServer extends Thread
    {
        public void run()
        {
            while (true)
            {
                try
                {
                    MessageType msg = (MessageType)sInput.readObject();
                    // if console mode print the message and add back the prompt
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
