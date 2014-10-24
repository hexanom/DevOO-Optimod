package fr.insalyon.optimod.models.factories;

/**
 * Factory for creating models
 */
public interface ModelFactory<T> {
    /**
     * Creates the model object
     * @return The new model
     */
    public T create() throws Exception;
}
