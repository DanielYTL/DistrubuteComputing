import java.net.*;
import java.io.*;
public class UDPClient{
    public static void main(String args[]){
    	
    	String message = "this is my message";
    	
		DatagramSocket datagram_socket = null;		
		try {
			datagram_socket = new DatagramSocket();    
			byte [] m = message.getBytes();
			InetAddress aHost = InetAddress.getByName("127.0.0.1");
			int serverPort = 6789;		                                                 
			DatagramPacket request = new DatagramPacket(m,  message.length(), aHost, serverPort);
			datagram_socket.send(request);			                        
			byte[] buffer = new byte[1000];
			DatagramPacket reply = new DatagramPacket(buffer, buffer.length);	
			datagram_socket.receive(reply);
			System.out.println("Reply from server: " + new String(reply.getData()));	
		}catch (SocketException e){
			System.out.println("Socket: " + e.getMessage());
		}catch (IOException e){
			System.out.println("IO: " + e.getMessage());
		}finally {
			if(datagram_socket != null) datagram_socket.close();
		}
	}		      	
}