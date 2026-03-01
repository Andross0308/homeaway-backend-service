package App;

import java.io.Serial;
import java.io.Serializable;

public record Bounds(long minLat, long maxLat, long minLong, long maxLong, String name) implements Serializable {


    @Serial
    private static final long serialVersionUID = 3L;

    @Override
    public long maxLat() {
        return maxLat;
    }

    @Override
    public long minLong() {
        return minLong;
    }

    @Override
    public long minLat() {
        return minLat;
    }

    @Override
    public long maxLong() {
        return maxLong;
    }

    @Override
    public String name() {
        return name;
    }
}
