package chessgame.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import chessgame.model.ChessModel;
import chessgame.network.commands.ChessCommand;
import chessgame.network.commands.NetworkAction;

/**
 * @author Adam Snaider
 *
 */
public class NetworkServer {
  private static final Logger logger = Logger.getLogger(NetworkServer.class
      .getName());
  private ServerSocket serverSocket;
  private ChessModel model;
  private ObjectOutputStream oos;

  /**
   * Creates a new server.
   * 
   * @param port
   *          integer that will represent the port open by the server.
   * @param model
   *          model where the game is taking place.
   */
  public NetworkServer(int port, ChessModel model) {
    this.model = model;
    try {
      serverSocket = new ServerSocket(port);
    } catch (IOException e) {
      logger.log(Level.SEVERE, "Cannot create server with port: " + port + ".",
          e);
    }
  }

  /**
   * @param action
   */
  public synchronized void sendNetworkCommand(NetworkAction action) {
    if (oos != null) {
      try {
        oos.writeObject(action);
        logger.log(Level.INFO, "Sending NetworkAction: " + action + ".");
      } catch (IOException e) {
        logger.log(Level.SEVERE, "Error sending action: " + action + ".", e);
      }
    } else {
      logger.log(Level.WARNING,
          "Socket not connected. NetworkAction was not sent.");
    }
  }

  /**
   * Gets a connection sent by a client.
   */
  public void acceptConnection() {
    Socket socket;
    while (true) {
      try {
        socket = serverSocket.accept();
        logger.log(Level.INFO, "Connection established.");
        service(socket);
      } catch (IOException e) {
        logger.log(Level.WARNING, "Could not connect to the client.", e);
      }
    }
  }

  /**
   * Exchanges data with the client.
   * 
   * @param socket
   *          Socket where the connection between the server and the client was
   *          made.
   */
  private void service(Socket socket) {
    InputStream is = null;
    ObjectInputStream ois = null;
    OutputStream os = null;
    oos = null;
    try {
      is = socket.getInputStream();
      ois = new ObjectInputStream(is);
      os = socket.getOutputStream();
      oos = new ObjectOutputStream(os);
      while (true) {
        ChessCommand cmd = (ChessCommand) ois.readObject();
        logger.log(Level.INFO, "Command: " + cmd);
        if (cmd.getNetworkAction() == NetworkAction.END_CONNECTION) {
          break;
        }
        cmd.execute(model);
        synchronized (this) {
          oos.writeObject(cmd);
        }
      }
    } catch (IOException e) {
      logger.log(Level.WARNING, "Communication error.", e);
    } catch (ClassNotFoundException e) {
      logger.log(Level.WARNING, "Internal error.", e);
    } finally {
      try {
        if (ois != null) {
          ois.close();
        }
        if (oos != null) {
          oos.close();
        }
        if (socket != null) {
          socket.close();
        }
      } catch (IOException e) {
        logger.log(Level.WARNING,
            "Cannot close the socket correctly, aborting!", e);
      }
    }
  }
}
