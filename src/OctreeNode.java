public class OctreeNode{

    private Body root;
    private Octree oct;
    private OctreeNode[] octants = new OctreeNode[8];

    public OctreeNode(Octree oct) {
        this.oct = oct;
        this.root = null;
        for (int i = 0; i < octants.length ; i++) {
            this.octants[i]=null;
        }
    }

    /**
     * divides cube into 8 octants
     */
    private void divideCube(){
        for (int i = 0; i < octants.length ; i++) {
            octants[i] = new OctreeNode(oct.newCube(i));
        }
    }
    /**
     * @param b always adds Body b to our octree without any exceptions, since there are no limitations
     *          whatsoever to adding a body.
     *          Furthermore divides cube into eight octants, if the body to add belongs to the same cube as
     *          a previously added body
     */
    public void add(Body b) {

        if (root == null) {
            root = b;
            return;
        }

        if (isOccupied()) {

            divideCube();
            put(this.root);
            put(b);
            root = root.plus(b);

        } else {

            root = root.plus(b);
            put(b);

        }
    }

    /**
     * @param b body b is examined of its position (associated octant is being calculated)
     */
    private void put(Body b) {
        for (int i = 0; i < octants.length ; i++) {
            if(b.in(oct.newCube(i))){
             octants[i].add(b);
             break;
            }
        }
    }

    /**
     * @return this method returns true if cube is occupied
     */
    private boolean isOccupied() {
        for (OctreeNode octant : octants) {
            if (octant != null) {
                return false;
            }
        }
        return true;
    }


    public void barnesHutAlgorithm(Body b) {

        if (root == null || b.equals(root))
            return;

        if (isOccupied())
            b.addForce(root);

        else {

            double d = oct.length();

            double r = root.distanceTo(b);

            // value determines the precision of simulation
            double threshold = 1;
            if ((r / d) > threshold)
                b.addForce(root);   // b is far away

            else {
                for (OctreeNode octant : octants) {
                    octant.barnesHutAlgorithm(b);
                }
            }
        }
    }
}
