package yes.tdd.dojo.domain.marsrover;

public class Location {
    private final Mars area;
    private Double x;
    private Double y;

    public Location(Double x, Double y, Mars mars) {
        this.area = mars;
        this.x = x;
        this.y = y;
        area.verify(x, y);
    }

    public String report() {
        return "I'm at (" + x + ", " + y + ")";
    }

    public void towards(Degree degree) {
        move(this.x + degree.orthogonalDecompose().getLeft(), this.y + degree.orthogonalDecompose().getRight());
    }

    public void back(Degree degree) {
        move(this.x - degree.orthogonalDecompose().getLeft(), this.y - degree.orthogonalDecompose().getRight());
    }

    private void move(Double targetX, Double targetY) {
        area.verify(targetX, targetY);
        this.x = targetX;
        this.y = targetY;
    }
}