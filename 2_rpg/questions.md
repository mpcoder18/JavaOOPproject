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
Lastly, the getters and setter methods also  provides more testabiltity. You can easily isolate the methods in a different class to ensure that the methods work as intended.

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
