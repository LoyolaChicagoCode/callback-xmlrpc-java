package callback;

import java.net.InetAddress;
import java.util.EventObject;
import java.util.Vector;

import org.apache.xmlrpc.WebServer;
import org.apache.xmlrpc.XmlRpcClientLite;

import edu.luc.cs.cpdc.xmlrpc.Serialization;
import edu.luc.cs.cpdc.xmlrpc.XmlRpcProxy;

/**
 * A simple XML-RPC client.  
 * To run, first start the server as described in its javadoc.
 * Then run instances of this client from the top-level project directory like so:
 * <pre>
 * java -classpath bin:lib/xmlrpc-1.2-b1.jar callback.ClientImpl [ serverhost ]
 * </pre>
 */

public class ClientImpl implements Client {

  public static final String DEFAULT_SERVER_HOST = "localhost";
  
  private int port;
  
  /**
   * Runs an instance of the simple XML-RPC client.
   * It finds an available port on which it listens for incoming callback invocations.   
   */
  public static void main(String[] args) throws Exception {
    if (args.length > 1) {
      System.out.println("usage: callback.ClientImpl [ serverhost ]");
      System.exit(1);
    }
    String serverUrl = "http://" + (args.length == 0 ? DEFAULT_SERVER_HOST : args[0]) + ":" + Server.PORT + "/";

    int port = findAvailablePort();
    System.out.println("using port " + port);
    // create a client implementation associated with the port found
    ClientImpl client = new ClientImpl(port);
    // listen on this port for callback invocations coming from the server
    WebServer webserver = new WebServer(port);
    webserver.addHandler("client", client);
    webserver.start();

    // have the client invoke some methods on the server
    client.doStuff(serverUrl);
//    System.exit(0);
  }
  
  /**
   * Finds an available local port starting with the default server port.
   * @return the available port
   */
  public static int findAvailablePort() {
    int port = Server.PORT + 1;
    Vector empty = new Vector();
    try {
      while (true) {
        String url = "http://" + InetAddress.getLocalHost().getHostAddress() + ":" + port + "/";
        // invoke the probe method
        new XmlRpcClientLite(url).execute("client.probe", empty);
        // if there is no exception, another client is already using this port
        port ++;
      }
    } catch (Exception e) { }
    return port;
  }
  
  public ClientImpl(int port) {
    this.port = port;
  }
  
  /**
   * Invokes various methods on the server.
   * @param serverUrl
   * @throws Exception
   */
  public void doStuff(final String serverUrl) throws Exception {
    String clientUrl = "http://" + InetAddress.getLocalHost().getHostAddress() + ":" + port + "/";

    // a small local wrapper object representing the remote server
    // and implementing the same interface as the remote server
    Server proxy = (Server) XmlRpcProxy.newInstance(serverUrl, "server", Server.class);
    
    // now we can invoke some of the server methods via the proxy
    proxy.setClient(clientUrl);
    proxy.someService("test");
    proxy.someOtherService(77);
    // we are using an EventObject as an example
    System.out.println(proxy.toString(Serialization.serialize(new EventObject(this))));
  }
  
  public int probe() {  
    System.out.println("probe()");
    return 0;
  }

  public int someCallback(int arg) {
    System.out.println("someCallback(" + arg + ")");
    return 0;
  }

  public int someOtherCallback(String arg) {
    System.out.println("someOtherCallback(\"" + arg + "\")");
    return 0;
  }
}