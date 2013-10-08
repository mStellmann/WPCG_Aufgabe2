package factories;

import interfaces.ITriangleMesh;

import javax.media.j3d.Appearance;
import javax.media.j3d.GeometryArray;
import javax.media.j3d.Shape3D;
import javax.media.j3d.TriangleArray;
import javax.vecmath.Color3f;

import classes.AppearanceHelper;
import classes.Triangle;

import com.sun.j3d.utils.geometry.GeometryInfo;
import com.sun.j3d.utils.geometry.NormalGenerator;

/**
 * Factory for converting a ITriangleMesh into a Shape3D.
 * 
 * @author Matthias Stellmann & Grzegorz Markiewicz
 *
 */
public class MeshShapeFactory {
	/**
	 * Creates a Shape3D-Object out of an ITriangleMesh.
	 * 
	 * @param mesh
	 *            ITriangleMesh to create the Shape3D-Object.
	 * @return Shape3D-Object
	 */
	public static Shape3D createMeshShape(ITriangleMesh mesh) {
		TriangleArray triAry = new TriangleArray(mesh.getNumberOfVertices(), TriangleArray.COORDINATES | TriangleArray.NORMALS);

		int i = 0;
		for (Triangle elem : mesh.getTriangleList()) {
			// Converting Vector3d to floatArray
			float[] normals = { (float) elem.getNormalVector().x, (float) elem.getNormalVector().y, (float) elem.getNormalVector().z };
			// Setting first coordinate and normal of the triangle
			triAry.setCoordinate(i, mesh.getVertex(elem.getI()));
			triAry.setNormal(i++, normals);
			// Setting second coordinate and normal of the triangle
			triAry.setCoordinate(i, mesh.getVertex(elem.getJ()));
			triAry.setNormal(i++, normals);
			// Setting third coordinate and normal of the triangle
			triAry.setCoordinate(i, mesh.getVertex(elem.getK()));
			triAry.setNormal(i++, normals);
		}

		GeometryInfo geoInfo = new GeometryInfo(triAry);
		
		// TODO Why is this necessary?
//		NormalGenerator ng = new NormalGenerator();
//		ng.generateNormals(geoInfo);
		
		GeometryArray geoAry = geoInfo.getGeometryArray();

		Shape3D resultShape = new Shape3D(geoAry, new Appearance());
		AppearanceHelper.setColor(resultShape,  new Color3f(0.2f, 0.7f, 0f));
		return resultShape;

	}
}
