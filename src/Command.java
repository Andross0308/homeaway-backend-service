import java.io.Serial;
import java.io.Serializable;

public enum Command implements Serializable {

    BOUNDS("bounds - Defines the new geographic bounding rectangle", "%s created.\n"),
    SAVE("save - Saves the current geographic bounding rectangle to a text file", "%s saved.\n"),
    LOAD("load - Load a geographic bounding rectangle from a text file", "%s loaded.\n"),
    SERVICE("service - Adds a new service to the current geographic bounding rectangle. The service may be eating, lodging or leisure", "%s %s added.\n"),
    SERVICES("services - Displays the list of services in current geographic bounding rectangle, in order of registration", "%s: %s (%d, %d).\n"),
    STUDENT("student - Adds a student to the current geographic bounding rectangle", "%s added.\n"),
    STUDENTS("students - Lists all the students or those of a given country in the current geographic bounding rectangle, in alphabetical order of the student's name", "%s: %s at %s.\n"),
    LEAVE("leave - Removes a student from the the current geographic bounding rectangle", "%s has left.\n"),
    GO("go - Changes the location of a student to a leisure service, or eating service", "%s is now at %s."),
    MOVE("move - Changes the home of a student", "lodging %s is now %s's home. %s is at home.\n"),
    USERS("users - List all students who are in a given service (eating or lodging)", "%s: %s\n"),
    STAR("star - Evaluates a service",  "Your evaluation has been registered!"),
    WHERE("where - Locates a student","%s is at %s %s (%d, %d).\n" ),
    VISITED("visited - Lists locations visited by one student", "%s\n"),
    RANKING("ranking - Lists services ordered by star", "%s: %d\n"),
    RANKED("ranked - Lists the service(s) of the indicated type with the given score that are closer to the student location", "%s\n"),
    TAG("tag - Lists all services that have at least one review whose description contains the specified word", "%s %s\n"),
    FIND("find - Finds the most relevant service of a certain type, for a specific student", "%s\n"),
    HELP("help - Shows the available commands", ""),
    EXIT("exit - Terminates the execution of the program", "Bye!"),
    UNKNOWN("", "Unknown command. Type help to see available commands.");

    @Serial private static final long serialVersionUID = 17L;

    private final String helpMsg;
    private final String successfulMsg;

    Command(String s, String string){
        this.helpMsg = s;
        this.successfulMsg = string;
    }

    String getHelpMsg(){
        return helpMsg;
    }

    String getSuccessfulMsg(){ return successfulMsg; }
}
