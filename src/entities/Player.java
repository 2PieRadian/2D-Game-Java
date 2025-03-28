package entities;

import static utils.LoadSave.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static utils.Constants.Directions.*;
import static utils.Constants.PlayerConstants.*;

public class Player extends Entity {
    // animationTick	Timer — how long we’ve been showing the current frame
    // animationSpeed	Threshold — how long to wait before switching frames
    // animationIndex	Which animation frame to show
    private int animationTick, animationIndex, animationSpeed = 25;
    private BufferedImage[][] animations;
    private int playerAction = IDLE;
    private boolean moving = false;
    private boolean attacking = false;
    private boolean left, up, right, down;
    private float playerSpeed = 2.0f;

    public Player(float x, float y) {
        super(x, y);
        getSubImages();
    }

    public void update() {
        updateAnimationTick();
        updatePosition();
        updatePlayerAction();
    }

    public void render(Graphics g) {
        g.drawImage(animations[playerAction][animationIndex], (int) x, (int) y, 4*64, 4*40, null);
    }

    private void getSubImages() {
        BufferedImage img = GetSpriteAtlas(PLAYER_ATLAS);

        // Storing all the sub-images
        animations = new BufferedImage[9][6];
        for (int i = 0; i < animations.length; i++) {
            for (int j = 0; j < animations[i].length; j++) {
                animations[i][j] = img.getSubimage(j * 64, i * 40, 64, 40);
            }
        }
    }

    private void updateAnimationTick() {
        animationTick++;

        if (animationTick >= animationSpeed) {
            animationTick = 0;
            animationIndex++;

            // So it does not reaches a blank sub-image
            if (animationIndex >= getNumberOfSprites(playerAction)) {
                animationIndex = 0;

                if (playerAction == ATTACK_JUMP_1)
                    attacking = false;
            }
        }
    }

    private void updatePlayerAction() {
        int startPlayerAction = playerAction;

        if (moving)
            playerAction = RUNNING;
        else
            playerAction = IDLE;

        if (attacking) {
            playerAction = ATTACK_JUMP_1;
        }

        if (startPlayerAction != playerAction) {
            animationTick = 0;
            animationIndex = 0;
        }
    }

    private void updatePosition() {
        moving = false;

        if (left && !right) {
            x -= playerSpeed;
            moving = true;
        } else if (right && !left) {
            x += playerSpeed;
            moving = true;
        }

        if (up && !down) {
            y -= playerSpeed;
            moving = true;
        } else if (down && !up) {
            y += playerSpeed;
            moving = true;
        }
    }

    // Getters and Setters for the Direction Fieldss
    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public void resetDirectionBooleans() {
        left = false;
        up = false;
        right = false;
        down = false;
    }

    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }
}
