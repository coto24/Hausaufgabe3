package entities;


public abstract class Person {
    private String firstName;
    private String lastName;

    public Person(){}

    public Person(String firstName, String lastName) throws CustomException {
        if (firstName.isEmpty() || lastName.isEmpty()) {
            throw new CustomException("Name cannot be null");
        }

        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "entities.Person{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
