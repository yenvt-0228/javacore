package Bai1;

public class Rectangle extends Shape {

    public Rectangle(double width, double height) {
        super(width, height);
    }

    public double getArea() {
        return width * height;
    }

    public double getPerimeter() {
        return 2 * (width + height);
    }

    @Override
    public String getInfo() {
        return String.format("Rectangle [width=%.2f, height=%.2f, area=%.2f, perimeter=%.2f]",
                width, height, getArea(), getPerimeter());
    }
}
