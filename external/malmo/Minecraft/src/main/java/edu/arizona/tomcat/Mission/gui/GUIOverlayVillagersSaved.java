package edu.arizona.tomcat.Mission.gui;

import com.microsoft.Malmo.MalmoMod;
import edu.arizona.tomcat.Mission.Client.TutorialClientMission;
import edu.arizona.tomcat.Mission.Client.ZombieClientMission;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class GUIOverlayVillagersSaved extends Gui {

    private final ResourceLocation bar =
        new ResourceLocation(MalmoMod.MODID, "textures/gui/hpbar.png");
    private final static int TEXTURE_WIDTH = 102;
    private final static int TEXTURE_HEIGHT = 8;

    private int totalNumberOfVillagers;
    private static GUIOverlayVillagersSaved instance;

    private GUIOverlayVillagersSaved() {}

    /**
     * Register to be a listener of a Minecraft event
     * @param totalNumberOfVillagers - Total numbers of villagers to be saved
     */
    public static void register(int totalNumberOfVillagers) {
        if (instance == null) {
            instance = new GUIOverlayVillagersSaved();
        }
        instance.totalNumberOfVillagers = totalNumberOfVillagers;
        MinecraftForge.EVENT_BUS.register(instance);
    }

    /**
     * Unregister as a listener from a Minecraft event
     */
    public static void unregister() {
        MinecraftForge.EVENT_BUS.unregister(instance);
    }

    @SubscribeEvent
    public void renderOverlay(RenderGameOverlayEvent event) {
        if (event.getType() == ElementType.TEXT) {
            int numberOfSavedVillagers = 0;

            if (MalmoMod.instance.getClient().getTomcatClientMission()
                    instanceof ZombieClientMission) {
                ZombieClientMission clientMission =
                    (ZombieClientMission)MalmoMod.instance.getClient()
                        .getTomcatClientMission();
                numberOfSavedVillagers =
                    clientMission.getNumberOfSavedVillagers();
            }
            else if (MalmoMod.instance.getClient().getTomcatClientMission()
                         instanceof TutorialClientMission) {
                TutorialClientMission clientMission =
                    (TutorialClientMission)MalmoMod.instance.getClient()
                        .getTomcatClientMission();
                numberOfSavedVillagers =
                    clientMission.getNumberOfSavedVillagers();
            }

            float fractionOfSavedVillagers = (float)numberOfSavedVillagers /
                                             (float)this.totalNumberOfVillagers;
            int currentWidth = (int)(fractionOfSavedVillagers * TEXTURE_WIDTH);
            Minecraft.getMinecraft().renderEngine.bindTexture(this.bar);
            drawTexturedModalRect(0, 0, 0, 0, TEXTURE_WIDTH, TEXTURE_HEIGHT);
            drawTexturedModalRect(
                0, 0, 0, TEXTURE_HEIGHT, currentWidth, TEXTURE_HEIGHT);
        }
    }
}
