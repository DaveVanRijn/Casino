/*
 * Casino
 * Roulette
 * Blackjack
 * Taal = Nederlands
 */
package views.shared;

import resources.java.shared.EncryptionKey;
import exception.shared.CharNotSupportedException;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    private final ArrayList<JPanel> PANELS;
    private final Font STANDARD_FONT = new Font("Tahoma", Font.PLAIN, 16);

    private static Main mainframe;
    private static JFrame popup;
    private static EncryptionKey key;
    private static double SIZE_FACTOR;

    /**
     * Creates new form Main
     */
    public Main() throws IOException {
        setUndecorated(true);
        key = new EncryptionKey();
        setSizeFactor();
        UIManager.put("OptionPane.messageFont", new FontUIResource(STANDARD_FONT));
        UIManager.put("OptionPane.buttonFont", new FontUIResource(STANDARD_FONT));

        initComponents();
        setIconImage(resources.java.shared.ImageLabel.getImageIcon("windowIcon").getImage());
        JPanel panel = new Login();
        PANELS = new ArrayList<>();
        PANELS.add(panel);
        pnlMain.setLayout(new BorderLayout());
        pnlMain.add(panel, BorderLayout.CENTER);
        setTitle("Casino");
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        String username = System.getProperty("user.name");
        if (username.contains("Charlotte") || username.contains("charlotte")) {
            CharlottePanel charlottePanel = new CharlottePanel();
        }
    }

    /**
     * Display a new Panel
     *
     * @param o The instance of the panel that is to be displayed
     */
    public static void setPanel(Object o) {
        JPanel panel = (JPanel) o;
        mainframe.pnlMain.removeAll();
        mainframe.PANELS.add(panel);
        mainframe.pnlMain.add(panel);
        mainframe.pack();
        mainframe.setLocationRelativeTo(null);
    }

    /**
     * Display the previous displayed panel
     */
    public static void setLastPanel() {
        mainframe.pnlMain.removeAll();

        mainframe.PANELS.remove(mainframe.PANELS.size() - 1);
        JPanel panel = mainframe.PANELS.get(mainframe.PANELS.size() - 1);
        mainframe.PANELS.remove(mainframe.PANELS.size() - 1);
        setPanel(panel);
        mainframe.pnlMain.repaint();
    }

    /**
     * Display an other JFrame with the specified JPanel instance
     *
     * @param o The instance of the panel that is to be displayed
     */
    public static void setPopup(Object o) {
        JPanel panel = (JPanel) o;
        
        popup = new JFrame();
        popup.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        popup.removeAll();
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

    public static void changeTitle(String title) {
        mainframe.setTitle(title);
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
    }

    public static int convertSize(int original) {
        return (int) (original * SIZE_FACTOR);
    }

    public static double getSizeFactor() {
        return SIZE_FACTOR;
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
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);

        }

        EventQueue.invokeLater(() -> {
            try {
                mainframe = new Main();
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel pnlMain;
    // End of variables declaration//GEN-END:variables
}
