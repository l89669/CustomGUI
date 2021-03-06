package custom.gui.mcobject;

import custom.gui.object.TextureManager;
import custom.gui.util.GuiUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GuiCustomButton extends GuiButton {

    String firstUrl, lastUrl;
    int textureX, textureY, firstTextureID, lastTextureID, firstColor, lastColor;
    float textureWidth, textureHeight;
    boolean isuploadTextureImage = false;

    public GuiCustomButton(int buttonId, int x, int y, int widthIn, int heightIn, float textureWidth, float textureHeight, int firstColor, int lastColor, String buttonText, String imgUrl, String lastUrl) {
        super(buttonId, x, y, widthIn, heightIn, buttonText);
        this.firstUrl = imgUrl;
        this.lastUrl = lastUrl;
        this.firstColor = firstColor;
        this.lastColor = lastColor;
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
    }

    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        if (this.visible) {
            this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
            FontRenderer fontrenderer = mc.fontRenderer;
            if (!isuploadTextureImage) {
                firstTextureID = GL11.glGenTextures();
                lastTextureID = GL11.glGenTextures();
                TextureUtil.uploadTextureImage(firstTextureID, TextureManager.getBufferedImage(firstUrl));
                TextureUtil.uploadTextureImage(lastTextureID, TextureManager.getBufferedImage(lastUrl));
                isuploadTextureImage = true;
            }
            if (this.hovered) {
                GlStateManager.bindTexture(lastTextureID);
            } else {
                GlStateManager.bindTexture(firstTextureID);
            }
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GuiUtil.drawModalRectWithCustomSizedTexture(x, y, textureX, textureY, width, height, textureWidth, textureHeight);
            if (this.hovered) {
                this.drawCenteredString(fontrenderer, this.displayString, this.x + this.width / 2, this.y + (this.height - 8) / 2, lastColor);
            } else {
                this.drawCenteredString(fontrenderer, this.displayString, this.x + this.width / 2, this.y + (this.height - 8) / 2, firstColor);
            }
            this.mouseDragged(mc, mouseX, mouseY);
        }
    }
}
