package maven_robots.logic;

public enum Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT;

    public Coord toCoord() {
        switch (this) {
            case UP:
                return new Coord(0, -1);
            
            case DOWN:
                return new Coord(0, 1);

            case LEFT:
                return new Coord(-1, 0);

            case RIGHT:
                return new Coord(1, 0);
        
            default:
                return new Coord(0, 0);
        }
    }

    public Coord getPosInDirection(Coord curPos) {
        Coord vecDir = toCoord();
        return new Coord(curPos.x + vecDir.x, curPos.y + vecDir.y);    
    }

    public static Direction toDirection(Coord diff) {
        if (diff.x == 0 && diff.y == -1) {
            return UP;
        }
        if (diff.x == 0 && diff.y == 1) {
            return DOWN;
        }
        if (diff.x == -1 && diff.y == 0) {
            return LEFT;
        }
        if (diff.x == 1 && diff.y == 0) {
            return RIGHT;
        }
        throw new IllegalArgumentException();
    }

    public Direction getOpposite() {
        switch (this) {
            case UP:
                return DOWN;
            
            case DOWN:
                return UP;

            case LEFT:
                return RIGHT;

            case RIGHT:
                return LEFT;
        
            default:
                throw new IllegalArgumentException("no opposite for this direction");
        }
    }
}
