package edu.luc.cs.cpdc.xmlrpc;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * This class provides static methods for explicit serialization 
 * and deserialization between objects and byte arrays.
 */
public class Serialization {
  
  /**
   * Serializes an object to a byte array.
   * @param object the object to serialize
   * @return the resulting byte array
   * @throws IOException
   */
  public static byte[] serialize(Serializable object) throws IOException {
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    ObjectOutputStream oos = new ObjectOutputStream(bos);
    oos.writeObject(object);
    oos.close();
    return bos.toByteArray();
  }

  /**
   * Deserializes an object from a byte array.
   * @param serialized the byte array containing the serialized object
   * @return the resulting Java object
   * @throws IOException
   * @throws ClassNotFoundException
   */
  public static Object deserialize(byte[] serialized) throws IOException, ClassNotFoundException {
    // the machinery for restoring a serialized object from a byte array
    ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(serialized));
    Object javaObject = ois.readObject();
    ois.close();
    return javaObject; 
  }

}
