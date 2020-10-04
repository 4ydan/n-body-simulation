
public class Octree {


    /*
    coordinates of octant-center
     */
    private double x;
    private double y;
    private double z;
    private double length; // side length of cube

    /**
     * Constructor: creates a new Octant with the given
     * parameters .
     *
     * @param x      x-coordinate of center of octant
     * @param y      y-coordinate of center of octant
     * @param z      z-coordinate of center of octant
     * @param length the side length of the octant
     */
    public Octree(double x, double y, double z, double length) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.length = length;
    }

    /**
     *
     * @return returns the length of this (side length of cube)
     */
    public double length() {
        return length;
    }

    /**
     * @param i determines which octant of this shall become a new Cube.
     *
     *         The parameter i is tied to the Nodearray in Octreenode.
     *          The order of nodes is equivalent to the order in which the octants have
     *          been defined in this class. (0=FTR,7=BBL)
     *
     * @return returns the respective octant
     */

    public Octree newCube(int i){
        switch(i){
            case 0:
                return this.FTR();
            case 1:
                return this.FTL();
            case 2:
                return this.FBR();
            case 3:
                return this.FBL();
            case 4:
                return this.BTR();
            case 5:
                return  this.BTL();
            case 6:
                return this.BBR();
            case 7:
                return this.BBL();
        }
        return null;
    }

    /**
     * First letter: F = Front, B=Back (z-movement)
     * Second letter: T=Top, B=Bottom (y-movement)
     * Third letter: L=Left, R=Right (x-movement)
     * returns the respective octant
     */

    private Octree FTR() {

        double x = this.x + this.length / 4.0;
        double y = this.y + this.length / 4.0;
        double z = this.z + this.length / 4.0;
        double length = this.length / 2.0;

        return new Octree(x, y, z, length);
    }

    private Octree FTL() {

        double x = this.x - this.length / 4.0;
        double y = this.y + this.length / 4.0;
        double z = this.z + this.length / 4.0;

        double length = this.length / 2.0;

        return new Octree(x, y, z, length);
    }

    private Octree FBR() {

        double x = this.x + this.length / 4.0;
        double y = this.y - this.length / 4.0;
        double z = this.z + this.length / 4.0;
        double length = this.length / 2.0;

        return new Octree(x, y, z, length);
    }

    private Octree FBL() {

        double x = this.x - this.length / 4.0;
        double y = this.y - this.length / 4.0;
        double z = this.z + this.length / 4.0;
        double length = this.length / 2.0;

        return new Octree(x, y, z, length);
    }

    private Octree BTR() {

        double x = this.x + this.length / 4.0;
        double y = this.y + this.length / 4.0;
        double z = this.z - this.length / 4.0;

        double length = this.length / 2.0;

        return new Octree(x, y, z, length);
    }

    private Octree BTL() {

        double x = this.x - this.length / 4.0;
        double y = this.y + this.length / 4.0;
        double z = this.z - this.length / 4.0;

        double length = this.length / 2.0;

        return new Octree(x, y, z, length);
    }

    private Octree BBR() {

        double x = this.x + this.length / 4.0;
        double y = this.y - this.length / 4.0;
        double z = this.z - this.length / 4.0;

        double length = this.length / 2.0;

        return new Octree(x, y, z, length);
    }

    private Octree BBL() {

        double x = this.x - this.length / 4;
        double y = this.y - this.length / 4;
        double z = this.z - this.length / 4;

        double length = this.length / 2;

        return new Octree(x, y, z, length);
    }

    /**
     *
     * @return returns true if parameters x,y,z are included in this (part of octant),
     *              returns false otherwise
     */
    public boolean contains(double x, double y, double z) {

        double half = this.length / 2.0;
        return (
                x <= this.x + half &&
                        x >= this.x - half &&
                        y <= this.x + half &&
                        y >= this.x - half &&
                        z <= this.x + half &&
                        z >= this.x - half
        );
    }
}
