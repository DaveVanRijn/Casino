/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views.roulette;

import views.shared.Main;
import object.shared.Player;
import object.roulette.Bet;
import object.roulette.Clickable;
import object.roulette.Wheel;
import resources.java.shared.Database;
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
import static resources.java.shared.ImageLabel.getButton;
import static resources.java.shared.ImageLabel.getImageIcon;
import static resources.java.shared.ImageLabel.getLabel;

/**
 *
 * @author Davey
 */
public class Betting extends javax.swing.JPanel {

    private final Database DB;
    private final Player CURRENT;
    private final List<Bet> BETS = new ArrayList<>();
    private final int MOUSE_X_MIN = 54;
    private final int MOUSE_X_MAX = 907;
    private final int MOUSE_Y_MIN = 20;
    private final int MOUSE_Y_MAX = 455;
    private final int CHIP_SIZE = 87;
    private final Map<Integer, Double> ANGLE_MAP = new HashMap<>();

    private final DecimalFormat DECI_FORM = new DecimalFormat("0.00");
    private final Font STANDARD_FONT = new Font("Tahoma", Font.PLAIN, 16);

    private List<Clickable> choices;
    static boolean isActive = true;
    private int currentWager = 10;
    private int wager = 0;
    private int money;
    private int random = -1;
    private static JDialog wheelFrame;

    /**
     * Creates new form Betting
     */
    public Betting() throws IOException, NullPointerException {
        initComponents();

        DB = new Database();
        CURRENT = DB.getCurrentPlayer();
        if (CURRENT == null) {
            throw new NullPointerException("Current player is null.");
        }
        money = CURRENT.getMoney();

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
                    new InputStreamReader(getClass().getResourceAsStream("/resources/data/list.txt")));
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
                btnBet100.setIcon(getImageIcon("chipBlackSelected"));
                btnBet50.setIcon(getImageIcon("chipBlue"));
                btnBet20.setIcon(getImageIcon("chipGreen"));
                btnBet10.setIcon(getImageIcon("chipRed"));
                btnBetCustom.setIcon(getImageIcon("chipWhite"));
                break;
            case "blue":
                btnBet100.setIcon(getImageIcon("chipBlack"));
                btnBet50.setIcon(getImageIcon("chipBlueSelected"));
                btnBet20.setIcon(getImageIcon("chipGreen"));
                btnBet10.setIcon(getImageIcon("chipRed"));
                btnBetCustom.setIcon(getImageIcon("chipWhite"));
                break;
            case "red":
                btnBet100.setIcon(getImageIcon("chipBlack"));
                btnBet50.setIcon(getImageIcon("chipBlue"));
                btnBet20.setIcon(getImageIcon("chipGreen"));
                btnBet10.setIcon(getImageIcon("chipRedSelected"));
                btnBetCustom.setIcon(getImageIcon("chipWhite"));
                break;
            case "green":
                btnBet100.setIcon(getImageIcon("chipBlack"));
                btnBet50.setIcon(getImageIcon("chipBlue"));
                btnBet20.setIcon(getImageIcon("chipGreenSelected"));
                btnBet10.setIcon(getImageIcon("chipRed"));
                btnBetCustom.setIcon(getImageIcon("chipWhite"));
                break;
            case "white":
                btnBet100.setIcon(getImageIcon("chipBlack"));
                btnBet50.setIcon(getImageIcon("chipBlue"));
                btnBet20.setIcon(getImageIcon("chipGreen"));
                btnBet10.setIcon(getImageIcon("chipRed"));
                btnBetCustom.setIcon(getImageIcon("chipWhiteSelected"));
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
                        lblCash.setText("Current money: \u20ac" + DECI_FORM.format(money));
                        BETS.add(new Bet(click.getMultiplier(), click.getNumbers(), currentWager, new JLabel()));
                        lblTotalWager.setText("Total wager: \u20ac" + calculateWager());
                        BETS.get(BETS.size() - 1).getLabel().addMouseListener(new MouseAdapter() {
                            @Override
                            public void mousePressed(MouseEvent e) {
                                layer.remove((JLabel) e.getSource());
                                layer.repaint();
                                for (int i = BETS.size() - 1; i > -1; i--) {
                                    Bet bet;
                                    if ((bet = BETS.get(i)).getLabel() == (JLabel) e.getSource()) {
                                        money += bet.getWager();
                                        lblCash.setText("Current money: \u20ac" + DECI_FORM.format(money));
                                        BETS.remove(bet);
                                        lblTotalWager.setText("Total wager: \u20ac" + calculateWager());
                                    }
                                }
                            }
                        });
                        int placeX = (click.getMinX() + click.getMaxX()) / 2 - 15;
                        int placeY = (click.getMinY() + click.getMaxY()) / 2 - 15;
                        BETS.get(BETS.size() - 1).getLabel().setBounds(placeX, placeY, 30, 30);
                        JLabel label = BETS.get(BETS.size() - 1).getLabel();
                        String img;
                        switch (currentWager) {
                            case 10:
                                img = "chipRedMini";
                                break;
                            case 20:
                                img = "chipGreenMini";
                                break;
                            case 50:
                                img = "chipBlueMini";
                                break;
                            case 100:
                                img = "chipBlackMini";
                                break;
                            default:
                                img = "chipWhiteMini";
                                break;
                        }
                        label.setIcon(getImageIcon(img));
                        layer.add(BETS.get(BETS.size() - 1).getLabel());
                        layer.moveToFront(BETS.get(BETS.size() - 1).getLabel());
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
        for (Bet bet : BETS) {
            wager += bet.getWager();
        }
        Wager = DECI_FORM.format(wager);
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
    private int getPayout() {
        int pay = 0;
        random = (int) (Math.random() * 37);

        for (Bet bet : BETS) {
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
            ANGLE_MAP.put(numbers[i], angle);
            angle += addAngle;
        }
    }

    private void initComps() {
        JLabel background = getLabel("green");
        background.setBounds(978, 0, 300, 500);
        layer.add(background);

        lblChooseWager = getLabel("lblChooseWager");
        lblChooseWager.setBounds(960, 40, 260, 53);

        lblTotalWager = new JLabel("Total wager: \u20ac" + calculateWager());
        lblTotalWager.setBounds(10, 5, 250, 20);
        lblTotalWager.setForeground(Color.WHITE);
        lblTotalWager.setFont(new Font("Tahoma", Font.PLAIN, 16));

        lblCurrentWager = new JLabel("Current wager: \u20ac"
                + DECI_FORM.format(currentWager));
        lblCurrentWager.setBounds(540, 5, 250, 20);
        lblCurrentWager.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblCurrentWager.setForeground(Color.WHITE);

        btnPlay = getButton("btnPlay");
        btnPlay.setBounds(1009, 333, 162, 53);
        btnPlay.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnPlay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isActive) {
                    if (BETS.size() > 0) {
                        CURRENT.addRoulettePlayed();
                        setActive(false);
                        double pay = getPayout();
                        money += pay;
                        CURRENT.setMoney(money);
                        double angle = (Math.toRadians((1440 + ANGLE_MAP.get(random)) * 10));
                        wheelFrame = new JDialog();
                        JLabel background = getLabel("backgroundWheel");

                        wheelFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
                        wheelFrame.setModal(true);
                        wheelFrame.addWindowListener(new WindowAdapter() {
                            @Override
                            public void windowClosing(WindowEvent e) {
                                try {
                                    Result result = new Result(random, pay, wager);
                                    lblCash.setText("Current money: \u20ac" + DECI_FORM.format(money));
                                    result.addWindowListener(new WindowAdapter() {
                                        @Override
                                        public void windowClosing(WindowEvent e) {
                                            for (Bet bet : BETS) {
                                                layer.remove(bet.getLabel());
                                            }
                                            setActive(true);
                                            layer.repaint();
                                            BETS.clear();
                                            wager = 0;
                                            lblTotalWager.setText("Total wager: \u20ac" + DECI_FORM.format(wager));
                                            result.dispose();
                                        }
                                    });
                                    wheelFrame.dispose();
                                } catch (IOException ex) {
                                    Logger.getLogger(Betting.class.getName()).log(Level.SEVERE, null, ex);
                                }
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

        btnBack = getButton("btnBack");
        btnBack.setBounds(1009, 406, 162, 53);
        btnBack.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnBack.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Main.setLastPanel();
                Main.changeTitle("Casino");
            }
        });

        btnBet10 = getButton("chipRedSelected");
        btnBet10.setBounds(1007, 112, 87, 87);
        btnBet10.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnBet10.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (isActive) {
                    currentWager = 10;
                    lblCurrentWager.setText("Current wager: \u20ac"
                            + DECI_FORM.format(currentWager));
                    selectChip("red");
                }
            }
        });

        btnBet20 = getButton("chipGreen");
        btnBet20.setBounds(1107, 112, CHIP_SIZE, CHIP_SIZE);
        btnBet20.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnBet20.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (isActive) {
                    currentWager = 20;
                    lblCurrentWager.setText("Current wager: \u20ac"
                            + DECI_FORM.format(currentWager));
                    selectChip("green");
                }
            }
        });

        btnBet50 = getButton("chipBlue");
        btnBet50.setBounds(957, 212, CHIP_SIZE, CHIP_SIZE);
        btnBet50.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnBet50.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (isActive) {
                    currentWager = 50;
                    lblCurrentWager.setText("Current wager: \u20ac"
                            + DECI_FORM.format(currentWager));
                    selectChip("blue");
                }
            }
        });

        btnBet100 = getButton("chipBlack");
        btnBet100.setBounds(1157, 212, CHIP_SIZE, CHIP_SIZE);
        btnBet100.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnBet100.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (isActive) {
                    currentWager = 100;
                    lblCurrentWager.setText("Current wager: \u20ac"
                            + DECI_FORM.format(currentWager));
                    selectChip("black");
                }
            }
        });

        btnBetCustom = getButton("chipWhite");
        btnBetCustom.setBounds(1057, 212, CHIP_SIZE, CHIP_SIZE);
        btnBetCustom.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnBetCustom.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (isActive) {
                    int customWager;

                    Box box = new Box(BoxLayout.PAGE_AXIS);
                    JPanel panel = new JPanel(new BorderLayout());
                    JLabel betLabel = new JLabel("Enter your wager:");
                    betLabel.setFont(STANDARD_FONT);
                    JTextField txt = new JTextField(Long.toString(currentWager), 10);
                    txt.setFont(STANDARD_FONT);
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
                                            + DECI_FORM.format(currentWager));
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

        lblCash = new JLabel("Current money: \u20ac" + DECI_FORM.format(money));
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

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/img/tableLayout.png"))); // NOI18N
        jLabel1.setPreferredSize(new java.awt.Dimension(1278, 500));

        layer.setLayer(jLabel1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout layerLayout = new javax.swing.GroupLayout(layer);
        layer.setLayout(layerLayout);
        layerLayout.setHorizontalGroup(
            layerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layerLayout.setVerticalGroup(
            layerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layerLayout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

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

        if (x >= MOUSE_X_MIN && x <= MOUSE_X_MAX && y >= MOUSE_Y_MIN && y <= MOUSE_Y_MAX) {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }
    }//GEN-LAST:event_formMouseMoved

    //Swing components
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLayeredPane layer;
    // End of variables declaration//GEN-END:variables
}
