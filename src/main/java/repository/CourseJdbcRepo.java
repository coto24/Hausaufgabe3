package repository;

import entities.Course;

import java.sql.SQLException;
import java.util.List;

public class CourseJdbcRepo extends SqlRepository<Course>{
    protected int indx;

    public CourseJdbcRepo() {
        this.indx = 1;
    }
    /**
     * @param entity -die gesuchte Entitat
     * @return null daca nu gaseste si entiatea daca da
     */
    @Override
    public Course find(Course entity) {
        String sql = String.format("select * from courses where id=%d or name='%s'", entity.getCourseId(), entity.getName());
        return getEntity(sql, new CourseRowMapper());
    }

    /**
     * returniert die Liste
     *
     * @return alle Entitaten
     */
    @Override
    public List<Course> getAll() {
        try {
            String sql = "select * from courses";
            return getRows(sql, new CourseRowMapper());
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * Fugt ein Element in der Liste hin
     *
     * @param entity -die Entitat, die hingefugt
     * @return null- wenn die Entitat erfolgreich
     * hingefugt wurde, oder die Entitat, wenn
     * sie schon in der Liste ist oder sie null
     * ist
     */
    @Override
    public Course add(Course entity) {
        if(entity.getCourseId()==0)
            entity.setCourseId(indx++);
        String sql = String.format("insert into courses values(%d, '%s', %d, %d, %d)",entity.getCourseId(),entity.getName(),entity.getTeacher(),entity.getMaxEnrollment(),entity.getCredits());
        try {
            updateTable(sql);
        }
        catch (SQLException exception) {
            System.out.println(exception.getMessage());
            return null;
        }
        return entity;
    }

    /*** loscht die gegebene Entitat aus
     * der Liste
     * @param entity -entity soll nicht null sein
     * @return die geloschte Entitat oder
     * null, wenn die Liste beinhaltet die
     * Entitat nicht
     * */
    @Override
    public Course delete(Course entity) {
        String sql1 = String.format("delete from courses where id=%d",  entity.getCourseId());
        String sql2 = String.format("delete from students_courses where id_course=%d", entity.getCourseId());
        try {
            updateTable(sql1);
            updateTable(sql2);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
        return entity;
    }

    /*** aktualisiert die gegebene Entitat in
     * der Liste
     * @param entity -entity soll nicht null sein
     *@return null - wenn die Entitat erfolgreich
     * aktualisiert wurde, anderer falls gibt
     * die Entitat zuruck (z.B die Entitat existiert
     * nicht).
     *
     */
    @Override
    public Course update(Course entity) {
        String sql = String.format("update courses " +
                "set id_teacher=%d, maxEnrollment=%d, credits=%d" +
                "where name='%s' or id=%d", entity.getTeacher(), entity.getMaxEnrollment(), entity.getCredits(), entity.getName(), entity.getCourseId());
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
