package netty.jsample;

import netty.jsample.server.TcpServer;

public class App 
{
    public static void main( String[] args ) throws InterruptedException
    {
    	new TcpServer().createServer();
    }
}
