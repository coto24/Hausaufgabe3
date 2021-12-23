package entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CourseTest {

    @Test
    void create(){
        //courses cannot have a null name
        try {
            Course course = new Course("",0,50,5);
        } catch (CustomException exception){
            assert(true);
        }

        //courses cannot have negative credits
        try {
            Course course = new Course("Mathematik",0,50,-5);
        } catch (CustomException exception) {
            assert(true);
        }
    }
}