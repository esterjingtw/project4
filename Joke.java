package ds.project4;

public class Joke {
    private String setup;
    private String delivery;

    // Constructors, getters, and setters
    public Joke(String setup, String delivery) {
        this.setup = setup;
        this.delivery = delivery;
    }

    public String getSetup() {
        return setup;
    }

    public void setSetup(String setup) {
        this.setup = setup;
    }

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }
}
