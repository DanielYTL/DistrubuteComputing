import java.net.*;
import java.io.*;

public class UDPServer{
    public static void main(String args[]){ 
    	DatagramSocket datagram_socket = null;
		try{
	    	datagram_socket = new DatagramSocket(6789);
			byte[] buffer = new byte[1000];
 			while(true){
 				DatagramPacket request = new DatagramPacket(buffer, buffer.length);
  				datagram_socket.receive(request);     
    			DatagramPacket reply = new DatagramPacket(request.getData(), request.getLength(), request.getAddress(), request.getPort());
    			datagram_socket.send(reply);
    		}
		}catch (SocketException e){
			System.out.println("Socket: " + e.getMessage());
		}catch (IOException e) {
			System.out.println("IO: " + e.getMessage());
		}finally {
			if(datagram_socket != null) datagram_socket.close();
		}
    }
}