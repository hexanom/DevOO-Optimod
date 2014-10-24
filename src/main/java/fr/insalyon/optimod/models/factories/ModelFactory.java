package fr.insalyon.optimod.models.factories;

import fr.insalyon.optimod.models.Model;

/**
 * Factory for creating models
 */
public interface ModelFactory<T extends Model> {
    /**
     * Creates the model object
     * @return The new model
     */
    public T create() throws Exception;
}
