package fr.insalyon.optimod;

import fr.insalyon.optimod.models.Map;
import fr.insalyon.optimod.models.TomorrowDeliveries;

/**
 * The Application singleton
 */
public class Application {
    private static Application oInstance = new Application();

    private Map mCurrentMap;
    private TomorrowDeliveries mCurrentDeliveries;

    /**
     * Asks for the singleton
     * @return The unique Application
     */
    public static Application getInstance() {
        return oInstance;
    }

    /**
     * The classic Java Main
     * @param args The CLI Arguments
     */
    public static void main(String args[]) {
        Application app = Application.getInstance();
        app.run();
    }

    /**
     * Default constructor
     *
     * Loads settings and initializes the Application's context
     */
    private Application() {

    }

    /**
     * Runs the application
     *
     * This method is only called by main
     */
    private void run() {
        System.out.println("Hello World");
    }

    /**
     * Gets the current map attached to the Application instance
     * @deprecated
     * @return A Map
     */
    public Map getCurrentMap() {
        return mCurrentMap;
    }

    /**
     * Sets the current map attached to the Application
     * @deprecated
     * @param currentMap A Map
     */
    public void setCurrentMap(Map currentMap) {
        mCurrentMap = currentMap;
    }

    /**
     * Gets the current deliveries attached to the application
     * @deprecated
     * @return A TomorrowDeliveries
     */
    public TomorrowDeliveries getCurrentDeliveries() {
        return mCurrentDeliveries;
    }

    /**
     * Sets the current deliveries attached to the application
     * @deprecated
     * @param currentDeliveries A TomorrowDeliveries
     */
    public void setCurrentDeliveries(TomorrowDeliveries currentDeliveries) {
        mCurrentDeliveries = currentDeliveries;
    }
}
