package callback;

/**
 * An XML-RPC client interface.   
 */
public interface Client {
  /**
   * Used to check whether a client is already running on this host using a specific port.
   * @return 0
   */
  int probe() throws Exception;
  /**
   * Used by the server to invoke callback functionality provided by the client.
   */
  int someCallback(int arg) throws Exception;
  /**
   * Used by the server to invoke some other callback functionality provided by the client.
   */
  int someOtherCallback(String arg) throws Exception;
}