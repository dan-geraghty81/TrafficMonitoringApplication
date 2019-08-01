package trafficmonitoringapplication.Network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import trafficmonitoringapplication.ServerGUI;

public class Server
{

    final Logger logger = LoggerFactory.getLogger(Server.class);

    private static int stationID;
    private ArrayList<StationThread> stationList;
    private boolean connected, serverStarted = false;
    private String serverIP, stationNo;
    private int portNo;
    private ServerGUI gui;

    public Server(String serverIP, int portNo, ServerGUI gui)
    {
        this.serverIP = serverIP;
        this.portNo = portNo;
        this.gui = gui;
        stationList = new ArrayList<>();
    }

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
            catch (Exception e)
            {
                displayMessage("Exception closing the server and clients: " + e);
            }
        }
        catch (IOException e)
        {
            displayMessage("Exception on new ServerSocket: " + e);
        }
    }

    public void stop()
    {
        connected = false;
        try
        {
            new Socket(serverIP, portNo);
        }
        catch (Exception e)
        {
            displayMessage("Station No: " + stationNo + " has stopped.");
        }
    }

    public void displayMessage(String msg)
    {
        gui.displayMessage(msg);
    }

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

    synchronized void remove(int id)
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

    class StationThread extends Thread
    {
        Socket socket;
        ObjectInputStream sInput;
        ObjectOutputStream sOutput;
        String message;
        String username;
        int id;
        MessageType mt;

        StationThread(Socket socket)
        {
            id = stationID++;
            this.socket = socket;
            System.out.println("Thread trying to create streams");
            try
            {
                sOutput = new ObjectOutputStream(socket.getOutputStream());
                sInput = new ObjectInputStream(socket.getInputStream());
//                try
//                {
//                    mt = (MessageType) sInput.readObject();
//                    if (mt.getType() == 0)
//                    {
//                        username = mt.getMessage();
//                        gui.displayNotifications(2, username);
//                    }
//                    else
//                    {
//                        sOutput.writeObject(new MessageType(5, "Error with username"));
//                        return;
//                    }
//                }
//                catch (ClassNotFoundException ex)
//                {
//                    System.out.println("Class Not Found: " + ex);
//                }
//                gui.displayNotifications(2, username);
            }
            catch (IOException e)
            {
                displayMessage("Exception creating new streams: " + e);
                return;
            }
        }

        public void run()
        {
            boolean connected = true;
            while (connected)
            {
                try
                {
                    mt = (MessageType) sInput.readObject();
                    gui.displayMessage("New data received from station: " + username);
                }
                catch (IOException e)
                {
                    displayMessage(username + " Exception reading Streams: " + e);
                    break;
                }
                catch (ClassNotFoundException e2)
                {
                    break;
                }

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
            remove(id);
            close();
        }

        private void close()
        {
            try
            {
                if (sOutput != null)
                {
                    sOutput.close();
                }
            }
            catch (Exception e)
            {
            }
            try
            {
                if (sInput != null)
                {
                    sInput.close();
                }
            }
            catch (Exception e)
            {
            };
            try
            {
                if (socket != null)
                {
                    socket.close();
                }
            }
            catch (Exception e)
            {
            }
        }

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
            }
            return true;
        }
    }
}
