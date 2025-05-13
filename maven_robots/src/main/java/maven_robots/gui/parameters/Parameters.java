package maven_robots.gui.parameters;

public class Parameters {
    private final int x;
    private final int y;
    private final int width;
    private final int height;

    public Parameters(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public static Parameters parseParameters(String property) {
        String[] propertyParts = property.split(";");

        int x = Integer.parseInt(propertyParts[0]);
        int y = Integer.parseInt(propertyParts[1]);
        int width = Integer.parseInt(propertyParts[2]);
        int height = Integer.parseInt(propertyParts[3]);

        return new Parameters(x, y, width, height);
    }
}
