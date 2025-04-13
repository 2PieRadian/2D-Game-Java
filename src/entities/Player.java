package entities;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static utils.Constants.Directions.*;
import static utils.Constants.PlayerConstants.*;
import static utils.HelperMethods.*;
import static utils.LoadSave.*;
import static main.Game.SCALE;

public class Player extends Entity {
    // animationTick	Timer — how long we’ve been showing the current frame
    // animationSpeed	Threshold — how long to wait before switching frames
    // animationIndex	Which animation frame to show
    private int animationTick, animationIndex, animationSpeed = 25;
    private BufferedImage[][] animations;
    private int playerAction = IDLE;
    private boolean moving = false;
    private boolean attacking = false;
    private boolean left, right, jump;
    private float playerSpeed = 2.0f;
    private int[][] levelData;

    // Offset Values for x and y of the player character
    private float offsetXDraw = 21 * SCALE;
    private float offsetYDraw = 4 * SCALE;
    private float playerWidth = 20 * SCALE;
    private float playerHeight = 27 * SCALE;

    // Gravity
    private float airSpeed = 0f;
    private float gravity = 0.04f * SCALE;
    private float jumpSpeed = -2.5f * SCALE;
    private float fallSpeedAfterCollision = 0.5f * SCALE;
    private boolean inAir = false;

    public Player(float x, float y, int width, int height) {
        super(x, y, width, height);
        getSubImages();
        initHitbox(x + Math.round(offsetXDraw), y + Math.round(offsetYDraw), Math.round(playerWidth), Math.round(playerHeight));
    }

    public void update() {
        updatePosition();
        updateAnimationTick();
        updatePlayerAction();
    }

    public void render(Graphics g) {
        g.drawImage(animations[playerAction][animationIndex], (int) (hitbox.x - offsetXDraw), (int) (hitbox.y -  offsetYDraw), (int) width, (int) height, null);
        drawHitbox(g);
    }

    public void loadLevelData(int[][] levelData) {
        this.levelData = levelData;

        if (!IsEntityOnFloor(hitbox, levelData))
            inAir = true;
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
            if (animationIndex >= GetNumberOfSprites(playerAction)) {
                animationIndex = 0;

                if (playerAction == ATTACK_JUMP_1)
                    attacking = false;
            }
        }
    }

    private void updatePlayerAction() {
        int initialPlayerAction = playerAction;

        if (attacking)
            playerAction = ATTACK_JUMP_1;
        else if (inAir) {
            if (airSpeed < 0)
                playerAction = JUMP;
            else
                playerAction = FALLING;
        } else if (moving) {
            playerAction = RUNNING;
        } else {
            playerAction = IDLE;
        }

        if (initialPlayerAction != playerAction) {
            animationTick = 0;
            animationIndex = 0;
        }
    }

    private void updatePosition() {
        moving = false;

        if (jump)
            jump();

        if (!left && !right && !inAir) return;

        float xSpeed = 0;

        if (left) {
            xSpeed -= playerSpeed;
        }
        if (right) {
            xSpeed += playerSpeed;
        }

        if (!inAir)
            if (!IsEntityOnFloor(hitbox, levelData))
                inAir = true;

        if (inAir) {
            // Update both X and Y positions
            if (CanMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, levelData)) {
                hitbox.y += airSpeed;
                airSpeed += gravity;
                updateXPos(xSpeed);
            } else {
                hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, airSpeed);
                // We hit something and airSpeed is greater than 0 === We hit the floor
                if (airSpeed > 0)
                    resetInAir();
                else
                    airSpeed = fallSpeedAfterCollision;
                updateXPos(xSpeed);
            }
        } else
            // Update sonly X position
            updateXPos(xSpeed);

        moving = true;
    }

    private void jump() {
        if (inAir) return;
        inAir = true;
        airSpeed = jumpSpeed;
    }

    private void resetInAir() {
        inAir = false;
        airSpeed = 0;
    }

    private void updateXPos(float xSpeed) {
        if (CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, levelData))
            hitbox.x += xSpeed;
        else
            hitbox.x = GetEntityXPosNextToWall(hitbox, xSpeed);
    }

    // Getters and Setters for the Direction Fieldss
    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void resetDirectionBooleans() {
        left = false;
        right = false;
    }

    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }

    public void setJump(boolean jump) {
        this.jump = jump;
    }
}
