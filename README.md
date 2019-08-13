# Develop Branch <br>

Build using JDK 12+ and the JavaFX JDK 12+

Create environment variable 'PATH_TO_FX' and set it to the 'lib' 
directory of the JavaFX JFK.

Build with vm option: '--module-path ${PATH_TO_FX} --add-modules 
javafx.controls'

Gradle takes care of all dependancies
