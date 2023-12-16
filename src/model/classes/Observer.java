package model.classes;

public interface Observer<T> {
    void calculateDelayProbability(T t);
}
