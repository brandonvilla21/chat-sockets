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
        do {
          in = new BufferedReader(new InputStreamReader(skCliente.getInputStream()));
          out = new PrintWriter(skCliente.getOutputStream(), true);
          System.out.println(in.readLine());
          System.out.println(in.readLine());
          System.out.println(in.readLine());

          Thread.sleep(200);
          text = sc.nextLine();
          // System.out.print("Texto: ");
          // message = sc.nextLine();
          out.println(text);
          // out.println(message);
          out.flush();
          System.out.println(in.readLine());
          System.out.println(in.readLine());

          System.out.print("Texto: ");
          String m2 = sc.nextLine();
          out.println(m2);
          
          System.out.println(in.readLine());

      
        } while ( !text.equals("bye") );
        skCliente.close(); 
    } catch( Exception e ) { 
      System.out.println( e.getMessage() ); 
    } 
  } 
  public static void main( String[] args ) { 
    new Cliente(); 
  }
} 