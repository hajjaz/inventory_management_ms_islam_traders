/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities;

import static Utilities.Utilities.ReadLanguageProperties;
import java.util.HashMap;
import javax.swing.JButton;

/**
 *
 * @author Hajjaz
 */
public abstract class UIInitializer extends javax.swing.JFrame {
    protected HashMap<String, JButton> buttonMap;
    
    
    
    protected void initialize() {
        buttonMap = new HashMap<>();
        initiateJButtons();
    }
    
    protected abstract void initiateJButtons();
    
}
