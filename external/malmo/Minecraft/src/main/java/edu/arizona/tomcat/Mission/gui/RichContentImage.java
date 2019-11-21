package edu.arizona.tomcat.Mission.gui;

import java.awt.Point;

import com.microsoft.Malmo.MalmoMod;

import net.minecraft.util.ResourceLocation;

public class RichContentImage {

	private String texture;
	private String caption;
	private int originXInTexture;
	private int originYInTexture;
	private int width;
	private int height;
	
	/**
	 * Constructor
	 * @param texture - Name of the file with the texture 256x256
	 * @param originXIntexture - Origin of the image in the texture in the axis X
	 * @param originYInTexture - Origin of the image in the texture in the axis Y
	 * @param width - Width of the image in the texture
	 * @param height - Height of the image in the texture 
	 */
	public RichContentImage(String texture, String caption, int originXIntexture, int originYInTexture, int width, int height) {
		this.texture = texture;
		this.caption = caption;
		this.originXInTexture = originXIntexture;
		this.originYInTexture = originYInTexture;
		this.width = width; 
		this.height = height;          
	}
	
	/**
	 * Retrieves the top-left point based on the center of the image
	 * @param center - Point where the center of the image has to be located
	 * @return
	 */
	public Point getTopLeftAnchorPoint(Point center) {
		int x = (int)(center.getX() - (this.width / 2));
		int y = (int)(center.getY() - (this.height / 2));
		return new Point(x, y);
	}
	
	/**
	 * Retrieves the texture resource location of the image
	 * @return
	 */
	public ResourceLocation getResourceLocation() {
		return new ResourceLocation(MalmoMod.MODID, "textures/gui/" + this.texture);
	}
	
	/**
	 * Retrieves the origin of the image in the texture in the axis X
	 * @return
	 */
	public int getOriginXInTexture() {
		return this.originXInTexture;
	}
	
	/**
	 * Retrieves the origin of the image in the texture in the axis Y
	 * @return
	 */
	public int getOriginYInTexture() {
		return this.originYInTexture;
	}
	
	/**
	 * Retrieves the width of the image in the texture
	 * @return
	 */
	public int getWidth() {
		return this.width;
	}
	
	/**
	 * Retrieves the height of the image in the texture
	 * @return
	 */
	public int getHeight() {
		return this.height;
	}
	
	/**
	 * Retrieves the image caption
	 * @return
	 */
	public String getCaption() {
		return this.caption;
	}
		
}