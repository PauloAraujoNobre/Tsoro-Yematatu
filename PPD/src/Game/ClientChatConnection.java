package Game;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientChatConnection {
	
	private Socket socket;
	private DataInputStream dataIn;
	private DataOutputStream dataOut;
	
	public ClientChatConnection() {

		System.out.println("----Client Chat----");
		
		try {
			socket = new Socket("localhost", 5080);
			dataIn = new DataInputStream(socket.getInputStream());
			dataOut = new DataOutputStream(socket.getOutputStream());
		}
		catch (IOException e) {
			System.out.println("IOException - ClientSideConnection()");
		}
	}
	
	public void sendMessage(String message) {
		try {
			dataOut.writeUTF(message);
			dataOut.flush();
		}
		catch (IOException e) {
			System.out.println("IO Exception - sendMessage()");
		}
	}
	
	public String receiveMessage () {
		String message = "";
		
		try {
			message = dataIn.readUTF();
		}
		catch (IOException e) {
			System.out.println("IO Exception - receiveButtonNum()");
		}
		
		return message;
	}
}