// Name: Minh Ngoc Le
// Class: COMP 142 (Section 2)
// Game Description:  the Santa Claus will collect gifts and give it to children. To collect gifts, the Santa use his beloved reindeer to guide him.
// I have neither given nor received unauthorized aid on this program.

package project6;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * This class represents a generic game on a canvas.
 */
public class SantaGame {
    private SimpleCanvas canvas;
    private boolean isVisible;
    private GameObject santa; // Create the object Santa Claus for the game
    private GameObject reindeer;
    private GameObject upper; // Create the upper pipe
    private GameObject lower; // Create the lower pipe
    private ArrayList<GameObject> uppers; // Create a list of upper pipes
    private ArrayList<GameObject> lowers; // Create a list of lower pipes
    //private int points;
    private SantaClock clock; // Create the clock to keep track the time
    private GameObject gift; // Create the gift
    private ArrayList<GameObject> gifts; // Create the list of gifts
    private int giftPoints = 0; // initialize the points 0
    private int giftEarned = 0; // initialize the gift earned by 0.
    private int lastYofDeer;
    private int lastoflastYofDeer;


    // add more instance variables here as necessary.

    /**
     * Construct a new instance of the game with a given width and height.
     */
    public SantaGame(int width, int height) {
        isVisible = false;
        canvas = new SimpleCanvas(width, height, "Santa Claus is coming to town !!!");
        uppers = new ArrayList();
        lowers = new ArrayList();
        clock = new SantaClock(0);
        gifts = new ArrayList();


        // Create game objects here if they should appear at the beginning of the game.

        // Create a Santa Claus
        santa = new GameObject();
        santa.setLocation(new Location(200, 300));
        santa.setBorderColor(Color.BLACK);
        santa.removeBorder();
        santa.setImageFilename("santaa.png");
        santa.setWidth(100);
        santa.setHeight(85);

        // Create a reindeer
        reindeer = new GameObject();
        reindeer.setLocation(new Location(300, 300));
        reindeer.removeBorder();
        reindeer.setImageFilename("reindeer.png");
        reindeer.setWidth(100);
        reindeer.setHeight(85);
        lastYofDeer = 300;
        lastoflastYofDeer = 300;


        // Create upper pipes and add them to the ArrayList.
        int numPipes = 6;
        for (int i = 0; i <= numPipes - 1; i++) {
            //System.out.println("added upper");
            upper = new GameObject();
            int upperY = (int) (Math.random() * 160);
            upper.setLocation(new Location(600 + i * 180, upperY));
            upper.setImageFilename("upper.png");
            upper.setWidth(150);
            upper.setHeight(450);
            uppers.add(upper);
        }

        // Create the lower pipes and add them to the ArrayList
        for (int i = 0; i <= numPipes - 1; i++) {
            //System.out.println("added lower");
            lower = new GameObject();
            int lowerY = (int) (Math.random() * 240) + 700;
            int upperY = uppers.get(i).getLocation().getY();
            if (lowerY - upperY <= santa.getHeight() + 40) {
                lowerY += 40;
            }
            lower.setLocation(new Location(600 + i * 180, lowerY));
            lower.setImageFilename("lower.png");
            lower.setWidth(150);
            lower.setHeight(450);
            lowers.add(lower);
        }

        // Create gifts and add them to the ArrayList
        int numGifts = numPipes / 6;
        for (int i = 0; i <= numGifts; i++) {
            gift = new GameObject();
            int giftX = 850 + i * 800;
            gift.setLocation(new Location(giftX, 450));
            gift.setImageFilename("gift.png");
            gift.setHeight(35);
            gift.setWidth(35);
            gifts.add(gift);
        }

    }

    /**
     * Draw the state of the game on the canvas.
     */
    public void draw() {
        canvas.clear(); // always clear first.

        // Draw gifts
        for (int i = 0; i <= gifts.size() - 1; i++) {
            gifts.get(i).draw(canvas);
        }
        // draw the santa
        santa.draw(canvas);
        reindeer.draw(canvas);


        // Draw upper pipes
        for (int i = 0; i <= uppers.size() - 1; i++) {
            uppers.get(i).draw(canvas);
        }

        // Draw lower pipes
        for (int i = 0; i <= lowers.size() - 1; i++) {
            lowers.get(i).draw(canvas);
        }

        int giftpoints = earnedPoints();
        int gif = giftEarned();
        canvas.setPenColor(Color.BLACK);
        canvas.drawString(400, 20, "Time passed: " + clock.getTime()); // Draw the String that shows the time passed on Canvas
        canvas.drawString(740, 20, "Points earned: " + giftpoints); // Draw the String that shows the points earned on Canvas.
        canvas.drawString(20, 20, "Gift Earned: " + gif); // Draw the String that shows the gift earned on the Canvas

        // Draw the String to notify on Canvas when the game is over.
        if (isGameOver()) {
            canvas.drawString(400, 400, "Game Over");
            canvas.drawString(360, 420, "Total points earned: " + giftPoints);
        }


        // Show the window if needs be.
        if (!isVisible) {
            canvas.show();
            isVisible = true;
        } else {
            canvas.update();
        }

    }

    /**
     * Is the game over?
     */
    private boolean isGameOver() { // The game is over when the santa overlapped with the pipes

        for (int i = 0; i <= uppers.size() - 1; i++) {

            if (santa.overlaps(uppers.get(i))) {
                System.out.println("Overlapped");
                return true;
            }

        }
        for (int i = 0; i <= lowers.size() - 1; i++) {

            if (santa.overlaps(lowers.get(i))) {
                System.out.println("Overlapped");
                return true;
            }

        }

        return false;


    }

    /**
     * Start the game running.
     */
    public void runGame() {
        // Create starting screen
        draw();
        canvas.setPenColor(Color.BLACK);
        canvas.drawString(400, 400, "Click mouse to start!");
        canvas.update();
        canvas.waitForClick();


        // Loop while the game is not over:
        while (!isGameOver()) {
            // Update the state of the game.
            // This might involve any number of things, but probably
            // involves moving any GameObjects however they should be moved,
            // updating any GameObjects that should be updated,
            // and checking for overlaps.

            // update the time
            clock.update();

            // Draw the state of the game.
            draw();
            drop();
            moveLeft();
            earnedPoints();
            removeGift();
            // handle keyboard

            handleKeyboard();

            // handle mouse
            handleMouse();

            canvas.pause(50); // can adjust this higher or lower as needed
        }
        // Game is now over,
        // draw the state of the game one last time in case there were any last updates
        // that we need to show.
        draw();

        // Do any other game wrapup things here.

    }

    /**
     * Do any keyboard-related things here.  You can test for any key being
     * pressed.  Use this list of "VK_" constants to find the one you want:
     * https://docs.oracle.com/en/java/javase/13/docs/api/java.desktop/java/awt/event/KeyEvent.html
     * You can take any other actions you want on your GameObjects here, based
     * on keypresses, like moving objects, interacting with other objects (attacking, defending, throwing
     * or giving objects, etc), or changing the state of an object (such as switching
     * between spells to cast, for instance).  You can also check for overlaps here, of course.
     */
    private void handleKeyboard() {

        // the santa jumps whenever you press the Space on your keyboard.

        if (canvas.isKeyPressed(KeyEvent.VK_SPACE)) {
            Location loc = reindeer.getLocation();
            int deerY = loc.getY();
            loc.setY(loc.getY() - 40);
            santa.getLocation().setY(lastoflastYofDeer);
            lastoflastYofDeer = lastYofDeer;
            lastYofDeer = deerY;

            System.out.println("Up arrow is pressed.");
        }


    }

    /**
     * Do any mouse-related things here.  This would probably involve using the
     * location of a mouse click to do things like interact with a clicked GameObject
     * in some way.  You can use the isInside() method in GameObject to test if a
     * mouse click (x, y) is inside the object.
     */
    private void handleMouse() {
        if (canvas.isMousePressed()) {
            int clickX = canvas.getMouseClickX();
            int clickY = canvas.getMouseClickY();
            System.out.println("Mouse click detected at " + clickX + " " + clickY);
        }
    }

    private void drop() {

        // The santa will always drop (go down) unless you press Space to jump.

        Location loc = reindeer.getLocation();
        int deerY = loc.getY();
        loc.setY(deerY + 10);
        santa.getLocation().setY(lastoflastYofDeer);
        lastoflastYofDeer = lastYofDeer;
        lastYofDeer = deerY;


    }

    public void moveLeft() {

        // move the pipes to the left

        for (GameObject upper : uppers) {
            Location upperLoc = upper.getLocation();
            upperLoc.setX(upperLoc.getX() - 15);
            if (upperLoc.getX() <= -100) {
                upperLoc.setX(1000);
                int upperY = (int) (Math.random() * 160);
                upperLoc.setY(upperY);

            }
        }

        for (GameObject lower : lowers) {
            Location lowerLoc = lower.getLocation();
            lowerLoc.setX(lowerLoc.getX() - 15);
            if (lowerLoc.getX() <= -100) {
                lowerLoc.setX(1000);
                int lowerY = (int) (Math.random() * 240) + 700;
                int i = lowers.indexOf(lower);
                int upperY = uppers.get(i).getLocation().getY();
                if (lowerY - upperY <= santa.getHeight() + 100) {
                    lowerY += 60;
                }
                lowerLoc.setY(lowerY);
            }
        }

        // move the gift to the left
        for (GameObject gift : gifts) {
            Location giftLoc = gift.getLocation();
            giftLoc.setX(giftLoc.getX() - 15);
            if (giftLoc.getX() <= -20) {
                giftLoc.setX(1800);
                giftLoc.setY(450);
            }
        }


    }

    public int earnedPoints() {
        return giftPoints;
    }

    public int giftEarned() {
        return giftEarned;
    }

    public void removeGift() {
        // Remove gift is my first idea, but then I want if the santa overlapped the gift, it will change the gift location.
        // The game actually just have 6 pipes, but I use the loop to make it happens again and again so that you can play until you loose.
        for (int i = 0; i <= gifts.size() - 1; i++) {
            if (santa.overlaps(gifts.get(i))) {
                gifts.get(i).getLocation().setY(-20);
                //gifts.remove(gifts.get(i));
                giftPoints += 20;
                giftEarned += 1;
            }
        }
    }
}
