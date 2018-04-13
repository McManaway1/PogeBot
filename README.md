# PogeBot Project
## Description
The PogeBot CommandLine Application is a personal project written in Java, and built upon the Java Discord API (JDA) created 
by [DV8FromTheWorld](https://github.com/DV8FromTheWorld/). The purpose of this project is to use the tools provided by JDA, 
by create a Bot that can respond to commands input by users in specific servers of DiscordApp.

## Dependencies 
This project relies on Gradle for importing the following dependencies:
* **[JDA](https://github.com/DV8FromTheWorld/JDA):** Provides an implementation for creating Bot-Accounts on DiscordApp.
* **[SLF4J-Simple](https://github.com/qos-ch/slf4j/tree/master/slf4j-simple):** Used by JDA for Logging.
* **[Jackson-YAML](https://github.com/FasterXML/jackson-dataformat-yaml):** Provides an implementation for reading YML, 
used for initializing Modules into the Project.

## Packs
One of the most important features of this project is the support of external Jar-Modules (Packs). Packs are built upon 
the PogeBot Jar, and allow users to create their own commands for users with limited knowledge of JDA. 

For a Pack to be accepted by the PogeBot PackLoader, it must support the following attributes:
* **Self-Contained Jar:** The Pack must be a `.jar` file. This jar may contain other imported libraries, however it should 
not contain any PogeBot FatJar contents. This Self-Contained Jar must also contain:
    * **pack.yml:** This contains all the information that the PogeBot PluginLoader can use to understand basic information
    about the pack, and what commands are provided.
    * **Main Pack Class:** This class must either implement `IPack` or extend `AbstractPack` (this class should be referenced,
    in the *pack.yml*). This will be the main 'class' to handle all events that may be passed from the PogeBot
    Application to the Pack (i.e. Pack Startup, Handling Messages or Pack Shutdown).
    
An example of the basic sourcecode for a pack can be found in `src/examples`.