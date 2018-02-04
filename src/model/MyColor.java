package model;

import java.awt.Color;
import java.awt.color.ColorSpace;

public class MyColor extends Color {

	public MyColor(int rgb) {
		super(rgb);
		// TODO Auto-generated constructor stub
	}

	public MyColor(int rgba , boolean hasalpha) {
		super(rgba , hasalpha);
		// TODO Auto-generated constructor stub
	}

	public MyColor(int r , int g , int b) {
		super(r , g , b);
		// TODO Auto-generated constructor stub
	}

	public MyColor(float r , float g , float b) {
		super(r , g , b);
		// TODO Auto-generated constructor stub
	}

	public MyColor(ColorSpace cspace , float[] components , float alpha) {
		super(cspace , components , alpha);
		// TODO Auto-generated constructor stub
	}

	public MyColor(int r , int g , int b , int a) {
		super(r , g , b , a);
		// TODO Auto-generated constructor stub
	}

	public MyColor(float r , float g , float b , float a) {
		super(r , g , b , a);
		// TODO Auto-generated constructor stub
	}

}
