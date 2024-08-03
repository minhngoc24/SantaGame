// Name: Minh Ngoc Le
// Class: COMP 142 (Section 2)
// Game Description:  the Santa Claus will collect gifts and give it to children. To collect gifts, the Santa use his beloved reindeer to guide him.
// I have neither given nor received unauthorized aid on this program.


package project6;

public class SantaClock {
    private int time = 0;

    public SantaClock(int time) {
        this.time = time;

    }

    public void update() {
        time += 1;
    }

    public int getTime() {
        return time;
    }
}
