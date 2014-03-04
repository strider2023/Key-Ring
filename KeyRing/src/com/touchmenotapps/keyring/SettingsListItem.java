package com.touchmenotapps.keyring;

public class SettingsListItem {

	private String mTitle;
	private String mDescription;
	private int mImageID;
	
	public SettingsListItem() { }
	
	/**
	 * @param mTitle
	 * @param mDescription
	 * @param mImageID
	 */
	public SettingsListItem(String mTitle, String mDescription, int mImageID) {
		super();
		this.mTitle = mTitle;
		this.mDescription = mDescription;
		this.mImageID = mImageID;
	}
	
	/**
	 * @return the mTitle
	 */
	public String getTitle() {
		return mTitle;
	}
	
	/**
	 * @param mTitle the mTitle to set
	 */
	public void setTitle(String mTitle) {
		this.mTitle = mTitle;
	}
	
	/**
	 * @return the mDescription
	 */
	public String getDescription() {
		return mDescription;
	}
	
	/**
	 * @param mDescription the mDescription to set
	 */
	public void setDescription(String mDescription) {
		this.mDescription = mDescription;
	}
	
	/**
	 * @return the mImageID
	 */
	public int getImageID() {
		return mImageID;
	}
	
	/**
	 * @param mImageID the mImageID to set
	 */
	public void setImageID(int mImageID) {
		this.mImageID = mImageID;
	}	
}
