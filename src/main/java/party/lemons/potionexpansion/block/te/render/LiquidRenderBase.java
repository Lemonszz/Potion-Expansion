package party.lemons.potionexpansion.block.te.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionHelper;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.FluidStack;
import org.lwjgl.opengl.GL11;
import party.lemons.potionexpansion.block.te.TileEntityCauldron;
import party.lemons.potionexpansion.fluid.ModFluids;
import party.lemons.potionexpansion.handler.client.RenderUtil;

/**
 * Created by Sam on 16/04/2018.
 */
public abstract class LiquidRenderBase<T extends TileEntityCauldron> extends TileEntitySpecialRenderer<T>
{
	@Override
	public void render(TileEntityCauldron te, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
	{
		if(te.getTank().getFluid() == null)
			return;

		FluidStack fluid = te.getTank().getFluid();
		TextureAtlasSprite sprite = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(fluid.getFluid().getStill(fluid).toString());

		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder buffer = tessellator.getBuffer();
		buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);

		Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);

		GlStateManager.pushMatrix();
		GlStateManager.disableLighting();
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glShadeModel(GL11.GL_SMOOTH);

		GlStateManager.translate(x, y, z);

		int brightness = Minecraft.getMinecraft().world.getCombinedLight(te.getPos(), fluid.getFluid().getLuminosity());
		RenderUtil.putTexturedQuad(buffer, sprite, 2 / 16D, getY(te, x, y, z), 2 / 16D, 12 / 16D, 0, 12 / 16d, EnumFacing.UP, fluid.getFluid().getColor(fluid), brightness, false);
		tessellator.draw();

		GlStateManager.disableBlend();
		GlStateManager.enableLighting();
		GlStateManager.popMatrix();
	}

	public abstract double getY(TileEntityCauldron tank,  double x, double y, double z);
}