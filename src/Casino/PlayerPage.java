/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Casino;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author Dave van Rijn, Student 500714558, Klas IS202
 */
public class PlayerPage extends javax.swing.JPanel {

    private final JTextField txtUsername;
    private final JPasswordField txtPassword;
    private final JTextField txtMoney;
    private final JButton btnEdit;
    private final JButton btnBack;
    private final JButton btnSave;
    private final JLabel lblUsername;
    private final JLabel lblPassword;
    private final JLabel lblMoney;
    private final JLabel lblRoulettePlayed;
    private final JLabel lblRoulettePlayedValue;
    private final JLabel lblRouletteWon;
    private final JLabel lblRouletteWonValue;
    private final JLabel lblRoulettePercentageWon;
    private final JLabel lblRoulettePercentageWonValue;
    private boolean editing = false;
    private final Font standardFont = new Font("Tahoma", Font.PLAIN, 16);

    /**
     * Creates new form PlayerPage
     */
    public PlayerPage() {
        initComponents();

        layer.moveToFront(lblBackground);
        txtUsername = new JTextField(Player.getCurrentPlayer().getUsername());
        txtPassword = new JPasswordField(Player.getCurrentPlayer().getPassword());
        txtMoney = new JTextField(Long.toString(Player.getCurrentPlayer().getMoney()));
        btnEdit = new JButton(new ImageIcon(getClass().getResource("/Img/btnEdit.png")));
        btnBack = new JButton(new ImageIcon(getClass().getResource("/Img/btnBack.png")));
        btnSave = new JButton(new ImageIcon(getClass().getResource("/Img/btnSave.png")));
        lblUsername = new JLabel("Username");
        lblPassword = new JLabel("Password");
        lblMoney = new JLabel("Amount of money");
        lblRoulettePlayed = new JLabel("Number of Roulette games played");
        lblRoulettePlayedValue = new JLabel(Integer.toString(
                Player.getCurrentPlayer().getRoulettePlayed()));
        lblRouletteWon = new JLabel("Number of Roulette games won");
        lblRouletteWonValue = new JLabel(Integer.toString(
                Player.getCurrentPlayer().getRouletteWon()));
        lblRoulettePercentageWon = new JLabel("Percentage of Roulette games won");
        lblRoulettePercentageWonValue = new JLabel(
                Player.getCurrentPlayer().getRoulettePercentageWon() + "%");

        lblUsername.setFont(standardFont);
        lblUsername.setForeground(Color.WHITE);
        lblPassword.setFont(standardFont);
        lblPassword.setForeground(Color.WHITE);
        lblMoney.setFont(standardFont);
        lblMoney.setForeground(Color.WHITE);
        lblRoulettePlayed.setFont(standardFont);
        lblRoulettePlayed.setForeground(Color.WHITE);
        lblRoulettePlayedValue.setFont(standardFont);
        lblRoulettePlayedValue.setForeground(Color.WHITE);
        lblRouletteWon.setFont(standardFont);
        lblRouletteWon.setForeground(Color.WHITE);
        lblRouletteWonValue.setFont(standardFont);
        lblRouletteWonValue.setForeground(Color.WHITE);
        lblRoulettePercentageWon.setFont(standardFont);
        lblRoulettePercentageWon.setForeground(Color.WHITE);
        lblRoulettePercentageWonValue.setFont(standardFont);
        lblRoulettePercentageWonValue.setForeground(Color.WHITE);
        txtUsername.setFont(standardFont);
        txtPassword.setFont(standardFont);
        txtMoney.setFont(standardFont);

        btnEdit.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                editing = true;
                txtUsername.setEnabled(true);
                txtPassword.setEnabled(true);
                txtPassword.setEchoChar((char) 0);
                txtMoney.setEnabled(true);

                layer.remove(btnEdit);
                layer.repaint();
                layer.add(btnSave);
                layer.moveToFront(btnSave);
            }
        });

        btnBack.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (!editing) {
                    Main.setLastPanel();
                } else {
                    JOptionPane.showMessageDialog(null, "You must save your changes "
                            + "before going back.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnSave.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int money;
                String password;
                String username;
                StringBuilder builder = new StringBuilder();
                try {
                    username = txtUsername.getText();
                    for (char c : txtPassword.getPassword()) {
                        builder.append(c);
                    }
                    password = builder.toString();
                    money = Integer.parseInt(txtMoney.getText());

                    if (username.isEmpty() || password.isEmpty()
                            || txtMoney.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Enter a username, "
                                + "password and amount of money!", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    } else {
                        Player.getCurrentPlayer().setUsername(username);
                        Player.getCurrentPlayer().setPassword(password);
                        Player.getCurrentPlayer().setMoney(money);
                        JOptionPane.showMessageDialog(null, "Your account "
                                + "information has been saved", "Succes",
                                JOptionPane.INFORMATION_MESSAGE);
                        txtUsername.setEnabled(false);
                        txtPassword.setEnabled(false);
                        txtPassword.setEchoChar('\u2022');
                        txtMoney.setEnabled(false);

                        layer.remove(btnSave);
                        layer.repaint();
                        layer.add(btnEdit);

                        layer.moveToFront(btnEdit);
                        editing = false;
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Enter a valid amount of "
                            + "money!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        txtUsername.setEnabled(false);
        txtPassword.setEnabled(false);
        txtMoney.setEnabled(false);

        lblUsername.setBounds(310, 170, lblUsername.getPreferredSize().width, 25);
        lblPassword.setBounds(310, 215, lblPassword.getPreferredSize().width, 25);
        lblMoney.setBounds(250, 260, lblMoney.getPreferredSize().width, 25);
        lblRoulettePlayed.setBounds(140, 305,
                lblRoulettePlayed.getPreferredSize().width, 25);
        lblRoulettePlayedValue.setBounds(400, 305,
                lblRoulettePlayedValue.getPreferredSize().width, 25);
        lblRouletteWon.setBounds(155, 350,
                lblRouletteWon.getPreferredSize().width, 25);
        lblRouletteWonValue.setBounds(400, 350,
                lblRouletteWonValue.getPreferredSize().width, 25);
        lblRoulettePercentageWon.setBounds(140, 395,
                lblRoulettePercentageWon.getPreferredSize().width, 25);
        lblRoulettePercentageWonValue.setBounds(400, 395,
                lblRoulettePercentageWonValue.getPreferredSize().width, 25);
        txtUsername.setBounds(400, 170, 120, 25);
        txtPassword.setBounds(400, 215, 120, 25);
        txtMoney.setBounds(400, 260, 120, 25);
        btnBack.setBounds(616, 20, 164, 53);
        btnEdit.setBounds(318, 440, 164, 53);
        btnSave.setBounds(318, 440, 164, 53);

        layer.add(lblUsername);
        layer.add(lblPassword);
        layer.add(lblMoney);
        layer.add(lblRoulettePlayed);
        layer.add(lblRoulettePlayedValue);
        layer.add(lblRouletteWon);
        layer.add(lblRouletteWonValue);
        layer.add(lblRoulettePercentageWon);
        layer.add(lblRoulettePercentageWonValue);
        layer.add(txtUsername);
        layer.add(txtPassword);
        layer.add(txtMoney);
        layer.add(btnBack);
        layer.add(btnEdit);

        layer.moveToFront(lblUsername);
        layer.moveToFront(lblPassword);
        layer.moveToFront(lblMoney);
        layer.moveToFront(lblRoulettePlayed);
        layer.moveToFront(lblRoulettePlayedValue);
        layer.moveToFront(lblRouletteWon);
        layer.moveToFront(lblRouletteWonValue);
        layer.moveToFront(lblRoulettePercentageWon);
        layer.moveToFront(lblRoulettePercentageWonValue);
        layer.moveToFront(txtUsername);
        layer.moveToFront(txtPassword);
        layer.moveToFront(txtMoney);
        layer.moveToFront(btnBack);
        layer.moveToFront(btnEdit);

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

        lblBackground.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/backgroundPlayer.png"))); // NOI18N

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
        layer.setLayer(lblBackground, javax.swing.JLayeredPane.DEFAULT_LAYER);

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
