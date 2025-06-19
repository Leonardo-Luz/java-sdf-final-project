package ifrs.edu.br.controllers;

/**
 * Controller
 */
public interface Controller<T> {

    public void insertHandler(T object);

    public T findHandler(int id);

    public void updateHandler(T object);

    public void deleteHandler(int id);
}
