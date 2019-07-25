# What is the cow sim?
The cow sim is an ambitious artificial intelligence project where a 
society of cows, acting similar to humans, create and live out a 
society. 

Check out a more in-depth presentation here: https://docs.google.com/presentation/u/2/d/1yiUae5--9wsAH2D4SWKuIOJiESNoR_YNxJHhiJk0rek/edit?usp=drive_web&ouid=104197416385618381584

Large focuses for this project:
1. Learn about professional software development practices
1. Design the project using software construction standards
1. Learn how to use git/github to work in a team environment
1. Write reliable and bug free code
1. Delve into the depths of java
1. Work with new artificial intelligence technologies
1. Work with graphics technologies
1. Create systems for successful idea integration 
1. Use project management standards
1. Design the user experience

# Releases (Current release: Version 0.2)

### Release 0.1:
<B> Adds prototypes 01 - 06 </B><br>
First release! <br>
Artificial intelligence is nowhere near implemented yet, but the base 
engine is concrete. <br>
Cows interact (little) with the world around them; they are able to 
construct buildings, and drink water. <br>
The world is quite small and does not have the capacity to get 
larger.<br>
The population limit for cows is around 200 due to poor 
optimization.<br>
Statistics for cows are kept track of, and are viewable through a menu. 
<br>
Having few actions to choose from causes not much difference between 
cows other than positioning. <br>
Cows 'interact' with each other purely based off of collisions. <br>
Relations with other cows increase at a random rate off of a collision. 
<br>
Cows enter buildings based off of collision. <br>
The user can select any cow. <br>
The user can open any cow's statistics menu.. <br>
The user can kill any cow or give any cow a disease. <br>
Disease only causes the cow to become thirsty and does not spread to 
other cows. <br>
Buildings can be selected to show how many cows are inside. <br><br>

Major bugs/issues: include improper movement and incorrect building 
entering/exiting. <br>

### Release 0.2:

<B> Adds prototypes 07 - 12 </B><br>
Bare bones genetic algorithm is fully implemented. <br>
Cows can interact with: Buildings, resources, and other cows in a 
standard manner. <br>
A tile system has been implemented which allows for standardization of 
movement and interaction. <br>
All buildings, resources, and terrain are tiles. <br>
All tiles are part of a regioning system that segregates different 
sections of tiles so that they can be drawn or not drawn. <br>
Regions are dynamically drawn based off of what the user is seeing. <br>
Desert and Mountainous biomes exist, which limit the kinds of tiles that 
can be placed upon them. <br>
Cows can navigate to the closest specific tile given a specific action. 
<br>
Every action and event can affect a different cow statistic. <br>
Movement has been further standardized to the effect that new actions 
are easy to generate. <br>
In-sim time has been further thought out and reflects a standard ratio 
to real time based of the speed that the sim is set at. <br>
The world can react to an increase in time through placing new resources 
like trees or darkening the screen to simulate daylight. <br>
Sprites are now dynamically loaded; only once per sprite. <br>
Cows' images reflect the action or lack thereof that they are doing. 
<br>
A cow's worth survival (both social and physical) is calculated for use 
in the genetic algorithm. <br>
Cows can have offspring at a reasonable rate, growing the population 
fairly linearly when in good conditions. <br>
Offspring are created in part by the genetic algorithm; their stats are 
a combination and mutation of their parents'. <br>
Event logging now saves to actual individual text files, one for each 
cow and one for the civilization itself. <br>
The UI has been further thought out, and now boasts fullscreen (on any 
size screen). <br>
A little bit of everything has been optimized, allowing for massive 
worlds and thousands of ImageView nodes/cows on the screen at the same 
time. <br><br>

Major bugs/issues include: Too many files open in logging system when 
their is a large population, and nonsensical timings for movement. <br>

### Release 0.3:

In development!;
