/**
 * The client class creates a connection to the server at default port 1337.
 * Waits for the user to input a single character with the keyboard. The
 * character has to be ’R’ (rock), ’P’ (paper) or ’S’ (scissors). Sends the
 * character to the server via the TCP protocol. Waits for a reply from the
 * Server. Prints the message received from the Server. Closes the connection.
 *
 * @author Mathias Schilling <https://github.com/pinkbigmacmedia>
 * @version 1.0
 * 
 */


import java.io.*;
import java.net.*;

class Client {

    /**
     * The host
     * 
     * @var string
     * 
     */
    private static String host = "localhost";

    /**
     * The port
     * 
     * @var integer
     */
    private static Integer port = 1337;

    /**
     * The version of the client class
     * 
     * @var double
     */
    private static Double versionNumber = 1.0;

    /**
     * A short welcome msg
     * 
     * @var string
     */
    private static String msgWelcome = "--- Welcome to Paper Scissors Stone V. "
	    + versionNumber + " --- \n";

    /**
     * The help context
     * 
     * @var string
     * 
     */
    private static String msgRules = "\nRule set:\n - (R)ock beats (S)cissors\n - (S)cissors beats (P)aper\n - (P)aper beats (R)ock\n";

    public static void main (String args[]) throws Exception {

	String input = "";
	String response;
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(
		System.in));
        System.out.println("Enter the server ip address:");
        Client.host=inFromUser.readLine();
        System.out.println("Enter the port number:");
        Client.port=Integer.parseInt(inFromUser.readLine());
	System.out.println(Client.msgWelcome);
        

	
        while(true) {
	Socket clientSocket = new Socket(Client.host, Client.port);
	ObjectOutputStream outToServer = new ObjectOutputStream(
		clientSocket.getOutputStream());
	ObjectInputStream inFromServer = new ObjectInputStream(clientSocket.getInputStream());

	    if (input.equals("-rules")) {
		System.out.println(Client.msgRules);
	    }

	    // Prompt user for select rock, paper or scissors ...
	    System.out
		    .println("Start the game by selecting (R)ock (P)aper, (S)cissors");
	    input = inFromUser.readLine();

	
	// Transmit input to the server and provide some feedback for the user
	outToServer.writeObject(input);
	System.out
		.println("\nYour input ("
			+ input
			+ ") was successfully transmitted to the server. Now wait for the result ...");

	// Catch respones

        response = (String)inFromServer.readObject();
    

	// Display respones
	System.out.println("Response from server: " + response);

	// Close socket
        System.out.println("Do you wish to continue playing? (y/n) ");
        String ch = inFromUser.readLine();
        outToServer.writeObject(ch.toUpperCase());
        response = (String)inFromServer.readObject();
        if(response.equals("EXIT")) {
            System.out.println("GAME OVER");
            break;
        }
        if(ch.equals("N")||ch.equals("n")){
	outToServer.close();
        inFromServer.close();
        clientSocket.close();
        break;
        }
        }

    }
}
