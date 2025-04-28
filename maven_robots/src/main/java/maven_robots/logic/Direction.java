package maven_robots.logic;

public enum Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT;

    public Coord toCoord() {
        switch (this) {
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

    public Coord getPosInDirection(Coord curPos) {
        Coord vecDir = toCoord();
        return new Coord(curPos.x + vecDir.x, curPos.y + vecDir.y);    
    }
}
