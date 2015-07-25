/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.rmit.Game2dEngine.node;

import au.com.rmit.Game2dEngine.common.Game2dEngineShared;
import au.com.rmit.Game2dEngine.gravity.Gravity;
import au.com.rmit.Game2dEngine.scene.Layer;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import static java.lang.Math.abs;
import java.util.HashMap;
import javax.imageio.ImageIO;

/**
 *
 * @author ricolwang
 */
public class Sprite extends Node
{

    public boolean bDrawFrame = false;
    public Color theColorOfFrame = Color.yellow;
    public boolean bCollisionDetect = false;
    public int collisionCategory = -1;
    public int collisionTargetCategory = -1;
    public HashMap<Sprite, Game2dEngineShared.TypeCollisionDetection> hashCollision = new HashMap();

    private final Color blackTransparent = new Color(0, 0, 0, 0);

    private double currentLife = 0;
    private boolean isAlive = true;

    public int layer = 0;
    public boolean bCustomDrawing = false;
    public static final long EVER = Long.MAX_VALUE;
    public double mass;
    public Gravity g;
    protected float alpha = 1;
    protected int red = 0;
    protected int green = 0;
    protected int blue = 0;
    private Color theColor = new Color(red / 255.0f, green / 255.0f, blue / 255.0f, alpha);

    protected double lastUpdateTime;
    public double lifetime = Sprite.EVER; //in seconds

    protected double starttime = System.currentTimeMillis();
    protected BufferedImage theImage;

    BufferedImage theImageCanvas;
    Graphics2D theGraphics;

    public Sprite(double x, double y, double width, double height, double mass)
    {
        super(x, y, width, height);

        this.mass = mass;
        this.lastUpdateTime = System.currentTimeMillis();
    }

    public Sprite(String imagename)
    {
        this(0, 0, 0, 0, 0);

        try
        {
            theImage = ImageIO.read(new File(imagename));
            this.initForImage();
        } catch (IOException e)
        {
            System.out.println("Sprite exception: " + e);
        }
    }

    final void initForImage()
    {
        this.setWidth(theImage.getWidth());
        this.setHeight(theImage.getHeight());
    }

    BufferedImage getTheImageCanvas()
    {
        if (theImageCanvas == null)
        {
            theImageCanvas = new BufferedImage(abs((int) width), abs((int) height), BufferedImage.TYPE_INT_ARGB);
        }
        return theImageCanvas;
    }

    Graphics2D getTheImageGraphics()
    {
        if (theGraphics == null)
        {
            theGraphics = this.getTheImageCanvas().createGraphics();
        }
        return theGraphics;
    }

    void releaseTheImageCanvas()
    {
        theGraphics = null;
        theImageCanvas = null;
    }

    public void applyGravity(Gravity g)
    {
        this.g = new Gravity(g.GX, g.GY);
    }

    public void updateState(double currentTime)
    {
        double t = (currentTime - this.lastUpdateTime) / 1000.0f;
        currentLife += t;
        if (currentLife >= lifetime)
        {
            this.setDead();
        }
    }

    public void updateGUI(Graphics2D g)
    {
        int tmpX = (int) x;
        int tmpY = (int) y;
        int tmpSceneWidth = this.theScene.getWidth();
        int tmpSceneHeight = this.theScene.getHeight();
        int w = (int) width;
        int h = (int) height;

        if (tmpX + w < 0 || tmpY + h < 0)
        {
            return;
        }

        if (tmpX > tmpSceneWidth || tmpY > tmpSceneHeight)
        {
            return;
        }

        if (abs(w) <= 0.1 || abs(h) <= 0.1)
        {
            return;
        }

        if (g == null)
        {
            return;
        }

        if (this.isAlive)
        {
            if (bCustomDrawing)
            {
                Graphics2D theGraphics2D = this.getTheImageGraphics();
                this.onCustomDraw(theGraphics2D);
            } else
            {
                if (this.theImage != null)
                {
                    Graphics2D theGraphics2D = this.getTheImageGraphics();
                    theGraphics2D.setBackground(blackTransparent);
                    theGraphics2D.clearRect(0, 0, w, h);
                    this.drawFrame(theGraphics2D);

                    AffineTransform old = theGraphics2D.getTransform();
                    theGraphics2D.rotate(angle, w / 2.0f, h / 2.0f);

                    int tmpImageWidth = this.theImage.getWidth();
                    int tmpImageHeight = this.theImage.getHeight();
                    int tmpImagePosX = (int) ((width - tmpImageWidth) / 2.0f);
                    int tmpImagePosY = (int) ((height - tmpImageHeight) / 2.0f);
                    theGraphics2D.drawImage(theImage, tmpImagePosX, tmpImagePosY, tmpImageWidth, tmpImageHeight, null);

                    theGraphics2D.setTransform(old);
                } else
                {
                    Graphics2D theGraphics2D = this.getTheImageGraphics();
                    theGraphics2D.setBackground(blackTransparent);
                    theGraphics2D.clearRect(0, 0, w, h);
                    this.drawFrame(theGraphics2D);

                    AffineTransform old = theGraphics2D.getTransform();

                    theGraphics2D.rotate(angle, w / 2.0f, h / 2.0f);
                    theGraphics2D.setColor(theColor);
                    theGraphics2D.fillArc(0, 0, w, h, 0, 360);

                    theGraphics2D.setTransform(old);
                }
            }

            if (theImageCanvas != null)
            {
                Composite old = g.getComposite();

                AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
                g.setComposite(ac);
                g.drawImage(theImageCanvas, (int) x, (int) y, null);

                g.setComposite(old);
            }
        }
    }

    public boolean isAlive()
    {
        return this.isAlive;
    }

    public void setImage(String imageName)
    {
        try
        {
            theImage = ImageIO.read(new File(imageName));
        } catch (IOException e)
        {
            theImage = null;
        }
    }

    @Override
    public void setWidth(double width)
    {
        super.setWidth(width);
        this.releaseTheImageCanvas();
    }

    @Override
    public void setHeight(double height)
    {
        super.setHeight(height);
        this.releaseTheImageCanvas();
    }

    public float getAlpha()
    {
        return alpha;
    }

    public void setAlpha(float value)
    {
        if (value >= 0 && value <= 1)
        {
            this.alpha = value;
            theColor = new Color(red / 255.0f, green / 255.0f, blue / 255.0f, alpha);
        }
    }

    public int getRed()
    {
        return red;
    }

    public int getGreen()
    {
        return green;
    }

    public int getBlue()
    {
        return blue;
    }

    public void setRed(int value)
    {
        if (value >= 0 && value <= 255)
        {
            this.red = value;
            theColor = new Color(red / 255.0f, green / 255.0f, blue / 255.0f, alpha);
        }
    }

    public void setGreen(int value)
    {
        if (value >= 0 && value <= 255)
        {
            this.green = value;
            theColor = new Color(red / 255.0f, green / 255.0f, blue / 255.0f, alpha);
        }
    }

    public void setBlue(int value)
    {
        if (value >= 0 && value <= 255)
        {
            this.blue = value;
            theColor = new Color(red / 255.0f, green / 255.0f, blue / 255.0f, alpha);
        }
    }

    public Color getColor()
    {
        return this.theColor;
    }

    public double getCentreX()
    {
        return this.x + width / 2.0;
    }

    public double getCentreY()
    {
        return this.y + height / 2.0;
    }

    public void setCentreX(double value)
    {
        this.setX(value - width / 2.0);
    }

    public void setCentreY(double value)
    {
        this.setY(value - height / 2.0);
    }

    public void setDead()
    {
        this.isAlive = false;
        this.hashCollision.clear();
        this.onDead();
    }

    public void onCustomDraw(Graphics2D theGraphics2D)
    {
        theGraphics2D.setBackground(blackTransparent);
        theGraphics2D.clearRect(0, 0, (int) width, (int) height);
    }

    void drawFrame(Graphics2D theGraphics2D)
    {
        if (this.bDrawFrame)
        {
            theGraphics2D.drawRect(0, 0, (int) width - 1, (int) height - 1);
        }
    }

    public void onDead()
    {

    }

    public boolean collideWith(Sprite target)
    {
        return x < target.x + target.width && x + width > target.x && y < target.y + target.height && y + height > target.y;
    }

    public void onCollideWith(Sprite target)
    {

    }

    public void onNotCollideWith(Sprite target)
    {

    }
    
    public void onAddToLayer(Layer theLayer)
    {
        
    }
    
    public void onRemovedFromLayer(Layer theLayer)
    {
        
    }
}
