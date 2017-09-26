import java.io.*;
import java.net.*;
import java.util.*;
public class ChatServer { 
    private static LinkedList<Socket> clientList = new LinkedList<Socket>(); 
    
    public static void main(String[] args) throws IOException {
        // Get the port and create a socket there.
        ServerSocket listener = new ServerSocket(8190);
        // Listen for clients. Start a new handler for each.
        // Add each client to the list.
        while (true) {
            Socket client = listener.accept();
            new ChatHandler(client).start();
            System.out.println("New client on client's port " +client.getPort());
            clientList.add(client);
        }
    }

    static synchronized void broadcast(String message, String name) throws IOException {
        // Sends the message to every client including the sender.
        Socket s;
        PrintWriter p;
        for (int i = 0; i < clientList.size(); i++){
            s = clientList.get(i);
            System.out.println(name + ": "  + message);
            p = new PrintWriter(s.getOutputStream(), true);
            p.println(name + ": "  + message);
            p.flush();
            
        }
        
    }
    static synchronized void remove(Socket s) {
        clientList.remove(s);
    }
} 

class ChatHandler extends Thread {
    /* 
    * The Chat Handler class is called from the Chat Server:
    * one thread for each client coming in to chat.
    */
    private BufferedReader in;
    private PrintWriter out;
    private Socket toClient;
    private String name;

    ChatHandler(Socket s) {
        toClient = s;
    }

    public void run() {
        try {
            // Welcome new client to the Chat Room.
            in = new BufferedReader(new InputStreamReader(toClient.getInputStream()));
            out = new PrintWriter(toClient.getOutputStream(), true);
            out.println("*** Welcome to the Chatter ***\n Type BYE to end\nWhat is your name?: \n");
            out.flush();
            String name ="nada";
            name = in.readLine();
            System.out.println(name);
            ChatServer.broadcast(name + " has joined the discussion.", "Chatter"); // Read lines and send them off for broadcasting.
            while (true) {
                String s = in.readLine();
                if (s.startsWith("BYE")) {
                    ChatServer.broadcast(name +
                    " has left the discussion.",
                    "Chatter");
                    break;
                }
                ChatServer.broadcast(s, name);
            }
            ChatServer.remove(toClient);
            toClient.close();
        } catch (Exception e) {
               e.printStackTrace();
        }
    }
}