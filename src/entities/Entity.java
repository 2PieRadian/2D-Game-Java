package entities;

import java.awt.*;

public abstract class Entity {

    protected float x, y;
    protected int width, height;
    protected Rectangle hitbox;

    public Entity(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        this.hitbox = new Rectangle((int) x, (int) y, width, height);
    }

    protected void updateHitbox () {
        hitbox.x = (int) x;
        hitbox.y = (int) y;
    }

    protected void drawHitbox(Graphics g) {
        g.setColor(Color.LIGHT_GRAY);
        g.drawRect((int) x, (int) y, width, height);
    }

    public Rectangle getHitbox() {
        return hitbox;
    }
}