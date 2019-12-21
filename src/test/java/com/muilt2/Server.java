package muilt2;

import socket.ServerHandler;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author chenbin
 * @ClassName Server
 * @Description TODO
 * @date 2019/12/7 17:45
 * @Vsersion
 */
public class Server {

    final static int PORT = 8765;

    public static void main(String[] args) throws Exception{

        ServerSocket server = null;
        BufferedReader in = null;
        PrintWriter out = null;
        try {

            server = new ServerSocket(PORT);
            System.out.println("server start...");
            Socket socket = null;
            HandlerExecutorPool handlerExecutorPool = new HandlerExecutorPool(50,100);
            while (true) {
                socket = server.accept();
                handlerExecutorPool.executor(new ServerHandler(socket));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        in.close();
        out.close();
        server.close();
    }
}
