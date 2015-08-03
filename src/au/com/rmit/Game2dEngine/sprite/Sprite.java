/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.rmit.Game2dEngine.sprite;

import au.com.rmit.Game2dEngine.action.Action;
import au.com.rmit.Game2dEngine.common.Game2dEngineShared;
import au.com.rmit.Game2dEngine.gravity.Gravity;
import au.com.rmit.Game2dEngine.interfaces.ICopy;
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
public abstract class Sprite extends Node implements ICopy
{

    public boolean bChild;
    public Sprite parent;
    public Scene theScene;

    public boolean bDrawFrame = false;
    public boolean bDrawCircle = false;
    public Color theColorOfFrame = Color.yellow;
    public Color theColorOfCircle = Color.red;
    public boolean bCollisionDetect = false;
    public HashMap<Sprite, Game2dEngineShared.TypeCollisionDetection> hashCollision = new HashMap();
    public boolean bCustomDrawing = false;
    public static final long EVER = Long.MAX_VALUE;
    public boolean bDeadIfNoActions;
    private boolean bTargetCollisionProcessed = false;

    private int layer = Scene.MIN_LAYER;
    private double lifetime = Sprite.EVER; //in seconds
    private double lastUpdateTime;
    private double starttime = System.currentTimeMillis();
    private double velocityX;
    private double velocityY;
    private double velocityAngle;
    private double currentLife = 0;
    private boolean isAlive = true;
    private boolean bShouldDie = false;
    private int collisionCategory = 0x00;
    private int collisionTargetCategory = 0x00;
    public boolean bCollisionArbitrary = false;

    private Set<Action> theSetOfActionsWillDelete = new HashSet<>();
    private Set<Action> theSetOfActionsWillAdd = new HashSet<>();
    private Set<Action> theSetOfActions = new HashSet<>();

    private Set<Action> theSetOfActionsInQueueWillDelete = new HashSet<>();
    private Queue<Set<Action>> theQueueOfActions = new LinkedList<>();

    private Set<Sprite> theSetOfChildrenWillDelete = new HashSet<>();
    private Set<Sprite> theSetOfChildrenWillAdd = new HashSet<>();
    private Set<Sprite> theSetOfChildren = new HashSet<>();

    private boolean bEnableGravity;
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

    public Sprite(double x, double y, double width, double height, double mass, double velocityX, double velocityY, double velocityAngle)
    {
        this(x, y, width, height, mass, velocityX, velocityY);

        this.velocityAngle = velocityAngle;
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
        if (bShouldDie)
        {
            this.setDead();
            return;
        }

        //how much time passed since last update
        double delta = currentTime - this.lastUpdateTime;
        double t = delta / 1000.0f; //in seconds
        currentLife += t;

        if (currentLife >= lifetime)
            this.setShouldDie();

        //update state
        if (this.g != null && this.bEnableGravity)
        {
            velocityX += this.g.GX * t;
            velocityY += this.g.GY * t;
        }

        double IncX = velocityX * t;
        double IncY = velocityY * t;

        x += IncX;
        y += IncY;

        double IncAngle = velocityAngle * t;
        angle += IncAngle;

        //perform actions
        //perform a set of actions in the queue one by one in sequence
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
                        aAction.clearSprite();
                    }
                }

                if (theSetOfActionsInQueueWillDelete.size() > 0)
                {
                    theSetOfQueuedActions.removeAll(theSetOfActionsInQueueWillDelete);
                    theSetOfActionsInQueueWillDelete.clear();
                }
            } else
                this.theQueueOfActions.remove(theSetOfQueuedActions);
        }

        //perform other actions
        //add new actions
        if (this.theSetOfActionsWillAdd.size() > 0)
        {
            this.theSetOfActions.addAll(this.theSetOfActionsWillAdd);
            this.theSetOfActionsWillAdd.clear();
        }

        if (this.theSetOfActions.size() <= 0 && this.theQueueOfActions.size() <= 0)
        {
            if (this.bDeadIfNoActions)
                this.setShouldDie();
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
                    aAction.clearSprite();
                }
            }
        }

        //delete old actions
        if (this.theSetOfActionsWillDelete.size() > 0)
        {
            this.theSetOfActions.removeAll(this.theSetOfActionsWillDelete);
            this.theSetOfActionsWillDelete.clear();
        }

        //add new children
        if (this.theSetOfChildrenWillAdd.size() > 0)
        {
            this.theSetOfChildren.addAll(this.theSetOfChildrenWillAdd);
            this.theSetOfChildrenWillAdd.clear();
        }

        //update its children
        for (Sprite aSprite : this.theSetOfChildren)
        {
            aSprite.updateState(currentTime);

            if (!aSprite.isAlive)
                this.theSetOfChildrenWillDelete.add(aSprite);
        }

        //delete old children
        if (this.theSetOfChildrenWillDelete.size() > 0)
        {
            this.theSetOfChildren.removeAll(this.theSetOfChildrenWillDelete);
            this.theSetOfChildrenWillDelete.clear();
        }

        this.lastUpdateTime = currentTime;
    }

    public void updateGUI(final Graphics2D g)
    {
        if (this.isAlive)
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

            int w = (int) getWidth();
            int h = (int) getHeight();

            if (tmpX + w < 0 || tmpY + h < 0)
                return;

            if (tmpX > tmpSceneWidth || tmpY > tmpSceneHeight)
                return;

            if (abs(w) <= 0.1 || abs(h) <= 0.1)
                return;

            if (g == null)
                return;

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

                AffineTransform old = theGraphics2D.getTransform();

                //rotate the angle
                theGraphics2D.rotate(angle, w / 2.0f, h / 2.0f);

                //draw itself
                if (this.theImage != null)
                {
                    //draw the image
                    int tmpImageWidth = this.theImage.getWidth();
                    int tmpImageHeight = this.theImage.getHeight();
                    int tmpImagePosX = (int) ((getWidth() - tmpImageWidth) / 2.0f);
                    int tmpImagePosY = (int) ((getHeight() - tmpImageHeight) / 2.0f);
                    theGraphics2D.drawImage(theImage, tmpImagePosX, tmpImagePosY, tmpImageWidth, tmpImageHeight, null);

                } else
                {
                    //fill
                    theGraphics2D.setColor(theColor);
                    theGraphics2D.drawRect(0, 0, w - 1, h - 1);
                }

                //draw its children
                for (Sprite aSprite : this.theSetOfChildren)
                    aSprite.updateGUI(theGraphics2D);

                //restore
                theGraphics2D.setTransform(old);

                this.drawFrame(theGraphics2D);
                this.drawCircle(theGraphics2D);
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
            theImageCanvas = new BufferedImage(abs((int) getWidth()), abs((int) getHeight()), BufferedImage.TYPE_INT_ARGB);
        return theImageCanvas;
    }

    private Graphics2D getTheImageGraphics()
    {
        if (theGraphics == null)
            theGraphics = this.getTheImageCanvas().createGraphics();
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
            theGraphics2D.setColor(theColorOfFrame);
            theGraphics2D.drawRect(0, 0, (int) getWidth() - 1, (int) getHeight() - 1);
        }
    }

    private void drawCircle(final Graphics2D theGraphics2D)
    {
        if (this.bDrawCircle)
        {
            theGraphics2D.setColor(theColorOfCircle);
            int tmpRadius = (int) this.getTheCircleShape().radius;
            int tmpX = (int) (this.getTheCircleShape().centreX - tmpRadius);
            int tmpY = (int) (this.getTheCircleShape().centreY - tmpRadius);
            theGraphics2D.drawOval(tmpX, tmpY, 2 * tmpRadius, 2 * tmpRadius);
        }
    }

    private void setDead()
    {
        if (!this.isAlive)
            return;

        this.isAlive = false;
        this.onDead();
    }

    protected void setShouldDie()
    {
        if (bShouldDie)
            return;

        bShouldDie = true;

        for (Sprite aSprite : this.theSetOfChildren)
            aSprite.setShouldDie();

        this.onWillDead();
    }

    public boolean getShouldDie()
    {
        return bShouldDie;
    }

    public void applyGravity(final Gravity theG)
    {
        if (theG != null)
            this.g = new Gravity(theG.GX, theG.GY);
        else
            this.g = null;
    }

    public void addAction(Action aAction)
    {
        aAction.setSprite(this);
        this.theSetOfActionsWillAdd.add(aAction);
    }

    public void enQueueActions(Set<Action> aSetOfActions)
    {
        for (Action aAction : aSetOfActions)
            aAction.setSprite(this);
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

    public void setVelocityAngle(double value)
    {
        this.velocityAngle = value;
    }

    public double getVelocityX()
    {
        return this.velocityX;
    }

    public double getVelocityY()
    {
        return this.velocityY;
    }

    public double getVelocityAngle()
    {
        return this.velocityAngle;
    }

    public double getMass()
    {
        return this.mass;
    }

    public void setMass(double mass)
    {
        if (mass >= 0)
            this.mass = mass;
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
        if (width >= 0)
        {
            super.setWidth(width);
            this.releaseTheImageCanvas();
        }
    }

    @Override
    public void setHeight(double height)
    {
        if (height >= 0)
        {
            super.setHeight(height);
            this.releaseTheImageCanvas();
        }
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

    public double getAngle()
    {
        return angle;
    }

    public void setAngle(double angle)
    {
        this.angle = angle;
    }

    public Gravity getGravity()
    {
        return this.g;
    }

    public double getStartTime()
    {
        return this.starttime;
    }

    @Override
    public Object getACopy()
    {
        return null;
    }

    @Override
    public void copyContent(Object theObject)
    {
        if (!(theObject instanceof Sprite))
            return;

        Sprite aCopy = (Sprite) theObject;

        aCopy.x = this.x;
        aCopy.y = this.y;
        aCopy.setWidth(this.getWidth());
        aCopy.setHeight(this.getHeight());
        aCopy.velocityX = this.velocityX;
        aCopy.velocityY = this.velocityY;
        aCopy.red = this.red;
        aCopy.green = this.green;
        aCopy.blue = this.blue;
        aCopy.angle = this.angle;
        aCopy.alpha = this.alpha;

        aCopy.bChild = this.bChild;
        aCopy.parent = this.parent;
        aCopy.theScene = this.theScene;

        aCopy.bDrawFrame = this.bDrawFrame;
        aCopy.bDrawCircle = this.bDrawCircle;
        aCopy.theColorOfFrame = this.theColorOfFrame;
        aCopy.theColorOfCircle = this.theColorOfCircle;
        aCopy.bCollisionDetect = this.bCollisionDetect;
        aCopy.bCustomDrawing = this.bCustomDrawing;
        aCopy.bDeadIfNoActions = this.bDeadIfNoActions;

        aCopy.layer = this.layer;
        aCopy.lifetime = this.lifetime;
        aCopy.lastUpdateTime = this.lastUpdateTime;
        aCopy.starttime = this.starttime;
        aCopy.velocityAngle = this.velocityAngle;
        aCopy.currentLife = this.currentLife;
        aCopy.isAlive = this.isAlive;
        aCopy.bShouldDie = this.bShouldDie;
        aCopy.collisionCategory = this.collisionCategory;
        aCopy.collisionTargetCategory = this.collisionTargetCategory;
        aCopy.identifier = this.identifier;

        if (this.g != null)
            aCopy.g = (Gravity) this.g.getACopy();
    }

    public boolean collideWith(final Sprite target)
    {
//        return super.rectangleOverlaps(target);
//        return super.circleOverlaps(target)
        return false;
    }

    public int getLayer()
    {
        return this.layer;
    }

    public void setLayer(int layer)
    {
        if (layer >= Scene.MIN_LAYER && layer <= Scene.MAX_LAYER)
            this.layer = layer;
    }

    public double getLife()
    {
        return this.lifetime;
    }
    
    public boolean isGravityEnabled()
    {
        return this.bEnableGravity;
    }
    
    public void enableGravity()
    {
        this.bEnableGravity = true;
    }
    
    public void disableGravity()
    {
        this.bEnableGravity = false;
    }

    public boolean getTargetCollisionProcessed()
    {
        return this.bTargetCollisionProcessed;
    }

    public void setTargetCollisionProcessed(boolean value)
    {
        this.bTargetCollisionProcessed = value;
    }

    public int getCollisionTargetCategory()
    {
        return this.collisionTargetCategory;
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

    public void setCollisionCategory(int theCollisionCategory)
    {
        if (this.isValidCategory(theCollisionCategory))
            this.collisionCategory = theCollisionCategory;
    }

    public int getCollisionCategory()
    {
        return this.collisionCategory;
    }

    public void addTargetCollisionCategory(int theTargetCollisionCategory)
    {
        if (this.isValidCategory(theTargetCollisionCategory))
            this.collisionTargetCategory |= theTargetCollisionCategory;
    }

    public void removeTargetCollisionCategory(int theTargetCollisionCategory)
    {
        if (this.isInTheTargetCollisionCategory(theTargetCollisionCategory))
            this.collisionTargetCategory ^= theTargetCollisionCategory;
    }

    public boolean isInTheTargetCollisionCategory(int theTargetCollisionCategory)
    {
        return (this.isValidCategory(theTargetCollisionCategory) && (this.collisionTargetCategory & theTargetCollisionCategory) > 0);
    }

    private boolean isValidCategory(int aCagegory)
    {
        return (aCagegory >= 0) && ((aCagegory % 2 == 0) || (aCagegory == 1));
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
        theGraphics2D.clearRect(0, 0, (int) getWidth(), (int) getHeight());
    }

    public void onWillDead()
    {

    }

    public void onDead()
    {
        this.hashCollision.clear();

        this.theSetOfActions.clear();
        this.theSetOfActionsInQueueWillDelete.clear();
        this.theSetOfActionsWillAdd.clear();
        this.theSetOfActionsWillDelete.clear();
        this.theSetOfChildren.clear();
        this.theSetOfChildrenWillAdd.clear();
        this.theSetOfChildrenWillDelete.clear();

        this.theQueueOfActions.clear();

        releaseTheImageCanvas();

        this.parent = null;
        this.theScene = null;
    }

    @Override
    public String toString()
    {
        return "class: " + this.getClass() + "; identifier: " + this.identifier;
    }

    public void print(String title)
    {
        System.out.println(title + this);
    }

}
