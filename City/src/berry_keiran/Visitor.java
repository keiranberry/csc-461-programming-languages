package berry_keiran;

public interface Visitor {
    void acceptEmpty(Empty empty);

    void acceptStreet(Street street);

    void acceptGreenspace(Greenspace greenspace);

    void acceptWater(Water water);

    void acceptBuilding(Building building);
}
