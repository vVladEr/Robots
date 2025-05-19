package maven_robots.logic.fields.cabels;

import java.util.Optional;

import maven_robots.logic.Coord;
import maven_robots.logic.Direction;

public class CabelPart {

    private final Coord partCoord;
    private Optional<Direction> from;
    private Optional<Direction> to;

    public CabelPart(Coord partCoord) {
        this.partCoord = partCoord;
        from = Optional.empty();
        to = Optional.empty();
    }

    public CabelPart(Coord partCoord, Direction from) {
        this(partCoord);
        this.from = Optional.of(from);
    }

    public Coord getCoord() {
        return partCoord;
    }

    public Optional<Direction> getFrom() {
        return from;
    }

    public void setTo(Direction dir) {
        to = Optional.of(dir);
    }

    public Optional<Direction> getTo() {
        return to;
    }

    public void resetTo() {
        from = Optional.empty();
    }


        @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((partCoord == null) ? 0 : partCoord.hashCode());
        result = prime * result + ((from == null) ? 0 : from.hashCode());
        result = prime * result + ((to == null) ? 0 : to.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CabelPart other = (CabelPart) obj;
        if (partCoord == null) {
            if (other.partCoord != null)
                return false;
        } else if (!partCoord.equals(other.partCoord))
            return false;
        if (from == null) {
            if (other.from != null)
                return false;
        } else if (!from.equals(other.from))
            return false;
        if (to == null) {
            if (other.to != null)
                return false;
        } else if (!to.equals(other.to))
            return false;
        return true;
    }
}
