package net.gegy1000.mininghelmet.common.event;

import net.gegy1000.mininghelmet.common.MiningHelmet;
import net.gegy1000.mininghelmet.common.item.ItemMiningHelmet;
import net.gegy1000.mininghelmet.common.tile.TileLight;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class CommonEventHandler
{
    @SubscribeEvent
    public void playerTick(TickEvent.PlayerTickEvent event)
    {
        EntityPlayer player = event.player;

        ItemStack helmetStack = player.getCurrentArmor(3);

        if (helmetStack != null && helmetStack.getItem() instanceof ItemMiningHelmet)
        {
            MovingObjectPosition mop = rayTrace(player, 20, 1.0F);

            if (!(mop.typeOfHit == MovingObjectPosition.MovingObjectType.MISS))
            {
                BlockPos pos;

                if (mop.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY)
                {
                    pos = mop.entityHit.getPosition();
                }
                else
                {
                    pos = mop.getBlockPos();
                    pos = pos.offset(mop.sideHit);
                }

                if (player.worldObj.getBlockState(pos).getBlock() == MiningHelmet.light_block)
                {
                    TileLight tileLight = (TileLight) player.worldObj.getTileEntity(pos);
                    tileLight.ticks = 0;
                }
                else
                {
                    if (player.worldObj.isAirBlock(pos))
                    {
                        player.worldObj.setBlockState(pos, MiningHelmet.light_block.getDefaultState());
                    }
                }
            }
        }
    }

    public Vec3 getPositionEyes(EntityPlayer player, float partialTick)
    {
        if (partialTick == 1.0F)
        {
            return new Vec3(player.posX, player.posY + (double)player.getEyeHeight(), player.posZ);
        }
        else
        {
            double d0 = player.prevPosX + (player.posX - player.prevPosX) * (double)partialTick;
            double d1 = player.prevPosY + (player.posY - player.prevPosY) * (double)partialTick + (double)player.getEyeHeight();
            double d2 = player.prevPosZ + (player.posZ - player.prevPosZ) * (double)partialTick;
            return new Vec3(d0, d1, d2);
        }
    }

    public MovingObjectPosition rayTrace(EntityPlayer player, double distance, float partialTick)
    {
        Vec3 vec3 = getPositionEyes(player, partialTick);
        Vec3 vec31 = player.getLook(partialTick);
        Vec3 vec32 = vec3.addVector(vec31.xCoord * distance, vec31.yCoord * distance, vec31.zCoord * distance);
        return player.worldObj.rayTraceBlocks(vec3, vec32, false, false, true);
    }
}
