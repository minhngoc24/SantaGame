// Name: Minh Ngoc Le
// Class: COMP 142 (Section 2)
// Game Description:  the Santa Claus will collect gifts and give it to children. To collect gifts, the Santa use his beloved reindeer to guide him.
// I have neither given nor received unauthorized aid on this program.


package project6;

/**
 * A Location represents an (x, y) location.
 */
public class Location {
    private int x, y;

    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
