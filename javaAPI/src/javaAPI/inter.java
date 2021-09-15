package javaAPI;

public interface inter {
	int connect(String ip, int port, String color, String red_stones) ;
	int draw_and_wait(String home, String away) ;
	char get_board(String ask);
}
