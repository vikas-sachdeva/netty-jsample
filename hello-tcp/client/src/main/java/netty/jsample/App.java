package netty.jsample;

import netty.jsample.client.TcpClient;

public class App 
{
    public static void main( String[] args ) throws InterruptedException
    {
    	new TcpClient().createClient();
    }
}
