package repository;


import java.util.Comparator;
import java.util.List;

public interface ICrudRepository<E>{


    /**
     *@param entity-die gesuchte Entitat
     *             entitiy soll nicht null sein
     *@return null daca nu gaseste si entiatea daca da
     */
    E find(E entity);

    /** returniert die Liste
     *@return alle Entitaten
     */
    List<E> getAll();

    /** Fugt ein Element in der Liste hin
     *@param entity-die Entitat, die hingefugt
     *             wird
     *@return null- wenn die Entitat erfolgreich
     * hingefugt wurde, oder die Entitat, wenn
     * sie schon in der Liste ist oder sie null
     * ist
     */
    E add(E entity);

    /*** loscht die gegebene Entitat aus
     * der Liste
     * @param entity-entity soll nicht null sein
     * @return die geloschte Entitat oder
     * null, wenn die Liste beinhaltet die
     * Entitat nicht
     * */
    E delete(E entity);

    /*** aktualisiert die gegebene Entitat in
     * der Liste
     * @param entity-entity soll nicht null sein
     *@return null - wenn die Entitat erfolgreich
     * aktualisiert wurde, anderer falls gibt
     * die Entitat zuruck (z.B die Entitat existiert
     * nicht).
     **/
    E update(E entity);
}
