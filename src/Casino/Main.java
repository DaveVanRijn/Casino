/*
 * Casino
 * Roulette
 * Blackjack
 * Taal = Nederlands
 */
package Casino;

import static Blackjack.PlaceBet.SIZE_FACTOR;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.FontUIResource;

/**
 *
 * @author Dave van Rijn, Student 500714558, Klas IS-202
 */
public class Main extends javax.swing.JFrame {

    private static Main mainframe;
    private final ArrayList<JPanel> panels;
    private static JFrame popup;
    private static ArrayList<Player> players = new ArrayList<>();
    private final File data = new File("data.dat");
    ObjectInputStream in;
    ObjectOutputStream out;
    private final Font standardFont = new Font("Tahoma", Font.PLAIN, 16);
    private static String rememberName = null;
    private static String rememberPass = null;
    private static EncryptionKey key;
    private static double SIZE_FACTOR;

    /**
     * Creates new form Main
     */
    public Main() {
        setUndecorated(true);
        key = new EncryptionKey();
        setSizeFactor();
        UIManager.put("OptionPane.messageFont", new FontUIResource(standardFont));
        UIManager.put("OptionPane.buttonFont", new FontUIResource(standardFont));
        initComponents();
        setIconImage(new ImageIcon(getClass().getResource("/Img/windowIcon.png")).getImage());
        try {
            //Initialize the data
            if (!data.createNewFile()) {
                read();
            } else {
                players = new ArrayList<>();
                rememberName = null;
                rememberPass = null;
                write();
            }
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        panels = new ArrayList<>();
        JPanel panel = new Login();
        panels.add(panel);
        pnlMain.setLayout(new BorderLayout());
        pnlMain.add(panel, BorderLayout.CENTER);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                write();
            }
        });
        setTitle("Casino");
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Display a new Panel
     *
     * @param o The instance of the panel that is to be displayed
     */
    public static void setPanel(Object o) {
        JPanel panel = (JPanel) o;
        mainframe.pnlMain.removeAll();
        mainframe.panels.add(panel);
        mainframe.pnlMain.add(panel);
        mainframe.pack();
        mainframe.setLocationRelativeTo(null);
    }

    /**
     * Display the previous displayed panel
     */
    public static void setLastPanel() {
        mainframe.pnlMain.removeAll();
        mainframe.panels.remove(mainframe.panels.size() - 1);
        JPanel panel = mainframe.panels.get(mainframe.panels.size() - 1);
        mainframe.panels.remove(mainframe.panels.size() - 1);
        setPanel(panel);
        mainframe.pnlMain.repaint();
    }

    /**
     * Display an other JFrame with the specified JPanel instance
     *
     * @param o The instance of the panel that is to be displayed
     */
    public static void setPopup(Object o) {
        popup = new JFrame();
        popup.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        popup.removeAll();
        JPanel panel = (JPanel) o;
        popup.add(panel);
        popup.pack();
        popup.setLocationRelativeTo(null);
    }

    /**
     * Dispose the popup frame
     */
    public static void closePopup() {
        if (popup.isActive()) {
            popup.dispose();
        }
    }

    /**
     * Get a list of the players of this game
     *
     * @return The list of players of this game
     */
    public static ArrayList<Player> getPlayers() {
        return mainframe.players;
    }

    /**
     * Add a player to the players list
     *
     * @param p The player
     */
    public static void addPlayer(Player p) {
        mainframe.players.add(p);
    }

    /**
     * Remove a player from the game
     *
     * @param p The player
     */
    public static void removePlayer(Player p) {
        mainframe.players.remove(p);
    }

    public static void changeTitle(String title) {
        mainframe.setTitle(title);
    }

    public static void rememberPlayer(Player player) {
        rememberName = player.getUsername();
        rememberPass = player.getPassword();
    }

    public static String[] getRemebered() {
        if (checkRemember()) {
            return new String[]{rememberName, rememberPass};
        }
        return null;
    }

    public static void clearRemember() {
        if (checkRemember()) {
            rememberName = null;
            rememberPass = null;
        }
    }

    public static String encrypt(String s) {
        try {
            return key.encrypt(s);
        } catch (CharNotSupportedException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static String decrypt(String encryption) {
        try {
            return key.decrypt(encryption);
        } catch (CharNotSupportedException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static String isEncryptSupported(String s) {
        return key.isSupported(s);
    }

    private static void setSizeFactor() {
        Rectangle screenSpace = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        Dimension currentScreenSize = new Dimension((int) screenSpace.getWidth(), (int) screenSpace.getHeight());
        Dimension standardScreenSize = new Dimension(1366, 728);

        SIZE_FACTOR = Math.min((double) currentScreenSize.width
                / standardScreenSize.width, (double) currentScreenSize.height
                / standardScreenSize.height);
        System.out.println(SIZE_FACTOR);
    }

    public static BufferedImage getImage(String name) {
        Image image = new ImageIcon(Main.class.getResource("/Img/" + name + ".png")).getImage();
        int width;
        int height;

        if (name.equals("backgroundBlackjack")) {
            Rectangle screenSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
            width = (int) screenSize.getWidth();
            height = (int) screenSize.getHeight();
        } else {
            width = (int) (image.getWidth(null) * SIZE_FACTOR);
            height = (int) (image.getHeight(null) * SIZE_FACTOR);
        }

        BufferedImage buff = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = buff.createGraphics();
        g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY));
        boolean done = g2d.drawImage(image, 0, 0, width, height, null);
        return buff;
    }

    public static int convertSize(int original) {
        return (int) (original * SIZE_FACTOR);
    }

    public static void exit() {
        int option = JOptionPane.showOptionDialog(null,
                "Are you sure you want to exit?", "Confirm",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                null, "No");
        if (option == JOptionPane.YES_OPTION) {
            mainframe.dispatchEvent(new WindowEvent(mainframe, WindowEvent.WINDOW_CLOSING));
        }
    }

    private static boolean checkRemember() {
        return (rememberName != null && rememberPass != null);
    }

    private static void read() {
        ObjectInputStream input = null;
        try {
            input = new ObjectInputStream(new FileInputStream(new File("data.dat")));
            players = (ArrayList<Player>) input.readObject();
            String remember = (String) input.readObject();
            if (!remember.equals("empty")) {
                rememberName = decrypt(remember.split(":")[0]);
                rememberPass = decrypt(remember.split(":")[1]);
            }
        } catch (IOException | ClassNotFoundException ex) {
            players = new ArrayList<>();
            rememberName = null;
            rememberPass = null;
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    private static void write() {
        ObjectOutputStream output = null;
        try {
            output = new ObjectOutputStream(new FileOutputStream(new File("data.dat")));
            String remember = "empty";
            if (rememberName != null && rememberPass != null) {
                remember = encrypt(rememberName) + ":" + encrypt(rememberPass);
            }
            output.writeObject(players);
            output.writeObject(remember);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlMain = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout pnlMainLayout = new javax.swing.GroupLayout(pnlMain);
        pnlMain.setLayout(pnlMainLayout);
        pnlMainLayout.setHorizontalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        pnlMainLayout.setVerticalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }

        EventQueue.invokeLater(() -> {
            mainframe = new Main();
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel pnlMain;
    // End of variables declaration//GEN-END:variables
}
