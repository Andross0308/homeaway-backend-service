package Service;

import Exceptions.InvalidServiceType;

import java.io.Serial;
import java.io.Serializable;

public enum ServiceTypeEnum implements Serializable{
    EATING,
    LODGING,
    LEISURE;

    @Serial
    private static final long serialVersionUID = 19L;

    /**
     * Gives the type of the service if it's a valid type(EATING, LODGING, LEISURE)
     * Best Case: O(1);
     * Worst Case: O(1);
     * Average Case: O(1);
     * @param type: Type of the service
     * @return the Enum with the type given
     * @throws InvalidServiceType if the type is not a valid type
     */
    public static ServiceTypeEnum getValue(String type){
        try{
            return ServiceTypeEnum.valueOf(type.toUpperCase());
        }catch (Exception e){
            throw new InvalidServiceType();
        }
    }

}
