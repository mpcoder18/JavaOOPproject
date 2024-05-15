# Question 1

In step 1, you were asked to create a `Room` class with a description, which will be printed if inspected. Two software developers proposed two different implementations for the `Room` class.

The first developer proposed one implementation:

```java
public class Room {

    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
```

The second developer proposed another implementation:

```java
public class Room {
    public String description;
}
```

Both developers are discussing which implementation is better and why. Please answer the following question:

Which of these two implementations would you select? And why?

Justify your answer in at least 250 words. Please explain the consequences, benefits and drawbacks of each implementation and support it with an example.

___

Answer: The biggest benefit of making a field private is that the field is protected from outside the class. With a public access modifier the access level is: inside a class, inside the package and outside the package, So basically everywhere. When you are using private fields it is impossible to alter the field of a class without using the methods that are public in that class. For example if you have a password field, you don’t want that field to be public because then everyone can have access to your password. Another example could be your bank account. From the bank’s perspective you don’t want anyone else to be able to change their balance. Finally, in our case, we don’t want anyone to alter the description of the room.
Furthermore, using private fields in combination with getters and setters also improves the flexibility. You could for instance make more additions to the getter and setter methods like a print statement.
Lastly, the getters and setter methods also  provides more testability. You can easily isolate the methods in a different class to ensure that the methods work as intended.

On the other hand a case where a public field could be beneficial are constants. In this case you do not have to make the constant in every class. This would lead to more simplistic code.

The benefits of making private fields with getters and setters heavily outweigh the benefits of using public fields. Therefore we prefer to use the class using the private field in combination with getters and setters.


___

# Question 2

In step 2, you are asked to create two interfaces: `Inspectable` and `Interactable`.
Interfaces by definition do not have implementations. They consist of method signatures:

```java
interface Inspectable {
    void inspect();
}
```

A software developer claims that interfaces are not useful, because they do not contain implementations. Thus, we should just use classes, and we do not need to define empty interfaces.

What do you think about this opinion? Do you agree or disagree with this opinion?

Please justify your answer in at least 250 words, and support your justifications with an example.

___

Answer:
We disagree with the claim that interfaces are not useful even tough interfaces are not required for a program to work. In the case of our program Inspectable and Interactable are not necessary interfaces but these interfaces do make your life easier.
What we mean with not necessary is that it is possible to code without interfaces. You could for example make all the classes with the same method but then you should make use of “instanceof” to determine which class object you want to use. This code can become very ugly and unreadable.
Interfaces solves this problem. Interfaces enables the concept of polymorphism which allows different classes to implement the same interface to get the same behaviours in different classes but a different implementation. For example you can have different animal classes: Dogs, cats, rhino’s and lions. These are all different animals but they do share behaviour like eating and making noise. In this animal case you could make a eating interface and a makeNoise interface. In the animal classes you can implement these interfaces and this enforces you to make use of the possible methods like eat() or makeNoise() in.
Secondly, Interface also allows for code reusability because you only have to make an interface ones and you can implement it everywhere you want.
Furthermore, interfaces also improves the code documentation and overall readability. For instance if you see that a class implements specific interfaces then you already know what kind of behaviour an object of that class can have.
Finally, you should be also considering an abstract class instead of an interface. Interfaces and abstract classes both share the kind of contract they make if you make use of them. In both cases you have that certain methods are enforced in the child class in the case of an abstract class and in the class where the interface is implemented in the case of an interface. The key difference between the 2 is the relationship with the classes that implement the interfaces/abstract classes. You should use an abstract class if the child classes are components of the super class for example a vehicle. A car is an example of a vehicle and could be a child class. We know for sure that a vehicle accelerates so that could be an abstract method. In the cases of a flyable interface can have a landing method and a takeoff method and a class that could implement this is an airplane or a helicopter. 
___

# Question 3

To save your game state, you were asked to use Java classes `FileOutputStream` and `ObjectOutputStream`.
These two classes are part of the Java libraries, and they are designed based on a specific design pattern.

Which design pattern do `FileOutputStream` and `ObjectOutputStream` implement?

Explain the roles of this design pattern and how `FileOutputStream` and `ObjectOutputStream` implement it. Also explain the benefit of implementing this design pattern.

___

Answer: `FileOutputStream` and `ObjectOutputStream` both implement the Decorator design
pattern. It allows adding new behaviors to objects dynamically by placing them inside
special wrapper objects that contain these behaviors. The Decorator design pattern
provides an alternative to creating subclasses to add new functionality to objects. The benefit
of implementing this design pattern is that it allows adding new functionality to an object
without altering the existing code. In the case of `FileOutputStream` and
`ObjectOutputStream`, they implement the Decorator design pattern by wrapping the
underlying streams with additional functionality. For example, `FileOutputStream` adds
the ability to write data to a file, while `ObjectOutputStream` adds the ability to write
objects to a stream. That way, the Decorator design pattern allows adding new
functionality without breaking the code that uses the original objects.

___
