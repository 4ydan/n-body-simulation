/**
 *  This Class is all about simulating a  given Universe of
 *  bodies by using the Barnes Hut Algorithm.
 *  All bodies are stored in an Octree.
 */
public class Universe  {
    private double dt = 0.1;
    private double radius = 3E07;

    /**
     * Random Body Generator, utilizes the Constructor of Body.java to create random bodies
     * @param bodies: defines the number of generated bodies
     */
    private Body[] generate(int bodies){
        Body[] mySystem = new Body[bodies];

        for (int i = 0; i < bodies; i++) {
            mySystem[i] = new Body();
        }
        return mySystem;
    }
    /**
     * simulates the given system
     * creates octree => calculates forces => draws bodies
     * @param i number of bodies
     */
    public void run(int i) {
        Body[] bodies=generate(i);
        StdDraw.show(0);
        StdDraw.setXscale(-radius, +radius);
        StdDraw.setYscale(-radius, +radius);

        for (double t = 0.0;  t<100000; t = t + dt) {
            Octree tree = new Octree(0, 0, 0,radius * 2);
            OctreeNode treeNode = new OctreeNode(tree);

            // Barnes Hut Tree
            for (Body b : bodies) {
                if (b.in(tree)) {
                    treeNode.add(b);
                }
            }

            for (Body body : bodies) {
                body.resetForce();
                treeNode.barnesHutAlgorithm(body);
            }

            for (Body body : bodies) {
                body.update(dt);
            }

            StdDraw.clear(StdDraw.BLACK);
            for (Body body : bodies) body.draw();

            StdDraw.show(10);
        }
    }

    /**
     *  Zoom in to Universe
     * @param factor: a positive integer zooms in, negative zooms out. 0 means default
     */
    public void zoom(int factor){
        if (factor > 0) radius = radius/factor;
        if (factor < 0) radius = radius*factor * -1;
    }

    /**
     *  Speed up Universe time
     * @param factor: a positive integer speeds the simulation up, a negative  down. 0 means default
     */
    public void speed(int factor) {
        if (factor > 0) dt = dt*factor;
        if (factor < 0) dt = dt/factor * -1;
    }
}
