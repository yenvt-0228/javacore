package Practice1;

public class Main {
    public static void main(String[] args) {
        Shape shape = new Shape(5, 3);
        Rectangle rectangle = new Rectangle(6, 4);
        Circle circle = new Circle(5);

        System.out.println("=== Shape ===");
        System.out.println(shape.getInfo());

        System.out.println("\n=== Rectangle ===");
        System.out.println(rectangle.getInfo());

        System.out.println("\n=== Circle ===");
        System.out.println(circle.getInfo());
    }
}
