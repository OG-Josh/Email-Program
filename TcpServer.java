import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
*  TCP Server Program.
*  Listens on a TCP port.
*  Receives a line of input from a TCP client.
*  Returns an upper case version of the line to the client.
*  Receives a second line of input from a TCP client.
*  Returns an upper case version of the line to the client.
*  Closes the socket connection.
*  author: Josh Y
*  Email:  joyang@chapman.edu
*  Date:  2/17/2020
*  version: 2.0
*/

class TcpServer {

  public static void main(String[] argv) throws Exception {
    String welcomeMessage = "Welcome";
    String clientSentence;
    String capitalizedSentence;

    ServerSocket welcomeSocket = null;

    try {
      welcomeSocket = new ServerSocket(6789);
    } catch (Exception e) {
      System.out.println("Failed to open socket connection");
      System.exit(0);
    }


    while (true) {
      System.out.println("Waiting for a client to connect.");
      Socket connectionSocket = welcomeSocket.accept();

      BufferedReader inFromClient = new BufferedReader(
          new InputStreamReader(connectionSocket.getInputStream()));
      PrintWriter outToClient = new PrintWriter(connectionSocket.getOutputStream(), true);

      outToClient.println(welcomeMessage);

      clientSentence = inFromClient.readLine();
      System.out.println(clientSentence);
      capitalizedSentence = clientSentence.toUpperCase();
      System.out.println(capitalizedSentence);
      outToClient.println(capitalizedSentence);

      clientSentence = inFromClient.readLine();
      System.out.println(clientSentence);
      capitalizedSentence = clientSentence.toUpperCase();
      System.out.println(capitalizedSentence);
      outToClient.println(capitalizedSentence);

      connectionSocket.close();
    }
  }
}
