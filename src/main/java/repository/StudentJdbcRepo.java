package repository;

import entities.Student;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;

public class StudentJdbcRepo extends SqlRepository<Student> {

    /**sucht der Index einer Element in der Liste
     *@param entity-die gesuchte Entitat
     *             entitiy soll nicht null sein
     *@return null daca nu gaseste si entiatea daca da
     */
    @Override
    public Student find(Student entity) {
        String sql = String.format("select * from students where id=%d or (firstName='%s' and lastName='%s')", entity.getStudentId(), entity.getFirstName(), entity.getLastName());
        return getEntity(sql, new StudentRowMapper());
    }

    /** returniert die Liste
     *@return alle Entitaten
     */
    @Override
    public List<Student> getAll() {
        try {
            String sql = "select * from students";
            return getRows(sql, new StudentRowMapper());
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
    public Student add(Student entity) {
        String sql = String.format("insert into students values(%d, %d, '%s', '%s')", entity.getStudentId(), entity.getTotalCredits(), entity.getFirstName(), entity.getLastName());
        try {
            updateTable(sql);
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
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
    public Student delete(Student entity) {
        String sql = String.format("delete from students where id=%d",  entity.getStudentId());
        String sql1 = String.format("delete from students_courses where id_student=%d", entity.getStudentId());
        try {
            updateTable(sql);
            updateTable(sql1);
        } catch (SQLException e) {
            e.printStackTrace();
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
    public Student update(Student entity) {
        String sql = String.format("update students " +
                "set firstName='%s', lastName='%s', totalCredits=%d" +
                "where studentId=%d", entity.getFirstName(), entity.getLastName(), entity.getTotalCredits(), entity.getStudentId());
        try {
            updateTable(sql);
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            return entity;
        }
        return null;
    }

}