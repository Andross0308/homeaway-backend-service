import App.*;
import Exceptions.*;
import Service.Service;
import Service.ServiceRecord;
import Students.Student;
import Students.StudentRecord;
import dataStructures.Iterator;
import dataStructures.TwoWayIterator;

import java.io.*;
import java.util.Objects;
import java.util.Scanner;

/**
 * @author André Martins (70920) anc.martins@campus.fct.unl.pt
 * @author Ricardo Moreira (71283) rim.moreira@campus.fct.unl.pt
 */

public class Main{


    private static final String INVALID_BOUNDS = "Invalid bounds.";
    private static final String NAME_EXISTS = "Bounds already exists. Please load it!";

    //Save Global Variables
    private static final String SPACE = " ";
    private static final String UNDERSCORE = "_";
    private static final String DIRECTORY = ".";
    private static final String FILE_TYPE = ".ser";

    private static final String LOADED_FAILED = "Bounds %s does not exists.\n";

    private static final String PARAGRAPH = "\n";
    private static final String THRIFTY_DISTRACTED = " %s is distracted!\n";


    //Users Global Variables
    private static final String BACKWARDS = "<";
    private static final String FORWARD = ">";
    private static final String INVALID_ORDER = "This order does not exists!";


    //Ranking Outputs
    private static final String RANKING_HEADER = "Services sorted in descending order";

    //Ranked Outputs
    private static final String RANKED_HEADER = "%s services closer with %d average\n";

    //Tags output
    private static final String NO_SERVICES_WITH_TAG = "There are no services with this tag!";

    /**
     * Gives the Command that the scanner read
     * @param in: Scanner to read inputs
     * @return the command if it's a valid one
     */
    private static Command getCommand(Scanner in){
        try{
            return Command.valueOf(in.next().toUpperCase());
        } catch (Exception e) {
            return Command.UNKNOWN;
        }
    }


    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Command c;
        HomeAwayApp app = new HomeAwayAppClass();
        do{
            c = getCommand(in);
            switch (c){
                case EXIT -> showExitMessage(in, app, c);
                case HELP -> showHelpMessage(in);
                case BOUNDS -> app = createBounds(in, app, c);
                case SAVE -> saveCurrentBounds(app, c);
                case LOAD -> app = loadBounds(in, app, c);
                case SERVICE -> createNewService(in, app, c);
                case SERVICES -> showAllServices(in, app, c);
                case STUDENT -> createNewStudent(in, app, c);
                case LEAVE -> leaveStudent(in, app, c);
                case STUDENTS -> showStudents(in, app, c);
                case GO -> goStudent(in, app, c);
                case MOVE -> studentMove(in, app, c);
                case USERS -> showUsersOfService(in, app, c);
                case WHERE -> whereIsStudent(in, app, c);
                case VISITED -> showVisitedLocations(in, app, c);
                case STAR -> evaluateService(in, app, c);
                case RANKING -> rankingService(in, app, c);
                case RANKED -> rankedService(in, app, c);
                case TAG -> listServicesWithTag(in, app, c);
                case FIND -> findMostRelevantService(in, app, c);
                default -> showUnknownMessage();

            }
        }while(!c.equals(Command.EXIT));
        in.close();
    }

    /**
     * Writes the UNKNOWN_MSG if no valid command was given
     */
    private static void showUnknownMessage(){
        System.out.println(Command.UNKNOWN.getSuccessfulMsg());
    }

    /**
     * Reads the line and gives the HELP_MSG if the command was HELP
     * @param in: Scanner to read the line
     */
    private static void showHelpMessage(Scanner in){
        in.nextLine();
        for(Command c: Command.values()){
            if(!c.equals(Command.UNKNOWN))
                System.out.println(c.getHelpMsg());
        }
    }

    /**
     * Saves the current geographic area and shows the EXIT_MSG
     * @param in: Scanner to read line
     * @param app: App to save in a file
     * @param c: Command EXIT
     */
    private static void showExitMessage(Scanner in, HomeAwayApp app, Command c){
        in.nextLine();
        saveApp(app);
        System.out.println(c.getSuccessfulMsg());
    }

    /**
     * Creates a new geographic area and saves the one in the system, if it exists
     * Command fails if the coordinates are not valid or
     * if a file with the name already exists in the root folder
     * @param in: Scanner to read the inputs
     * @param app: App to change
     * @param c: Command BOUNDS
     * @return the app with the new geographic area or with the previous one if it fails
     */
    private static HomeAwayApp createBounds(Scanner in, HomeAwayApp app, Command c){
        long maxLat = in.nextLong();
        long minLon = in.nextLong();
        long minLat = in.nextLong();
        long maxLon = in.nextLong();
        String name = in.nextLine().trim();
        File file = hasName(name);
        if(!app.validBounds(minLat, maxLat, minLon, maxLon)){
            System.out.println(INVALID_BOUNDS);
        }
        else if(file != null || (app.getBounds().name() != null && name.equalsIgnoreCase(app.getBounds().name()))){
            System.out.println(NAME_EXISTS);
        }
        else{
            if(app.getBounds().name() != null)
                saveApp(app);
            app = new HomeAwayAppClass(minLat, maxLat, minLon, maxLon, name);
            System.out.printf(c.getSuccessfulMsg(), name);
        }
        return app;
    }

    /**
     * Verifies if a given name has a file with that name
     * @param name: Name of the file
     * @return the file if it exists, null otherwise
     */
    private static File hasName(String name){
        String file = name.toLowerCase().replace(SPACE, UNDERSCORE) + FILE_TYPE;
        File dir = new File(DIRECTORY);
        for(File f: Objects.requireNonNull(dir.listFiles())){
            if(f.isFile() && f.getName().equals(file)){
                return f;
            }
        }
        return null;
    }

    /**
     * Saves the current geographic area in a file with the name of that area
     * @param app: App with the information to be saved
     */
    private static void saveApp(HomeAwayApp app) {
        String name = app.getBounds().name();
        if (name != null){
            String filePath = name.replace(SPACE, UNDERSCORE).toLowerCase() + FILE_TYPE;
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
                oos.writeObject(app);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Verifies if the app is valid to be saved
     * @param app: App to verify
     * @param c: Command SAVE
     */
    private static void saveCurrentBounds(HomeAwayApp app, Command c){
        try{
            app.hasBounds();
            saveApp(app);
            System.out.printf(c.getSuccessfulMsg(), app.getBounds().name());
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Loads an area that is saved in the root package
     * If there is a valid geographic area, that one will be saved
     * The command fails if there is no file with the name
     * @param in: Scanner to read the input
     * @param app: App to change
     * @param c: Command LOAD
     * @return the app with the loaded information or the previous one
     */
    private static HomeAwayApp loadBounds(Scanner in, HomeAwayApp app, Command c) {
        String name = in.nextLine().trim().replace(" ", "_");
        String filePath = name.toLowerCase() + FILE_TYPE;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            if(app.getBounds().name() != null)
                saveApp(app);
            app = (HomeAwayApp) ois.readObject();
            System.out.printf(c.getSuccessfulMsg(), app.getBounds().name());
        }catch (FileNotFoundException e) {
            System.out.printf(LOADED_FAILED, name);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return app;
    }

    /**
     * Creates a new service inside the current geographic area
     * The command fails if there is no current area
     * if the type is not valid,
     * if the coordinates are outside the geographic area,
     * if the price or value are outside the possible values,
     * or if a service with that name already exists in the system
     * @param in: Scanner to read the inputs
     * @param app: App of the current geographic area
     * @param c: Command SERVICE
     */
    private static void createNewService(Scanner in, HomeAwayApp app, Command c){
        String type = in.next().toLowerCase().trim();
        long lat = in.nextLong();
        long log = in.nextLong();
        int price = in.nextInt();
        int value = in.nextInt();
        String name = in.nextLine().trim();
        try{
            app.createNewService(type, lat, log, price, value, name);
            System.out.printf(c.getSuccessfulMsg(), type, name);
        }catch(ObjectAlreadyExists e){
            System.out.printf(e.getMessage(), app.giveServiceRecord(name).name());
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Lists all the services inside the system, in order of insertion
     * The command fails if there is no valid area,
     * if there is no services to display
     * @param in: Scanner to read the inputs
     * @param app: App of the current
     * @param c: Command SERVICES
     */
    private static void showAllServices(Scanner in, HomeAwayApp app, Command c){
        in.nextLine();
        try{
            Iterator<Service> it = app.giveListOfServices();
            while(it.hasNext()){
                ServiceRecord s = it.next().getRecord();
                System.out.printf(c.getSuccessfulMsg(), s.name(), s.type(), s.lat(), s.log());
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Adds a new student to the system.
     * The command fails if there is no valid geographic area,
     * if the type is not valid,
     * the home is not a lodging service in the system,
     * the lodging is full,
     * or if a student with that name already exists in the system
     * @param in: Scanner to read the inputs
     * @param app: App of the system
     * @param c: Command STUDENT
     */
    private static void createNewStudent(Scanner in, HomeAwayApp app, Command c){
        String type = in.nextLine().trim();
        String name = in.nextLine();
        String country = in.nextLine();
        String home = in.nextLine();
        try {
            app.createNewStudent(type, name, country, home);
            System.out.printf(c.getSuccessfulMsg(), name);
        }catch(ObjectAlreadyExists e){
            System.out.printf(e.getMessage(), app.giveStudentRecord(name).name());
        }catch(HomeDoesNotExistInTheSystem e){
            System.out.printf(e.getMessage(), home);
        }catch(LodgingIsFull e){
            System.out.printf(e.getMessage(), app.giveLodgingRecord(home).name());
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Removes a student from the system.
     * The command fails if there is no valid geographic area,
     * or if the student doesn't exist in the system
     * @param in: Scanner that reads the inputs
     * @param app: App of the system
     * @param c: Command LEAVE
     */
    private static void leaveStudent(Scanner in, HomeAwayApp app, Command c){
        String name = in.nextLine().trim();
        try{
            String s = app.removeStudent(name);
            System.out.printf(c.getSuccessfulMsg(), s);
        }catch(DoesNotExistInTheSystem e){
            System.out.printf(e.getMessage(), name);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Lists all the students in the system in insertion order
     * or the students of a given country in alphabetical order
     * The command fails if there is no valid geographic area,
     * if there is no students in the system,
     * if no students or the country exist
     * @param in: Scanner to read inputs
     * @param app: App of the system
     * @param c: Command STUDENTS
     */
    private static void showStudents(Scanner in, HomeAwayApp app, Command c){
        String country = in.nextLine().trim();
        try{
            Iterator<Student> it = app.giveListOfStudents(country);
            while(it.hasNext()){
                Student s = it.next();
                ServiceRecord service = app.giveServiceRecord(s.getLocation());
                StudentRecord record = s.getRecord();
                System.out.printf(c.getSuccessfulMsg(), record.name(), record.type(), service.name());
            }
        }catch(NoStudentOfCountryToDisplay e){
            System.out.printf(e.getMessage(), country);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Changes the location of a student to an eating service, or leisure service
     * The command fails if there is no valid geographic area,
     * if the student or the location doesn't exist in the system,
     * if the location is a lodging service,
     * if the new location it's the current student location
     * if the new location it's an eating and it's full
     * @param in: Scanner to read the inputs
     * @param app: App with the system
     * @param c: Command GO
     */
    private static void goStudent(Scanner in, HomeAwayApp app, Command c){
        String student = in.nextLine().trim();
        String newLocation = in.nextLine();
        try{
            app.changeLocationOfStudent(student, newLocation);
            StudentRecord s = app.giveStudentRecord(student);
            ServiceRecord service = app.giveServiceRecord(newLocation);
            if(app.thriftyIsDistracted(student, newLocation))
                System.out.printf(c.getSuccessfulMsg() + THRIFTY_DISTRACTED, s.name(), service.name(), s.name());
            else
                System.out.printf(c.getSuccessfulMsg() + PARAGRAPH, s.name(), service.name());
        }catch(ServiceDoesNotExistInTheSystem e){
            System.out.printf(e.getMessage(), newLocation);
        }catch(DoesNotExistInTheSystem e){
            System.out.printf(e.getMessage(), student);
        }
        catch(InvalidChangeOfLocation | EatingServiceIsFull e){
            System.out.printf(e.getMessage(), app.giveServiceRecord(newLocation).name());
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Changes the home of a student.
     * The command fails if there is no valid geographic area,
     * if the lodging or student doesn't exist in the system
     * if the new Home is the students home,
     * if the new Home is full,
     * if the student is thrifty and the new home is same price or more expensive
     * @param in: Scanner that reads the input
     * @param app: App of the system
     * @param c: Command MOVE
     */
    private static void studentMove(Scanner in, HomeAwayApp app, Command c){
        String student = in.nextLine().trim();
        String newHome = in.nextLine();
        try{
            StudentRecord s = app.giveStudentRecord(student);
            ServiceRecord service = app.giveLodgingRecord(newHome);
            app.changeStudentHome(student, newHome);
            System.out.printf(c.getSuccessfulMsg(), service.name(), s.name(), s.name());
        }catch(DoesNotExistInTheSystem e){
            System.out.printf(e.getMessage(), student);
        }catch (HomeDoesNotExistInTheSystem e){
            System.out.printf(e.getMessage(), newHome);
        } catch( StudentTriesMovingToSameHouse | InvalidMoveOfStudent e){
            System.out.printf(e.getMessage(), app.giveStudentRecord(student).name());
        }catch(LodgingIsFull e){
            System.out.printf(e.getMessage(), app.giveServiceRecord(newHome).name());
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Lists all students currently in a given eating or lodging service in the desired order.
     * if order is ">" - from oldest to newest
     * if order is "<" - from newest to oldest.
     * The command fails if there is no valid geographic area,
     * if the order type is not valid,
     * if the service doesn't exist in the system,
     * if the service is leisure service,
     * if the service doesn't have students
     * @param in: Scanner that reads the inputs
     * @param app: App of the system
     * @param c: Command USERS
     */
    private static void showUsersOfService(Scanner in, HomeAwayApp app, Command c){
        String order = in.next().trim();
        String service = in.nextLine().trim();
        try{
            app.hasBounds();
            TwoWayIterator<Student> it = app.listUsersOfService(service);
            if((order.equals(FORWARD))) {
                while (it.hasNext()) {
                    StudentRecord s = it.next().getRecord();
                    System.out.printf(c.getSuccessfulMsg(), s.name(), s.type());
                }
            }
            else if(order.equals(BACKWARDS)) {
                it.fullForward();
                while (it.hasPrevious()) {
                    StudentRecord s = it.previous().getRecord();
                    System.out.printf(c.getSuccessfulMsg(), s.name(), s.type());
                }
            }
            else {
                System.out.println(INVALID_ORDER);
            }
        }catch (DoesNotExistInTheSystem e){
            System.out.printf(e.getMessage(), service);
        } catch(ServiceDoesNotStoreStudents | ServiceHasNoStudents e) {
            System.out.printf(e.getMessage(), app.giveServiceRecord(service).name());
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Locates a student.
     * The command fails if there is no valid geographic area,
     * if the student doesn't exist in the system
     * @param in: Scanner to read the inputs
     * @param app: App with the system
     * @param c: Command WHERE
     */
    private static void whereIsStudent(Scanner in , HomeAwayApp app, Command c){
        String name = in.nextLine().trim();
        try{
            StudentRecord student = app.giveStudentRecord(name);
            ServiceRecord s = app.whereIsStudent(student.name()).getRecord();
            System.out.printf(c.getSuccessfulMsg(), student.name(), s.name(),
                    s.type(), s.lat(), s.log());
        }catch(DoesNotExistInTheSystem e){
            System.out.printf(e.getMessage(), name);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Lists the locations visited and stored by one student.
     * The command fails if there is no geographic area,
     * if the student doesn't exist in the system,
     * if the student is thrifty,
     * if the student has not visited any locations
     * @param in: Scanner to read the inputs
     * @param app: App of the system
     * @param c: Command VISITED
     */
    private static void showVisitedLocations(Scanner in, HomeAwayApp app, Command c){
        String student = in.nextLine().trim();
        try{
            Iterator<String> it = app.showVisitedLocations(student);
            while(it.hasNext()){
                String s = it.next();
                System.out.printf(c.getSuccessfulMsg(), s);
            }
        } catch(DoesNotExistInTheSystem e){
            System.out.printf(e.getMessage(), student);
        }catch(StudentIsThrifty | StudentHasNoVisitedLocations e){
            System.out.printf(e.getMessage(), app.giveStudentRecord(student).name());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Evaluates a service with an Integer Number and a Description
     * The command fails if there is no geographic area,
     * if the number is outside the valid interval,
     * if the service doesn't exist in the system
     * @param in: Scanner to read the inputs
     * @param app: App with the system
     * @param c: Command STAR
     */
    private static void evaluateService(Scanner in, HomeAwayApp app, Command c){
        int rating = in.nextInt();
        String service = in.nextLine().trim();
        String description = in.nextLine();
        try{
            app.evaluateService(rating, service, description);
            System.out.println(c.getSuccessfulMsg());
        }catch(DoesNotExistInTheSystem e){
            System.out.printf(e.getMessage(), service);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Lists all services ordered by star.
     * The command fails if there is no geographic area,
     * if there is no services in the systems
     * @param in: Scanner that reads the inputs
     * @param app: App with the system
     * @param c: Command RANKING
     */
    private static void rankingService(Scanner in, HomeAwayApp app, Command c){
        in.nextLine();
        try{
            Iterator<Service> it = app.showServicesByRank();
            System.out.println(RANKING_HEADER);
            while(it.hasNext()){
                Service s = it.next();
                System.out.printf(c.getSuccessfulMsg(), s.getRecord().name(), s.getRating());
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Lists the service(s) of the indicated type with the given score that are closer to the student location.
     * The command fails if there is no geographic area,
     * if the score is outside the valid interval,
     * if the student doesn't exist in the system,
     * if type is not a valid type,
     * if no services of the type exist in the system,
     * if no services of the type with the given score exist.
     * @param in: Scanner that reads the inputs
     * @param app: App with the system
     * @param c: Command RANKED
     */
    private static void rankedService(Scanner in, HomeAwayApp app, Command c){
        String type = in.next().trim();
        int rate = in.nextInt();
        String name = in.nextLine().trim();
        try{
            Iterator<Service> it = app.showClosestServices(type, rate, name);
            System.out.printf(RANKED_HEADER, type, rate);
            while(it.hasNext()){
                ServiceRecord s = it.next().getRecord();
                System.out.printf(c.getSuccessfulMsg(), s.name());
            }
        }catch (DoesNotExistInTheSystem e){
            System.out.printf(e.getMessage(), name);
        } catch (NoServicesOfTypeInTheSystem | NoServiceWithThatRating e){
            System.out.printf(e.getMessage(), type);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * List all services that contains the specified tag in reviews.
     * The command fails if there is no geographic area,
     * if there is no service with the tag
     * @param in: Scanner to read the inputs
     * @param app: App with the system
     * @param c: Command TAG
     */
    private static void listServicesWithTag(Scanner in, HomeAwayApp app, Command c){
        String tag = in.nextLine().trim();
        try{
            Iterator<Service> it = app.listAllTags(tag);
            if(!it.hasNext()) System.out.println(NO_SERVICES_WITH_TAG);
            while(it.hasNext()){
                ServiceRecord s = it.next().getRecord();
                System.out.printf(c.getSuccessfulMsg(),s.type(), s.name());
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Finds the most relevant service of a certain type, for a specific student.
     * If the student is Thrifty, finds the less expensive service of the type
     * Else finds the best average service of the type.
     * The command fails if there is no geographic area,
     * if the type is not a valid type,
     * if the student doesn't exist in the system,
     * if no service of the type exist in the system
     * @param in: Scanner to read inputs
     * @param app: App of the system
     * @param c: Command FIND
     */
    private static void findMostRelevantService(Scanner in, HomeAwayApp app, Command c){
        String name = in.nextLine().trim();
        String type = in.nextLine();
        try{
            String s  = app.findService(type, name);
            System.out.printf(c.getSuccessfulMsg(), s);
        }catch (DoesNotExistInTheSystem e){
            System.out.printf(e.getMessage(), name);
        }catch(NoServicesOfTypeInTheSystem e){
            System.out.printf(e.getMessage(), type);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
