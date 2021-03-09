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
*  author: Joshua Yang
*  Email:  joyang@chapman.edu
*  Date:  2/22/2021
*  version: 3.1
*/

class Email {

  public static void main(String[] argv) throws Exception {

    //Retriving user's input for email cover
    BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

    System.out.println("SENDER EMAIL: ");
    final String sender = userInput.readLine();

    System.out.println("RCPT EMAIL: ");
    final String recipient = userInput.readLine();
    
    System.out.println("RCPT NAME: ");
    final String from = userInput.readLine();

    System.out.println("SENDER NAME: ");
    final String to = userInput.readLine();

    System.out.println("EMAIL SUBJECT: ");
    final String emailSubject = userInput.readLine();

    //Initialization data
    String data = "";           //Stores data/content of email
    String closeFlag;       //Closing Flag
    //boolean flag = false;       //Flag to stop email's body input

    //Email Body Contents
    /*
    do
    {
      String userContent = userInput.readLine();      
      if(userContent == ".")
      {
        flag = true;
      }
      data += userContent + "\n"; //Loads user's entered data into content holder 
    
    }while(flag != true);
    */
    while (true) {
      String userContent = userInput.readLine();
      if (userContent.equals(".")) {
        closeFlag = userContent;  
        break;
      }
      data += userContent + "\n"; //Loads user's entered data into content holder 
    }
    //User input process completed

    //Establish link to host
    //INSERT SOMETHING HERE
    Socket clientSocket = null;
    try {
      clientSocket = new Socket("smtp.chapman.edu", 25);
    } catch (Exception e) {
      System.out.println("Failed to open socket connection");
      System.exit(0);
    }
    PrintWriter outToServer = new PrintWriter(clientSocket.getOutputStream(), true);
    BufferedReader inFromServer = new BufferedReader(
        new InputStreamReader(clientSocket.getInputStream()));

    //220 edric.chapman.edu ESMTP Postfix
    String welcomeMessage = inFromServer.readLine();
    System.out.println("FROM SERVER:" + welcomeMessage);

    //Exchange & Receive message w/ server
    //220 edric.chapman.edu Step
    //String inputConnect = userInput.readLine();
    //System.out.println(inputConnect);

    //Connect to ICD server 
    //HELO icd Step 
    //String inConnect2 = userInput.readLine();
    String inConnect2 = "HELO icd.chapman.edu";
    System.out.println(inConnect2);
    outToServer.println(inConnect2);
    String serverMsg = inFromServer.readLine();
    System.out.println("Server: " + serverMsg);

    //250 edric.chapman.edu
    System.out.println("FROM: " + sender);
    outToServer.println("MAIL FROM: " + sender);
    //Send mail to edric.chapman.edu
    String serverMsg2 = inFromServer.readLine(); 
    System.out.println("Server: " + serverMsg2);
    
    //250 2.1.0 Ok
    System.out.println("TO: " + recipient);
    outToServer.println("RCPT TO: " + recipient);
    //Check 250 response
    String serverMsg3 = inFromServer.readLine();
    System.out.println("Server: " + serverMsg3);

    //250 2.1.0 Ok
    String inConnect3 = "DATA";
    //System.out.println(inConnect3);
    outToServer.println(inConnect3);
    //Confirm data with host
    String serverMsg4 = inFromServer.readLine();
    System.out.println("Server: " + serverMsg4);

    //Sender Info
    System.out.println("FROM: " + from);
    outToServer.println("FROM: " + from);

    //Receiver Info
    System.out.println("TO: " + to);
    outToServer.println("TO: " + to);
    
    //Email Subject
    System.out.println("SUBJECT: " + emailSubject);
    outToServer.println("SUBJECT: " + emailSubject);

    //Email Contents
    System.out.println(data);
    outToServer.println(data);

    //Closing State
    System.out.println(closeFlag);
    outToServer.println(closeFlag);
    String serverMsg5 = inFromServer.readLine();
    System.out.println("FROM SERVER: " + serverMsg5);
    outToServer.println("QUIT");
    String serverMsg6 = inFromServer.readLine();
    System.out.println("FROM SERVER: " + serverMsg6);

    // Close the socket connection
    clientSocket.close();
  }
}
