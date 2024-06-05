package nl.rug.oop.rts;

import com.formdev.flatlaf.FlatDarculaLaf;

import javax.swing.*;

/**
 * Main class of the application.
 */
public class Main {

    /**
     * Main method of the application.
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        FlatDarculaLaf.setup(); // Dark mode
        SwingUtilities.invokeLater(MainSetup::new);
    }
}