package Practice1;

public class Circle extends Shape {
    private double radius;

    public Circle(double radius) {
        super(2 * radius, 2 * radius);
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }

    public double getDiameter() {
        return width;
    }

    public double getArea() {
        return Math.PI * radius * radius;
    }

    public double getCircumference() {
        return getDiameter() * 3.14;
    }

    @Override
    public String getInfo() {
        return String.format("Circle [radius=%.2f, area=%.2f, circumference=%.2f]",
                radius, getArea(), getCircumference());
    }
}
