package Connect6API;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Api implements ApiInterface {

	// static numbers
		final static private int EMPTY = 0;
		final static private int WHITE = 1;
		final static private int BLACK = 2;
		final static private int RED = 3;

		// port
		private InputStream input;
		private OutputStream output;
		private static Board board;
		private Socket socket = null;
		private int color = 0;
		private int opponent = 0;

		@Override
		public String connect(String ip, int port, String col) {
			// TODO connect to server
			board = new Board();

			// connect
			try {
				socket = new Socket(ip, port);
				System.out.println("Socket connected to ip: " + ip + " port: " + port);
				
				socket.setTcpNoDelay(true); // no delay protocol

				// to server
				output = socket.getOutputStream();
				// from server
				input = socket.getInputStream();
			} catch (UnknownHostException e) {
				System.err.println("IP not determined");
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				System.err.println("Invalid port values");
				e.printStackTrace();
			}  catch (SocketException e1) {
				e1.printStackTrace();
			} catch (IOException e) {
				System.err.println("IOException");
				e.printStackTrace();
			}
			
			// get color
			if(col.toLowerCase().compareTo("white") == 0) {
				color = WHITE;
				opponent = BLACK;
			}
			else if (col.toLowerCase().compareTo("black") == 0) {
				color = BLACK;
				opponent = WHITE;
			}
			else {
				System.err.println("wrong color input");
				System.exit(1);
			}
			
			// get red stones from server
			byte[] red = new byte[15];
			byte[] num_string = new byte[4];

			int num_int=0;

			try {
				input.read(num_string, 0, 4);
				num_int = byteToInt(num_string);
				red = new byte[num_int];
				input.read(red, 0, num_int);
			} catch (IOException e) {
				System.err.println("IOException: read red stones");
				e.printStackTrace();
			}
			
			System.out.println("[connect] Got red stones: " + new String(red));
			
			String[] red_stones = new String(red).split(":");
			
			for(String stone : red_stones) {
				board.putStone(stone, RED);
			}
			
			return new String(red);
		}

		@Override
		public String drawAndWait(String draw) {							// here - check valid input?
			// TODO give draw and return string from server
			System.out.println("[drawAndWait] draw: " + draw);
			
			// if empty string --> just wait without draw
			if(draw.compareTo("") != 0) {
				draw(draw);
			}

			String result = waitStones();
			
			board.printBoard();
			
			return result;
		}

		@Override
		public String getBoard(String ask) {			// here - return value unification?
			int col = board.getColor(ask);
			String color = "";
			switch (col ) {
				case EMPTY:
					color = "EMPTY";
					break;
				case WHITE:
					color = "WHITE";
					break;
				case BLACK:
					color = "BLACK";
					break;
				case RED:
					color = "RED";
					break;
			}
			return color;
		}
		
		private void draw(String draw) {
			byte[] send = draw.getBytes();					// here - byte size?
			int length_send = send.length;
			
			try {
				output.write(intToByte(length_send));
				output.write(send);
				output.flush();
			} catch (IOException e) {
				System.err.println("IOException - draw");
				e.printStackTrace();
			}
			
			String[] stones = draw.split(":");
			
			for(String stone: stones) {
				board.putStone(stone, color);
			}
			
		}
		
		private String waitStones() {
			byte[] getBytes;							// here - byte size?
			byte[] string_size = new byte[4];
			int num_int = 0;
			String result = null;
			
			try {
				input.read(string_size, 0, 4);
				num_int = byteToInt(string_size);
				getBytes = new byte[num_int];
				input.read(getBytes, 0, num_int);
				result = new String(getBytes, 0, num_int);
			} catch (IOException e) {
				System.err.println("IOException - wait");
				e.printStackTrace();
			}
			
			System.out.println("[waitStones] result: " + result);

			String[] stones = result.split(":");
			
			if(opponent == -1) {
				System.err.println("[waitStones] invalid color");
				System.exit(1);
			}
			
			for(String stone: stones) {
				board.putStone(stone, opponent);
			}
			
			return result;
		}

		private int byteToInt(byte[] bytes) {
			return ((bytes[3] & 0xFF) << 24) | 
				((bytes[2] & 0xFF) << 16) | 
				((bytes[1] & 0xFF) << 8 ) | 
				((bytes[0] & 0xFF) << 0 );
		}

		private byte[] intToByte(int intValue) {
			byte[] byteArray = new byte[4];
			byteArray[3] = (byte)(intValue >> 24);
			byteArray[2] = (byte)(intValue >> 16);
			byteArray[1] = (byte)(intValue >> 8);
			byteArray[0] = (byte)(intValue);
			return byteArray;
		}

}
