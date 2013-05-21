package com.goraksh.games.example;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author niteshk
 *
 */
public class ImageToggler {

	private final String BUG_IMAGE = "bug.jpg";
	private final String WALK1 = "walk-1.jpg";
	private final String WALK2 = "walk-2.jpg";
	private final String WING1 = "wings-1.jpg";
	private final String WING2 = "wings-2.jpg";
	private List<String> imageUrlList;
	private int lastAccessedIndex;
	private int size;
	private static ImageToggler instance;

	public static synchronized ImageToggler getInstance() {
		if (instance == null) {
			instance = new ImageToggler();
		}
		return instance;
	}

	private ImageToggler() {
		init();
	}

	private void init() {
		imageUrlList = new ArrayList<>();
		//imageUrlList.add(WALK1);
		//imageUrlList.add(WALK2);
		imageUrlList.add(WING1);
		imageUrlList.add(WING2);
		size = imageUrlList.size();
	}

	public String toggleAndGet() {
		if (lastAccessedIndex == size)
			lastAccessedIndex = 0;
		return imageUrlList.get(lastAccessedIndex++);

	}
	
	public String toggleAndGet( int num) {
		//if (lastAccessedIndex == size)
		//	lastAccessedIndex = 0;
		int index = num%2;
		System.out.println("Returnning Image  Url:" + index);
		return imageUrlList.get( index );

	}

}
