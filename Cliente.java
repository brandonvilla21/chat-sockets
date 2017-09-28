import java.io.*; 
import java.net.*; 
import java.util.*; 

class Cliente { 
    static final String HOST = "localhost";//"localhost"; 
    static final int PUERTO=8190;
    private BufferedReader in;
    private PrintWriter out;
   
  public Cliente( ) { 
    try {
        Scanner sc = new Scanner(System.in);
        String text="";
        String message;
        Socket skCliente;
        skCliente = new Socket( HOST , PUERTO );
        in = new BufferedReader(new InputStreamReader(skCliente.getInputStream()));
        out = new PrintWriter(skCliente.getOutputStream(), true);
        
        receive(in, skCliente);
        send(out, sc);

  
    } catch( Exception e ) { 
      System.out.println( e.getMessage() ); 
    } 
  }

  public static void send( PrintWriter out, Scanner sc ) throws Exception {
    new Thread(new Runnable (){
      public void run() {
        System.out.println("send run()");
        while(true) {
          String text = sc.nextLine();
          if (text.equals("BYE"))
            break;
          out.println(text);
          out.flush();
        }
      }
    }).start();
  } 

  public static void receive( BufferedReader in, Socket skCliente ) throws Exception {
    new Thread( new Runnable() {

      public void run() {
        try {
          System.out.println("receive run()");
          while(true)
            System.out.println(in.readLine());
        
        } catch( Exception e) {
          e.printStackTrace();
        }
                  
      }
    }).start();
  }
  public static void main( String[] args ) { 
    new Cliente(); 
  }
} 