package Game;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class GameServer {
	private ServerSocket ss;
	private ServerSocket chatSocket;
	
	private int numPlayers;
	
	private ServerSideConnection player1;
	private ServerSideConnection player2;
	
	private ServerChatConnection chatP1;
	private ServerChatConnection chatP2;
	
	private int turnsMade;
	
	private int player1ButtonNum;
	private int player2ButtonNum;
	
	private String player1Chat;
	private String player2Chat;
	
	public GameServer() {
		System.out.println("============= Game Server =============");
		numPlayers = 0;		
		turnsMade = 0;
		
		try {
			ss = new ServerSocket(5000);
			chatSocket = new ServerSocket(5080);
		} catch(IOException ex) {
			System.out.println("IOException from GameServer Contructor");
		}
	}
	
	public void acceptConnections() {
		try {
			System.out.println("Waiting for conenctions...");
			while(numPlayers < 2) {
				numPlayers++;
				
				Socket s = ss.accept();
				ServerSideConnection ssc = new ServerSideConnection(s, numPlayers);
				Thread t = new Thread(ssc);
				t.start();
				
				Socket chatS = chatSocket.accept();
				ServerChatConnection chatSsc = new ServerChatConnection(chatS, numPlayers); 
				
				if (numPlayers == 1) {
					player1 = ssc;
					chatP1 = chatSsc;
				} else {
					player2 = ssc;
					chatP2 = chatSsc;
				}
				
				Thread chatT = new Thread(chatSsc);
				chatT.start();
			}
			
			System.out.println("We no have 2 players. No longer accepting connections");
		} catch(IOException ex) {
			System.out.println("IOException from acceptConnections()");
		}
	}
	
	public class ServerSideConnection implements Runnable {
		private Socket socket;
		private DataInputStream dataIn;
		private DataOutputStream dataOut;
		private int playerId;
		
		public ServerSideConnection(Socket s, int id) {
			socket = s;
			playerId = id;
			try {
				dataIn = new DataInputStream(socket.getInputStream());
				dataOut = new DataOutputStream(socket.getOutputStream());
			} catch(IOException ex) {
				System.out.println("IOException from SSC constructor");
			}
		}
		
		public void run() {
			try {
				dataOut.write(playerId);
				dataOut.flush();
				
				while(true) {
					if (playerId == 1) {
						player1ButtonNum = dataIn.read();
						System.out.println("Player 1 clicked button #" + player1ButtonNum);
						player2.sendButtonNum(player1ButtonNum);
					} else {
						player2ButtonNum = dataIn.read();
						System.out.println("Player 2 clicked button #" + player2ButtonNum);
						player1.sendButtonNum(player2ButtonNum);
					}
					turnsMade++;
					if (turnsMade >= 20) {
						System.out.println("Max turns exceeded ");
						break;
					}
				}
				player1.closeConnection();
				player2.closeConnection();
			} catch(IOException ex) {
				System.out.println("IOException from run() ssc");
			}
		}
		
		public void sendButtonNum(int n) {
			try {
				dataOut.write(n);
				dataOut.flush();
			} catch(IOException ex) {
				System.out.println("IOException from sendButtonNum() scc");
			}
		}
		
		public void closeConnection() {
			try {
				socket.close();
				System.out.println("Connection closed");
			} catch(IOException ex) {
				System.out.println("IOException from closeConnection() SSC");
			}
		}
	}
	
	public class ServerChatConnection implements Runnable {
		private Socket socket;
		private DataInputStream dataIn;
		private DataOutputStream dataOut;
		private int playerId;
		
		public ServerChatConnection(Socket s, int id) {
			socket = s;
			playerId = id;
			try {
				dataIn = new DataInputStream(socket.getInputStream());
				dataOut = new DataOutputStream(socket.getOutputStream());
			} catch(IOException ex) {
				System.out.println("IOException from SSC constructor");
			}
		}

		public void run() {
			try {
				
				while(true) {
					if (playerId == 1) {
						player1Chat = dataIn.readUTF();
						System.out.println("player1Chat: " + player1Chat);
						chatP2.sendMessage(player1Chat);
					} else {
						player2Chat = dataIn.readUTF();
						chatP1.sendMessage(player2Chat);
					}
				}
			} catch(IOException ex) {
				System.out.println("IOException from run() ssc");
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
	}
	
	public static void main(String[] args) {
		GameServer gs = new GameServer();
		gs.acceptConnections();
	}
}
