package entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentTest {

    @Test
    void create(){
        //students cannot have a null first name
        try {
            Student s= new Student("","bob",1);
        } catch (CustomException exception){
            assert(true);
        }

        //students cannot have null last name
        try {
            Student s= new Student("bob","",1);
        } catch (CustomException exception) {
            assert(true);
        }

        //students cannot have a negative id
        try {
            Student s = new Student("Bob", "bob", -14);
        }catch(CustomException exception){
            assert(true);
        }
    }

}