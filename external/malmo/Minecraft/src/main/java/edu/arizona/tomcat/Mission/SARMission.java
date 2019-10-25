package edu.arizona.tomcat.Mission;

import com.microsoft.Malmo.Schemas.EntityTypes;

import edu.arizona.tomcat.Emotion.EmotionHandler;
import edu.arizona.tomcat.Mission.gui.SimpleGUI;
import edu.arizona.tomcat.World.Drawing;
import edu.arizona.tomcat.World.CompositeEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SARMission extends Mission {

	private boolean zombieHordeCreated;
	private boolean feedbackRequested;

	public SARMission() {
		super();
		this.zombieHordeCreated = false;
		this.timeLimitInSeconds = 100;
	}

	/**
	 * Creates a horde of 3 zombies in front of the player 
	 * @param world - Mission world
	 * @throws Exception 
	 */
	private void createZombieHorde(World world) throws Exception {
		int distance = 5; // Number of voxels apart from the player
		int playersX = (int) Minecraft.getMinecraft().player.posX;
		int playersZ = (int) Minecraft.getMinecraft().player.posZ;
		int playersY = (int) Minecraft.getMinecraft().player.posY;

		Drawing drawing = new Drawing();

		// Create zombie in front of the player
		CompositeEntity zombie1 = new CompositeEntity(playersX, playersY, playersZ + distance, EntityTypes.ZOMBIE);

		// Create zombie to the northwest of the player
		CompositeEntity zombie2 = new CompositeEntity(playersX + distance, playersY, playersZ + distance/2, EntityTypes.ZOMBIE);

		// Create zombie to the northeast of the player
		CompositeEntity zombie3 = new CompositeEntity(playersX - distance, playersY, playersZ + distance/2, EntityTypes.ZOMBIE);

		drawing.addObject(zombie1);
		drawing.addObject(zombie2);
		drawing.addObject(zombie3);
		this.drawingHandler.draw(world, drawing);

		this.zombieHordeCreated = true;
	}

	/**
	 * Creates a horde of zombies all around the player 
	 * @param world - Mission world
	 * @throws Exception 
	 */
	private void createZombieMegaHorde(World world) throws Exception {
		int distance = 10; // Number of voxels apart from the player
		int playersX = (int) Minecraft.getMinecraft().player.posX;
		int playersZ = (int) Minecraft.getMinecraft().player.posZ;
		int playersY = (int) Minecraft.getMinecraft().player.posY;

		Drawing drawing = new Drawing();

		// Create zombie in front of the player
		CompositeEntity zombie1 = new CompositeEntity(playersX, playersY, playersZ + distance, EntityTypes.ZOMBIE);

		// Create zombie to the northwest of the player
		CompositeEntity zombie2 = new CompositeEntity(playersX + distance, playersY, playersZ + distance/2, EntityTypes.ZOMBIE);

		// Create zombie to the left of the player
		CompositeEntity zombie3 = new CompositeEntity(playersX + distance, playersY, playersZ, EntityTypes.ZOMBIE);

		// Create zombie to the southwest of the player
		CompositeEntity zombie4 = new CompositeEntity(playersX + distance, playersY, playersZ - distance/2, EntityTypes.ZOMBIE);

		// Create zombie behind the player
		CompositeEntity zombie5 = new CompositeEntity(playersX, playersY, playersZ - distance, EntityTypes.ZOMBIE);

		// Create zombie to the southeast of the player
		CompositeEntity zombie6 = new CompositeEntity(playersX - distance, playersY, playersZ - distance/2, EntityTypes.ZOMBIE);

		// Create zombie to the right of the player
		CompositeEntity zombie7 = new CompositeEntity(playersX - distance, playersY, playersZ, EntityTypes.ZOMBIE);

		// Create zombie to the northeast of the player
		CompositeEntity zombie8 = new CompositeEntity(playersX - distance, playersY, playersZ + distance/2, EntityTypes.ZOMBIE);

		drawing.addObject(zombie1);
		drawing.addObject(zombie2);
		drawing.addObject(zombie3);
		drawing.addObject(zombie4);
		drawing.addObject(zombie5);
		drawing.addObject(zombie6);
		drawing.addObject(zombie7);
		drawing.addObject(zombie8);
		this.drawingHandler.draw(world, drawing);

		this.zombieHordeCreated = true;
	}

	/**
	 * Request for the player's feedback about his emotion
	 */
	private void requestFeedback() {
		SimpleGUI simpleGUI = new SimpleGUI();
		simpleGUI.addListener(this);
		Minecraft.getMinecraft().displayGuiScreen(simpleGUI);
		this.feedbackRequested = true;
	}

	@Override
	protected void createPhases() {
		// No phase
	}

	@Override
	protected void updateScene(World world) {
		try {
			if (this.getRemainingSeconds() < 90 && !this.zombieHordeCreated) {
				this.createZombieHorde(world);
			}

			// Ask for feedback for the first time
			if (this.getRemainingSeconds() > 50 && this.getRemainingSeconds() < 60 && !this.feedbackRequested) {
				this.requestFeedback();
			}

			// Allow feedback request for the second time
			if (this.getRemainingSeconds() > 45 && this.getRemainingSeconds() < 50) {
				this.feedbackRequested = false;
			}

			// Ask for feedback for the second time
			if (this.getRemainingSeconds() < 45 && !this.feedbackRequested) {
				this.requestFeedback();
			}

			if (this.currentEmotion == EmotionHandler.Emotion.CALMNESS) {
				this.createZombieMegaHorde(world);
				this.currentEmotion = null;

				world.setBlockToAir(new BlockPos(2, 2, 30));
				world.setBlockToAir(new BlockPos(2, 2, 31));
				world.setBlockToAir(new BlockPos(2, 2, 32));
				world.setBlockToAir(new BlockPos(2, 2, 33));
			}			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	@Override
	public void init(World world) {
		// No initialization required

	}


}