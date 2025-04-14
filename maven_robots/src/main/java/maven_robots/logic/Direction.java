package maven_robots.logic;

public enum Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT;

    public static Coord getDir(Direction dir) {
        switch (dir) {
            case UP:
                return new Coord(0, 1);
            
            case DOWN:
                return new Coord(0, -1);

            case LEFT:
                return new Coord(-1, 0);

            case RIGHT:
                return new Coord(1, 0);
        
            default:
                return new Coord(0, 0);
        }
    }
}
