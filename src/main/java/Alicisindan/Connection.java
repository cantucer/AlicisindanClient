package Alicisindan;

import java.io.*;
import java.net.*;

/**
 * Server connection class.
 * 
 * @author cantucer2@gmail.com
 * @version 25.02.2023
 */
public class Connection implements Runnable {
    
    /**
     * Server's fixed values.
     */
    private static final String HOST = "13.48.73.251";
    private static final int PORT = 2037;
    
    /**
     * Instance variables.
     */
    private Request request;
    private Response response;
    
    
    /**
     * Constructor taking request object.
     * 
     * @param request object
     */
    public Connection(Request request) {
        this.request = request;

    }

    
    /**
     * Establishes connection to server.Creates a thread to send the request object and wait for the response object.
     * Returns the received response object.
     * 
     * @param request object
     * @return response object
     * @throws InterruptedException when the thread failed unexpectedly.
     */
    public static Response connect(Request request) throws InterruptedException  {
        Connection con = new Connection(request);
        Thread thread = new Thread(con);
        thread.start();
        thread.join();
        return con.response;
    }
    
    
    /**
     * Connects to server inside a thread.
     * Sends a request object and waits for response object.
     * Then closes the socket.
     */
    @Override
    public void run() {
        try {
            Socket socket = new Socket(HOST, PORT);
            OutputStream outputStream = socket.getOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(request);

            ObjectInputStream objectInputStream = getStream(socket.getInputStream());
            response = (Response) objectInputStream.readObject();

            socket.close();
        }
        
        catch (IOException exception) {
            System.out.println("IOException occured when trying to connect to server through default socket.");
        }
        catch (Exception exception) {
            System.out.println("Unknown exception occured when trying to connect to server through default socket.");
        }
        
    }    
    
    
    /**
     * Used to synchronize Threads.
     * 
     * @param inputStream of the socket
     * @return ObjectInputStream
     * @throws IOException when something goes as bad as possible.
     */
    private synchronized ObjectInputStream getStream(InputStream inputStream) throws IOException {
        return new ObjectInputStream(inputStream);
    }
    
}
