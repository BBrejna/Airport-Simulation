package model.classes;

public interface Observer<T> {
    void updateState(T t);
}
