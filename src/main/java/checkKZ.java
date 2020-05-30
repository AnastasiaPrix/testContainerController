public class checkKZ {
    private double ust = 0.5;
    private boolean trip = false;

    public boolean chekTrip(double signal) {
        if (signal>ust) trip=true; else trip = false;
    return trip;}
}
