/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package object.shared;

import java.awt.Dimension;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JLabel;
import static resources.java.shared.ImageLabel.getImageIcon;
import static views.shared.Main.convertSize;

/**
 *
 * @author Dave
 */
public class NumberLabel extends JLabel {

    private static Map<Integer, Dimension> sizes;

    public NumberLabel(int number, NumberLabel prev, Point loc) {
        super();

        if (prev == null && loc == null) {
            throw new NullPointerException();
        }
        if (number < 0 || number > 9) {
            throw new IndexOutOfBoundsException("number must be >= 0 && <= 9");
        }
        initSizes();
        
        setSize(sizes.get(number));
        setLocation(prev, loc);
        setIcon(getImageIcon(Integer.toString(number)));
    }
    
    public static int getTotalWidth(String[] numbers){
        initSizes();
        int size = 0;
        for(String n : numbers){
            size += sizes.get(Integer.parseInt(n)).width;
        }
        
        return size;
    }

    private void setLocation(NumberLabel prev, Point loc) {
        if (prev != null) {
            int x = prev.getLocation().x + prev.getSize().width;
            int y = prev.getLocation().y;

            super.setLocation(x, y);
        } else if (loc != null) {
            super.setLocation(loc);
        } else {
            super.setLocation(0, 0);
        }
    }

    private static void initSizes() {
        final int height = convertSize(28);
        sizes = new HashMap<>();
        sizes.put(0, new Dimension(convertSize(20), height));
        sizes.put(1, new Dimension(convertSize(13), height));
        sizes.put(2, new Dimension(convertSize(19), height));
        sizes.put(3, new Dimension(convertSize(20), height));
        sizes.put(4, new Dimension(convertSize(20), height));
        sizes.put(5, new Dimension(convertSize(19), height));
        sizes.put(6, new Dimension(convertSize(18), height));
        sizes.put(7, new Dimension(convertSize(17), height));
        sizes.put(8, new Dimension(convertSize(20), height));
        sizes.put(9, new Dimension(convertSize(18), height));
    }
}
