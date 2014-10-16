package fr.insalyon.optimod;

/**
 * The Application singleton
 */
public class Application {
    private static Application ourInstance = new Application();

    /**
     * Asks for the singleton
     * @return The unique Application
     */
    public static Application getInstance() {
        return ourInstance;
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
    public Application() {

    }

    /**
     * Runs the application
     *
     * This method is only called by main
     */
    private void run() {
        System.out.println("Hello World");
    }
}
