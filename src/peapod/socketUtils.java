package peapod;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

import java.net.InetAddress;

public class socketUtils 
{
	static Socket clientSocket=null;
    static DataOutputStream outToServer=null;
    static BufferedReader inFromServer=null;

	public static boolean socketConnect()
	{
		String ipAddress, portString;
		int portNumber;
		boolean rc=false;
		
		try 
		{
//			config.txt must be outside of package, but inside source
//			File file = new File("config.txt");
	        if (!peapod.ipAddress.getText().trim().isEmpty())
	        {
               ipAddress  = peapod.ipAddress.getText();
               portString = peapod.portNum.getText();
           
               portNumber = Integer.parseInt(portString);
               System.out.println(ipAddress);
	        }
	        else
	        {
	           ipAddress  = "localhost";
	           portNumber = 3333;
	        }
  
           clientSocket  = new Socket(ipAddress, portNumber);
           
           outToServer   = new DataOutputStream(clientSocket.getOutputStream());
           inFromServer  = new BufferedReader(
   	                       new InputStreamReader(clientSocket.getInputStream()));
           
           rc= true;
		}
		catch (ConnectException ex)
		{
			ex.printStackTrace();
		}					
		catch (UnknownHostException ex)
	    {
			ex.printStackTrace();
	    }
		catch (IOException ex) 
	    {
			ex.printStackTrace();
	    }
		
		return rc;
	}
	
	public static boolean sendMessage(String msg)
	{
		boolean rc=false;
		
		try 
		{
			outToServer.writeBytes(msg + "\r\n");
			rc = true;
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			Alert loginerr = new Alert(
					AlertType.ERROR,
					"SERVERS COULD NOT BE REACHED",
					ButtonType.OK);
			loginerr.setTitle("Disconnect Error");
			loginerr.setHeaderText("Disconnect Error");
			loginerr.showAndWait();
			
			peapod.send = false;
		}
		
		return rc;
	}
	
	public String recvMessage()
	{
		String msg=null;
		
		try
		{
			msg = inFromServer.readLine();
		} 
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return msg;
	}
	
	public static boolean closeSocket()
	{
		boolean rc=false;
		
		try
		{
			clientSocket.close();
                        rc=true;
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return rc;
	}
}
