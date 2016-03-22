/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Roulette;

import Casino.Player;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.text.DecimalFormat;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

/**
 *
 * @author Davey
 */
public class Result extends javax.swing.JDialog {

    private final Font standardFont;
    private final DecimalFormat deciForm = new DecimalFormat("0.00");

    /**
     * Creates new form Result
     */
    public Result() {
        this.standardFont = new Font("Tahoma", Font.PLAIN, 16);
        setTitle("Result");
        setIconImage(new ImageIcon(getClass().getResource("/Img/windowIcon.png")).getImage());
//        setUndecorated(true);
        initComponents();
    }

    /**
     * Create Result object 
     * 
     * @param random The randomly picked roulette result number
     * @param gain The gain made by the player this bet
     * @param wager The total wager the player bet
     */
    public Result(int random, double gain, double wager) {
        this();

        JLabel lblNumberImg = new JLabel(new ImageIcon(getClass().getResource("/Img/wheel" + random + ".png")));
        JLabel lblNumber = new JLabel("The winning number is: " + random + "!");
        JLabel lblPayout = new JLabel();
        JButton btnContinue = new JButton(new ImageIcon(getClass().getResource("/Img/btnContinue.png")));

        lblNumberImg.setBounds(173, 15, 54, 82);
        if (gain - wager > 0) {
            Player.getCurrentPlayer().addRouletteWon();
            lblNumberImg.setBorder(BorderFactory.createLineBorder(Color.GREEN, 2));
            lblPayout.setText("You earned \u20ac" + deciForm.format(gain - wager) + "!");
        } else if (gain - wager == 0) {
            lblNumberImg.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
            lblPayout.setText("You made no profit this game..");
        } else {
            lblNumberImg.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
            lblPayout.setText("You lost \u20ac" + deciForm.format(wager - gain) + "...");
        }

        lblNumber.setBounds(100, 127, 200, 20);
        lblNumber.setFont(standardFont);
        lblNumber.setForeground(Color.WHITE);

        lblPayout.setBounds(100, 167, 230, 20);
        lblPayout.setFont(standardFont);
        lblPayout.setForeground(Color.WHITE);

        btnContinue.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                proceed();
            }
        });
        
        btnContinue.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                proceed();
            }
        });
        btnContinue.setBounds(118, 219, 164, 53);
        btnContinue.requestFocusInWindow();

        layer.add(lblNumberImg);
        layer.add(lblNumber);
        layer.add(lblPayout);
        layer.add(btnContinue);

        layer.moveToFront(lblNumberImg);
        layer.moveToFront(lblNumber);
        layer.moveToFront(lblPayout);
        layer.moveToFront(btnContinue);
        setAlwaysOnTop(true);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Throw closing event for the displayed frame
     */
    private void proceed() {
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
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

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setResizable(false);
        setSize(new java.awt.Dimension(0, 0));

        layer.setMaximumSize(new java.awt.Dimension(400, 300));

        background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/background.png"))); // NOI18N

        javax.swing.GroupLayout layerLayout = new javax.swing.GroupLayout(layer);
        layer.setLayout(layerLayout);
        layerLayout.setHorizontalGroup(
            layerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(background)
        );
        layerLayout.setVerticalGroup(
            layerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(background)
        );
        layer.setLayer(background, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(layer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(layer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel background;
    private javax.swing.JLayeredPane layer;
    // End of variables declaration//GEN-END:variables
}

