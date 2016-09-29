/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Roulette;

import Casino.Main;
import Casino.Player;
import Roulette.Objects.Bet;
import Roulette.Objects.Clickable;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import org.netbeans.lib.awtextra.AbsoluteConstraints;
import org.netbeans.lib.awtextra.AbsoluteLayout;

/**
 *
 * @author Davey
 */
public class Betting extends javax.swing.JPanel {

    private List<Clickable> choices;
    private final List<Bet> bets = new ArrayList<>();
    private final int mouseXMin = 54;
    private final int mouseXMax = 907;
    private final int mouseYMin = 20;
    private final int mouseYMax = 455;
    private final int chipSize = 87;
    private int currentWager = 10;
    private long wager = 0;
    private JLabel lblTotalWager;
    private JLabel lblCash;
    private JLabel lblCurrentWager;
    private JLabel lblChooseWager;
    private JButton btnBet10;
    private JButton btnBet20;
    private JButton btnBet50;
    private JButton btnBet100;
    private JButton btnBetCustom;
    private JButton btnPlay;
    private JButton btnBack;
    private long money = Player.getCurrentPlayer().getMoney();
    private int random = -1;
    static boolean isActive = true;
    private final Map<Integer, Double> angleMap = new HashMap<>();
    private static JDialog wheelFrame;
    //Utils
    private final DecimalFormat deciForm = new DecimalFormat("0.00");
    private final Font standardFont = new Font("Tahoma", Font.PLAIN, 16);

    /**
     * Creates new form Betting
     */
    public Betting() {
        initComponents();
        initMap();
        initComps();

        Main.changeTitle("Roulette");
    }

    /**
     * Make the list of clickable locations
     *
     * @return The list of clickables
     */
    private ArrayList<Clickable> makeList() {
        ArrayList<Clickable> list = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(getClass().getResourceAsStream("/Data/list.txt")));
            String line;
            try {
                while ((line = reader.readLine()) != null) {
                    String[] array = line.split(" ");
                    String name = array[0];
                    int minX = Integer.parseInt(array[1]);
                    int maxX = Integer.parseInt(array[2]);
                    int minY = Integer.parseInt(array[3]);
                    int maxY = Integer.parseInt(array[4]);
                    int multiplier = Integer.parseInt(array[5]);
                    String[] stringNumbers = array[6].split(",");
                    int[] numbers = new int[stringNumbers.length];
                    for (int i = 0; i < stringNumbers.length; i++) {
                        numbers[i] = Integer.parseInt(stringNumbers[i]);
                    }
                    list.add(new Clickable(name, minX, maxX, minY, maxY, numbers, multiplier));
                }
            } catch (IOException ex) {
                Logger.getLogger(Betting.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (NumberFormatException ex) {
            Logger.getLogger(Betting.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    private void selectChip(String chipName) {
        switch (chipName) {
            case "black":
                btnBet100.setIcon(new ImageIcon(getClass().getResource("/Img/chipBlackSelected.png")));
                btnBet50.setIcon(new ImageIcon(getClass().getResource("/Img/chipBlue.png")));
                btnBet20.setIcon(new ImageIcon(getClass().getResource("/Img/chipGreen.png")));
                btnBet10.setIcon(new ImageIcon(getClass().getResource("/Img/chipRed.png")));
                btnBetCustom.setIcon(new ImageIcon(getClass().getResource("/Img/chipWhite.png")));
                break;
            case "blue":
                btnBet100.setIcon(new ImageIcon(getClass().getResource("/Img/chipBlack.png")));
                btnBet50.setIcon(new ImageIcon(getClass().getResource("/Img/chipBlueSelected.png")));
                btnBet20.setIcon(new ImageIcon(getClass().getResource("/Img/chipGreen.png")));
                btnBet10.setIcon(new ImageIcon(getClass().getResource("/Img/chipRed.png")));
                btnBetCustom.setIcon(new ImageIcon(getClass().getResource("/Img/chipWhite.png")));
                break;
            case "red":
                btnBet100.setIcon(new ImageIcon(getClass().getResource("/Img/chipBlack.png")));
                btnBet50.setIcon(new ImageIcon(getClass().getResource("/Img/chipBlue.png")));
                btnBet20.setIcon(new ImageIcon(getClass().getResource("/Img/chipGreen.png")));
                btnBet10.setIcon(new ImageIcon(getClass().getResource("/Img/chipRedSelected.png")));
                btnBetCustom.setIcon(new ImageIcon(getClass().getResource("/Img/chipWhite.png")));
                break;
            case "green":
                btnBet100.setIcon(new ImageIcon(getClass().getResource("/Img/chipBlack.png")));
                btnBet50.setIcon(new ImageIcon(getClass().getResource("/Img/chipBlue.png")));
                btnBet20.setIcon(new ImageIcon(getClass().getResource("/Img/chipGreenSelected.png")));
                btnBet10.setIcon(new ImageIcon(getClass().getResource("/Img/chipRed.png")));
                btnBetCustom.setIcon(new ImageIcon(getClass().getResource("/Img/chipWhite.png")));
                break;
            case "white":
                btnBet100.setIcon(new ImageIcon(getClass().getResource("/Img/chipBlack.png")));
                btnBet50.setIcon(new ImageIcon(getClass().getResource("/Img/chipBlue.png")));
                btnBet20.setIcon(new ImageIcon(getClass().getResource("/Img/chipGreen.png")));
                btnBet10.setIcon(new ImageIcon(getClass().getResource("/Img/chipRed.png")));
                btnBetCustom.setIcon(new ImageIcon(getClass().getResource("/Img/chipWhiteSelected.png")));
                break;
        }
    }

    /**
     * Get the Bet object at the specified point
     *
     * @param p The point (location) of the bet
     */
    private void getBet(Point p) {
        if (isActive) {
            int x = (int) p.getX();
            int y = (int) p.getY();
            for (Clickable click : choices) {
                int maxx = click.getMaxX();
                int minx = click.getMinX();
                int maxy = click.getMaxY();
                int miny = click.getMinY();

                if (x >= minx && x <= maxx && y >= miny && y <= maxy) {
                    if (currentWager <= money) {
                        money -= currentWager;
                        lblCash.setText("Current money: \u20ac" + deciForm.format(money));
                        bets.add(new Bet(click.getMultiplier(), click.getNumbers(), currentWager, new JLabel()));
                        lblTotalWager.setText("Total wager: \u20ac" + calculateWager());
                        bets.get(bets.size() - 1).getLabel().addMouseListener(new MouseAdapter() {
                            @Override
                            public void mousePressed(MouseEvent e) {
                                layer.remove((JLabel) e.getSource());
                                layer.repaint();
                                for (int i = bets.size() - 1; i > -1; i--) {
                                    Bet bet;
                                    if ((bet = bets.get(i)).getLabel() == (JLabel) e.getSource()) {
                                        money += bet.getWager();
                                        lblCash.setText("Current money: \u20ac" + deciForm.format(money));
                                        bets.remove(bet);
                                        lblTotalWager.setText("Total wager: \u20ac" + calculateWager());
                                    }
                                }
                            }
                        });
                        int placeX = (click.getMinX() + click.getMaxX()) / 2 - 15;
                        int placeY = (click.getMinY() + click.getMaxY()) / 2 - 15;
                        bets.get(bets.size() - 1).getLabel().setBounds(placeX, placeY, 30, 30);
                        JLabel label = bets.get(bets.size() - 1).getLabel();
                        String img;
                        switch (currentWager) {
                            case 10:
                                img = "/Img/chipRedMini.png";
                                break;
                            case 20:
                                img = "/Img/chipGreenMini.png";
                                break;
                            case 50:
                                img = "/Img/chipBlueMini.png";
                                break;
                            case 100:
                                img = "/Img/chipBlackMini.png";
                                break;
                            default:
                                img = "/Img/chipWhiteMini.png";
                                break;
                        }
                        label.setIcon(new ImageIcon(getClass().getResource(img)));
                        layer.add(bets.get(bets.size() - 1).getLabel());
                        layer.moveToFront(bets.get(bets.size() - 1).getLabel());
                    } else {
                        JOptionPane.showMessageDialog(null, "Not enough money left!",
                                "Too bad", JOptionPane.ERROR_MESSAGE);
                    }
                    break;
                }
            }
        }
        btnPlay.requestFocusInWindow();
    }

    /**
     * Calculate the total wager
     *
     * @return String representation of the total wager
     */
    private String calculateWager() {
        String Wager;
        wager = 0;
        for (Bet bet : bets) {
            wager += bet.getWager();
        }
        Wager = deciForm.format(wager);
        return Wager;
    }

    public static void setActive(boolean active) {
        isActive = active;
    }

    /**
     * Get the payout of the played game
     *
     * @return Payout of the played game, returns 0 if the player lost the bet
     */
    private double getPayout() {
        double pay = 0;
        random = (int) (Math.random() * 37);

        for (Bet bet : bets) {
            int[] numbers = bet.getNumbers();
            for (int number : numbers) {
                if (number == random) {
                    pay += bet.getWager() * bet.getMultiplier();
                }
            }
        }

        return pay;
    }

    public static void closeWheelFrame() {
        wheelFrame.dispatchEvent(new WindowEvent(wheelFrame, WindowEvent.WINDOW_CLOSING));
    }

    private void initMap() {
        int[] numbers = new int[37];
        numbers[0] = 12;
        numbers[1] = 35;
        numbers[2] = 3;
        numbers[3] = 26;
        numbers[4] = 0;
        numbers[5] = 32;
        numbers[6] = 15;
        numbers[7] = 19;
        numbers[8] = 4;
        numbers[9] = 21;
        numbers[10] = 2;
        numbers[11] = 25;
        numbers[12] = 17;
        numbers[13] = 34;
        numbers[14] = 6;
        numbers[15] = 27;
        numbers[16] = 13;
        numbers[17] = 36;
        numbers[18] = 11;
        numbers[19] = 30;
        numbers[20] = 8;
        numbers[21] = 23;
        numbers[22] = 10;
        numbers[23] = 5;
        numbers[24] = 24;
        numbers[25] = 16;
        numbers[26] = 33;
        numbers[27] = 1;
        numbers[28] = 20;
        numbers[29] = 14;
        numbers[30] = 31;
        numbers[31] = 9;
        numbers[32] = 22;
        numbers[33] = 18;
        numbers[34] = 29;
        numbers[35] = 7;
        numbers[36] = 28;

        double angle = 0.0;
        double addAngle = 360.0 / 37.0;
        for (int i = numbers.length - 1; i >= 0; i--) {
            angleMap.put(numbers[i], angle);
            angle += addAngle;
        }
    }

    private void initComps() {
        JLabel background = new JLabel(new ImageIcon(getClass().getResource("/Img/green.png")));
        background.setBounds(978, 0, 300, 500);
        layer.add(background);

        lblChooseWager = new JLabel(new ImageIcon(getClass().getResource("/Img/lblChooseWager.png")));
        lblChooseWager.setBounds(960, 40, 260, 53);

        lblTotalWager = new JLabel("Total wager: \u20ac" + calculateWager());
        lblTotalWager.setBounds(10, 5, 250, 20);
        lblTotalWager.setForeground(Color.WHITE);
        lblTotalWager.setFont(new Font("Tahoma", Font.PLAIN, 16));

        lblCurrentWager = new JLabel("Current wager: \u20ac"
                + deciForm.format(currentWager));
        lblCurrentWager.setBounds(540, 5, 250, 20);
        lblCurrentWager.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblCurrentWager.setForeground(Color.WHITE);

        btnPlay = new JButton(new ImageIcon(getClass().getResource("/Img/btnPlay.png")));
        btnPlay.setBounds(1009, 333, 162, 53);
        btnPlay.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnPlay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isActive) {
                    if (bets.size() > 0) {
                        Player.getCurrentPlayer().addRoulettePlayed();
                        setActive(false);
                        double pay = getPayout();
                        money += pay;
                        Player.getCurrentPlayer().setMoney(money);
                        double angle = (Math.toRadians((1440 + angleMap.get(random)) * 10));
                        wheelFrame = new JDialog();
                        JLabel background = new JLabel(new ImageIcon(getClass().getResource("/Img/backgroundWheel.png")));

                        wheelFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
                        wheelFrame.setModal(true);
                        wheelFrame.addWindowListener(new WindowAdapter() {
                            @Override
                            public void windowClosing(WindowEvent e) {
                                Result result = new Result(random, pay, wager);
                                lblCash.setText("Current money: \u20ac" + deciForm.format(money));
                                result.addWindowListener(new WindowAdapter() {
                                    @Override
                                    public void windowClosing(WindowEvent e) {
                                        for (Bet bet : bets) {
                                            layer.remove(bet.getLabel());
                                        }
                                        setActive(true);
                                        layer.repaint();
                                        bets.clear();
                                        wager = 0;
                                        lblTotalWager.setText("Total wager: \u20ac" + deciForm.format(wager));
                                        result.dispose();
                                    }
                                });
                                wheelFrame.dispose();
                            }
                        });
                        wheelFrame.setLayout(new AbsoluteLayout());
                        wheelFrame.setUndecorated(true);
                        wheelFrame.setSize(324, 324);
                        wheelFrame.add(new Wheel(angle), new AbsoluteConstraints(12, 12, 300, 300));
                        wheelFrame.add(background, new AbsoluteConstraints(0, 0, 324, 324));
                        wheelFrame.setLocationRelativeTo(null);
                        wheelFrame.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(null, "Place at least one bet!",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        btnBack = new JButton(new ImageIcon(getClass().getResource("/Img/btnBack.png")));
        btnBack.setBounds(1009, 406, 162, 53);
        btnBack.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnBack.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Main.setLastPanel();
                Main.changeTitle("Casino");
            }
        });

        btnBet10 = new JButton(new ImageIcon(getClass().getResource("/Img/chipRedSelected.png")));
        btnBet10.setBounds(1007, 112, 87, 87);
        btnBet10.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnBet10.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (isActive) {
                    currentWager = 10;
                    lblCurrentWager.setText("Current wager: \u20ac"
                            + deciForm.format(currentWager));
                    selectChip("red");
                }
            }
        });

        btnBet20 = new JButton(new ImageIcon(getClass().getResource("/Img/chipGreen.png")));
        btnBet20.setBounds(1107, 112, chipSize, chipSize);
        btnBet20.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnBet20.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (isActive) {
                    currentWager = 20;
                    lblCurrentWager.setText("Current wager: \u20ac"
                            + deciForm.format(currentWager));
                    selectChip("green");
                }
            }
        });

        btnBet50 = new JButton(new ImageIcon(getClass().getResource("/Img/chipBlue.png")));
        btnBet50.setBounds(957, 212, chipSize, chipSize);
        btnBet50.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnBet50.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (isActive) {
                    currentWager = 50;
                    lblCurrentWager.setText("Current wager: \u20ac"
                            + deciForm.format(currentWager));
                    selectChip("blue");
                }
            }
        });

        btnBet100 = new JButton(new ImageIcon(getClass().getResource("/Img/chipBlack.png")));
        btnBet100.setBounds(1157, 212, chipSize, chipSize);
        btnBet100.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnBet100.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (isActive) {
                    currentWager = 100;
                    lblCurrentWager.setText("Current wager: \u20ac"
                            + deciForm.format(currentWager));
                    selectChip("black");
                }
            }
        });

        btnBetCustom = new JButton(new ImageIcon(getClass().getResource("/Img/chipWhite.png")));
        btnBetCustom.setBounds(1057, 212, chipSize, chipSize);
        btnBetCustom.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnBetCustom.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (isActive) {
                    int customWager;

                    Box box = new Box(BoxLayout.PAGE_AXIS);
                    JPanel panel = new JPanel(new BorderLayout());
                    JLabel betLabel = new JLabel("Enter your wager:");
                    betLabel.setFont(standardFont);
                    JTextField txt = new JTextField(Long.toString(currentWager), 10);
                    txt.setFont(standardFont);
                    betLabel.setAlignmentX(LEFT_ALIGNMENT);
                    txt.setAlignmentX(LEFT_ALIGNMENT);
                    box.add(betLabel);
                    box.add(Box.createVerticalStrut(5));
                    box.add(txt);
                    panel.add(box, BorderLayout.CENTER);

                    txt.addFocusListener(new FocusAdapter() {
                        @Override
                        public void focusGained(FocusEvent ev) {
                            txt.setText("");
                        }
                    });

                    do {
                        int confirm = JOptionPane.showOptionDialog(null, panel,
                                "Custom wager", JOptionPane.YES_NO_OPTION,
                                JOptionPane.PLAIN_MESSAGE, null,
                                new Object[]{"Ok", "Cancel"}, "Ok");
                        if (confirm == JOptionPane.YES_OPTION) {
                            try {
                                customWager = Integer.parseInt(txt.getText());
                                if (customWager > 0) {
                                    currentWager = customWager;
                                    lblCurrentWager.setText("Current wager: \u20ac"
                                            + deciForm.format(currentWager));
                                    selectChip("white");
                                } else {
                                    throw new NumberFormatException();
                                }
                            } catch (NumberFormatException ex) {
                                JOptionPane.showMessageDialog(null,
                                        "Enter a valid positive number!",
                                        "Error", JOptionPane.ERROR_MESSAGE);
                                customWager = 0;
                            }
                        } else {
                            break;
                        }
                    } while (customWager == 0);
                }
            }
        });

        lblCash = new JLabel("Current money: \u20ac" + deciForm.format(money));
        lblCash.setBounds(270, 5, 250, 20);
        lblCash.setForeground(Color.WHITE);
        lblCash.setFont(new Font("Tahoma", Font.PLAIN, 16));

        layer.add(btnBet10);
        layer.add(btnBet20);
        layer.add(btnBet50);
        layer.add(btnBet100);
        layer.add(btnBetCustom);
        layer.add(lblTotalWager);
        layer.add(lblCash);
        layer.add(lblCurrentWager);
        layer.add(lblChooseWager);
        layer.add(btnPlay);
        layer.add(btnBack);

        layer.moveToFront(btnBet10);
        layer.moveToFront(btnBet20);
        layer.moveToFront(btnBet50);
        layer.moveToFront(btnBet100);
        layer.moveToFront(btnBetCustom);
        layer.moveToFront(lblCash);
        layer.moveToFront(lblTotalWager);
        layer.moveToFront(lblCurrentWager);
        layer.moveToFront(lblChooseWager);
        layer.moveToFront(btnPlay);
        layer.moveToFront(btnBack);
        choices = makeList();
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (money > 0) {
                    getBet(e.getPoint());
                } else {
                    JOptionPane.showMessageDialog(null, "No money left!", "Too bad",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        btnPlay.requestFocusInWindow();
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
        jLabel1 = new javax.swing.JLabel();

        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                formMouseMoved(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/tableLayout.png"))); // NOI18N

        javax.swing.GroupLayout layerLayout = new javax.swing.GroupLayout(layer);
        layer.setLayout(layerLayout);
        layerLayout.setHorizontalGroup(
            layerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layerLayout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(0, 300, Short.MAX_VALUE))
        );
        layerLayout.setVerticalGroup(
            layerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layerLayout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layer.setLayer(jLabel1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(layer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(layer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseMoved
        int x = evt.getPoint().x;
        int y = evt.getPoint().y;

        if (x >= mouseXMin && x <= mouseXMax && y >= mouseYMin && y <= mouseYMax) {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }
    }//GEN-LAST:event_formMouseMoved


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLayeredPane layer;
    // End of variables declaration//GEN-END:variables
}
