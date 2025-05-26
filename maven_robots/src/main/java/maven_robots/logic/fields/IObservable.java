package maven_robots.logic.fields;

public interface IObservable {
    void addObserver(FieldObserver observer);
    void removeObserver(FieldObserver observer);
    void notifyObservers();
}
