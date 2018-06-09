package tools;

import java.util.Scanner;

import com.badlogic.gdx.tiledmappacker.TiledMapPacker;
import com.badlogic.gdx.tiledmappacker.TiledMapPacker.TiledMapPackerSettings;
import com.badlogic.gdx.tools.texturepacker.TexturePacker.Settings;

public class TexturePacker {
	public static void main(String[] args) {
		Settings settings = new Settings();
		
		settings.maxWidth = 2048;
		settings.maxHeight = 2048;
		settings.duplicatePadding = true;

		Scanner sc = new Scanner(System.in);

		do {
			System.out.println("Input dir?");
			String src = sc.nextLine();
			System.out.println("Output dir?");
			String dst = sc.nextLine();
			System.out.println("Pack name?");
			String pck = sc.nextLine();
			try {
				com.badlogic.gdx.tools.texturepacker.TexturePacker.process(settings, src, dst, pck);

				TiledMapPackerSettings test = new TiledMapPackerSettings();
				test.tilesetOutputDirectory = dst;
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("Stop? (y/n)");
		} while (sc.next() != "y");
		sc.close();
	}
}
