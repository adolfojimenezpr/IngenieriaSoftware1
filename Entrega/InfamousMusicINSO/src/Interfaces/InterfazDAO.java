package Interfaces;

import java.util.ArrayList;

public interface InterfazDAO<T> {

	public int create(T elemento);

	public boolean delete(Object clave);

	public boolean update(T elemento);

	public T read(Object clave);

	public ArrayList<T> readAll();
}
