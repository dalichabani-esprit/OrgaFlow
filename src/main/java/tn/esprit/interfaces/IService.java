
package tn.esprit.interfaces;

import tn.esprit.models.Candidat;
import tn.esprit.models.Employes;

import java.util.List;

public interface IService<T> {
    void add(T user);
    List<T> getAll();
    void update(T user);
    void delete(T user);
    T getByIduser(int i);
    boolean emailExiste(String email);

    int getTotalCandidats();

    int getTotalEmployes();

    List<Candidat> searchCandidatsByKeyword(String keyword);

   List<Employes> searchEmployesByKeyword(String keyword);
}
