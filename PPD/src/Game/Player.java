package Game;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Player extends JFrame {
	private int width;
	private int hight;
	
	private JTextArea message;
	
	private  JButton b1;
	private  JButton b2;
	private  JButton b3;
	private  JButton b4;
	private  JButton b5;
	private  JButton b6;
	private  JButton b7;
	private  JButton bGiveUp;

	private int playerId;
	private int otherPlayer;
	
	private int[] values;
	private int turnsMade;
	
	private boolean buttonsEnabled;
	
	private JLabel displayField;
	private JDialog dialog;
	private ImageIcon defaultImage;
	private ImageIcon emptyImage;
	private ImageIcon p1Image;
	private ImageIcon p2Image;
	private ImageIcon p1EscImage;
	private ImageIcon p2EscImage;
	
	private JTextArea chatArea;
	private JButton chatButton;
	private JScrollPane chatScrollPane;
	private JTextField chatTextField;
	
	private ClientSideConnection csc;
	private ClientChatConnection chatConnection;
	
	public Player(int w, int h) {
		width = w;
		hight = h;
		
		message = new JTextArea();
		
		b1 = new JButton("1");
		b2 = new JButton("2");
		b3 = new JButton("3");
		b4 = new JButton("4");
		b5 = new JButton("5");
		b6 = new JButton("6");
		b7 = new JButton("7");
		bGiveUp = new JButton("Give Up");
		
		values = new int[7];
		Arrays.fill(values, 0);
		turnsMade = 0;
		
		this.displayField = new JLabel();

		this.defaultImage = new ImageIcon(this.getClass().getResource("imgs/default.png"));
		this.emptyImage = new ImageIcon(this.getClass().getResource("imgs/empty.png"));
		this.p1Image = new ImageIcon(this.getClass().getResource("imgs/p1.png"));
		this.p2Image = new ImageIcon(this.getClass().getResource("imgs/p2.png"));
		this.p1EscImage = new ImageIcon(this.getClass().getResource("imgs/p1Esc.png"));
		this.p2EscImage = new ImageIcon(this.getClass().getResource("imgs/p2Esc.png"));
		
		this.chatArea = new JTextArea();
		this.chatButton = new JButton();
		this.chatScrollPane = new JScrollPane();
		this.chatTextField = new JTextField();
	}
	
	public void setUpGUI() {
		this.setBackground(Color.WHITE);
		this.setSize(width, hight);
		this.setTitle("Player #" + playerId);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.setContentPane(displayField);
		
		chatTextField.setBounds(47, 950, 480, 40);

		chatButton.setText("Send");
		chatButton.setBounds(567, 890, 100, 100);
		
		chatArea.setEditable(false);
		chatArea.setColumns(20);
		chatArea.setRows(5);
		chatArea.setWrapStyleWord(true);
		chatArea.setLineWrap(true);
		chatArea.setFont(chatArea.getFont().deriveFont(15f));
		
		chatScrollPane.setViewportView(chatArea);
		chatScrollPane.setBounds(47, 600, 480, 330);
		
		this.add(chatTextField);
		this.add(chatButton);
		this.add(chatScrollPane);
		
		bGiveUp.setBounds(567, 600, 100, 100);

		ActionListener al = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				declareWinner(otherPlayer, true);
			}
		};
		
		bGiveUp.addActionListener(al);
		
		b1.setBounds(290, 40, 80, 80);
		b1.setOpaque(false);
		b1.setContentAreaFilled(false);
		b1.setBorderPainted(false);
		b1.setFocusable(false);
		b1.setIcon(defaultImage);
		b1.setDisabledIcon(defaultImage);
		b1.setForeground(Color.WHITE);
		b1.setActionCommand("0");

		b2.setBounds(148, 257, 80, 80);
		b2.setOpaque(false);
		b2.setContentAreaFilled(false);
		b2.setBorderPainted(false);
		b2.setFocusable(false);
		b2.setIcon(defaultImage);
		b2.setDisabledIcon(defaultImage);
		b2.setForeground(Color.BLACK);
		b2.setActionCommand("1");
		
		b3.setBounds(290, 257, 80, 80);
		b3.setOpaque(false);
		b3.setContentAreaFilled(false);
		b3.setBorderPainted(false);
		b3.setFocusable(false);
		b3.setIcon(defaultImage);
		b3.setDisabledIcon(defaultImage);
		b3.setForeground(Color.BLACK);
		b3.setActionCommand("2");

		b4.setBounds(448, 257, 80, 80);
		b4.setOpaque(false);
		b4.setContentAreaFilled(false);
		b4.setBorderPainted(false);
		b4.setFocusable(false);
		b4.setIcon(defaultImage);
		b4.setDisabledIcon(defaultImage);
		b4.setForeground(Color.WHITE);
		b4.setActionCommand("3");

		b5.setBounds(46, 480, 80, 80);
		b5.setOpaque(false);
		b5.setContentAreaFilled(false);
		b5.setBorderPainted(false);
		b5.setFocusable(false);
		b5.setIcon(defaultImage);
		b5.setDisabledIcon(defaultImage);
		b5.setForeground(Color.WHITE);
		b5.setActionCommand("4");

		b6.setBounds(290, 480, 80, 80);
		b6.setOpaque(false);
		b6.setContentAreaFilled(false);
		b6.setBorderPainted(false);
		b6.setFocusable(false);
		b6.setIcon(defaultImage);
		b6.setDisabledIcon(defaultImage);
		b6.setForeground(Color.WHITE);
		b6.setActionCommand("5");

		b7.setBounds(557, 480, 80, 80);
		b7.setOpaque(false);
		b7.setContentAreaFilled(false);
		b7.setBorderPainted(false);
		b7.setFocusable(false);
		b7.setIcon(defaultImage);
		b7.setDisabledIcon(defaultImage);
		b7.setForeground(Color.WHITE);
		b7.setActionCommand("6");
		
		message.setText("Creating a simple turn-based game in Java");
		message.setWrapStyleWord(true);
		message.setLineWrap(true);
		message.setEditable(false);
		
		this.add(message);
		this.add(b1);
		this.add(b2);
		this.add(b3);
		this.add(b4);
		this.add(b5);
		this.add(b6);
		this.add(b7);
		this.add(bGiveUp);
		
		Thread chatThread = new Thread(new Runnable() {
			public void run() {
				updateChat();
			}
		});
		chatThread.start();
		
		if (playerId == 1) {
//			chatArea.append("You a player #1. You go first.");
			otherPlayer = 2;
			buttonsEnabled = true;
		} else {
//			chatArea.append("You a player #2. Wait for your turn.\n");
			otherPlayer = 1;
			buttonsEnabled = false;
			Thread t = new Thread(new Runnable() {
				public void run() {
					updateTurn();
				}
			});
			t.start();
		}
		
		toggleButtons(0);
		
		this.setVisible(true);
	}
	
	public void connectToServer() {
		csc = new ClientSideConnection();
		chatConnection = new ClientChatConnection();
	}

	public void setUpButtons() {
		ActionListener al = new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				JButton b = (JButton) ae.getSource();
				int bNum = Integer.parseInt(b.getText());
				
//				chatArea.append("Player #" + playerId + " clicked button #" + bNum + ". Now wait for player #" + otherPlayer + "\n");
				turnsMade++;
				System.out.println("Turns made: " + turnsMade);
				
				buttonsEnabled = false;
				
				updateValuesCurrent(bNum, playerId);
				toggleButtons(0);
				setEmptyPiece(playerId == 1 ? 0 : 1);
				
				csc.sendButtonNum(bNum);				
				checkWinner();
				
				Thread t = new Thread(new Runnable() {
					public void run() {
						updateTurn();
					}
				});
				t.start();
			}
		};
		
		b1.addActionListener(al);
		b2.addActionListener(al);
		b3.addActionListener(al);
		b4.addActionListener(al);
		b5.addActionListener(al);
		b6.addActionListener(al);
		b7.addActionListener(al);
	}

	public void toggleButtons(int fixTurn) {
		b1.setDisabledIcon(values[0] == 1 ? p1EscImage : (values[0] == 2 ? p2EscImage : defaultImage));
		b2.setDisabledIcon(values[1] == 1 ? p1EscImage : (values[1] == 2 ? p2EscImage : defaultImage));
		b3.setDisabledIcon(values[2] == 1 ? p1EscImage : (values[2] == 2 ? p2EscImage : defaultImage));
		b4.setDisabledIcon(values[3] == 1 ? p1EscImage : (values[3] == 2 ? p2EscImage : defaultImage));
		b5.setDisabledIcon(values[4] == 1 ? p1EscImage : (values[4] == 2 ? p2EscImage : defaultImage));
		b6.setDisabledIcon(values[5] == 1 ? p1EscImage : (values[5] == 2 ? p2EscImage : defaultImage));
		b7.setDisabledIcon(values[6] == 1 ? p1EscImage : (values[6] == 2 ? p2EscImage : defaultImage));
		
		if (!countPieces(playerId) || (playerId == 1 && turnsMade + fixTurn < 4)) {
			b1.setEnabled(values[0] == 0 && buttonsEnabled ? true : false);
			b2.setEnabled(values[1] == 0 && buttonsEnabled ? true : false);
			b3.setEnabled(values[2] == 0 && buttonsEnabled ? true : false);
			b4.setEnabled(values[3] == 0 && buttonsEnabled ? true : false);
			b5.setEnabled(values[4] == 0 && buttonsEnabled ? true : false);
			b6.setEnabled(values[5] == 0 && buttonsEnabled ? true : false);
			b7.setEnabled(values[6] == 0 && buttonsEnabled ? true : false);
			
			if (playerId == 1) {
				b1.setIcon(values[0] == 0 && buttonsEnabled ? p1Image : defaultImage);
				b2.setIcon(values[1] == 0 && buttonsEnabled ? p1Image : defaultImage);
				b3.setIcon(values[2] == 0 && buttonsEnabled ? p1Image : defaultImage);
				b4.setIcon(values[3] == 0 && buttonsEnabled ? p1Image : defaultImage);
				b5.setIcon(values[4] == 0 && buttonsEnabled ? p1Image : defaultImage);
				b6.setIcon(values[5] == 0 && buttonsEnabled ? p1Image : defaultImage);
				b7.setIcon(values[6] == 0 && buttonsEnabled ? p1Image : defaultImage);
			} else if (playerId == 2) {
				b1.setIcon(values[0] == 0 && buttonsEnabled ? p2Image : defaultImage);
				b2.setIcon(values[1] == 0 && buttonsEnabled ? p2Image : defaultImage);
				b3.setIcon(values[2] == 0 && buttonsEnabled ? p2Image : defaultImage);
				b4.setIcon(values[3] == 0 && buttonsEnabled ? p2Image : defaultImage);
				b5.setIcon(values[4] == 0 && buttonsEnabled ? p2Image : defaultImage);
				b6.setIcon(values[5] == 0 && buttonsEnabled ? p2Image : defaultImage);
				b7.setIcon(values[6] == 0 && buttonsEnabled ? p2Image : defaultImage);
			}
		} else {
			b1.setEnabled(values[0] == playerId && buttonsEnabled ? true : false);
			b2.setEnabled(values[1] == playerId && buttonsEnabled ? true : false);
			b3.setEnabled(values[2] == playerId && buttonsEnabled ? true : false);
			b4.setEnabled(values[3] == playerId && buttonsEnabled ? true : false);
			b5.setEnabled(values[4] == playerId && buttonsEnabled ? true : false);
			b6.setEnabled(values[5] == playerId && buttonsEnabled ? true : false);
			b7.setEnabled(values[6] == playerId && buttonsEnabled ? true : false);
			
			if (playerId == 1) {
				b1.setIcon(values[0] == playerId && buttonsEnabled ? p1Image : defaultImage);
				b2.setIcon(values[1] == playerId && buttonsEnabled ? p1Image : defaultImage);
				b3.setIcon(values[2] == playerId && buttonsEnabled ? p1Image : defaultImage);
				b4.setIcon(values[3] == playerId && buttonsEnabled ? p1Image : defaultImage);
				b5.setIcon(values[4] == playerId && buttonsEnabled ? p1Image : defaultImage);
				b6.setIcon(values[5] == playerId && buttonsEnabled ? p1Image : defaultImage);
				b7.setIcon(values[6] == playerId && buttonsEnabled ? p1Image : defaultImage);
			} else if (playerId == 2) {
				b1.setIcon(values[0] == playerId && buttonsEnabled ? p2Image : defaultImage);
				b2.setIcon(values[1] == playerId && buttonsEnabled ? p2Image : defaultImage);
				b3.setIcon(values[2] == playerId && buttonsEnabled ? p2Image : defaultImage);
				b4.setIcon(values[3] == playerId && buttonsEnabled ? p2Image : defaultImage);
				b5.setIcon(values[4] == playerId && buttonsEnabled ? p2Image : defaultImage);
				b6.setIcon(values[5] == playerId && buttonsEnabled ? p2Image : defaultImage);
				b7.setIcon(values[6] == playerId && buttonsEnabled ? p2Image : defaultImage);
			}
			
			int emptyPlace = getEmptyPlace();
			
			if (emptyPlace == 1) {
				b6.setEnabled(false);
				b7.setEnabled(false);
			} else if (emptyPlace == 2) {
				b5.setEnabled(false);
				b7.setEnabled(false);
			} else if (emptyPlace == 3) {
				b5.setEnabled(false);
				b6.setEnabled(false);
			} else if (emptyPlace == 4) {
				b3.setEnabled(false);
				b4.setEnabled(false);
			} else if (emptyPlace == 5) {
				b2.setEnabled(false);
				b4.setEnabled(false);
			} else if (emptyPlace == 6) {
				b2.setEnabled(false);
				b3.setEnabled(false);
			}
		}
	}
	
	public int getEmptyPlace() {
		for (int i = 0; i < values.length; i++) {
			if (values[i] == 0) {
				return i;
			}
		}
		
		return -1;
	}
	
	public void updateTurn() {
		int n = csc.reciveButtonNum();
		if (n == 255) {
			declareWinner(playerId, true);
		} else {			
			message.setText("Your enemy clicked button #" + n + ". Your turn.\n");
			
			updateValuesCurrent(n, otherPlayer);	
			
			buttonsEnabled = true;
			checkWinner();
			
			toggleButtons(1);
			
			setEmptyPiece(1);
		}
	}

	private void checkWinner() {
		if (values[0] == values[2] && values[2] == values[5] && values[0] != 0) {
			declareWinner(values[0], false);
		} else if (values[1] == values[2] && values[2] == values[3] && values[1] != 0) {
			declareWinner(values[1], false);
		} else if (values[4] == values[5] && values[5] == values[6] && values[4] != 0) {
			declareWinner(values[4], false);
		} else if (values[0] == values[1] && values[1] == values[4] && values[0] != 0) {
			declareWinner(values[0], false);
		} else if (values[0] == values[3] && values[3] == values[6] && values[0] != 0) {
			declareWinner(values[0], false);
		}
	}
	
	public void updateValuesCurrent(int bNum, int value) {
		if (!countPieces(value)) {
			values[bNum - 1] = value;
		} else {
			for (int i = 0; i < values.length; i++) {
				if (values[i] == 0) {
					values[i] = value;
					values[bNum - 1] = 0;
				}
			}
		}
		
		printValues();
	}
	
	public boolean countPieces(int currentPlayer) {
		int count = 0;
		for (int i = 0; i < values.length; i++) {
			if (values[i] == currentPlayer) {
				count++;
			}
		}
		
		return (count > 2 ? true : false);
	}
	
	public void declareWinner(int pId, boolean giveUp) {
		message.setText("Player #" + pId + " WON!");
		System.out.println("Player #" + pId + " WON!");
		buttonsEnabled = false;
		
		dialog = new JDialog(this, "Dialog Player #" + playerId);
        dialog.add(new JLabel("Player #" + pId + " WON!" + (giveUp ? " Other player give up" : "")));

        dialog.setBounds(290, 287, 250, 100);
        dialog.setVisible(true);
         
    	csc.sendButtonNum(giveUp ? -1 : -2);
	}
	
	//Client Connection
	private class ClientSideConnection {
		private Socket socket;
		private DataInputStream dataIn;
		private DataOutputStream dataOut;
		
		public ClientSideConnection() {
			System.out.println("========= Client =========");
			try {
				socket = new Socket("localhost", 5000);
				dataIn = new DataInputStream(socket.getInputStream());
				dataOut = new DataOutputStream(socket.getOutputStream());
				playerId = dataIn.read();
			} catch(IOException ex) {
				System.out.println("IOException from CSC constructor");
			}
		}
		
		public void sendButtonNum(int n) {
			try {
				dataOut.write(n);
				dataOut.flush();
			} catch (IOException ex) {
				System.out.println("IOException from sendButtonNum() CSC");
			}
		}
		
		public int reciveButtonNum() {
			int n = -1;
			try {
				n = dataIn.read();
				System.out.println("Player #" + otherPlayer + " clicked button #" + n);
			} catch(IOException ex) {
				System.out.println("IOException from reciveButton() CSC");
			}
			
			return n;
		}
		
		public void closeConnection() {
			try {
				socket.close();
				System.out.println("--- CONECCTION CLOSED ---");
			} catch(IOException ex) {
				System.out.println("IOException from closeConnection() CSC");
			}
		}
	}

	public void printValues() {
		System.out.print("[");
		
		for (int i = 0 ; i < values.length; i++) {
			System.out.print(" " + values[i]);			
		}
		
		System.out.println(" ]");
	}
	
	public void setEmptyPiece(int fixTurn) {
		if (countPieces(playerId) && (turnsMade + fixTurn) > 3) {					
			int emptyPlace = getEmptyPlace();
			
			switch(emptyPlace) {
			case 0:
				b1.setDisabledIcon(emptyImage);
				break;
			case 1:
				b2.setDisabledIcon(emptyImage);
				break;
			case 2:
				b3.setDisabledIcon(emptyImage);
				break;
			case 3:
				b4.setDisabledIcon(emptyImage);
				break;
			case 4:
				b5.setDisabledIcon(emptyImage);
				break;
			case 5:
				b6.setDisabledIcon(emptyImage);
				break;
			case 6:
				b7.setDisabledIcon(emptyImage);
				break;
			}
		}
	}
	
	public void sendChatMessage() {
		String message = chatTextField.getText();
		
		if (!message.isEmpty()) {
			chatArea.append("\nPlayer #" + playerId + ": " + message);
			chatConnection.sendMessage(message);
			chatTextField.setText("");
		}
	}
	
	public void updateChat() {
		String message = "";
		
		while (!message.equalsIgnoreCase("exit")) {
			message = chatConnection.receiveMessage();
			chatArea.append("\nPlayer #" + otherPlayer + ": " + message);	
		}
	}
	
	public void setUpChat() {
		ActionListener actionListener = new ActionListener() 
		{
			public void actionPerformed(ActionEvent ae) {
				sendChatMessage();
			}
		};
		
		KeyListener keyListener = new KeyAdapter()
		{
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					sendChatMessage();
				}
			}
		};
		
		chatButton.addActionListener(actionListener);
		chatTextField.addKeyListener(keyListener);
	}
	
	public static void main(String[] args) {
		Player p = new Player(700, 1200);
		p.connectToServer();
		p.setUpGUI();
		p.setUpButtons();
		p.setUpChat();
	}
}