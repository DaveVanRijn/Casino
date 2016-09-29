/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Casino;

import static Casino.Main.convertSize;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author Dave van Rijn, Student 500714558, Klas IS-202
 */
public class Login extends javax.swing.JPanel {

    private final JButton btnLogin;
    private final JButton btnNewUser;
    private final JButton btnRegister;
    private final JButton btnBack;
    private JButton btnExit;
    private final JTextField txtLgnName;
    private final JPasswordField txtLgnPass;
    private final JTextField txtNewName;
    private final JPasswordField txtNewPass;
    private final JTextField txtNewMoney;
    private final JLabel lblUsername;
    private final JLabel lblPassword;
    private final JLabel lblMoney;
    private final JLabel lblNameError;
    private final JLabel lblPassError;
    private final JCheckBox chkRemember;
    private final Font standardFont = new Font("Tahoma", Font.PLAIN, 16);
    private boolean legitName = true;
    private boolean register = true;

    /**
     * Creates new form Login
     */
    public Login() {
        initComponents();
        String[] remember = Main.getRemebered();
        Action loginAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login(txtLgnName.getText(), txtLgnPass.getPassword());
            }
        };

        Action registerAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                register(txtNewName.getText(), txtNewPass.getPassword(),
                        txtNewMoney.getText());
            }
        };

        btnLogin = new JButton(
                new ImageIcon(getClass().getResource("/Img/btnLogin.png")));
        btnNewUser = new JButton(
                new ImageIcon(getClass().getResource("/Img/btnNewUser.png")));
        btnRegister = new JButton(
                new ImageIcon(getClass().getResource("/Img/btnRegister.png")));
        btnBack = new JButton(
                new ImageIcon(getClass().getResource("/Img/btnBack.png")));
        txtLgnName = new JTextField();
        txtLgnName.setFont(standardFont);
        txtLgnPass = new JPasswordField();
        chkRemember = new JCheckBox("Onthoud mij");
        chkRemember.setFont(standardFont);
        chkRemember.setForeground(new Color(255, 212, 0));
        chkRemember.setOpaque(false);
        if (remember != null) {
            txtLgnName.setText(remember[0]);
            txtLgnPass.setText(remember[1]);
            chkRemember.setSelected(true);
        }
        txtNewName = new JTextField();
        txtNewName.setFont(standardFont);
        txtNewName.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                for (Player p : Main.getPlayers()) {
                    if (p.getUsername().equals(txtNewName.getText())) {
                        legitName = false;
                        System.out.println("False");
                        break;
                    }
                    legitName = true;
                }
                String isSupported;
                if (!legitName) {
                    lblNameError.setVisible(true);
                    lblNameError.setText("'" + txtNewName.getText() + "' already exists!");
                    lblNameError.setSize(lblNameError.getPreferredSize().width, 25);
                } else if ((isSupported = Main.isEncryptSupported(txtNewName.getText())) != null) {
                    lblNameError.setVisible(true);
                    lblNameError.setText(isSupported + " cannot be used!");
                    lblNameError.setSize(lblNameError.getPreferredSize().width, 25);
                } else {
                    lblNameError.setVisible(false);
                }
                checkRegister();
            }
        });
        txtNewName.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                String isSupported = Main.isEncryptSupported(txtNewName.getText());
                if (isSupported != null) {
                    lblNameError.setText(isSupported + " cannot be used!");
                    lblNameError.setVisible(true);
                    lblNameError.setSize(lblNameError.getPreferredSize().width, 25);
                } else {
                    lblNameError.setVisible(false);
                }
                checkRegister();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String isSupported = Main.isEncryptSupported(txtNewName.getText());
                if (isSupported != null) {
                    lblNameError.setText(isSupported + " cannot be used!");
                    lblNameError.setVisible(true);
                    lblNameError.setSize(lblNameError.getPreferredSize().width, 25);
                } else {
                    lblNameError.setVisible(false);
                }
                checkRegister();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                String isSupported = Main.isEncryptSupported(txtNewName.getText());
                if (isSupported != null) {
                    lblNameError.setText(isSupported + " cannot be used!");
                    lblNameError.setVisible(true);
                    lblNameError.setSize(lblNameError.getPreferredSize().width, 25);
                } else {
                    lblNameError.setVisible(false);
                }
                checkRegister();
            }

        });
        txtNewPass = new JPasswordField();
        txtNewPass.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                String pass = String.valueOf(txtNewPass.getPassword());
                String isSupported = Main.isEncryptSupported(pass);
                if (isSupported != null) {
                    lblPassError.setText(isSupported + " cannot be used!");
                    lblPassError.setVisible(true);
                    lblPassError.setSize(lblPassError.getPreferredSize().width, 25);
                } else {
                    lblPassError.setVisible(false);
                }
                checkRegister();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String pass = String.valueOf(txtNewPass.getPassword());
                String isSupported = Main.isEncryptSupported(pass);
                if (isSupported != null) {
                    lblPassError.setText(isSupported + " cannot be used!");
                    lblPassError.setVisible(true);
                    lblPassError.setSize(lblPassError.getPreferredSize().width, 25);
                } else {
                    lblPassError.setVisible(false);
                }
                checkRegister();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                String pass = String.valueOf(txtNewPass.getPassword());
                String isSupported = Main.isEncryptSupported(pass);
                if (isSupported != null) {
                    lblPassError.setText(isSupported + " cannot be used!");
                    lblPassError.setVisible(true);
                    lblPassError.setSize(lblPassError.getPreferredSize().width, 25);
                } else {
                    lblPassError.setVisible(false);
                }
                checkRegister();
            }

        });
        txtNewMoney = new JTextField();
        txtNewMoney.setFont(standardFont);

        lblUsername = new JLabel("Username");
        lblUsername.setFont(standardFont);
        lblUsername.setForeground(Color.WHITE);
        lblPassword = new JLabel("Password");
        lblPassword.setFont(standardFont);
        lblPassword.setForeground(Color.WHITE);
        lblMoney = new JLabel("Amount of money");
        lblMoney.setFont(standardFont);
        lblMoney.setForeground(Color.WHITE);
        lblNameError = new JLabel("'" + txtNewName.getText() + "' already exists!");
        lblNameError.setFont(standardFont);
        lblNameError.setForeground(new Color(255, 179, 0));
        lblNameError.setVisible(false);
        lblPassError = new JLabel();
        lblPassError.setFont(standardFont);
        lblPassError.setForeground(new Color(255, 179, 0));
        lblPassError.setVisible(false);

        layer.moveToFront(lblBackground);
        btnLogin.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                login(txtLgnName.getText(), txtLgnPass.getPassword());
            }
        });

        btnNewUser.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                layer.remove(btnNewUser);
                layer.remove(btnLogin);
                layer.remove(txtLgnName);
                layer.remove(txtLgnPass);
                layer.remove(chkRemember);
                layer.add(txtNewName);
                layer.add(txtNewPass);
                layer.add(btnRegister);
                layer.add(btnBack);
                layer.add(lblMoney);
                layer.add(txtNewMoney);
                layer.repaint();

                layer.moveToFront(txtNewName);
                layer.moveToFront(txtNewPass);
                layer.moveToFront(btnRegister);
                layer.moveToFront(btnBack);
                layer.moveToFront(lblUsername);
                layer.moveToFront(lblPassword);
                layer.moveToFront(lblMoney);
                layer.moveToFront(txtNewMoney);
            }
        });

        btnRegister.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (legitName && register) {
                    register(txtNewName.getText(), txtNewPass.getPassword(),
                            txtNewMoney.getText());
                } else {
                    JOptionPane.showMessageDialog(null, "Check username or password!",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnBack.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                layer.remove(txtNewName);
                layer.remove(txtNewPass);
                layer.remove(txtNewMoney);
                layer.remove(lblMoney);
                layer.remove(btnRegister);
                layer.remove(btnBack);
                layer.repaint();
                layer.add(txtLgnName);
                layer.add(txtLgnPass);
                layer.add(chkRemember);
                layer.add(btnLogin);
                layer.add(btnNewUser);

                layer.moveToFront(txtLgnName);
                layer.moveToFront(txtLgnPass);
                layer.moveToFront(chkRemember);
                layer.moveToFront(btnLogin);
                layer.moveToFront(btnNewUser);
            }
        });

        txtLgnName.addActionListener(loginAction);
        txtLgnPass.addActionListener(loginAction);
        txtNewName.addActionListener(registerAction);
        txtNewPass.addActionListener(registerAction);
        txtNewMoney.addActionListener(registerAction);

        btnLogin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnNewUser.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnRegister.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnBack.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        btnLogin.setBounds(226, 330, 162, 53);
        btnNewUser.setBounds(410, 330, 162, 53);
        btnRegister.setBounds(226, 330, 162, 53);
        btnBack.setBounds(410, 330, 162, 53);
        txtLgnName.setBounds(410, 190, 120, 25);
        txtLgnPass.setBounds(410, 235, 120, 25);
        chkRemember.setBounds(410, 280, 120, 25);
        txtNewName.setBounds(410, 190, 120, 25);
        txtNewPass.setBounds(410, 235, 120, 25);
        txtNewMoney.setBounds(410, 280, 120, 25);
        lblUsername.setBounds(310, 190, lblUsername.getPreferredSize().width, 25);
        lblPassword.setBounds(310, 235, lblPassword.getPreferredSize().width, 25);
        lblMoney.setBounds(250, 280, lblMoney.getPreferredSize().width, 25);
        lblNameError.setBounds(540, 190, lblNameError.getPreferredSize().width, 25);
        lblPassError.setBounds(540, 235, lblPassError.getPreferredSize().width, 25);

        layer.add(txtLgnName);
        layer.add(txtLgnPass);
        layer.add(chkRemember);
        layer.add(btnLogin);
        layer.add(btnNewUser);
        layer.add(lblUsername);
        layer.add(lblPassword);
        layer.add(lblNameError);
        layer.add(lblPassError);

        layer.moveToFront(txtLgnName);
        layer.moveToFront(txtLgnPass);
        layer.moveToFront(chkRemember);
        layer.moveToFront(btnLogin);
        layer.moveToFront(btnNewUser);
        layer.moveToFront(lblUsername);
        layer.moveToFront(lblPassword);
        layer.moveToFront(lblNameError);
        layer.moveToFront(lblPassError);
        
        initComps();

    }

    private void checkRegister() {
        register = !(lblNameError.isVisible() || lblPassError.isVisible());
    }

    private void login(String username, char[] password) {
        if (username.isEmpty() || password.length == 0) {
            JOptionPane.showMessageDialog(null, "Enter a username and password!",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            StringBuilder builder = new StringBuilder();
            for (char b : password) {
                builder.append(b);
            }
            String pass = builder.toString();

            ArrayList<Player> players = Main.getPlayers();
            for (Player player : players) {
                if (player.getUsername().equals(username)
                        && player.getPassword().equals(pass)) {
                    Player.setCurrentPlayer(player);
                    if (chkRemember.isSelected()) {
                        Main.rememberPlayer(player);
                    } else {
                        Main.clearRemember();
                    }
                }
            }
            if (Player.getLoggedIn()) {
                Main.setPanel(new Startpage());
            } else {
                JOptionPane.showMessageDialog(null, "Invalid combination of "
                        + "username and password!", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void register(String username, char[] password, String money) {
        if (username.isEmpty() || password.length == 0 || money.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Enter a username, password and "
                    + "amount of money!", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            StringBuilder builder = new StringBuilder();
            for (char c : password) {
                builder.append(c);
            }
            String pass = builder.toString();
            try {
                money = money.replace(",", ".");
                int cash = Integer.parseInt(money);
                if (cash <= 0) {
                    throw new NumberFormatException();
                }
                Main.addPlayer(Player.newPlayer(username, pass, cash));
                login(username, password);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Enter a valid amount of "
                        + "money!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void initComps(){
        btnExit = new JButton(new ImageIcon(getClass().getResource("/Img/btnExit.png")));
        btnExit.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnExit.setFocusable(false);
        btnExit.setPressedIcon(new ImageIcon(getClass().getResource("/Img/btnExitPressed.png")));
        btnExit.addMouseListener(new MouseAdapter(){
           @Override
           public void mouseReleased(MouseEvent e){
               Main.exit();
           }
        });
        
        //Bounds
        btnExit.setBounds(convertSize(616), convertSize(10), convertSize(164), convertSize(53));
        
       //add
        layer.add(btnExit);
        
        
        //Move to front
        layer.moveToFront(btnExit);
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

        lblBackground.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/backgroundCasinoTitle.png"))); // NOI18N

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
