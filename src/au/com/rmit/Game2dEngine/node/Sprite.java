/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.rmit.Game2dEngine.node;

import au.com.rmit.Game2dEngine.action.Action;
import au.com.rmit.Game2dEngine.common.Game2dEngineShared;
import au.com.rmit.Game2dEngine.gravity.Gravity;
import au.com.rmit.Game2dEngine.scene.Layer;
import au.com.rmit.Game2dEngine.scene.Scene;
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
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import javax.imageio.ImageIO;

/**
 *
 * @author ricolwang
 */
public class Sprite extends Node
{

    public boolean bChild;
    public Sprite parent;
    public Scene theScene;

    public boolean bDrawFrame = false;
    public Color theColorOfFrame = Color.yellow;
    public boolean bCollisionDetect = false;
    public int collisionCategory = -1;
    public int collisionTargetCategory = -1;
    public HashMap<Sprite, Game2dEngineShared.TypeCollisionDetection> hashCollision = new HashMap();
    public boolean bCustomDrawing = false;
    public static final long EVER = Long.MAX_VALUE;
    public boolean bDeadIfNoActions;

    private int layer = 0;
    private double lifetime = Sprite.EVER; //in seconds
    private double lastUpdateTime;
    private double starttime = System.currentTimeMillis();
    private double velocityX;
    private double velocityY;
    private double currentLife = 0;
    private boolean isAlive = true;

    private Set<Action> theSetOfActionsWillDelete = new HashSet<>();
    private Set<Action> theSetOfActionsWillAdd = new HashSet<>();
    private Set<Action> theSetOfActions = new HashSet<>();

    private Set<Action> theSetOfActionsInQueueWillDelete = new HashSet<>();
    private Queue<Set<Action>> theQueueOfActions = new LinkedList<>();

    private Set<Sprite> theSetOfChildrenWillDelete = new HashSet<>();
    private Set<Sprite> theSetOfChildrenWillAdd = new HashSet<>();
    private Set<Sprite> theSetOfChildren = new HashSet<>();

    private double mass;
    private Gravity g;
    private float alpha = 1;
    private int red = 0;
    private int green = 0;
    private int blue = 0;
    private double angle;
    private Color theColor = new Color(red / 255.0f, green / 255.0f, blue / 255.0f, alpha);

    private BufferedImage theImageCanvas;
    private Graphics2D theGraphics;
    private BufferedImage theImage;
    private final Color blackTransparent = new Color(0, 0, 0, 0);

    public Sprite(double x, double y, double width, double height, double mass, double velocityX, double velocityY)
    {
        super(x, y, width, height);
        this.mass = mass;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.lastUpdateTime = System.currentTimeMillis();
    }

    public Sprite(String imagename)
    {
        this(0, 0, 0, 0, 0, 0, 0);

        try
        {
            theImage = ImageIO.read(new File(imagename));
            this.initForImage();
        } catch (IOException e)
        {
            System.out.println("Sprite exception: " + e);
        }
    }

    public Sprite()
    {
        this(0, 0, 0, 0, 0, 0, 0);
    }

    public Sprite(double x, double y, double width, double height)
    {
        this(x, y, width, height, 0, 0, 0);
    }

    public void updateState(double currentTime)
    {
        //how much time passed since last update
        double delta = currentTime - this.lastUpdateTime;
        double t = delta / 1000.0f; //in seconds
        currentLife += t;

        if (currentLife >= lifetime)
        {
            this.setDead();
        }

        if (this.isAlive())
        {
            //update state
            if (this.g != null)
            {
                velocityX += this.g.GX * t;
                velocityY += this.g.GY * t;
            }

            double IncX = velocityX * t;
            double IncY = velocityY * t;

            x += IncX;
            y += IncY;

            //perform actions
            //perform a action in the queue one by one in sequence
            Set<Action> theSetOfQueuedActions = this.theQueueOfActions.peek();

            if (theSetOfQueuedActions != null)
            {
                if (theSetOfQueuedActions.size() > 0)
                {
                    for (Action aAction : theSetOfQueuedActions)
                    {
                        this.onActionRunning(aAction);
                        aAction.perform(delta);

                        if (aAction.bComplete)
                        {
                            theSetOfActionsInQueueWillDelete.add(aAction);
                            this.onActionComplete(aAction);
                        }
                    }

                    theSetOfQueuedActions.removeAll(theSetOfActionsInQueueWillDelete);
                    theSetOfActionsInQueueWillDelete.clear();
                } else
                {
                    this.theQueueOfActions.remove(theSetOfQueuedActions);
                }
            }

            //perform other actions
            //add new actions
            this.theSetOfActions.addAll(this.theSetOfActionsWillAdd);
            this.theSetOfActionsWillAdd.clear();

            if (this.theSetOfActions.size() <= 0 && this.theQueueOfActions.size() <= 0)
            {
                if (this.bDeadIfNoActions)
                {
                    this.setDead();
                }
            } else
            {
                //run actions
                for (Action aAction : this.theSetOfActions)
                {
                    this.onActionRunning(aAction);
                    aAction.perform(delta);

                    if (aAction.bComplete)
                    {
                        this.theSetOfActionsWillDelete.add(aAction);
                        this.onActionComplete(aAction);
                    }
                }
            }

            //delete old actions
            this.theSetOfActions.removeAll(this.theSetOfActionsWillDelete);
            this.theSetOfActionsWillDelete.clear();

            //add new children
            this.theSetOfChildren.addAll(this.theSetOfChildrenWillAdd);
            this.theSetOfChildrenWillAdd.clear();

            //update its children
            for (Sprite aSprite : this.theSetOfChildren)
            {
                aSprite.updateState(currentTime);

                if (!aSprite.isAlive)
                {
                    this.theSetOfChildrenWillDelete.add(aSprite);
                }
            }

            //delete old children
            this.theSetOfChildren.removeAll(this.theSetOfChildrenWillDelete);
            this.theSetOfChildrenWillDelete.clear();

            this.lastUpdateTime = currentTime;
        }
    }

    public void updateGUI(final Graphics2D g)
    {
        int tmpX = (int) x;
        int tmpY = (int) y;

        int tmpSceneWidth;
        int tmpSceneHeight;

        if (bChild)
        {
            tmpSceneWidth = (int) this.parent.getWidth();
            tmpSceneHeight = (int) this.parent.getHeight();
        } else
        {
            tmpSceneWidth = this.theScene.getWidth();
            tmpSceneHeight = this.theScene.getHeight();
        }

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
                //clear background
                Graphics2D theGraphics2D = this.getTheImageGraphics();
                theGraphics2D.setBackground(blackTransparent);
                theGraphics2D.clearRect(0, 0, w, h);
                this.drawFrame(theGraphics2D);

                AffineTransform old = theGraphics2D.getTransform();

                //rotate the angle
                theGraphics2D.rotate(angle, w / 2.0f, h / 2.0f);

                //draw itself
                if (this.theImage != null)
                {
                    //draw the image
                    int tmpImageWidth = this.theImage.getWidth();
                    int tmpImageHeight = this.theImage.getHeight();
                    int tmpImagePosX = (int) ((width - tmpImageWidth) / 2.0f);
                    int tmpImagePosY = (int) ((height - tmpImageHeight) / 2.0f);
                    theGraphics2D.drawImage(theImage, tmpImagePosX, tmpImagePosY, tmpImageWidth, tmpImageHeight, null);

                } else
                {
                    //fill
                    theGraphics2D.setColor(theColor);
                    theGraphics2D.fillRect(0, 0, w, h);
                }

                //draw its children
                for (Sprite aSprite : this.theSetOfChildren)
                {
                    aSprite.updateGUI(theGraphics2D);
                }

                //restore
                theGraphics2D.setTransform(old);
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

    private void initForImage()
    {
        this.setWidth(theImage.getWidth());
        this.setHeight(theImage.getHeight());
    }

    private BufferedImage getTheImageCanvas()
    {
        if (theImageCanvas == null)
        {
            theImageCanvas = new BufferedImage(abs((int) width), abs((int) height), BufferedImage.TYPE_INT_ARGB);
        }
        return theImageCanvas;
    }

    private Graphics2D getTheImageGraphics()
    {
        if (theGraphics == null)
        {
            theGraphics = this.getTheImageCanvas().createGraphics();
        }
        return theGraphics;
    }

    private void releaseTheImageCanvas()
    {
        theGraphics = null;
        theImageCanvas = null;
    }

    private void drawFrame(final Graphics2D theGraphics2D)
    {
        if (this.bDrawFrame)
        {
            theGraphics2D.drawRect(0, 0, (int) width - 1, (int) height - 1);
        }
    }

    public void applyGravity(final Gravity g)
    {
        this.g = new Gravity(g.GX, g.GY);
    }

    public void addAction(Action aAction)
    {
        aAction.setSprite(this);
        this.theSetOfActionsWillAdd.add(aAction);
    }

    public void removeAction(Action aAction)
    {
        aAction.clearSprite();
        this.theSetOfActionsWillDelete.remove(aAction);
    }

    public void enQueueActions(Set<Action> aSetOfActions)
    {
        for (Action aAction : aSetOfActions)
        {
            aAction.setSprite(this);
        }
        this.theQueueOfActions.add(aSetOfActions);
    }

    public int getActionCount()
    {
        return this.theSetOfActions.size() + this.theQueueOfActions.size();
    }

    public void setVelocityX(double value)
    {
        this.velocityX = value;
    }

    public void setVelocityY(double value)
    {
        this.velocityY = value;
    }

    public double getVelocityX()
    {
        return this.velocityX;
    }

    public double getVelocityY()
    {
        return this.velocityY;
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

    public double getAngle()
    {
        return angle;
    }

    public void setAngle(double angle)
    {
        this.angle = angle;
    }

    public void setDead()
    {
        this.isAlive = false;
        this.hashCollision.clear();
        this.onDead();
    }

    public Gravity getGravity()
    {
        return this.g;
    }

    public double getStartTime()
    {
        return this.starttime;
    }

    public boolean collideWith(final Sprite target)
    {
        return x < target.x + target.width && x + width > target.x && y < target.y + target.height && y + height > target.y;
    }

    public int getLayer()
    {
        return this.layer;
    }

    public void setLayer(int layer)
    {
        this.layer = layer;
    }

    public double getLife()
    {
        return this.lifetime;
    }

    public void setLifeTime(double life)
    {
        this.lifetime = life;
    }

    public void addAChild(Sprite aSprite)
    {
        this.theSetOfChildrenWillAdd.add(aSprite);
        aSprite.parent = this;
        aSprite.bChild = true;
    }

    public void removeAChild(Sprite aSprite)
    {
        this.theSetOfChildrenWillDelete.add(aSprite);
        aSprite.parent = null;
        aSprite.bChild = false;
    }

    public void clearChildren()
    {
        this.theSetOfChildrenWillDelete.addAll(this.theSetOfChildren);
    }

    public void onCollideWith(final Sprite target)
    {

    }

    public void onNotCollideWith(final Sprite target)
    {

    }

    public void onAddToLayer(final Layer theLayer)
    {

    }

    public void onRemovedFromLayer(final Layer theLayer)
    {

    }

    public void onActionRunning(final Action theAction)
    {
    }

    public void onActionComplete(final Action theAction)
    {
    }

    public void onCustomDraw(final Graphics2D theGraphics2D)
    {
        theGraphics2D.setBackground(blackTransparent);
        theGraphics2D.clearRect(0, 0, (int) width, (int) height);
    }

    public void onDead()
    {
        this.clearChildren();
        this.hashCollision.clear();
    }

}
