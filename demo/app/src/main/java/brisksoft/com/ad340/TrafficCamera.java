package brisksoft.com.ad340;

public class TrafficCamera {

    String label;
    String image;
    String owner; // agency
    double[] coordinates;

    public TrafficCamera(String label, String owner, String image, double[] coordinates) {
        this.label = label;
        this.owner = owner;
        this.image = image;
        this.coordinates = coordinates;
    }
}
