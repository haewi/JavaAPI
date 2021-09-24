package Client;

import Connect6API.Api;

public class Dummy {

	final static private int EMPTY = 0;
	final static private int WHITE = 1;
	final static private int BLACK = 2;
	final static private int RED = 3;

    int[][] board = new int[19][19];

    public void init(){
        for(int i=0; i<19; i++){
            for(int j=0; j<19; j++){
                board[i][j] = EMPTY;
            }
        }
    }
    

    private String generateDraw() {
    	
    	String result = oneDraw() + ":" + oneDraw();
    	
//    	System.out.println("[generateHome] result: " + result);
    	
    	return result;
    }
    
    private String oneDraw() {
    	int first = (int)(Math.random()*19);
    	char letter = (char) (first + 'A');
    	if(first >= 8) {
    		letter = (char) (first + 'A' + 1);
    	}
    	
    	int second = (int) (Math.random()*19) + 1;
    	String num = "";
    	if(second>9) {
    		num = Integer.toString(second);
    	}
    	else {
    		num = "0" + Integer.toString(second);
    	}
    	
    	String result = Character.toString(letter) + num;
    	
//    	System.out.println("[oneHome] result: " + result);
    	
    	return result;
    }

    public static void main(String[] args) {
        Dummy dummy = new Dummy();
        dummy.init();
        
        System.out.println("args: " + args.length);
        if(args.length != 3) {
        	System.err.println("");
        }

        Api api = new Api();
        
        int port = Integer.parseInt(args[1]);
        

        String result = api.connect(args[0], port, args[2]);
        System.out.println("Dummy connect result: " + result);
        
        if(args[2].toLowerCase().compareTo("black") == 0) {
        	System.out.println("I am Black");
        	String first = api.drawAndWait("K10");
        }
        else if(args[2].toLowerCase().compareTo("white") == 0) {
        	System.out.println("I am White");
        	String first = api.drawAndWait("");
        }
        else {
        	System.err.println("command line argument error");
        }
        
        while(true) {
        	String draw = dummy.generateDraw();
        	
        	System.out.println("draw: " + draw);
        	
        	String wait = api.drawAndWait(draw);
        	System.out.println("wait: " + wait);
        }

    }

}
