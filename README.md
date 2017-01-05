# Drones_project
This is a Drones Simulator created for Artificiall intelligence class.

## Install
1. First install [Sarl.io](http://www.sarl.io/)
2. Clone this repository inside sarl workspace.
3. Import project in Sarl eclipse File > Import > Existing Project into Workspace then select the project folder
4. Rebuild the libraries path :
4.1. In package explorer right click on JRE System library then select Build Path > Configure Build Path
4.2 In the window edit all library with red cross and give the path of corresponding libraries in "src/DroneProjectLibs"
4.3 For LWJGL add the native library location inside "src/DroneProjectLibs/lwjgl/native"
5.Create a new Configuration "Java application" choose as main class "MainProgramm"

##About Agent

Our drone agent should explore an unknow environement to find a target. Our agents have 2 behaviour. Their first behaviour is to exploring (like reactive agents ).
When one of them find target he start communication with others to inform them of the target position.
At this moment agent start a cognitif behaviour to rush the target.

##Contribution

	Clément Ziane
	Willy Gael Kouete Gnoukouo
	Mickael Rakotoarisoa
