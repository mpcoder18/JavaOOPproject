# Question 1

What is the difference between a class and an object?

___

Answer:

___

# Question 2

Given are the following three classes:

`Person.java`:

```java
public class Person {
    private String name;

    public Person(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void printName() {
        System.out.println(name);
    }
}
```

`PersonModifier.java`:

```java
public class PersonModifier {

    public Person modifyPerson1(Person person) {
        person.setName("Bert");
        return person;
    }

    public Person modifyPerson2(Person person) {
        person.setName("Gerry");
        person = new Person("James");
        return person;
    }
}
```

`Main.java`:

```java
public class Main {

    public static void main(String[] args) {
        Person person1 = new Person("John");
        Person person2 = new Person("Bob");

        PersonModifier personModifier = new PersonModifier();
        Person modifiedPerson1 = personModifier.modifyPerson1(person1);
        Person modifiedPerson2 = personModifier.modifyPerson2(person2);

        person1.printName();
        person2.printName();
        modifiedPerson1.printName();
        modifiedPerson2.printName();
    }
}
```

What is the output of this program? Explain why.

___

Answer: The output should be
```
Bert
Gerry
Bert
James
```
In the main method, person1 and person2 are created and initialized with the names "John" and "Bob" respectively.
However, personModifier has 2 methods, that are called in the main method, that call the setter method of the Person object.
In this case, personModifier.modifyPerson1(person1) will set the name of the argument (person1) to "Bert" and return the person1 object, and
personModifier.modifyPerson2(person2) will set the name to Gerry, but creates a new person named "James" that is then returned.
In this case, modifiedPerson1 contains the person1 object, and both are named "Bert", whilst modifiedPerson2 contains the newly
created person named "James", while person2 has had its named changed to "Gerry". Hence with the printName method called we get the
previously mentioned output.

___
