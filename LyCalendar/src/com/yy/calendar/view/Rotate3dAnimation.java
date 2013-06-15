package com.yy.calendar.view;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * An animation that rotates the view on the Y axis between two specified
 * angles. This animation also adds a translation on the Z axis (depth) to
 * improve the effect.
 */
public class Rotate3dAnimation extends Animation {
    private final float fromDegrees;

    private final float toDegrees;

    private final float centerX;

    private final float centerY;

    private final float depthZ;

    private final boolean reverse;

    private Camera camera;

    /**
     * Creates a new 3D rotation on the Y axis. The rotation is defined by its
     * start angle and its end angle. Both angles are in degrees. The rotation
     * is performed around a center point on the 2D space, definied by a pair of
     * X and Y coordinates, called centerX and centerY. When the animation
     * starts, a translation on the Z axis (depth) is performed. The length of
     * the translation can be specified, as well as whether the translation
     * should be reversed in time.
     * 
     * @param fromDegrees the start angle of the 3D rotation
     * @param toDegrees the end angle of the 3D rotation
     * @param centerX the X center of the 3D rotation
     * @param centerY the Y center of the 3D rotation
     * @param reverse true if the translation should be reversed, false
     *            otherwise
     */
    public Rotate3dAnimation(float fromDegrees, float toDegrees, float centerX, float centerY, float depthZ, boolean reverse) {
        this.fromDegrees = fromDegrees;
        this.toDegrees = toDegrees;
        this.centerX = centerX;
        this.centerY = centerY;
        this.depthZ = depthZ;
        this.reverse = reverse;
    }

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        camera = new Camera();
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        final float fromDegrees = this.fromDegrees;
        float degrees = fromDegrees + ((toDegrees - fromDegrees) * interpolatedTime);

        final float centerX = this.centerX;
        final float centerY = this.centerY;
        final Camera camera = this.camera;

        final Matrix matrix = t.getMatrix();

        camera.save();
        if (reverse) {
            camera.translate(0.0f, 0.0f, depthZ * interpolatedTime);
        } else {
            camera.translate(0.0f, 0.0f, depthZ * (1.0f - interpolatedTime));
        }
        camera.rotateY(degrees);
        camera.getMatrix(matrix);
        camera.restore();

        matrix.preTranslate(-centerX, -centerY);
        matrix.postTranslate(centerX, centerY);
    }
}
