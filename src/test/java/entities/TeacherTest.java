package entities;

import org.junit.jupiter.api.Test;


class TeacherTest {
    @Test
    void create(){
        //teachers cannot have a null first name
        try {
            Teacher t= new Teacher("","bob");
        } catch (CustomException exception){
            assert(true);
        }

        //teachers cannot have null last name
        try {
            Teacher t= new Teacher("bob","");
        } catch (CustomException exception) {
            assert(true);
        }
    }
}