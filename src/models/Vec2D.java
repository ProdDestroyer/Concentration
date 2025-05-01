package models;

public class Vec2D {

    public float x;
    public float y;

    public Vec2D(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vec2D(Vec2D vec2D) {
        this.x = vec2D.x;
        this.y = vec2D.y;
    }

    public Vec2D() {
    }

    public Vec2D getNormal() {
        return new Vec2D(this.y, -this.x);
    }

    public Vec2D getSecondNormal() {
        return new Vec2D(-this.y, this.x);
    }

    public Vec2D normalizeNew() {
        return this.scaleAsNew(1.0f/length());
    }

    public Vec2D normalize() {
        return this.scale(1.0f/length());
    }

    public static float dotProduct(Vec2D vec1, Vec2D vec2) {
        return vec1.getX() * vec2.getX() + vec1.getY() * vec2.getY();
    }

    public Vec2D add(Vec2D vec2D) {
        this.x += vec2D.x;
        this.y += vec2D.y;
        return this;
    }

    public Vec2D addAsNew(Vec2D vec2D) {
        return new Vec2D(this.x + vec2D.x, this.y + vec2D.y);
    }

    public Vec2D scale(float scale) {
        this.x *= scale;
        this.y *= scale;
        return this;
    }

    public Vec2D scaleAsNew(float scale) {
        return new Vec2D(this.x * scale, this.y * scale);
    }

    public Vec2D substract(Vec2D vec2D) {
        this.add(vec2D.scaleAsNew(-1));
        return this;
    }

    public Vec2D substractAsNew(Vec2D vec2D) {
        return this.addAsNew(vec2D.scaleAsNew(-1));
    }

    public float lengthSqrd() {
        return (this.x * this.x) + (this.y * this.y);
    }

    public float length() {
        return (float)(Math.sqrt(lengthSqrd()));
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Vec2D [x=" + x + ", y=" + y + "]";
    }

}
