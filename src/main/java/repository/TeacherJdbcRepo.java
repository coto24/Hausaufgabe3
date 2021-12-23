package repository;

import entities.Teacher;

import java.sql.SQLException;
import java.util.List;

public class TeacherJdbcRepo extends SqlRepository<Teacher>{
    protected int indx;

    public TeacherJdbcRepo() {
        this.indx = 1;
    }

    /***
     *
     * @param entity-die gesuchte Entitat
     * @return null daca nu gaseste si entiatea daca da
     */
    @Override
    public Teacher find(Teacher entity) {
        String sql = String.format("select * from teachers where id= %d or (firstName='%s' and lastName='%s')", entity.getTeacherId(),entity.getFirstName(),entity.getLastName());
        return getEntity(sql, new TeacherRowMapper());
    }

    @Override
    public List<Teacher> getAll() {
        try {
            String sql = "select * from teachers";
            return getRows(sql, new TeacherRowMapper());
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    /** Fugt ein Element in der Liste hin
     *@param entity-die Entitat, die hingefugt
     *             wird
     *@return null- wenn die Entitat erfolgreich
     * hingefugt wurde, oder die Entitat, wenn
     * sie schon in der Liste ist oder sie null
     * ist
     */
    @Override
    public Teacher add(Teacher entity) {
        if(entity.getTeacherId()==0)
            entity.setTeacherId(indx++);
        String sql = String.format("insert into teachers values(%d, '%s', '%s')", entity.getTeacherId(), entity.getFirstName(), entity.getLastName());
        try {
            updateTable(sql);
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());;
            return entity;
        }
        return null;
    }

    /*** loscht die gegebene Entitat aus
     * der Liste
     * @param entity-entity soll nicht null sein
     * @return die geloschte Entitat oder
     * null, wenn die Liste beinhaltet die
     * Entitat nicht
     * */
    @Override
    public Teacher delete(Teacher entity) {
        String sql = String.format("delete from teachers where id=%d",  entity.getTeacherId());
        try {
            updateTable(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());;
            return null;
        }
        return entity;
    }

    /*** aktualisiert die gegebene Entitat in
     * der Liste
     * @param entity-entity soll nicht null sein
     *@return null - wenn die Entitat erfolgreich
     * aktualisiert wurde, anderer falls gibt
     * die Entitat zuruck (z.B die Entitat existiert
     * nicht).
     **/
    @Override
    public Teacher update(Teacher entity) {
        String sql = String.format("update teachers " +
                "set firstName= '%s', lastName= '%s'" +
                "where id= %d", entity.getFirstName(), entity.getLastName(), entity.getTeacherId());
        try {
            updateTable(sql);
        }
        catch (SQLException exception) {
            System.out.println(exception.getMessage());
            return entity;
        }
        return null;
    }
}
