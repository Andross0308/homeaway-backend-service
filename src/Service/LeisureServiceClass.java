package Service;

import java.io.Serial;

public class LeisureServiceClass extends ServiceClass {

    @Serial
    private static final long serialVersionUID = 8L;

    //Price of the ticket
    private final int price;

    /**
     * Constructor
     * @param lat: Latitude of the service
     * @param log: Longitude of the service
     * @param price: Price of the ticket
     * @param discount: Student discount of the service
     * @param name: Name of the service
     * @param lastChanged: Number of total ratings in the service
     */
    public LeisureServiceClass(long lat, long log, int price,
                               int discount, String name, int lastChanged){
        super(ServiceTypeEnum.LEISURE, lat, log, name, lastChanged);
        this.price = price - (price * discount/100);
    }

    /**
     * Gives the price of the service
     * The price of the leisure service is the price of the ticket with the student discount
     * Best Case: O(1);
     * Worst Case: O(1);
     * Average Case: O(1);
     * @return service price
     */
    @Override
    public int getPrice() {
        return price;
    }
}
