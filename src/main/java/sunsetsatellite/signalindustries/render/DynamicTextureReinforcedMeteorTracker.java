package sunsetsatellite.signalindustries.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.render.dynamictexture.DynamicTexture;
import net.minecraft.client.render.stitcher.TextureRegistry;
import net.minecraft.core.world.chunk.ChunkCoordinates;
import sunsetsatellite.signalindustries.SignalIndustries;
import sunsetsatellite.signalindustries.util.MeteorLocation;

import java.awt.image.BufferedImage;

import static java.lang.Math.PI;

public class DynamicTextureReinforcedMeteorTracker extends DynamicTexture {

	private Minecraft mc;

	private byte[] compassImageData;

	private double angleFinal;
	private double delta;

	private double scaleFactor;

	public DynamicTextureReinforcedMeteorTracker(Minecraft minecraft) {
		this.mc = minecraft;
	}

	@Override
	public void postInit() {
		setTexture(TextureRegistry.getTexture("signalindustries:item/reinforced_meteor_tracker"));

		BufferedImage atlas = texture.parentAtlas.atlas;
		compassImageData = new byte[texture.getArea() * 4];

		for(int x = 0; x < texture.width; x++) {
			for(int y = 0; y < texture.height; y++) {
				putPixel(compassImageData, y * texture.width + x, atlas.getRGB(texture.iconX + x, texture.iconY + y));
			}
		}

		scaleFactor = texture.width / 16.0;
	}

	@Override
	public void update() {

		for (int _x = 0; _x < texture.width; _x++) {
			for(int _y = 0; _y < texture.height; _y++) {
				int i = _y * texture.width + _x;

				int a = this.compassImageData[i * 4 + 3] & 0xFF;
				int r = this.compassImageData[i * 4 + 0] & 0xFF;
				int g = this.compassImageData[i * 4 + 1] & 0xFF;
				int b = this.compassImageData[i * 4 + 2] & 0xFF;

				this.imageData[i * 4 + 0] = (byte)r;
				this.imageData[i * 4 + 1] = (byte)g;
				this.imageData[i * 4 + 2] = (byte)b;
				this.imageData[i * 4 + 3] = (byte)a;
			}
		}

		double angle = 0.0D;
		if(this.mc.theWorld != null && this.mc.thePlayer != null) {
			ChunkCoordinates chunk = null;
			double distance = Double.MAX_VALUE;
			for (MeteorLocation meteorLocation : SignalIndustries.meteorLocations) {
				ChunkCoordinates location = meteorLocation.location;
				if(location.getSqDistanceTo((int) this.mc.thePlayer.x, (int) this.mc.thePlayer.y, (int) this.mc.thePlayer.z) < distance){
					distance = location.getSqDistanceTo((int) this.mc.thePlayer.x, (int) this.mc.thePlayer.y, (int) this.mc.thePlayer.z);
					chunk = location;
				}
			}
			if(chunk != null){
				double var23 = (double)chunk.x - this.mc.thePlayer.x;
				double var25 = (double)chunk.z - this.mc.thePlayer.z;
				angle = (double)(this.mc.thePlayer.yRot - 90.0F) * PI / 180.0D - Math.atan2(var25, var23);
			} else {
				return;
			}
		}

		double angleSmooth;
		angleSmooth = angle - this.angleFinal;
		while (angleSmooth < -PI) {
			angleSmooth += 2 * PI;
		}

		while(angleSmooth >= PI) {
			angleSmooth -= 2 * PI;
		}

		if(angleSmooth < -1.0D) {
			angleSmooth = -1.0D;
		}

		if(angleSmooth > 1.0D) {
			angleSmooth = 1.0D;
		}

		this.delta += angleSmooth * 0.1D;
		this.delta *= 0.8D;
		this.angleFinal += this.delta;

		double x = Math.sin(this.angleFinal);
		double y = Math.cos(this.angleFinal);

		int x2;
		int y2;
		int j;
		int r;
		int g;
		int b;
		int a;

		double xs = (texture.width / 2.0) + 0.5;
		double ys = (texture.height / 2.0) - 0.5;

		for(int i = (int)(-4 * scaleFactor); i <= (int)(4 * scaleFactor); ++i) {
			x2 = (int)(xs + y * (double)i * 0.3D);
			y2 = (int)(ys - x * (double)i * 0.3D * 0.5D);
			j = y2 * texture.width + x2;
			r = 100;
			g = 100;
			b = 100;
			a = 255;

			this.imageData[j * 4 + 0] = (byte)r;
			this.imageData[j * 4 + 1] = (byte)g;
			this.imageData[j * 4 + 2] = (byte)b;
			this.imageData[j * 4 + 3] = (byte)a;
		}

		for(int i = (int)(-8 * scaleFactor); i <= (int)(16 * scaleFactor); ++i) {
			x2 = (int)(xs + x * (double)i * 0.3D);
			y2 = (int)(ys + y * (double)i * 0.3D * 0.5D);
			j = y2 * texture.width + x2;
			r = i >= 0 ? 255 : 200; //
			g = i >= 0 ? i*8 : 10; // determines the color of the pointer
			b = i >= 0 ? 20 : 128; //
			a = 255;

			this.imageData[j * 4 + 0] = (byte)r;
			this.imageData[j * 4 + 1] = (byte)g;
			this.imageData[j * 4 + 2] = (byte)b;
			this.imageData[j * 4 + 3] = (byte)a;
		}
	}
	
//	private final Minecraft mc;
//
//	private final byte[] compassImageData;
//
//	private double angleFinal;
//	private double delta;
//
//	private final double scaleFactor;
//
//	private final int resolution = 16;
//
//	public DynamicTextureMeteorTracker(Minecraft minecraft) {
//		super();
//
//		this.mc = minecraft;
//
//		compassImageData = new byte[resolution * resolution * 4];
//		BufferedImage compass = mc.renderEngine.getImage("/assets/signalindustries/textures/item/meteor_tracker.png");
//
//		for(int x=0; x < resolution; x++) {
//			for(int y=0; y < resolution; y++) {
//				putPixel(compassImageData, y * resolution + x, compass.getRGB(x, y));
//			}
//		}
//
//		scaleFactor = resolution / 16.0;
//	}
//
//	@Override
//	public void postInit() {
//
//	}
//
//	@Override
//	public void update() {
//		for(int i = 0; i < resolution * resolution; i++) {
//			int a = this.compassImageData[i * 4 + 3] & 0xFF;
//			int r = this.compassImageData[i * 4] & 0xFF;
//			int g = this.compassImageData[i * 4 + 1] & 0xFF;
//			int b = this.compassImageData[i * 4 + 2] & 0xFF;
//
//			this.imageData[i * 4] = (byte)r;
//			this.imageData[i * 4 + 1] = (byte)g;
//			this.imageData[i * 4 + 2] = (byte)b;
//			this.imageData[i * 4 + 3] = (byte)a;
//		}
//
//		double angle = 0.0D;
//		if(this.mc.theWorld != null && this.mc.thePlayer != null) {
//			ChunkCoordinates chunk = null;
//			double distance = Double.MAX_VALUE;
//			for (ChunkCoordinates meteorLocation : SignalIndustries.meteorLocations) {
//				if(meteorLocation.getSqDistanceTo((int) this.mc.thePlayer.x, (int) this.mc.thePlayer.y, (int) this.mc.thePlayer.z) < distance){
//					distance = meteorLocation.getSqDistanceTo((int) this.mc.thePlayer.x, (int) this.mc.thePlayer.y, (int) this.mc.thePlayer.z);
//					chunk = meteorLocation;
//				}
//			}
//			if(chunk != null){
//				double var23 = (double)chunk.x - this.mc.thePlayer.x;
//				double var25 = (double)chunk.z - this.mc.thePlayer.z;
//				angle = (double)(this.mc.thePlayer.yRot - 90.0F) * PI / 180.0D - Math.atan2(var25, var23);
//			} else {
//				return;
//			}
//
//		}
//
//		double angleSmooth;
//		for(angleSmooth = angle - this.angleFinal; angleSmooth < -PI; angleSmooth += 2 * PI) {
//        }
//
//		while(angleSmooth >= PI) {
//			angleSmooth -= 2 * PI;
//		}
//
//		if(angleSmooth < -1.0D) {
//			angleSmooth = -1.0D;
//		}
//
//		if(angleSmooth > 1.0D) {
//			angleSmooth = 1.0D;
//		}
//
//		this.delta += angleSmooth * 0.1D;
//		this.delta *= 0.8D;
//		this.angleFinal += this.delta;
//
//		double x = Math.sin(this.angleFinal);
//		double y = Math.cos(this.angleFinal);
//
//		int x2;
//		int y2;
//		int j;
//		int r;
//		int g;
//		int b;
//		int a;
//
//		double xs = (resolution / 2.0) + 0.5;
//		double ys = (resolution / 2.0) - 0.5;
//
//
//
//		for(int i = (int)(-4 * scaleFactor); i <= (int)(4 * scaleFactor); ++i) {
//			x2 = (int)(xs + y * (double)i * 0.3D);
//			y2 = (int)(ys - x * (double)i * 0.3D * 0.5D);
//			j = y2 * resolution + x2;
//			r = 100;
//			g = 100;
//			b = 100;
//			a = 255;
//
//			this.imageData[j * 4] = (byte)r;
//			this.imageData[j * 4 + 1] = (byte)g;
//			this.imageData[j * 4 + 2] = (byte)b;
//			this.imageData[j * 4 + 3] = (byte)a;
//		}
//
//		for(int i = (int)(-8 * scaleFactor); i <= (int)(16 * scaleFactor); ++i) {
//			x2 = (int)(xs + x * (double)i * 0.3D);
//			y2 = (int)(ys + y * (double)i * 0.3D * 0.5D);
//			j = y2 * resolution + x2;
//			r = i >= 0 ? 255 : 200; //
//			g = i >= 0 ? i*8 : 10; // determines the color of the pointer
//			b = i >= 0 ? 20 : 128; //
//			a = 255;
//
//			this.imageData[j * 4] = (byte)r;
//			this.imageData[j * 4 + 1] = (byte)g;
//			this.imageData[j * 4 + 2] = (byte)b;
//			this.imageData[j * 4 + 3] = (byte)a;
//		}
//	}
}
