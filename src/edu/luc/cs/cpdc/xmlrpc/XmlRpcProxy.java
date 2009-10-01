package edu.luc.cs.cpdc.xmlrpc;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.Vector;

import org.apache.xmlrpc.XmlRpcClient;
import org.apache.xmlrpc.XmlRpcClientLite;

/**
 * A factory for XML-RPC client proxies that implement the specified interface.
 * Proxies created using this factory hide the details of the 
 * XmlRpcClient.execute method.
 */
public class XmlRpcProxy {

  /**
   * Creates a client proxy that implements the specified interface
   * and invokes XML-RPC methods through the URL and handler name provided. 
   * @param url the URL of the XML-RPC server
   * @param handlerName the handler name
   * @param serverInterface the interface to implement
   * @return the client proxy instance
   * @throws MalformedURLException if the provided server URL is not well-formed
   */
  public static Object newInstance(String url, final String handlerName, Class serverInterface) throws MalformedURLException {
    final XmlRpcClient client = new XmlRpcClientLite(url);

    InvocationHandler handler = new InvocationHandler() {
      public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Vector params = new Vector();
        params.addAll(Arrays.asList(args));
        return client.execute(handlerName + "." + method.getName(), params);
      }
    };

    Object proxyInstance = 
      Proxy.newProxyInstance(serverInterface.getClassLoader(), new Class[] { serverInterface }, handler);

    return proxyInstance;
  }
}