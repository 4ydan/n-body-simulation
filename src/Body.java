import java.awt.*;

/**
 * This class is all about Celestial Bodies, their distance & force to each other
 *
 */
public class Body {

    private static final double G = 6.67e-11;
    private double mass;
    private double radius;
    private Color color; // for drawing the body.
    private double rx, ry, rz;       // position, coordinates of bodies are essentially the key of our octree
    private double vx, vy, vz;       // velocity
    private double fx, fy, fz;       // force

    private Body(double rx, double ry, double rz, double vx, double vy, double vz, double mass, Color color) {
        this.rx    = rx;
        this.ry    = ry;
        this.rz    = rz;
        this.vx    = vx;
        this.vy    = vy;
        this.vz    = vz;
        this.mass  = mass;
        this.color = color;
    }

    public Body() {
        this.rx    = randomDouble(-3E07, 3E07);
        this.ry    = randomDouble(-3E07, 3E07);
        this.rz    = randomDouble(-3E07, 3E07);
        this.vx    = randomDouble(-1E03, 1E03);
        this.vy    = randomDouble(-1E03, 1E03);
        this.vz    = randomDouble(-1E03, 1E03);
        this.mass    = randomDouble(1E10, 1E25);
        this.radius    = randomDouble(1E3, 1E5);
        this.color = new Color((int)(Math.random() * 255), (int)(Math.random() * 255), (int)(Math.random() * 255));
    }

    // Returns the distance between this celestial body and the specified 'body'.
    public double distanceTo(Body body) {
        double dx = rx - body.rx;
        double dy = ry - body.ry;
        double dz = rz - body.rz;
        return Math.sqrt(dx*dx + dy*dy + dz*dz);
    }

    /**
     * Updates the velocity and position of the  Body
     *
     * @param dt timestep for the simulation
     */
    public void update(double dt) {
        vx += dt * fx / mass;
        vy += dt * fy / mass;
        vz += dt * fz / mass;
        rx += dt * vx;
        ry += dt * vy;
        rz += dt * vz;
    }

    public void resetForce() {
        fx = 0.0;
        fy = 0.0;
        fz = 0.0;
    }

    /**
     * Calculates the force acting between the  body b, and
     * adds this to the force acting on this.
     *
     * @param b the body whose force on this body to calculate
     */
    public void addForce(Body b) {
        Body a = this;
        double EPS = 3E4;
        double dx = b.rx - a.rx;
        double dy = b.ry - a.ry;
        double dz = b.rz - a.rz;
        double dist = Math.sqrt(dx*dx + dy*dy + dz*dz);
        double F = (G * a.mass * b.mass) / (dist*dist + EPS*EPS);
        a.fx += F * dx / dist;
        a.fy += F * dy / dist;
        a.fz += F * dz / dist;
    }


    // Draws the celestial body to the current StdDraw canvas as a dot using 'color' of this body.
    // The radius of the dot is in relation to the radius of the celestial body
    // (use a conversion based on the logarithm as in 'Simulation.java').
    public void draw() {
        StdDraw.setPenColor(this.color);
        StdDraw.filledCircle(this.rx, this.ry, this.radius/100);
    }

    private double randomDouble(double min, double max) {
        return (Math.random() * ((max - min) + 1)) + min;
    }

    /**
     * @param oct octant which is being examined relative to this
     * @return returns true if x,y,z coordinates of this are situated inside oct
     */
    boolean in(Octree oct) {
        return oct.contains(this.rx, this.ry, this.rz);
    }

    //calculates center of mass for 2 bodies and returns it in form of an "invisible body" for the force calculation
    public Body plus(Body b) {
        Body a = this;

        double m = a.mass + b.mass;
        double x = (a.rx * a.mass + b.rx * b.mass) / m;
        double y = (a.ry * a.mass + b.ry * b.mass) / m;
        double z = (a.rz * a.mass + b.rz * b.mass) / m;

        return new Body(x, y, z, 0,0 ,0, m, a.color);

    }

}
