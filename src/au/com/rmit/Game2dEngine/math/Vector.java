/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.rmit.Game2dEngine.math;

/**
 *
 * @author ricolwang
 */
public class Vector
{
    public double x;
    public double y;
    
    public Vector(double x, double y)
    {
        this.x = x;
        this.y = y;
    }
    
    public Vector addVector(Vector B)
    {
        Vector C = new Vector(x, y);
        C.x += B.x;
        C.y += B.y;
        return C;
    }
    
    public Vector subVector(Vector B)
    {
        Vector C = new Vector(x, y);
        C.x -= B.x;
        C.y -= B.y;
        return C;
    }
    
    public double dotProduct(Vector B)
    {
        return x * B.x + y * B.y;
    }
    
    public double getMagitude()
    {
        return Math.sqrt(x * x + y * y);
    }
    
    public Vector multiplyNumber(double number)
    {
        Vector C = new Vector(x * number, y * number);
        return C;
    }
    
    public Vector divideByNumber(double number)
    {
        return this.multiplyNumber(1 / number);
    }
    
    public Vector getTheUnitVector()
    {
        Vector C = new Vector(x, y);
        return C.divideByNumber(this.getMagitude());
    }
    
    public double getCosAngleForVector(Vector B)
    {
        return this.dotProduct(B) / (this.getMagitude() * B.getMagitude());
    }
    
    public Vector getNegativeVector()
    {
        Vector C = new Vector(-x, -y);
        return C;
    }
    
    public Vector getProjectVectorOn(Vector B)
    {
        Vector C = B.getTheUnitVector();
        return C.multiplyNumber(this.getMagitude() * this.getCosAngleForVector(B));
    }
    
}
