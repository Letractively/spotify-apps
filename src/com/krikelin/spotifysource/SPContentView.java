package com.krikelin.spotifysource;
import java.awt.Component;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class SPContentView extends BufferedScrollPane implements SPPart {


	public int getSelectedIndex()
	{
		int i=0;
		// iterate through all items
		for(ISPEntry entry : getEntries())
		{
			if(entry.isSelected())
			{
				return i;
			}
			i++;
		}
		return -1;
		
	}
	public void setSelectedIndex(int mSelectedIndex)
	{
		// iterate through all items
		for(ISPEntry entries : getEntries())
		{
			entries.setSelected(false);
		}
		getEntries().get(mSelectedIndex).setSelected(true);
		
	}
	public void selectPrevious()
	{
		setSelectedIndex(getSelectedIndex()-1);
	}
	public void selectNext()
	{
		setSelectedIndex(getSelectedIndex()+1);
	}
	private ISPViewFilter mViewFilter;
	/**
	 * gets the view filter
	 * @return
	 */
	public ISPViewFilter getViewFilter()
	{
		return mViewFilter;
	}
	/**
	 * sets the view filter
	 * @param filter
	 */
	public void setFilter(ISPViewFilter filter)
	{
		this.mViewFilter = filter;
	}
	/**
	 * Occurs when filtering views
	 */
	public void filter(String title)
	{
		if(mViewFilter != null)
		{
			// TODO Add view filter
		}
		
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5311929409284734353L;

	@Override
	public SpotifyWindow getContext() {
		// TODO Auto-generated method stub
		return mContext;
	}
	
	private SpotifyWindow mContext;
	private SPActivity mSPActivity;
	public SPContentView(SPActivity activity,SpotifyWindow mContext)
	{
		super(mContext,new JPanel());
		  
		mSPActivity=activity;
		this.mContext=mContext;
		setBorder(BorderFactory.createEmptyBorder());
		// Set absolute layout
		setFocusable(true);
		super.addMouseListener(new MouseListener(){
 
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				for(ISPEntry entry : getEntries())
				{
					entry.setSelected(false);
					if(entry instanceof JComponent)
					{
						((JComponent)entry).repaint();
					}
				}
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		mPanel.addKeyListener(new KeyListener(){

			@Override
			public void keyPressed(KeyEvent arg0) {
				switch(arg0.getKeyCode())
				{
				case KeyEvent.VK_RIGHT:
					getContext().playNext();
					break;
				case KeyEvent.VK_LEFT:
					getContext().playPrevious();
					break;
				case KeyEvent.VK_DOWN:				
					selectNext();
					break;
				case KeyEvent.VK_UP:
					selectPrevious();
					break;
				}
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				// TODO Auto-generated method stub
				switch(arg0.getKeyCode())
				{
				
				}
			}
			
		});
		
		
		
	}
	
	/**
	 * Gets an item selection
	 * @return
	 */
	public ArrayList<ISPEntry> getSelectedItems()
	{
		ArrayList<ISPEntry> entries = new ArrayList<ISPEntry>();
		@SuppressWarnings("unused")
		int i=0;
		for(ISPEntry elm : getEntries())
		{
			
			
				if(elm instanceof ISPEntry)
				{
					if(((ISPEntry) elm).isSelected())
						entries.add((ISPEntry)elm);
				}
			
			i++;
			
		}
		return entries;
	}
	/**
	 * Gets a range of entries
	 * @param start
	 * @param count
	 * @return
	 */
	public ArrayList<ISPEntry> getEntriesByRange(int start,int count)
	{
		ArrayList<ISPEntry> entries = new ArrayList<ISPEntry>();
		int i=0;
		for(ISPEntry elm : getEntries())
		{
			if( i >= start && (start-i) <= count)
			{
				if(elm instanceof ISPEntry)
				{
					entries.add((ISPEntry)elm);
				}
			}
			i++;
			
		}
		return entries;
	}
	
	@Override
	public Component add(Component elm)
	{
		
		if(elm instanceof SPWidget)
			mSPElements.add((SPWidget)elm);
		return super.add(elm);
	}
	/**
	 * Returns all entries in the view
	 * @return
	 */
	public ArrayList<ISPEntry> getEntries()
	{
		
		ArrayList<ISPEntry> entries = new ArrayList<ISPEntry>();
		for(SPWidget elm : mSPElements)
		{
			// if the entry is instance of SPELementColletion assign it
			if(elm instanceof SPElementGroup)
			{
				for(SPElement elm2 : ((SPElementGroup)elm).getElements())
				{
					if(elm2 instanceof SPEntry)
						entries.add((SPEntry)elm2);
				}
			}
			if( elm instanceof SPListView)
			{
				SPTableModel model = (SPTableModel)((SPListView)elm).getModel();
				
				for(int i=0; i < model.getRowCount(); i++)
				{
				
					ISPEntry entry = (ISPEntry)model.getData(i);
					entries.add(entry);
					
				}
			}	
			if(elm instanceof SPEntry)
			{
				entries.add((SPEntry)elm);
			}
			
			if(elm instanceof SPListView)
			{
				SPTableModel spm = (SPTableModel)((SPListView)elm).getModel();
				for( int r = 0; r < spm.getRowCount() ; r++)
				{
					entries.add((ISPEntry)spm.getData(r));
				}
			}
		}
		
		return entries;
	}
	public SPActivity getHost()
	{
		return mSPActivity;
	}
	public ArrayList<SPWidget> getSPElements() {
		return mSPElements;
	}

	private ArrayList<SPWidget> mSPElements = new ArrayList<SPWidget>();

}
