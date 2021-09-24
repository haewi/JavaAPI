package Connect6API;

public interface ApiInterface {
	String connect(String ip, int port, String col);
	String drawAndWait(String draw);
	String getBoard(String ask);
}
