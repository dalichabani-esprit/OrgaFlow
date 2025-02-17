package tn.esprit.interfaces;
import javafx.scene.control.ListView;
import tn.esprit.models.Candidat;

import java.util.List;

public interface IService<T> {

    void add(T t);

    List<T> getAll();

    void update(T t);

    T getById(int id);

    void delete(T t);





}
