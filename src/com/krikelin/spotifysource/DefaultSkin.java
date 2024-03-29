package com.krikelin.spotifysource;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import com.android.ninepatch.NinePatch;

public class DefaultSkin extends SPSkin {
	public static final int ICON_START = 28500+0;
	int num_icons = 3;
	private Image mSelectionBackground;
	@Override
	public void drawText(String str,Color colour,Graphics g,int x,int y,boolean shadow)
	{
		drawText(str, colour,  g, x, y,shadow ? mShadowColor : null);
	}
	/**
	 * Draws text on the specified location
	 */
	@Override
	public void drawText(String str,Color colour, Graphics g,int x,int y,Color shadowColor)
	{

		drawText(str, colour, g, x, y, shadowColor,0,1);
	}
	@Override
	public void drawText(String str,Color colour, Graphics g,int x,int y,Color shadowColor,int shdX,int shdY)
	{

		if(shadowColor != null)
		{
			// draw shadow
			g.setColor(shadowColor);
			g.drawString(str,x+shdX,y+shdY);
		}
		// draw text
		g.setColor(colour);
		g.drawString(str,x, y);
	}
	private Image[] mIcons;
	/**
	 * Image fields
	 */
	private Color mPlayingEntryBg;
	private Color mPlayingEntryFg;
	private Image mButtonImage;
	private Image mHeaderImage;
	private Image mVDividerImage;
	private Image mButtonPressedImage;
	@Override
	public NinePatch getButtonPressedImage()
	{
		return NinePatch.load((BufferedImage)mButtonPressedImage, true, true);
	}
	private Color mBackgroundColor;
	private Color mForeColor;
	private Color mLinkColor;
	private Color mAlternateFgColor;
	private Color mAlternatingBgColor;
	private Image mSectionBackground;
	private Color mShadowColor;
	private Image mSectionForeground;
	private Hashtable<Integer,Image> mExtraImages = new Hashtable<Integer,Image>();
	private Image mTabBackground;
	private Color mTabForeground;
	private Image mReleaseImage;
	private Image mActiveTabBackground;
	private Color mActiveTabForeColor;
	private Color mSelectionBg;
	private Color mSelectionFg;
	private Font mFont;
	private Color mTabShadowColor;
	/***
	 * Gets pixel from an image
	 * @param img
	 * @param x
	 * @param y
	 * @return
	 */
	public Color getPixel(BufferedImage img,int x,int y)
	{

		BufferedImage image = img;
		int c = image.getRGB(x,y);
		Color color = new Color(c,false);
		return color;
	}
	
	public Image sliceBitmap(Image src,int left,int top, int width,int height)
	{
	
		// Create target buffer
		BufferedImage bi = new BufferedImage(width,height, BufferedImage.TYPE_INT_RGB);
		
		// Copy pixels to the buffer
		
	
			bi.getGraphics().drawImage(src, 0,0,width,height,  left,top+4,left+width,top+height,(ImageObserver)null);
		
		
		return bi;
		
	}
	
	/**
	 * Public properties
	 */
	public static final int IMG_SHADOW = 210081;
	public static final int IMG_CHEADER = 28100;
	public static final int COLOR_TAB_SHADOW = 7;
	public DefaultSkin()
	{
		/*
		 * Load the image into the memory and handle it
		 */
		InputStream stream = getClass().getResourceAsStream("skin.png");
		try {
			BufferedImage src =  ImageIO.read(stream);
			BufferedImage dest  =new BufferedImage(src.getWidth(),src.getHeight(),BufferedImage.TYPE_INT_ARGB);
			Graphics2D graphics2D = dest.createGraphics() ;
			graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			graphics2D.drawImage(src, 0, 0, src.getWidth(), src.getHeight(), null) ;
			
			BufferedImage r = dest;
			
		
			dest.createGraphics();
			mFont = new Font("Tahoma",8,11);
			/**
			 * Define colors.
			 * The first row of pixels in the image is reserved as the definition of 
			 * the color scheme. Each position from 0 - size is an container for
			 * an rgb color which defines a certain part of the colour scheme, by this
			 * way we can minimize the size of the theme bitmap slighly.
			 * Positions is following:
			 * 
			 * 0,0 - General Foreground color of the app
			 * 1,0 - General background color of the app
			 * 2,0 - General alternative background color for alternating rows
			 * 3,0 - general alternative foreground color on alternate rows
			 * 4,0 - color for links in the app
			 * 5,0 - Foreground color of inactive tab
			 * 6,0 - Foreground color of active tab
			 * 
			 */
			mTabForeground = getPixel(r,12,0);
			mTabShadowColor = getPixel(r,11,0);
			mActiveTabForeColor = getPixel(r,13,0);
			mBackgroundColor = getPixel(r,1,0);
			mShadowColor = getPixel(r,15,0);
			mForeColor = getPixel(r,0,0);
			mAlternatingBgColor = getPixel(r,2,0);
			mLinkColor = getPixel(r,0,5);
			mSelectionFg = getPixel(r,6,0);
			mSelectionBg = getPixel(r,5,0);
 			mAlternateFgColor = getPixel(r,1,0);
 			
			
			mPlayingEntryBg =getPixel(r,18,0); 
			mPlayingEntryFg =getPixel(r,19,0);
			/**
			 * Load icons. May change in the future
			 */
			
			mIcons = new Image[num_icons];
			for(int i=0; i < num_icons; i++ )
			{
				mIcons[i] = sliceBitmap(r,159+i*16,107,16,16);
				
			}
			/**
			 * Slice bitmap
			 */
			mSelectionBackground = sliceBitmap(r,169,23,27,27);
			mHeaderImage = sliceBitmap(r,33,353,56,48);
			mReleaseImage = sliceBitmap(r,0,107,158,158);
			mTabBackground = sliceBitmap(r,67,18,66,261);
			mActiveTabBackground = sliceBitmap(r,0,18,66,261);
			mSectionBackground = sliceBitmap(r,67,18,64,266);
			mVDividerImage = sliceBitmap(r,245,45,23,23);
			mExtraImages.put(DefaultSkin.IMG_CHEADER,sliceBitmap(r,5,273, 19,30));
			mExtraImages.put(DefaultSkin.IMG_SHADOW,sliceBitmap(r,195,57,51,23));
			dashedBg = sliceBitmap(r,338,83,64,64);
			// set playback icons
			mPlaybackIconImage = new Image[3];
			mButtonImage = sliceBitmap(r,202,225,55,28);
			mButtonPressedImage =sliceBitmap(r,202,250,55,28);
			
			mPlaybackIconImage[0]= sliceBitmap(r,0,83,24,24);
			mPlaybackIconImage[1]= sliceBitmap(r,0,83,24,24);
			mIcons[0] = sliceBitmap(r,275,198, 14, 14);
			mPlaybackIconImage[2]= sliceBitmap(r,0,83,24,24);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public String getCSS() {
		// TODO Auto-generated method stub
		
		return "*{font-family:\"Tahoma\";}img{border:1px solid #333333; box-shadow: 2px 2px 12px #000000;} body{color:#"+toHTMLColor(getForeColor())+"; background-color: #"+toHTMLColor(getBackgroundColor())+";}";
	}
	private Image dashedBg;
	@Override
	public NinePatch getButtonImage() {
		// TODO Auto-generated method stub
		
		return NinePatch.load((BufferedImage)mButtonImage, true, false);
	}

	@Override
	public Image getHeaderImage() {
		// TODO Auto-generated method stub
		return mHeaderImage;
	}

	@Override
	public Image getShadowImage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Image getBottomImage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Color getBackgroundColor() {
		// TODO Auto-generated method stub
		return mBackgroundColor;
	}

	@Override
	public Color getForeColor() {
		// TODO Auto-generated method stub
		return mForeColor;
	}

	@Override
	public Color getLinkColor() {
		// TODO Auto-generated method stub
		return mLinkColor;
	}

	@Override
	public Color getAlternateForeColor() {
		// TODO Auto-generated method stub
		return mAlternateFgColor;
	}

	@Override
	public Color getAlternateBgColor() {
		// TODO Auto-generated method stub
		return mAlternatingBgColor;
	}

	@Override
	public Image getSectionBackground() {
		// TODO Auto-generated method stub
		return mSectionBackground;
	}

	@Override
	public Image getSectionForeground() {
		// TODO Auto-generated method stub
		return mSectionForeground;
	}

	@Override
	public Image getTabBarBackground() {
		// TODO Auto-generated method stub
		return mTabBackground;
	}

	@Override
	public Image getActiveTabBackground() {
		// TODO Auto-generated method stub
		return mActiveTabBackground;
	}

	@Override
	public Image getTabBackground() {
		// TODO Auto-generated method stub
		return mTabBackground;
	}

	@Override
	public Color getTabForeColor() {
		// TODO Auto-generated method stub
		return mTabForeground;
	}

	@Override
	public Color getActiveTabForeColor() {
		// TODO Auto-generated method stub
		return mActiveTabForeColor;
	}

	@Override
	public Color getSelectionBg() {
		// TODO Auto-generated method stub
		return mSelectionBg;
	}

	@Override
	public Color getSelectionFg() {
		// TODO Auto-generated method stub
		return mSelectionFg;
	}

	@Override
	public Font getFont() {
		// TODO Auto-generated metho

		return mFont;
	}
	
	@Override
	public Image getReleaseImage() {
		// TODO Auto-generated method stub
		return mReleaseImage;
	}
	@Override
	public Image getSelectionBackground() {
		// TODO Auto-generated method stub
		return mSelectionBackground;
	}
	private Image[] mPlaybackIconImage;
	@Override
	public Color getColorById(int id) {
		// TODO Auto-generated method stub
		switch(id)
		{
		case DefaultSkin.IMG_VERTICAL_DIVIDER_FG:
			return Color.white;
		case DefaultSkin.COLOR_TAB_SHADOW:
			return mTabShadowColor;
		}
		return null;
	}
	@Override
	public Image getImageById(int id) {
		// TODO Auto-generated method stub
		
		if( id >= DefaultSkin.ICON_START && id <= DefaultSkin.ICON_START + num_icons)
		{
			return mIcons[id - DefaultSkin.ICON_START];
		}
		switch(id)
		{
		
		case DefaultSkin.IMG_SHADOW:
			return mExtraImages.get(DefaultSkin.IMG_SHADOW);
		case DefaultSkin.IMG_VERTICAL_DIVIDER:
			return mVDividerImage;
		}
		if(id >=  DefaultSkin.IMG_PLAYBACK_ICON && id <= DefaultSkin.IMG_PLAYBACK_ICON_SELECTED)
		{
			
			return mPlaybackIconImage[DefaultSkin.IMG_PLAYBACK_ICON_SELECTED-id];
		
		}
	
		return  mExtraImages.get(id);
	}
	@Override
	public Color getPlayingBg() {
		// TODO Auto-generated method stub
		return mPlayingEntryBg;
	}
	@Override
	public Color getPlayingFg() {
		// TODO Auto-generated method stub
		return mPlayingEntryFg;
	}
	@Override
	public void drawPopularity(float popularity, int countTaks, Graphics g,
			int x, int y,int height,int mode) {
		// TODO Auto-generated method stub
		{
			int range = (int) ( countTaks * popularity);
			for(int i=0; i < countTaks; i++)
			{
						Color fg ;
						switch(mode)
						{
						case DefaultSkin.MODE_NORMAL:
								fg = getForeColor();
								break;
						case DefaultSkin.MODE_PLAYING:
								fg = getPlayingFg();
								break;
						case DefaultSkin.MODE_SELECTED:
								fg = getSelectionFg();
								break;
							default:
								fg = getForeColor();
						}
						Color tak = (i < range) ? fg : fg.darker().darker();
						g.setColor(tak);
						g.drawLine(x+i*3, y, x + i*3, y + height);
						g.drawLine(x+(i*3)+1, y, x + i*3+1, y + height);
						
			}
		}
	}
	@Override
	public Image getDashedBackground() {
		// TODO Auto-generated method stub
		return dashedBg;
	}
 
}
