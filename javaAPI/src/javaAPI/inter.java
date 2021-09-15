package javaAPI;

public interface inter {
	int connect(String ip, int port, String color, String red_stones) ;
	int drawAndWait(String home, String away) ;
	char getBoard(String ask);
}
