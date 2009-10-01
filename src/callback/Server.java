package callback;

/**
 * An XML-RPC server interface.
 */
public interface Server {
  /**
   * The port number where all clients expect to find the server.
   */
  int PORT = 10800;
  /**
   * Used by clients to register with this server.
   */
  int setClient(String clientUrl) throws Exception;
  /**
   * Used by clients to invoke some functionality.
   */
  int someService(String msg) throws Exception;
  /**
   * Used by clients to invoke some other functionality.
   */
  int someOtherService(int value) throws Exception;
  /**
   * Used by clients to invoke yet some other functionality.
   */
  String toString(byte[] serializedObject) throws Exception;
}