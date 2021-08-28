/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wang.Game2dEngine.Cache

import java.awt.image.BufferedImage
import java.io.File
import java.io.IOException
import java.util.*
import javax.imageio.ImageIO

/**
 * @author ricolwang
 */
class ResourceCache
{
    private val imagesMapping = HashMap<String, BufferedImage>()
    fun loadImage(name: String): BufferedImage?
    {
        try
        {
            val file = File(name)
            if (file == null)
            {
                error("Image not found for $name")
                return null
            }
            val image = ImageIO.read(file)
            if (image == null)
            {
                error("Image not found for $name")
                return null
            }
            return image
        } catch (ex: IOException)
        {
            error("Image not found for $name")
        }
        return null
    }

    @Synchronized
    fun getImage(name: String): BufferedImage?
    {
        if (imagesMapping.containsKey(name))
        {
            return imagesMapping[name]
        } else
        {
            val newImage = loadImage(name)
            if (newImage != null)
            {
                imagesMapping[name] = newImage
                return newImage
            }
        }
        return null
    }

    private fun error(msg: String)
    {
        println(msg)
    }

    fun clear()
    {
        imagesMapping.clear()
    }

    companion object
    {
        var sharedInstance = ResourceCache()
    }
}