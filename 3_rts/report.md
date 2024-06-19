# Report

Niels de Boer (s4611896) & Foo Bar (s2345678)

## Introduction

This program is a real-time strategy game (RTS) where the user can
simulate battles between two teams of factions. The user can freely
manipulate the map to create new nodes and edges, and place armies on
nodes. The armies move randomly between nodes and events can occur,
having random effects on the armies. They also fight each other when
they meet. The user can save and load states of the game from files,
and sound effects are played in response to certain actions.

## Program design

> *Here you go over the structure of the program. Try not to go too in-depth here implementation-wise, but rather discuss the important components and relations between them. 
> If you think it can help, feel free to add a simple diagram here. The design of the program should be clear to the reader. 
> 
> In particular, describe the model of the program. How is it structured? How did you make sure to separate the different aspects of the program?
> How do the `model`, `view` and `controller` interact with each other?
> Additionally, you should include some design decisions in here. There is no need to provide an explanation for every single thing, 
> but there are often multiple ways of implementing a feature and in those cases it makes sense to state why you chose one over the other.*

> Expected length: as much as you need to explain the above.

## Evaluation of the program

> *Discuss the stability of your implementation. What works well? Are there any bugs? Is everything tested properly? Are there still features that have not been implemented? Also, if you had the time, what improvements would you make to your implementation? Are there things which you would have done completely differently?*

>Expected length: ~300-500 words

## Questions

Please answer the following questions:

1. In this assignment, the program should follow the Model View Controller (MVC) pattern. Please explain the design of the program in terms of the MVC pattern. Specifically try to answer the following questions:
   - MVC consists of three components: Model, view and controller. Can you please explain the role of each component? Please provide examples of these roles from the assignment. How are these three roles (i.e. Model, view and controller) are implemented in the assignment?
   - MVC enforces special constraints on the dependencies between its three components: Model, view and controller. Please explain these constraints, and why are they important?

___

Answer: In the MVC pattern, the model is responsible for the data and the logic of the program.
The view is responsible for the user interface and the controller is responsible for the interaction
between the model and the view. In the assignment, the model is represented by the `GraphModel` class,
which contains data about the nodes and edges of the graph, which in turn contain data about the armies
and events. The view is represented by the `GraphView` class, which contains the user interface components
and the controller is represented by the `GraphController` class, which contains methods to interact with
the model and the view.

The constraints of the MVC pattern are that the model, view and controller should not depend on each other,
and fulfill their roles independently, allowing us to change one component without affecting the others.
___

2. The Swing library provides the ability to create nested user interface components. In this assignment, you created multiple JPanel components on the user interface. These contain other user interface components to build-up a tree of user interface components.
Which design pattern does Swing implement to create a hierarchy of user interface components? Please explain this pattern and how it is implemented in Swing.

___

Answer:

___

3. The Observer pattern is useful to implement the MVC pattern. Can you please explain the relationship between the Observer pattern and the MVC pattern?
Please provide an example from the assignment on how the Observer pattern supports implementing the MVC pattern.

___

Answer:

___

## Process evaluation

We first wrote a single class that would act as a controller and model for the graph, as well as a bit of the view,
event if there was another class for that. That means that once we realized that it wasn't proper MVC, we had to
rewrite a lot of the code when we were at the simulation step. We still managed to rewrite the code in time, but it
was a bit of a hassle. We should have thought more about the design of the program beforehand, as well as some parts
of our current design definitely could have been improved for a better MVC pattern implementation. We also focused
on clean UX design, which we think we did well, but it took a lot of debugging to get it right. By the end of the
deadline, we thought about adding more features (additional sounds, better UI, user money and versus computer mode),
but we decided to focus on rewriting parts of the code to make it cleaner.


## Conclusions

> *Add a very short summary/concluding remarks here*
