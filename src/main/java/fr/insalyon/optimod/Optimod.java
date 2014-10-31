package fr.insalyon.optimod;

import fr.insalyon.optimod.controllers.ApplicationController;

import javax.swing.*;

/**
 * The Application singleton
 */
public class Optimod {
    private final ApplicationController mApplicationController;

    /**
     * The classic Java Main
     * @param args The CLI Arguments
     */
    public static void main(String args[]) {
        System.setProperty("apple.laf.useScreenMenuBar", "true");
        System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Test");
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        Optimod app = new Optimod();
        app.run();
    }

    /**
     * Default constructor
     *
     * Loads settings and initializes the Application's context
     */
    public Optimod() {
        mApplicationController = new ApplicationController();
    }

    /**
     * Runs the application
     *
     * This method is only called by main
     */
    public void run() {
        mApplicationController.onStart();
    }
}
