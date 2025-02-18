package tn.esprit.interfaces;

import java.util.List;

public interface IService<T> {
    void add(T user);
    List<T> getAll();
    void update(T user);
    void delete(T user);
    T getByIduser(int i);

}
