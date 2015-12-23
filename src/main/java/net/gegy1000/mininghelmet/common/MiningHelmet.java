package net.gegy1000.mininghelmet.common;

import net.gegy1000.mininghelmet.common.block.BlockLight;
import net.gegy1000.mininghelmet.common.event.CommonEventHandler;
import net.gegy1000.mininghelmet.common.item.ItemMiningHelmet;
import net.gegy1000.mininghelmet.common.proxy.CommonProxy;
import net.gegy1000.mininghelmet.common.tile.TileLight;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = "mininghelmet", name = "Mining Helmet", version = "0.0.1")
public class MiningHelmet
{
    @Mod.Instance("mininghelmet")
    public static MiningHelmet instance;

    @SidedProxy(clientSide = "net.gegy1000.mininghelmet.client.proxy.ClientProxy", serverSide = "net.gegy1000.mininghelmet.common.proxy.CommonProxy")
    public static CommonProxy proxy;

    public static BlockLight light_block;
    public static ItemMiningHelmet mining_helmet;

    public static ItemArmor.ArmorMaterial MINING_HELMET = EnumHelper.addArmorMaterial("MINING_HELMET", "mining_helmet", 15, new int[] { 2, 6, 5, 2 }, 9);

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        light_block = new BlockLight();
        mining_helmet = new ItemMiningHelmet(MINING_HELMET);

        MinecraftForge.EVENT_BUS.register(new CommonEventHandler());

        GameRegistry.registerBlock(light_block, "light_block");
        GameRegistry.registerTileEntity(TileLight.class, "mininghelmet:light_block");
        GameRegistry.registerItem(mining_helmet, "mining_helmet");

        GameRegistry.addRecipe(new ItemStack(mining_helmet),
                " G ",
                "IOI",
                "III",
                'I', Blocks.iron_block, 'G', Blocks.glowstone, 'O', new ItemStack(Items.dye, 1, 14)
                );

        proxy.preInit();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.init();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        proxy.postInit();
    }
}
