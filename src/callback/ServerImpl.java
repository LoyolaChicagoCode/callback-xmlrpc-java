package callback;

import org.apache.xmlrpc.WebServer;

import edu.luc.cs.cpdc.xmlrpc.Serialization;
import edu.luc.cs.cpdc.xmlrpc.XmlRpcProxy;

/**
 * A simple XML-RPC server.  
 * To run, start this server from the top-level project directory like so:
 * <pre>
 * java -classpath bin:lib/xmlrpc-1.2-b1.jar callback.ServerImpl
 * </pre>
 */

public class ServerImpl implements Server {

  public static void main(String[] args) throws Exception {
    WebServer webserver = new WebServer(PORT);
    webserver.addHandler("server", new ServerImpl());
    webserver.start();
  }

  public int setClient(final String clientUrl) throws Exception {
    System.out.println("setClient(\"" + clientUrl + "\")");
    
    // a small local wrapper object representing the remote client
    // and implementing the same interface as the remote client
    Client proxy = (Client) XmlRpcProxy.newInstance(clientUrl, "client", Client.class);
    
    // now we can invoke some of the callback methods provided by the client
    proxy.someCallback(33);
    proxy.someOtherCallback("hello");

    return 0;
  }

  public int someService(String msg) {
    System.out.println("someService(\"" + msg + "\")");
    return 0;
  }

  public int someOtherService(int value) {
    System.out.println("someOtherService(" + value + ")");
    return 2 * value;
  }
  
  public String toString(byte[] serializedObject) throws Exception {
    String result = Serialization.deserialize(serializedObject).toString(); 
    System.out.println("toString(" + result + ")");
    return result;
  }
}
