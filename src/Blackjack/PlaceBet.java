/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Blackjack;

import Casino.Main;
import Casino.Player;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 *
 * @author Dave van Rijn, Student 500714558, Klas IS202
 */
public class PlaceBet extends javax.swing.JPanel {

    private final int BETTING_SCREEN = 0;
    private final Dimension STANDARD_SCREEN_SIZE = new Dimension(1366, 768);
    public static double SIZE_FACTOR;
    private Font STANDARD_FONT;
    
    private long bet = 0;
    private long money = Player.getCurrentPlayer().getMoney();
    private LinkedList<Long> bets = new LinkedList<>();
    private boolean betPlaced = false;

    private JButton bet10;
    private JButton bet20;
    private JButton bet50;
    private JButton bet100;
    private JButton betCustom;
    private JButton btnPlay;
    private JButton btnRemoveLastBet;
    private JButton btnRemoveBet;
    private JButton btnBack;
    private JLabel lblYourMoney;
    private JLabel lblCurrentBet;
    private JLabel lblPlaceBet;
    private JSeparator sep;

    /**
     * Creates new form Game
     */
    public PlaceBet() {
        initComponents();
        initComps();
        Main.changeTitle("Blackjack");
    }

    private void setScreen(int screen) {
        layer.removeAll();
        layer.add(background);
        layer.add(btnBack);
        layer.moveToFront(background);
        layer.moveToFront(btnBack);

        switch (screen) {
            case 0:
                Color sepColor = new Color(10, 63, 19);

                bet10.setBounds(getSize(236), getSize(254), getSize(90), getSize(90));
                bet10.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

                bet20.setBounds(getSize(356), getSize(254), getSize(90), getSize(90));
                bet20.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

                bet50.setBounds(getSize(176), getSize(374), getSize(90), getSize(90));
                bet50.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

                bet100.setBounds(getSize(416), getSize(374), getSize(90), getSize(90));
                bet100.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

                betCustom.setBounds(getSize(296), getSize(374), getSize(90), getSize(90));
                betCustom.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

                btnPlay.setBounds(getSize(259), getSize(514), getSize(164), getSize(53));
                btnPlay.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

                btnRemoveLastBet.setBounds(getSize(942), getSize(333), getSize(164), getSize(53));
                btnRemoveLastBet.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

                btnRemoveBet.setBounds(getSize(942), getSize(401), getSize(164), getSize(53));
                btnRemoveBet.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

                sep.setSize(1, getSize(678));
                sep.setLocation(getSize(683), getSize(90));
                sep.setBackground(sepColor);
                sep.setForeground(sepColor);

                lblYourMoney.setFont(STANDARD_FONT);
                lblYourMoney.setForeground(Color.WHITE);
                lblCurrentBet.setFont(STANDARD_FONT);
                lblCurrentBet.setForeground(Color.WHITE);

                lblPlaceBet.setBounds(getSize(601), getSize(18), getSize(164), getSize(53));
                lblYourMoney.setBounds(getSize(942), getSize(254/*220*/), lblYourMoney.getPreferredSize().width, getSize(25));
                lblCurrentBet.setBounds(getSize(942), getSize(293), lblCurrentBet.getPreferredSize().width, getSize(25));

                layer.add(sep);
                layer.add(lblPlaceBet);
                layer.add(lblYourMoney);
                layer.add(lblCurrentBet);
                layer.add(bet10);
                layer.add(bet20);
                layer.add(bet50);
                layer.add(bet100);
                layer.add(betCustom);
                layer.add(btnPlay);
                layer.add(btnRemoveLastBet);
                layer.add(btnRemoveBet);

                layer.moveToFront(sep);
                layer.moveToFront(lblPlaceBet);
                layer.moveToFront(lblCurrentBet);
                layer.moveToFront(lblYourMoney);
                layer.moveToFront(bet10);
                layer.moveToFront(bet20);
                layer.moveToFront(bet50);
                layer.moveToFront(bet100);
                layer.moveToFront(betCustom);
                layer.moveToFront(btnPlay);
                layer.moveToFront(btnRemoveLastBet);
                layer.moveToFront(btnRemoveBet);
                break;
        }
    }

    public static BufferedImage getImage(String name) {
        Image image = new ImageIcon(PlaceBet.class.getResource("/Img/" + name + ".png")).getImage();
        int width;
        int height;

        if (name.equals("backgroundBlackjack")) {
            Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
            width = screen.width;
            height = screen.height;
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

    public static int getSize(int x) {
        return (int) (x * SIZE_FACTOR);
    }

    private void initComps() {
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        SIZE_FACTOR = Math.max((double) screensize.width / STANDARD_SCREEN_SIZE.width,
                (double) screensize.height / STANDARD_SCREEN_SIZE.height);
        STANDARD_FONT = new Font("Tahoma", Font.PLAIN, getSize(16));
        
        background.setIcon(new ImageIcon(getImage("backgroundBlackjack")));
        bet10 = new JButton(new ImageIcon(getImage("chipRedBL")));
        bet10.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (money >= 10) {
                    bets.push(10L);
                    bet += 10;
                    money -= 10;
                    betPlaced = true;
                    lblYourMoney.setText("Your money: \u20ac" + money);
                    lblYourMoney.setSize(lblYourMoney.getPreferredSize().width, getSize(25));
                    lblCurrentBet.setText("Current bet: \u20ac" + Long.toString(bet));
                    lblCurrentBet.setSize(lblCurrentBet.getPreferredSize().width, getSize(25));
                } else {
                    showError("Not enough money left!");
                }
            }
        });

        bet20 = new JButton(new ImageIcon(getImage("chipGreenBL")));
        bet20.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (money > - 20) {
                    bets.push(20L);
                    bet += 20;
                    money -= 20;
                    betPlaced = true;
                    lblYourMoney.setText("Your money: \u20ac" + money);
                    lblYourMoney.setSize(lblYourMoney.getPreferredSize().width, getSize(25));
                    lblCurrentBet.setText("Current bet: \u20ac" + Long.toString(bet));
                    lblCurrentBet.setSize(lblCurrentBet.getPreferredSize().width, getSize(25));
                } else {
                    showError("Not enough money left!");
                }
            }
        });

        bet50 = new JButton(new ImageIcon(getImage("chipBlueBL")));
        bet50.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (money >= 50) {
                    bets.push(50L);
                    bet += 50;
                    money -= 50;
                    betPlaced = true;
                    lblYourMoney.setText("Your money: \u20ac" + money);
                    lblYourMoney.setSize(lblYourMoney.getPreferredSize().width, getSize(25));
                    lblCurrentBet.setText("Current bet: \u20ac" + Long.toString(bet));
                    lblCurrentBet.setSize(lblCurrentBet.getPreferredSize().width, getSize(25));
                } else {
                    showError("Not enough money left!");
                }
            }
        });

        bet100 = new JButton(new ImageIcon(getImage("chipBlackBL")));
        bet100.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (money >= 100) {
                    bets.push(100L);
                    bet += 100;
                    money -= 100;
                    betPlaced = true;
                    lblYourMoney.setText("Your money: \u20ac" + money);
                    lblYourMoney.setSize(lblYourMoney.getPreferredSize().width, getSize(25));
                    lblCurrentBet.setText("Current bet: \u20ac" + Long.toString(bet));
                    lblCurrentBet.setSize(lblCurrentBet.getPreferredSize().width, getSize(25));
                } else {
                    showError("Not enough money left!");
                }
            }
        });

        betCustom = new JButton(new ImageIcon(getImage("chipWhiteBL")));
        betCustom.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                long customBet;

                Box box = new Box(BoxLayout.PAGE_AXIS);
                JPanel panel = new JPanel(new BorderLayout());
                JLabel lblBet = new JLabel("Enter your bet:");
                JTextField txt = new JTextField("Your bet", 10);

                lblBet.setFont(STANDARD_FONT);
                lblBet.setAlignmentX(LEFT_ALIGNMENT);

                txt.setFont(STANDARD_FONT);
                txt.setAlignmentX(LEFT_ALIGNMENT);
                txt.addFocusListener(new FocusAdapter() {
                    @Override
                    public void focusGained(FocusEvent e) {
                        txt.setText("");
                    }

                    @Override
                    public void focusLost(FocusEvent e) {
                        if (txt.getText().isEmpty()) {
                            txt.setText("Your bet");
                        }
                    }
                });

                box.add(lblBet);
                box.add(Box.createVerticalStrut(5));
                box.add(txt);
                panel.add(box, BorderLayout.CENTER);

                do {
                    int option = JOptionPane.showOptionDialog(null, panel, "Enter your Bet", JOptionPane.YES_NO_OPTION,
                            JOptionPane.PLAIN_MESSAGE, null,
                            new Object[]{"Ok", "Cancel"}, "Ok");

                    if (option == JOptionPane.YES_OPTION) {
                        try {
                            customBet = Long.parseLong(txt.getText());

                            if (customBet > 0) {
                                if (customBet <= money) {
                                    if (bets.size() > 0) {
                                        bets.clear();
                                        money += bet;
                                    }
                                    bets.push(customBet);
                                    bet = customBet;
                                    money -= customBet;
                                    betPlaced = true;

                                    lblYourMoney.setText("Your money: \u20ac" + money);
                                    lblYourMoney.setSize(lblYourMoney.getPreferredSize().width, getSize(25));
                                    lblCurrentBet.setText("Current bet: \u20ac" + Long.toString(bet));
                                    lblCurrentBet.setSize(lblCurrentBet.getPreferredSize().width, getSize(25));
                                } else {
                                    showError("Not enough money left!");
                                    customBet = 0;
                                }
                            } else {
                                throw new NumberFormatException();
                            }
                        } catch (NumberFormatException ex) {
                            showError("Enter a valid positive number!");
                            customBet = 0;
                        }
                    } else {
                        break;
                    }
                } while (customBet <= 0);
            }
        });

        btnPlay = new JButton(new ImageIcon(getImage("btnPlay")));
        btnPlay.addMouseListener(new MouseAdapter(){
            @Override
            public void mousePressed(MouseEvent e){
                Main.setPanel(new DealGame());
            }
        });
        
    

        btnRemoveLastBet = new JButton(new ImageIcon(getImage("btnRemoveLastBet")));
        btnRemoveLastBet.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (bets.size() > 0) {
                    long lastBet = bets.pop();
                    bet -= lastBet;
                    money += lastBet;
                    if (bets.isEmpty()) {
                        betPlaced = false;
                    }

                    lblYourMoney.setText("Your money: \u20ac" + money);
                    lblYourMoney.setSize(lblYourMoney.getPreferredSize().width, getSize(25));
                    lblCurrentBet.setText("Current bet: \u20ac" + Long.toString(bet));
                    lblCurrentBet.setSize(lblCurrentBet.getPreferredSize().width, getSize(25));
                }
            }
        });

        btnRemoveBet = new JButton(new ImageIcon(getImage("btnRemoveBet")));
        btnRemoveBet.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                bets.clear();
                money += bet;
                bet = 0;
                betPlaced = false;

                lblYourMoney.setText("Your money: \u20ac" + money);
                lblYourMoney.setSize(lblYourMoney.getPreferredSize().width, getSize(25));
                lblCurrentBet.setText("Current bet: \u20ac" + Long.toString(bet));
                lblCurrentBet.setSize(lblCurrentBet.getPreferredSize().width, getSize(25));
            }
        });

        btnBack = new JButton(new ImageIcon(getImage("btnBack")));
        btnBack.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnBack.setBounds(getSize(1182), getSize(18), getSize(164), getSize(53));
        btnBack.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (betPlaced) {
                    int option = JOptionPane.showOptionDialog(null, "You will lose your bet "
                            + "if you go back now, proceed?", "Confirm",
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                            null, null, "No");
                    if (option == JOptionPane.YES_OPTION) {
                        Player.getCurrentPlayer().setMoney(money);
                        Main.setLastPanel();
                        Main.changeTitle("Casino");
                    }
                } else {
                    Player.getCurrentPlayer().setMoney(money);
                    Main.setLastPanel();
                    Main.changeTitle("Casino");
                }
            }
        });

        lblYourMoney = new JLabel("Your money: \u20ac" + money);
        lblCurrentBet = new JLabel("Current bet: \u20ac" + Long.toString(bet));

        lblPlaceBet = new JLabel(new ImageIcon(getImage("lblPlaceBet")));

        sep = new JSeparator(SwingConstants.VERTICAL);

        setScreen(BETTING_SCREEN);
    }

    private void showError(String text) {
        JOptionPane.showMessageDialog(null, text, "Error", JOptionPane.ERROR_MESSAGE);
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
        background = new javax.swing.JLabel();

        background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/backgroundBlackjack.png"))); // NOI18N

        javax.swing.GroupLayout layerLayout = new javax.swing.GroupLayout(layer);
        layer.setLayout(layerLayout);
        layerLayout.setHorizontalGroup(
            layerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(background, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layerLayout.setVerticalGroup(
            layerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(background, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layer.setLayer(background, javax.swing.JLayeredPane.DEFAULT_LAYER);

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
    private javax.swing.JLabel background;
    private javax.swing.JLayeredPane layer;
    // End of variables declaration//GEN-END:variables
}
