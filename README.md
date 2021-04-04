# A Small Game 
(name not completely set yet)

## About the Game:
This application is a game where the user is stuck in a forest and must make it out by both
raising their progress to 100% and keeping their health above 0%. While on the way out, the user 
will encounter different obstacles, where they will be presented with different options to choose from.
Each option will either result in an increase/decrease in progress and/or health. Once the obstacle is over,
a chest may or may not appear to the user containing exactly 2 items. Uer may select up to 1 to add to inventory.
Items will be able to be used between each obstacle, effects are unknown to user. Inventory only has 3 spaces total.
Progress will never be able to reach below 0, but if health is ever <= 0, the user loses. 

Between each obstacle, the user should be able to:
- view current progress
- view inventory & use item if there are any
- view current stats
- continue on journey
- change name
- or quit

## Why it's of interest to me and who will use it:
One reason why this project is of interest to me is that I've always found playing small games like this fun,
and good way for me to de-stress after a long day. Also, not only will I enjoy creating the different
obstacles, the options, and all the possible results, I'm also looking forward to making this a graphical application.
I've never really done anything like this before, and being able to play around with the different elements of the game
while trying to make it look nice, will most likely be challenging, yet rewarding.

The main purpose of this game is to provide a small distraction from other things in life, and so, the target
audience is anyone who would like to take some time off and play a small game that will hopefully leave them 
feeling better than before.

## User stories:
- As a user, I want to be able to add items found in chests to my inventory (add multiple X's to Y)
- As a user, I want to be able to name my character, and be able to change their name during the game
- As a user, I want to be able to view my current progress and health
- As a user, I want to be able to view my inventory, and use/get rid of items inside of it
- As a user, I want to save my progress (current status bars, inventory and name) to file
- As a user, I want to be able to resume the game with saved progress bars, inventory and name from file

Phase 4: Task 2 - I refactored the Item class in the model package so it is more robust. One of the constructors now
    throws a NoSuchItemExistsException if the int passed in does not have an item associated with it
    - tests for it are at the bottom of the ItemTest class
 
 Phase 4: Task 3 - One factoring I would redo is the relationship between Inventory and Chest. Currently, i have Chest extends Inventory,
 and although it is true that chest shares a lot of the same functionality as inventory, it's not exactly true
 that a chest is an inventory. Instead, I would have a different abstract class (called Storage, maybe), that implements
 the methods that both chest and inventory share and then have both chest and inventory extend that class.
        - have the item limit on the subclasses