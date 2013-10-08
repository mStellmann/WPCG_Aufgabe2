package main;

import factories.MeshShapeFactory;
import factories.TriangleMeshFactory;
import interfaces.ITriangleMesh;

import javax.media.j3d.Background;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.PointLight;
import javax.swing.JFrame;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

import classes.TriangleMesh;

import com.sun.j3d.utils.behaviors.vp.OrbitBehavior;
import com.sun.j3d.utils.universe.SimpleUniverse;

public class CG2Frame extends JFrame {

	/**
	 * Gernerated serialnumber.
	 */
	private static final long serialVersionUID = -6619879728982489623L;

	/**
     * Canvas object for the 3D content.
     */
    private Canvas3D canvas3D;

    /**
     * Simple universe (provides reasonable default values).
     */
    protected SimpleUniverse universe;

    /**
     * Scene graph for the 3D content scene.
     */
    protected BranchGroup scene = new BranchGroup();

    /**
     * Default constructor.
     */
    public CG2Frame() {
        // Create canvas object to draw on
        canvas3D = new Canvas3D(SimpleUniverse.getPreferredConfiguration());

        // The SimpleUniverse provides convenient default settings
        universe = new SimpleUniverse(canvas3D);
        universe.getViewingPlatform().setNominalViewingTransform();

        // Setup lighting
        addLight(universe);

        // Allow for mouse control
        OrbitBehavior ob = new OrbitBehavior(canvas3D);
        ob.setSchedulingBounds(new BoundingSphere(new Point3d(0, 0, 0),
                Double.MAX_VALUE));
        universe.getViewingPlatform().setViewPlatformBehavior(ob);

        // Set the background color
        Background background = new Background(new Color3f(0.9f, 0.9f, 0.9f));
        BoundingSphere sphere = new BoundingSphere(new Point3d(0, 0, 0), 100000);
        background.setApplicationBounds(sphere);
        scene.addChild(background);

        // Setup frame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Aufgabe 2 - Triangles everywhere");
        setSize(500, 500);
        getContentPane().add("Center", canvas3D);
        setVisible(true);
    }

    /**
     * Setup the lights in the scene. Attention: The light need to be added to
     * the scene before the scene in compiled (see createSceneGraph()).
     */
    private void addLight(SimpleUniverse universe) {
        addPointLight(new Point3f(1, 1, 1));
        addPointLight(new Point3f(-1, -1, -1));
        addPointLight(new Point3f(1, -1, 1));
        addDirectionalLight(new Vector3f(0, 0, 1));
    }

    void addPointLight(Point3f position) {
        PointLight light = new PointLight();
        light.setPosition(position);
        light.setColor(new Color3f(1, 1, 1));
        light.setInfluencingBounds(new BoundingSphere(
                new Point3d(0.0, 0.0, 0.0), 100.0));
        scene.addChild(light);
    }

    void addDirectionalLight(Vector3f direction) {
        DirectionalLight light = new DirectionalLight();
        light.setDirection(direction);
        light.setColor(new Color3f(1, 1, 1));
        light.setInfluencingBounds(new BoundingSphere(
                new Point3d(0.0, 0.0, 0.0), 100.0));
        scene.addChild(light);
    }

    /**
     * Create the default scene graph.
     */
    protected void createSceneGraph() {        
       ITriangleMesh mesh = new TriangleMesh();
       // Testing objects
//       TriangleMeshFactory.createTriangle(mesh);
       TriangleMeshFactory.createSphere(mesh);

       // Adding mesh to our scene
        scene.addChild(MeshShapeFactory.createMeshShape(mesh));

        // Assemble scene
        scene.compile();
        universe.addBranchGraph(scene);
    }
	
	/**
	 * Starting method.
	 * 
	 * @param args Program arguments.
	 */
	public static void main(String[] args) {
		 // Create the central frame
        CG2Frame frame = new CG2Frame();
        // Add content to the scene graph
        frame.createSceneGraph();
	}

}
