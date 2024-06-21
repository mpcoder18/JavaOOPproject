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

According to the MVC pattern, we have 3 main classes in our program:
- `GraphModel` which contains the data of the graph, such as the nodes and edges, and the armies and events on them. It also contains methods to manipulate the logic and data of the graph.
- `GraphView` which contains the user interface components, such as the options menu and top bar, and the graph itself. It also contains methods to manipulate the user interface, under the classes `KeyHandler` and `MouseHandler`.
- `GraphController` which contains methods to interact with the model and the view, such as the simulation loop and the mouse and keyboard input handling.

The model, view and controller interact with each other by the observer pattern. The model is being observed by the view. The view, being interacted on, redirects the input to the controller, which then acts on the model. The model then notifies the view of any changes, which then prompts the view to redraw itself.
We made use of instanceof to differentiate between nodes and edges, as both implement the `Selectable` interface. We also made use of the `Command` pattern to implement undo/redo functionality.

## Evaluation of the program

The program is stable and works well. We have tested the program thoroughly and there are no known bugs. We have implemented a lot of features other than the required ones, such as sounds, undo/redo, keyboard shortcuts, loading files, zooming in and out, etc.
We tried to make all current features as user-friendly as possible, and we think we succeeded in that. The selection of nodes/edges for example should not have any edge cases where it doesn't work as expected, the user is also able to select anywhere on the edge to select it, as both the edge and the nodes have proper hitboxes.
The user also can see via the options panel the list of recent events, can easily manipulate armies and events via their lists on nodes and edges. Also adding armies and events have a random default selection if the user doesn't want to choose a specific one.
If we had more time, we would have liked to add more features, such as user money, versus computer mode, balanced random events, etc.
If we had done something differently, we would probably have thought more about the design of the program beforehand, as well as some parts of our current design definitely could have been improved for a better MVC pattern implementation.
The main problem might have been organization/time: Since we first started with implementing the different required features without really understanding our implementation
of the MVC pattern was incorrect done, we had to rewrite a lot of the code and move things around, which was a bit of a hassle due to the sheer amount of code we had written.
We think our implementation is definitely better now, but we hope the code is still clean and understandable.

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

Answer: Java Swing uses the composite pattern. Here the JComponent class serves as the abstract base class for all UI components, making it the Component in the Composite Pattern. Here's how the pattern maps to Swing:
Firstly you have the superclass JComponent for all components, leafs and composites. 
It provides a common set of methods that all components share. Then comes the  classes Composite  Classes like JPanel, JFrame, JScrollPane, etc., act as composite nodes. These components can contain other JComponent objects. For example, a JPanel can contain other JPanel objects, as well as leafs.
 The leafs consists of Concrete classes like JButton, JLabel, JTextField, etc. these act as the leaf nodes. They represent the actual UI elements that don't contain other components.


___

3. The Observer pattern is useful to implement the MVC pattern. Can you please explain the relationship between the Observer pattern and the MVC pattern?
Please provide an example from the assignment on how the Observer pattern supports implementing the MVC pattern.

___

Answer: The Observer pattern is used to implement the MVC pattern by allowing the model to notify the view of any changes, which then prompts the view to redraw itself. In the assignment, the `GraphModel` class is being observed by the `GraphView` class. The `GraphModel` class notifies the `GraphView` class of any changes, which then prompts the `GraphView` class to redraw itself.

___

## Process evaluation

We first wrote a single class that would act as a controller and model for the graph, as well as a bit of the view,
event if there was another class for that. That means that once we realized that it wasn't proper MVC, we had to
rewrite a lot of the code when we were at the simulation step. We still managed to rewrite the code in time, but it
was a bit of a hassle. We should have thought more about the design of the program beforehand, as well as some parts
of our current design definitely could have been improved for a better MVC pattern implementation. We also focused
on clean UX design, which we think we did well, but it took a lot of debugging to get it right. By the end of the
deadline, we thought about adding more features (additional sounds, better UI, user money and versus computer mode,
balanced random events, etc.) but we decided to focus on rewriting parts of the code to make it cleaner.


## Conclusions

In conclusion, we have implemented all the required features of the assignment, and added some extra ones. We utilized
the MVC pattern to structure our program, and the observer pattern to make the model and view interact with each other.
We have tested the program thoroughly and there are no known bugs.
