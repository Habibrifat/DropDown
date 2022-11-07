/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.dropdown;

import static java.awt.Color.green;
import java.awt.Container;
import javax.swing.JComboBox;
import javax.swing.JFrame;

/**
 *
 * @author Rifat-IT
 */
public class ComboBox extends JFrame {
    private Container c;
    private JComboBox jb;
    private final String[] items = {"Apple","Ball","Cat","Dog","Egg","Fish"};
    
    ComboBox(){
        initComponents();
    }
    
    public static void main(String[] args) {
        
        ComboBox frame = new ComboBox();
        frame.setVisible(true);
    }

    private void initComponents() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(100, 100, 500, 500);
        this.setTitle("ComboBox Demo");
        
        c = this.getContentPane();
        c.setLayout(null);
        c.setBackground(green);
        
//        JComboBox
        jb = new JComboBox(items);
        jb.setBounds(50, 150, 100, 50);
        jb.setEditable(true);
        
        
        c.add(jb);
        
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
           
    
}
