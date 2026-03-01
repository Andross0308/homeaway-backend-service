package Service;

import java.io.Serial;
import java.io.Serializable;

public record ServiceRecord(String type, long lat, long log, String name) implements Serializable {



    @Serial
    private static final long serialVersionUID = 11L;

    /**
     * Gives the type of the service
     * Best Case: O(1);
     * Worst Case: O(1);
     * Average Case: O(1);
     * @return service type
     */
    @Override
    public String type() {
        return type;
    }

    /**
     * Gives the latitude of the service
     * Best Case: O(1);
     * Worst Case: O(1);
     * Average Case: O(1);
     * @return service lat
     */
    @Override
    public long lat() {
        return lat;
    }

    /**
     * Gives the longitude of the service
     * Best Case: O(1);
     * Worst Case: O(1);
     * Average Case: O(1);
     * @return service log
     */
    @Override
    public long log() {
        return log;
    }

    /**
     * Gives the name of the service
     * Best Case: O(1);
     * Worst Case: O(1);
     * Average Case: O(1);
     * @return service name
     */
    @Override
    public String name() {
        return name;
    }
}
