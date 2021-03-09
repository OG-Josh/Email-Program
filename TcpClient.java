import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
/**
*  TCP Client Program.
*  Receives two sentences of input from the keyboard and
*  stores them in separate variables.
*  Connects to a TCP Server.
*  Waits for a Welcome message from the server.
*  Sends the first sentence to the server.
*  Receives a response from the server and displays it.
*  Sends the second sentence to the server.
*  Receives a second response from the server and displays it.
*  Closes the socket and exits.
*  author: Josh Y
*  Email:  joyang@chapman.edu
*  Date:  2/17/2021
*  version: 3.1
*/

class TcpClient {

  public static void main(String[] argv) throws Exception {
    // Get user input
    BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
    System.out.print("Type the first sentence: ");
    final String sentence1 = inFromUser.readLine();

    System.out.print("Type the second sentence: ");
    final String sentence2 = inFromUser.readLine();
    // Finished getting user input

    // Connect to the server
    Socket clientSocket = null;

    try {
      clientSocket = new Socket("localhost", 6789);
    } catch (Exception e) {
      System.out.println("Failed to open socket connection");
      System.exit(0);
    }
    PrintWriter outToServer = new PrintWriter(clientSocket.getOutputStream(), true);
    BufferedReader inFromServer =  new BufferedReader(
        new InputStreamReader(clientSocket.getInputStream()));

    // Exchange messages with the server
    // Recive and display the Welcome Message
    String welcomeMessage = inFromServer.readLine();
    System.out.println("FROM SERVER:" + welcomeMessage);

    // Send the first sentence and display the response
    System.out.println(sentence1);
    outToServer.println(sentence1);
    String modifiedSentence = inFromServer.readLine();
    System.out.println("FROM SERVER: " + modifiedSentence);

    // Sent the second sentence and display the reponse
    System.out.println(sentence2);
    outToServer.println(sentence2);
    modifiedSentence = inFromServer.readLine();
    System.out.println("FROM SERVER: " + modifiedSentence);

    // Close the socket connection
    clientSocket.close();
  }
}
