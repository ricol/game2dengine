/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.rmit.Game2dEngine.Cache;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import javax.imageio.ImageIO;

/**
 *
 * @author ricolwang
 */
public class ResourceCache
{

    static ResourceCache instance = new ResourceCache();

    public static ResourceCache getSharedInstance()
    {
        return instance;
    }

    private HashMap<String, BufferedImage> imagesMapping = new HashMap<>();

    BufferedImage loadImage(String name)
    {
        try
        {
            File file = new File(name);
            if (file == null)
            {
                error("Image not found for " + name);
                return null;
            }
            BufferedImage image = ImageIO.read(file);
            if (image == null)
            {
                error("Image not found for " + name);
                return null;
            }
            return image;
        } catch (IOException ex)
        {
            error("Image not found for " + name);
        }

        return null;
    }

    synchronized public BufferedImage getImage(String name)
    {
        if (imagesMapping.containsKey(name))
        {
            return imagesMapping.get(name);
        } else
        {
            BufferedImage newImage = loadImage(name);
            if (newImage != null)
            {
                imagesMapping.put(name, newImage);
                return newImage;
            }
        }
        return null;
    }

    private void error(String msg)
    {
        System.out.println(msg);
    }

    public void clear()
    {
        imagesMapping.clear();
    }
}
