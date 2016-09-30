/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views.shared;

import resources.java.shared.Database;
import static resources.java.shared.ImageLabel.getButton;
import static resources.java.shared.ImageLabel.getImageIcon;
import views.blackjack.PlaceBet;
import static views.shared.Main.convertSize;
import views.roulette.Betting;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;

/**
 *
 * @author Dave van Rijn, Student 500714558, Klas IS202
 */
public class Startpage extends javax.swing.JPanel {

    /**
     * Creates new form Startpage
     */
    public Startpage() throws IOException {
        initComps();
    }

    private void initComps() throws IOException {
        Database DB = new Database();
        initComponents();
        JButton btnRoulette = getButton("btnRoulette");
        JButton btnBlackjack = getButton("btnBlackjack");
        JButton btnLogout = getButton("btnLogout");
        JButton btnPlayer = getButton("btnPlayer");

        btnRoulette.setBounds(convertSize(262), convertSize(180), convertSize(275), convertSize(110));
        btnRoulette.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnBlackjack.setBounds(convertSize(262), convertSize(320), convertSize(275), convertSize(110));
        btnBlackjack.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnLogout.setBounds(convertSize(635), convertSize(20), convertSize(124), convertSize(40));
        btnLogout.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnPlayer.setBounds(convertSize(491), convertSize(20), convertSize(124), convertSize(40));

        btnRoulette.addMouseListener(new MouseAdapter() {
            private boolean pressed = false;
            private boolean entered = false;

            @Override
            public void mouseEntered(MouseEvent e) {
                entered = true;
            }

            @Override
            public void mouseExited(MouseEvent e) {
                entered = false;
            }

            @Override
            public void mousePressed(MouseEvent e) {
                pressed = true;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (pressed && entered) {
                    pressed = false;
                    try {
                        Main.setPanel(new Betting());
                    } catch (IOException | NullPointerException ex) {
                        Logger.getLogger(Startpage.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });

        btnBlackjack.addMouseListener(new MouseAdapter() {
            private boolean pressed = false;
            private boolean entered = false;

            @Override
            public void mouseEntered(MouseEvent e) {
                entered = true;
            }

            @Override
            public void mouseExited(MouseEvent e) {
                entered = false;
            }

            @Override
            public void mousePressed(MouseEvent e) {
                pressed = true;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (pressed && entered) {
                    pressed = false;
                    try {
                        Main.setPanel(new PlaceBet());
                    } catch (IOException ex) {
                        Logger.getLogger(Startpage.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });

        btnLogout.addMouseListener(new MouseAdapter() {
            private boolean pressed = false;
            private boolean entered = false;

            @Override
            public void mouseEntered(MouseEvent e) {
                entered = true;
            }

            @Override
            public void mouseExited(MouseEvent e) {
                entered = false;
            }

            @Override
            public void mousePressed(MouseEvent e) {
                pressed = true;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (pressed && entered) {
                    pressed = false;
                    try {
                        DB.putCurrentPlayer(null);
                        Main.setPanel(new Login());
                    } catch (IOException ex) {
                        Logger.getLogger(Startpage.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });

        btnPlayer.addMouseListener(new MouseAdapter() {
            private boolean pressed = false;
            private boolean entered = false;

            @Override
            public void mouseEntered(MouseEvent e) {
                entered = true;
            }

            @Override
            public void mouseExited(MouseEvent e) {
                entered = false;
            }

            @Override
            public void mousePressed(MouseEvent e) {
                pressed = true;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (pressed && entered) {
                    pressed = false;
                    try {
                        Main.setPanel(new PlayerPage());
                    } catch (IOException ex) {
                        Logger.getLogger(Startpage.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });

        lblBackground.setIcon(getImageIcon("backgroundStartpage"));

        layer.add(btnRoulette);
        layer.add(btnBlackjack);
        layer.add(btnLogout);
        layer.add(btnPlayer);

        layer.moveToFront(lblBackground);
        layer.moveToFront(btnRoulette);
        layer.moveToFront(btnBlackjack);
        layer.moveToFront(btnLogout);
        layer.moveToFront(btnPlayer);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        layer = new javax.swing.JLayeredPane();
        lblBackground = new javax.swing.JLabel();

        lblBackground.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/img/backgroundStartpage.png"))); // NOI18N

        layer.setLayer(lblBackground, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout layerLayout = new javax.swing.GroupLayout(layer);
        layer.setLayout(layerLayout);
        layerLayout.setHorizontalGroup(
            layerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblBackground, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layerLayout.setVerticalGroup(
            layerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblBackground, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(layer)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(layer)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLayeredPane layer;
    private javax.swing.JLabel lblBackground;
    // End of variables declaration//GEN-END:variables
}
