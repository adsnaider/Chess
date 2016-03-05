package chessgame.login;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Inet4Address;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import chessgame.components.PieceColor;
import chessgame.controller.ChessController;
import chessgame.model.ChessModel;
import chessgame.model.ChessModelImpl;
import chessgame.network.ChessModelNetworkImpl;
import chessgame.network.NetworkServer;
import chessgame.view.ChessView;

/**
 * @author Adam Snaider
 *
 */
@SuppressWarnings("serial")
public class ViewLogIn extends JPanel implements ActionListener {

  private JFrame logInFrame;
  private JTextField IPAdress = new JTextField();
  private JButton signIn = new JButton("Sign in");
  private Label user = new Label("IP Number:");
  private JCheckBox serverPlayer = new JCheckBox("Play as server.");
  private JTextPane IPServer = new JTextPane();

  /**
   * Creates a ViewLogIn object.
   */
  public ViewLogIn() {
    IPServer.setContentType("text/html"); // let the text pane know this is what
                                          // you want
    IPServer.setEditable(false); // as before
    IPServer.setBackground(null); // this is the same as a JLabel
    IPServer.setBorder(null); // remove the border
    IPServer.setVisible(false);
    try {
      IPServer.setText("<html>Your IP: "
          + Inet4Address.getLocalHost().getHostAddress() + "</html>");
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
    initialize();
  }

  private void initialize() {
    user.setAlignment(Label.CENTER);
    GridBagLayout layout = new GridBagLayout();
    GridBagConstraints c = new GridBagConstraints();
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        logInFrame = new JFrame("Sign in");
        logInFrame.add(ViewLogIn.this);
        logInFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        logInFrame.pack();
        logInFrame.setSize(300, 90);
        logInFrame.setResizable(false);
        logInFrame.setVisible(true);
      }
    });
    setLayout(layout);
    c.weighty = 0.20;
    c.weightx = 0.6;
    c.gridwidth = 2;
    c.anchor = GridBagConstraints.NORTH;
    add(IPServer, c);
    c.gridwidth = 1;
    c.fill = GridBagConstraints.HORIZONTAL;
    c.anchor = GridBagConstraints.CENTER;
    c.gridx = 0;
    c.gridy = 0;
    add(user, c);
    c.gridx = 1;
    c.gridy = 0;
    add(IPAdress, c);
    c.gridx = 0;
    c.gridy = 1;
    add(signIn, c);
    c.gridx = 1;
    c.gridy = 1;
    add(serverPlayer, c);
    signIn.addActionListener(this);
    checkCheckBox();
  }

  @Override
  public void actionPerformed(ActionEvent arg0) {
    PieceColor color;
    final ChessModel model;
    if (serverPlayer.isSelected()) {
      color = ((int) (Math.random() * 2)) == 0 ? PieceColor.WHITE
          : PieceColor.BLACK;
      model = new ChessModelImpl(color);
      final NetworkServer server = new NetworkServer(8888, model);
      ((ChessModelImpl) model).setServer(server);
      Thread serverThread = new Thread(new Runnable() {
        @Override
        public void run() {
          server.acceptConnection();
        }
      });
      serverThread.start();
    } else {
      model = new ChessModelNetworkImpl(IPAdress.getText(), 8888);
      color = model.getMyColor();
    }
    ChessController controller = new ChessController(model);
    ChessView view = new ChessView(controller, color);
    controller.setView(view);
    model.setView(view);
    view.initialize();
    logInFrame.setVisible(false);
  }

  public void checkCheckBox() {
    ChangeListener changeListener = new ChangeListener() {
      public void stateChanged(ChangeEvent changeEvent) {
        if (serverPlayer.isSelected()) {
          user.setVisible(false);
          IPAdress.setVisible(false);
          IPServer.setVisible(true);
        } else {
          user.setVisible(true);
          IPAdress.setVisible(true);
          IPServer.setVisible(false);
        }
      }
    };
    serverPlayer.addChangeListener(changeListener);
  }
}
