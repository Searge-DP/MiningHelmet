package net.gegy1000.mininghelmet.common.item;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class ItemMiningHelmet extends ItemArmor
{
    public ItemMiningHelmet(ArmorMaterial material)
    {
        super(material, 15, 0);
        this.setUnlocalizedName("mining_helmet");
    }

    public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type)
    {
        return "mininghelmet:textures/armor/mining_helmet.png";
    }
}
