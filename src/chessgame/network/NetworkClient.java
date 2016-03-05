package chessgame.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

import chessgame.network.commands.ChessCommand;
import chessgame.network.commands.NetworkAction;

/**
 * @author Adam Snaider
 *
 */
public class NetworkClient {

  private static final Logger logger = Logger.getLogger(NetworkClient.class
      .getName());
  private String host;
  private int port;
  private Socket socket;
  private InputStream is;
  private ObjectInputStream ois;
  private OutputStream os;
  private ObjectOutputStream oos;
  private ChessCommand command;
  private ChessModelNetworkImpl model;

  public NetworkClient(String host, int port, ChessModelNetworkImpl model) {
    this.host = host;
    this.port = port;
    this.model = model;
    connect();
  }

  /**
   * Connects the client to the server.
   */
  public void connect() {
    try {
      socket = new Socket(host, port);
      is = socket.getInputStream();
      os = socket.getOutputStream();
      oos = new ObjectOutputStream(os);
      ois = new ObjectInputStream(is);
      logger.log(Level.INFO, "Client is connected to the host.");
    } catch (UnknownHostException e) {
      logger.log(Level.WARNING, "Could not locate the specified host.", e);
    } catch (IOException e) {
      logger.log(Level.SEVERE, "Connection error.", e);
    }
    Thread recieveThread = new Thread(new Runnable() {
      @Override
      public void run() {
        while (true) {
          Object obj = null;
          try {
            obj = ois.readObject();
            logger.log(Level.INFO, "Object recieved: " + obj);
          } catch (ClassNotFoundException | IOException e) {
            logger.log(Level.WARNING, "Couldn't recieve the message.", e);
          }
          if (obj instanceof ChessCommand) {
            synchronized (NetworkClient.this) {
              if (command == null) {
                command = (ChessCommand) obj;
              } else {
                logger.log(Level.SEVERE, "Bug, command was recieved while "
                    + "another command wasn't yet read. Not read command: "
                    + command + " New command: " + obj);
              }
              NetworkClient.this.notify();
            }
          } else if (obj instanceof NetworkAction) {
            processNetworkAction((NetworkAction) obj);
          }
        }
      }
    });
    recieveThread.start();
  }

  private void processNetworkAction(final NetworkAction cmd) {
    Thread processThread = new Thread(new Runnable() {
      public void run() {
        switch (cmd) {
        case CHANGE_TURN:
          model.changeTurn();
          break;
        case REFRESH:
          model.refreshView();
        default:
          break;
        }
      }
    });
    processThread.start();
  }

  /**
   * Sends an object to the server.
   * 
   * @param obj
   *          object which will be sent.
   */
  public void send(Object obj) {
    try {
      oos.writeObject(obj);
      logger.log(Level.INFO, "Object sent: " + obj);
    } catch (IOException e) {
      logger.log(Level.WARNING, "Couldn't send the message.", e);
    }
  }

  public synchronized ChessCommand recieve() {
    if (command == null) {
      try {
        this.wait();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    ChessCommand result = command;
    command = null;
    return result;
  }
}
