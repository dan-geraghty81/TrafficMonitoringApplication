/**
 * Class: Server.java
 *
 * @author Daniel Geraghty
 *
 * Developed: August 2019
 * 
 * Version: 1.0
 *
 * Purpose: Class to setup the server functions
 *
 * Assessment 2 - ICTPRG523
 */
package trafficmonitoringapplication.Network;

//<editor-fold defaultstate="collapsed" desc="Imports">
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import trafficmonitoringapplication.ServerGUI;
//</editor-fold>

public class Server
{

//<editor-fold defaultstate="collapsed" desc="Global Variables">
    private static int stationID;
    private ArrayList<StationThread> stationList;
    private boolean connected, serverStarted = false;
    private String serverIP, stationNo;
    private int portNo;
    private ServerGUI gui;
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Constructor">
    /**
     * Constructor to define the server details
     * @param serverIP Server IP address
     * @param portNo Server port number
     * @param gui Server GUI
     */
    public Server(String serverIP, int portNo, ServerGUI gui)
    {
        this.serverIP = serverIP;
        this.portNo = portNo;
        this.gui = gui;
        stationList = new ArrayList<>();
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Start & Stop Methods">
    /**
     * Method to start the server and wait for connections from client
     */
    public void start()
    {
        connected = true;
        try
        {
            ServerSocket serverSocket = new ServerSocket(portNo);
            while (true)
            {
                if (!serverStarted)
                {
                    gui.displayNotifications(1, serverIP);
                    serverStarted = true;
                }
                Socket socket = serverSocket.accept();
                if (!connected)
                {
                    break;
                }
                StationThread st = new StationThread(socket);
                stationList.add(st);
                st.start();
            }
            try
            {
                serverSocket.close();
                for (int i = 0; i < stationList.size(); i++)
                {
                    StationThread st = stationList.get(i);
                    try
                    {
                        st.sInput.close();
                        st.sOutput.close();
                        st.socket.close();
                    }
                    catch (IOException e)
                    {
                        displayMessage("Error on close: " + e);
                    }
                }
            }
            catch (IOException e)
            {
                displayMessage("Exception closing the server and clients: " + e);
            }
        }
        catch (IOException e)
        {
            displayMessage("Exception on new ServerSocket: " + e);
        }
    }
    
    /**
     * Method to close the connections and stop the server from running
     */
    public void stop()
    {
        connected = false;
        try
        {
            new Socket(serverIP, portNo);
        }
        catch (IOException e)
        {
            displayMessage("Station No: " + stationNo + " has stopped.");
        }
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Helper Methods">
    /**
     * Method to send a message to be displayed on the Server GUI
     * @param msg Message to be displayed
     */
    public void displayMessage(String msg)
    {
        gui.displayMessage(msg);
    }
    
    /**
     * Method to check is Monitoring Stations are still connected and if not
     * closes the connection and removes it from the list
     */
    public void displayStationList()
    {
        if (stationList.isEmpty())
        {
            displayMessage("No Stations are connected.");
        }
        else
        {
            for (int i = 0; i < stationList.size(); ++i)
            {
                StationThread st = stationList.get(i);
                if (!st.writeMsg(new MessageType(MessageType.STATIONS, "Check")))
                {
                    stationList.remove(i);
                }
                displayMessage("Station: " + st.username + " is connected.");
            }
        }
    }
    
    /**
     * Method to remove a Monitoring Station from stationList array list
     *
     * @param id stationID
     */
    private void remove(int id)
    {
        for (int i = 0; i < stationList.size(); i++)
        {
            StationThread st = stationList.get(i);
            if (st.id == id)
            {
                stationList.remove(i);
                return;
            }
        }
    }
//</editor-fold>

    /**
     * Class to create a thread for each client that connects to the server
     */
    class StationThread extends Thread
    {

        Socket socket;
        ObjectInputStream sInput;
        ObjectOutputStream sOutput;
        String message;
        String username;
        int id;
        MessageType mt;

        /**
         * Constructor to open input/output streams to the client
         * @param socket the socket the client is connecting on
         */
        StationThread(Socket socket)
        {
            id = stationID++;
            this.socket = socket;
            System.out.println("Thread trying to create streams");
            try
            {
                sOutput = new ObjectOutputStream(socket.getOutputStream());
                sInput = new ObjectInputStream(socket.getInputStream());
            }
            catch (IOException e)
            {
                displayMessage("Exception creating new streams: " + e);
            }
        }

        /**
         * Method that allows the thread to accept and handle the incoming data
         */
        @Override
        public void run()
        {
            boolean connected = true;
            while (connected)
            {
                try
                {
                    Object data = sInput.readObject();
                    System.out.println(data);
                    mt = (MessageType) data;
                    System.out.println(mt.getMessage());
                    String message = mt.getMessage();
                    switch (mt.getType())
                    {
                        case MessageType.LOGIN:
                            username = mt.getMessage();
                            gui.displayNotifications(2, username);
                            break;
                        case MessageType.LOGOUT:
                            gui.displayNotifications(3, username);
                            connected = false;
                            break;
                        case MessageType.MESSAGE:
                            gui.displayMessage(message);
                            break;
                        case MessageType.DATA:
                            gui.receiveTableData(message);
                            gui.displayMessage("New data received from station: " + username);
                            break;
                        case MessageType.STATIONS:
                            for (int i = 0; i < stationList.size(); ++i)
                            {
                                StationThread ct = stationList.get(i);
                                displayMessage((i + 1) + ") " + ct.username);
                            }
                            break;
                    }
                }
                catch (IOException e)
                {
                    displayMessage(username + " Exception reading Streams: " + e);
                    break;
                }
                catch (ClassNotFoundException e2)
                {
                    System.out.println("CNFE: " + e2);
                    break;
                }

            }
            remove(id);
            close();
        }

        /**
         * Method to close the input/output streams and close the connection to the client
         */
        private void close()
        {
            try
            {
                if (sOutput != null)
                {
                    sOutput.close();
                }
                if (sInput != null)
                {
                    sInput.close();
                }
                if (socket != null)
                {
                    socket.close();
                }
            }
            catch (IOException e)
            {
                System.out.println("Error: " + e);
            } 
        }

        /**
         * Method to send a message to the client to check if they are connected
         * If not remove the connection
         * @param msg Message to be sent
         * @return True/False if connected
         */
        private boolean writeMsg(MessageType msg)
        {
            // if Client is still connected send the message to it
            if (!socket.isConnected())
            {
                close();
                return false;
            }
            // write the message to the stream
            try
            {
                sOutput.writeObject(msg);
            } // if an error occurs, do not abort just inform the user
            catch (IOException e)
            {
                displayMessage("Error sending message to " + username);
                displayMessage(e.toString());
                remove(id);
            }
            return true;
        }
    }
}
