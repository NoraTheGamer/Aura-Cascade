package pixlepix.auracascade.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;
import pixlepix.auracascade.block.tile.AuraTilePedestal;
import pixlepix.auracascade.main.AuraUtil;

/**
 * Created by pixlepix on 12/6/14.
 */
public class RenderPedestal extends TileEntitySpecialRenderer {

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f, int digProgress) {
//        RenderBlocks.getInstance().blockAccess = tileEntity.getWorldObj(); todo 1.8.8 why the hell is the tesr rendering a static model
//        RenderBlocks.getInstance().renderBlockAllFaces(tileEntity.getBlockType(), (int) x, (int) y, (int) z);

        AuraTilePedestal pedestal = (AuraTilePedestal) tileEntity;
        if (pedestal.itemStack != null) {
            if (pedestal.entityItem == null || !ItemStack.areItemStacksEqual(pedestal.entityItem.getEntityItem(), pedestal.itemStack)) {
                pedestal.entityItem = new EntityItem(tileEntity.getWorld(), x, y, z, ((AuraTilePedestal) tileEntity).itemStack);
            }
            EntityItem entityItem = pedestal.entityItem;
            x = x + .5;
            y = y + 1.16;
            z = z + .5;

            GlStateManager.pushMatrix();
            GlStateManager.enableLighting();

            pedestal.frames++;
            //This parameter is never used ._.
            Minecraft.getMinecraft().entityRenderer.disableLightmap();

            entityItem.setRotationYawHead(pedestal.frames);

            //Prevent 'jump' in the bobbing
            //Bobbing is calculated as the age plus the yaw
            AuraUtil.setItemAge(entityItem, (int) (400F - pedestal.frames));

            Minecraft.getMinecraft().getRenderManager().renderEntityWithPosYaw(entityItem, x, y, z, 0, entityItem.rotationYaw);
            GlStateManager.disableLighting();
            GlStateManager.popMatrix();


            Minecraft.getMinecraft().entityRenderer.enableLightmap();

            /*
            GL11.glPushMatrix();
            this.entityItem.hoverStart = 0.0F;
            RenderItem.renderInFrame = true;
            GL11.glTranslatef((float) x + 0.5F, (float) y + 2.02F, (float) z + 0.3F);
            //GL11.glRotatef(180, 0, 1, 1);

            RenderManager.instance.renderEntityWithPosYaw(this.entityItem, 0.0D, 0.0D, 0.0D, (float) Math.PI, 0);
            RenderItem.renderInFrame = false;
            GL11.glPopMatrix();
            */
        }
    }
}
