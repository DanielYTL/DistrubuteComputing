import java.net.*;
import java.io.*;

public class FibReader {

    static Socket appSoc;
    static BufferedReader in;
    static String message;
    
    public static void main(String argv[]) {
        try {
            appSoc = new Socket("localhost",2001);
            in = new BufferedReader(new
              InputStreamReader(appSoc.getInputStream()));
            for (int i = 0; i < 100; i++) {
                message = in.readLine();
                System.out.println(message);
            }

        }
        catch (Exception e) {
            System.out.println("Died... " +
            e.toString());
        }

    }
}
