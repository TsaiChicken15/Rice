package net.minecraft.client.entity;

import java.util.Iterator;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockWall;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.MovingSoundMinecartRiding;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiCommandBlock;
import net.minecraft.client.gui.GuiEnchantment;
import net.minecraft.client.gui.GuiHopper;
import net.minecraft.client.gui.GuiMerchant;
import net.minecraft.client.gui.GuiRepair;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiScreenBook;
import net.minecraft.client.gui.inventory.GuiBeacon;
import net.minecraft.client.gui.inventory.GuiBrewingStand;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiCrafting;
import net.minecraft.client.gui.inventory.GuiDispenser;
import net.minecraft.client.gui.inventory.GuiEditSign;
import net.minecraft.client.gui.inventory.GuiFurnace;
import net.minecraft.client.gui.inventory.GuiScreenHorseInventory;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.command.server.CommandBlockLogic;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.client.C0CPacketInput;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.network.play.client.C13PacketPlayerAbilities;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraft.potion.Potion;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatFileWriter;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovementInput;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IInteractionObject;
import net.minecraft.world.World;
import rice.Client;
import rice.events.Event;
import rice.events.EventType;
import rice.events.listeners.EventMove;
import rice.events.listeners.EventUpdate;

public class EntityPlayerSP extends AbstractClientPlayer
{
    public final NetHandlerPlayClient sendQueue;
    private final StatFileWriter field_146108_bO;
    private double field_175172_bI;
    private double field_175166_bJ;
    private double field_175167_bK;
    private float field_175164_bL;
    private float field_175165_bM;
    private boolean field_175170_bN;
    public boolean field_175171_bO;
    private int field_175168_bP;
    private boolean field_175169_bQ;
    private String clientBrand;
    public MovementInput movementInput;
    protected Minecraft mc;

    /**
     * Used to tell if the player pressed forward twice. If this is at 0 and it's pressed (And they are allowed to
     * sprint, aka enough food on the ground etc) it sets this to 7. If it's pressed and it's greater than 0 enable
     * sprinting.
     */
    protected int sprintToggleTimer;

    /** Ticks left before sprinting is disabled. */
    public int sprintingTicksLeft;
    public float renderArmYaw;
    public float renderArmPitch;
    public float prevRenderArmYaw;
    public float prevRenderArmPitch;
    private int horseJumpPowerCounter;
    private float horseJumpPower;

    /** The amount of time an entity has been in a Portal */
    public float timeInPortal;

    /** The amount of time an entity has been in a Portal the previous tick */
    public float prevTimeInPortal;
    private static final String __OBFID = "CL_00000938";

    public EntityPlayerSP(Minecraft mcIn, World worldIn, NetHandlerPlayClient p_i46278_3_, StatFileWriter p_i46278_4_)
    {
        super(worldIn, p_i46278_3_.func_175105_e());
        this.sendQueue = p_i46278_3_;
        this.field_146108_bO = p_i46278_4_;
        this.mc = mcIn;
        this.dimension = 0;
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
        return false;
    }

    /**
     * Heal living entity (param: amount of half-hearts)
     */
    public void heal(float p_70691_1_) {}

    /**
     * Called when a player mounts an entity. e.g. mounts a pig, mounts a boat.
     */
    public void mountEntity(Entity entityIn)
    {
        super.mountEntity(entityIn);

        if (entityIn instanceof EntityMinecart)
        {
            this.mc.getSoundHandler().playSound(new MovingSoundMinecartRiding(this, (EntityMinecart)entityIn));
        }
    }
    
    public void moveEntity(double x, double y, double z) 
    {
        EventMove event = new EventMove(x, y, z);
        Client.onEvent(event);
        
        x = event.getX();
        y = event.getY();
        z = event.getZ();
    	
    	if (this.noClip)
        {
            this.func_174826_a(this.getEntityBoundingBox().offset(x, y, z));
            this.func_174829_m();
        }
        else
        {
            this.worldObj.theProfiler.startSection("move");
            double var7 = this.posX;
            double var9 = this.posY;
            double var11 = this.posZ;

            if (this.isInWeb)
            {
                this.isInWeb = false;
                x *= 0.25D;
                y *= 0.05000000074505806D;
                z *= 0.25D;
                this.motionX = 0.0D;
                this.motionY = 0.0D;
                this.motionZ = 0.0D;
            }

            double var13 = x;
            double var15 = y;
            double var17 = z;
            boolean var19 = this.onGround && this.isSneaking() && this instanceof EntityPlayer;

            if (var19 || event.isSafewalk)
            {
                double var20;

                for (var20 = 0.05D; x != 0.0D && this.worldObj.getCollidingBoundingBoxes(this, this.getEntityBoundingBox().offset(x, -1.0D, 0.0D)).isEmpty(); var13 = x)
                {
                    if (x < var20 && x >= -var20)
                    {
                        x = 0.0D;
                    }
                    else if (x > 0.0D)
                    {
                        x -= var20;
                    }
                    else
                    {
                        x += var20;
                    }
                }

                for (; z != 0.0D && this.worldObj.getCollidingBoundingBoxes(this, this.getEntityBoundingBox().offset(0.0D, -1.0D, z)).isEmpty(); var17 = z)
                {
                    if (z < var20 && z >= -var20)
                    {
                        z = 0.0D;
                    }
                    else if (z > 0.0D)
                    {
                        z -= var20;
                    }
                    else
                    {
                        z += var20;
                    }
                }

                for (; x != 0.0D && z != 0.0D && this.worldObj.getCollidingBoundingBoxes(this, this.getEntityBoundingBox().offset(x, -1.0D, z)).isEmpty(); var17 = z)
                {
                    if (x < var20 && x >= -var20)
                    {
                        x = 0.0D;
                    }
                    else if (x > 0.0D)
                    {
                        x -= var20;
                    }
                    else
                    {
                        x += var20;
                    }

                    var13 = x;

                    if (z < var20 && z >= -var20)
                    {
                        z = 0.0D;
                    }
                    else if (z > 0.0D)
                    {
                        z -= var20;
                    }
                    else
                    {
                        z += var20;
                    }
                }
            }

            List var53 = this.worldObj.getCollidingBoundingBoxes(this, this.getEntityBoundingBox().addCoord(x, y, z));
            AxisAlignedBB var21 = this.getEntityBoundingBox();
            AxisAlignedBB var23;

            for (Iterator var22 = var53.iterator(); var22.hasNext(); y = var23.calculateYOffset(this.getEntityBoundingBox(), y))
            {
                var23 = (AxisAlignedBB)var22.next();
            }

            this.func_174826_a(this.getEntityBoundingBox().offset(0.0D, y, 0.0D));
            boolean var54 = this.onGround || var15 != y && var15 < 0.0D;
            AxisAlignedBB var24;
            Iterator var55;

            for (var55 = var53.iterator(); var55.hasNext(); x = var24.calculateXOffset(this.getEntityBoundingBox(), x))
            {
                var24 = (AxisAlignedBB)var55.next();
            }

            this.func_174826_a(this.getEntityBoundingBox().offset(x, 0.0D, 0.0D));

            for (var55 = var53.iterator(); var55.hasNext(); z = var24.calculateZOffset(this.getEntityBoundingBox(), z))
            {
                var24 = (AxisAlignedBB)var55.next();
            }

            this.func_174826_a(this.getEntityBoundingBox().offset(0.0D, 0.0D, z));

            if (this.stepHeight > 0.0F && var54 && (var13 != x || var17 != z))
            {
                double var56 = x;
                double var25 = y;
                double var27 = z;
                AxisAlignedBB var29 = this.getEntityBoundingBox();
                this.func_174826_a(var21);
                y = (double)this.stepHeight;
                List var30 = this.worldObj.getCollidingBoundingBoxes(this, this.getEntityBoundingBox().addCoord(var13, y, var17));
                AxisAlignedBB var31 = this.getEntityBoundingBox();
                AxisAlignedBB var32 = var31.addCoord(var13, 0.0D, var17);
                double var33 = y;
                AxisAlignedBB var36;

                for (Iterator var35 = var30.iterator(); var35.hasNext(); var33 = var36.calculateYOffset(var32, var33))
                {
                    var36 = (AxisAlignedBB)var35.next();
                }

                var31 = var31.offset(0.0D, var33, 0.0D);
                double var67 = var13;
                AxisAlignedBB var38;

                for (Iterator var37 = var30.iterator(); var37.hasNext(); var67 = var38.calculateXOffset(var31, var67))
                {
                    var38 = (AxisAlignedBB)var37.next();
                }

                var31 = var31.offset(var67, 0.0D, 0.0D);
                double var68 = var17;
                AxisAlignedBB var40;

                for (Iterator var39 = var30.iterator(); var39.hasNext(); var68 = var40.calculateZOffset(var31, var68))
                {
                    var40 = (AxisAlignedBB)var39.next();
                }

                var31 = var31.offset(0.0D, 0.0D, var68);
                AxisAlignedBB var69 = this.getEntityBoundingBox();
                double var70 = y;
                AxisAlignedBB var43;

                for (Iterator var42 = var30.iterator(); var42.hasNext(); var70 = var43.calculateYOffset(var69, var70))
                {
                    var43 = (AxisAlignedBB)var42.next();
                }

                var69 = var69.offset(0.0D, var70, 0.0D);
                double var71 = var13;
                AxisAlignedBB var45;

                for (Iterator var44 = var30.iterator(); var44.hasNext(); var71 = var45.calculateXOffset(var69, var71))
                {
                    var45 = (AxisAlignedBB)var44.next();
                }

                var69 = var69.offset(var71, 0.0D, 0.0D);
                double var72 = var17;
                AxisAlignedBB var47;

                for (Iterator var46 = var30.iterator(); var46.hasNext(); var72 = var47.calculateZOffset(var69, var72))
                {
                    var47 = (AxisAlignedBB)var46.next();
                }

                var69 = var69.offset(0.0D, 0.0D, var72);
                double var73 = var67 * var67 + var68 * var68;
                double var48 = var71 * var71 + var72 * var72;

                if (var73 > var48)
                {
                    x = var67;
                    z = var68;
                    this.func_174826_a(var31);
                }
                else
                {
                    x = var71;
                    z = var72;
                    this.func_174826_a(var69);
                }

                y = (double)(-this.stepHeight);
                AxisAlignedBB var51;

                for (Iterator var50 = var30.iterator(); var50.hasNext(); y = var51.calculateYOffset(this.getEntityBoundingBox(), y))
                {
                    var51 = (AxisAlignedBB)var50.next();
                }

                this.func_174826_a(this.getEntityBoundingBox().offset(0.0D, y, 0.0D));

                if (var56 * var56 + var27 * var27 >= x * x + z * z)
                {
                    x = var56;
                    y = var25;
                    z = var27;
                    this.func_174826_a(var29);
                }
            }

            this.worldObj.theProfiler.endSection();
            this.worldObj.theProfiler.startSection("rest");
            this.func_174829_m();
            this.isCollidedHorizontally = var13 != x || var17 != z;
            this.isCollidedVertically = var15 != y;
            this.onGround = this.isCollidedVertically && var15 < 0.0D;
            this.isCollided = this.isCollidedHorizontally || this.isCollidedVertically;
            int var57 = MathHelper.floor_double(this.posX);
            int var58 = MathHelper.floor_double(this.posY - 0.20000000298023224D);
            int var59 = MathHelper.floor_double(this.posZ);
            BlockPos var26 = new BlockPos(var57, var58, var59);
            Block var60 = this.worldObj.getBlockState(var26).getBlock();

            if (var60.getMaterial() == Material.air)
            {
                Block var28 = this.worldObj.getBlockState(var26.offsetDown()).getBlock();

                if (var28 instanceof BlockFence || var28 instanceof BlockWall || var28 instanceof BlockFenceGate)
                {
                    var60 = var28;
                    var26 = var26.offsetDown();
                }
            }

            this.func_180433_a(y, this.onGround, var60, var26);

            if (var13 != x)
            {
                this.motionX = 0.0D;
            }

            if (var17 != z)
            {
                this.motionZ = 0.0D;
            }

            if (var15 != y)
            {
                var60.onLanded(this.worldObj, this);
            }

            if (this.canTriggerWalking() && !var19 && this.ridingEntity == null)
            {
                double var61 = this.posX - var7;
                double var64 = this.posY - var9;
                double var66 = this.posZ - var11;

                if (var60 != Blocks.ladder)
                {
                    var64 = 0.0D;
                }

                if (var60 != null && this.onGround)
                {
                    var60.onEntityCollidedWithBlock(this.worldObj, var26, this);
                }

                this.distanceWalkedModified = (float)((double)this.distanceWalkedModified + (double)MathHelper.sqrt_double(var61 * var61 + var66 * var66) * 0.6D);
                this.distanceWalkedOnStepModified = (float)((double)this.distanceWalkedOnStepModified + (double)MathHelper.sqrt_double(var61 * var61 + var64 * var64 + var66 * var66) * 0.6D);

                if (this.distanceWalkedOnStepModified > (float)this.nextStepDistance && var60.getMaterial() != Material.air)
                {
                    this.nextStepDistance = (int)this.distanceWalkedOnStepModified + 1;

                    if (this.isInWater())
                    {
                        float var34 = MathHelper.sqrt_double(this.motionX * this.motionX * 0.20000000298023224D + this.motionY * this.motionY + this.motionZ * this.motionZ * 0.20000000298023224D) * 0.35F;

                        if (var34 > 1.0F)
                        {
                            var34 = 1.0F;
                        }

                        this.playSound(this.getSwimSound(), var34, 1.0F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.4F);
                    }

                    this.func_180429_a(var26, var60);
                }
            }

            try
            {
                this.doBlockCollisions();
            }
            catch (Throwable var52)
            {
                CrashReport var63 = CrashReport.makeCrashReport(var52, "Checking entity block collision");
                CrashReportCategory var65 = var63.makeCategory("Entity being checked for collision");
                this.addEntityCrashInfo(var65);
                throw new ReportedException(var63);
            }

            boolean var62 = this.isWet();

            if (this.worldObj.func_147470_e(this.getEntityBoundingBox().contract(0.001D, 0.001D, 0.001D)))
            {
                this.dealFireDamage(1);

                if (!var62)
                {
                    ++this.fire;

                    if (this.fire == 0)
                    {
                        this.setFire(8);
                    }
                }
            }
            else if (this.fire <= 0)
            {
                this.fire = -this.fireResistance;
            }

            if (var62 && this.fire > 0)
            {
                this.playSound("random.fizz", 0.7F, 1.6F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.4F);
                this.fire = -this.fireResistance;
            }

            this.worldObj.theProfiler.endSection();
        }
    }
    
    

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        if (this.worldObj.isBlockLoaded(new BlockPos(this.posX, 0.0D, this.posZ)))
        {
            super.onUpdate();

            if (this.isRiding())
            {
                this.sendQueue.addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook(this.rotationYaw, this.rotationPitch, this.onGround));
                this.sendQueue.addToSendQueue(new C0CPacketInput(this.moveStrafing, this.moveForward, this.movementInput.jump, this.movementInput.sneak));
            }
            else
            {
                this.func_175161_p();
            }
        }
    }

    public void func_175161_p()
    {
    	EventUpdate event = new EventUpdate(this.posX, this.getEntityBoundingBox().minY, this.posZ, this.rotationYaw, this.rotationPitch, this.onGround);
    	event.setType(EventType.PRE);
    	Client.onEvent(event);

        boolean var1 = this.isSprinting();

        if (var1 != this.field_175171_bO)
        {
            if (var1)
            {
                this.sendQueue.addToSendQueue(new C0BPacketEntityAction(this, C0BPacketEntityAction.Action.START_SPRINTING));
            }
            else
            {
                this.sendQueue.addToSendQueue(new C0BPacketEntityAction(this, C0BPacketEntityAction.Action.STOP_SPRINTING));
            }

            this.field_175171_bO = var1;
        }

        boolean var2 = this.isSneaking();

        if (var2 != this.field_175170_bN)
        {
            if (var2)
            {
                this.sendQueue.addToSendQueue(new C0BPacketEntityAction(this, C0BPacketEntityAction.Action.START_SNEAKING));
            }
            else
            {
                this.sendQueue.addToSendQueue(new C0BPacketEntityAction(this, C0BPacketEntityAction.Action.STOP_SNEAKING));
            }

            this.field_175170_bN = var2;
        }

        if (this.func_175160_A())
        {
            double var3 = event.getX() - this.field_175172_bI;
            double var5 = event.getY() - this.field_175166_bJ;
            double var7 = event.getZ() - this.field_175167_bK;
            double var9 = (double)(event.getYaw() - this.field_175164_bL);
            double var11 = (double)(event.getPitch() - this.field_175165_bM);
            boolean var13 = var3 * var3 + var5 * var5 + var7 * var7 > 9.0E-4D || this.field_175168_bP >= 20;
            boolean var14 = var9 != 0.0D || var11 != 0.0D;

            if (this.ridingEntity == null)
            {
                if (var13 && var14)
                {
                    this.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(event.getX(), event.getY(), event.getZ(), event.getYaw(), event.getPitch(), event.isOnGround()));
                }
                else if (var13)
                {
                    this.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(event.getX(), event.getY(), event.getZ(), event.isOnGround()));
                }
                else if (var14)
                {
                    this.sendQueue.addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook(event.getYaw(), event.getPitch(), event.isOnGround()));
                }
                else
                {
                    this.sendQueue.addToSendQueue(new C03PacketPlayer(event.isOnGround()));
                }
            }
            else
            {
                this.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(this.motionX, -999.0D, this.motionZ, event.getYaw(), event.getPitch(), event.isOnGround()));
                var13 = false;
            }

            ++this.field_175168_bP;

            if (var13)
            {
                this.field_175172_bI = event.getX();
                this.field_175166_bJ = event.getY();
                this.field_175167_bK = event.getZ();
                this.field_175168_bP = 0;
            }

            if (var14)
            {
                this.field_175164_bL = event.getYaw();
                this.field_175165_bM = event.getPitch();
            }
        }
        
        event.setType(EventType.POST);
    	Client.onEvent(event);
    }

    /**
     * Called when player presses the drop item key
     */
    public EntityItem dropOneItem(boolean p_71040_1_)
    {
        C07PacketPlayerDigging.Action var2 = p_71040_1_ ? C07PacketPlayerDigging.Action.DROP_ALL_ITEMS : C07PacketPlayerDigging.Action.DROP_ITEM;
        this.sendQueue.addToSendQueue(new C07PacketPlayerDigging(var2, BlockPos.ORIGIN, EnumFacing.DOWN));
        return null;
    }

    /**
     * Joins the passed in entity item with the world. Args: entityItem
     */
    protected void joinEntityItemWithWorld(EntityItem p_71012_1_) {}

    /**
     * Sends a chat message from the player. Args: chatMessage
     */
    public void sendChatMessage(String p_71165_1_)
    {
        this.sendQueue.addToSendQueue(new C01PacketChatMessage(p_71165_1_));
    }

    /**
     * Swings the item the player is holding.
     */
    public void swingItem()
    {
        super.swingItem();
        this.sendQueue.addToSendQueue(new C0APacketAnimation());
    }

    public void respawnPlayer()
    {
        this.sendQueue.addToSendQueue(new C16PacketClientStatus(C16PacketClientStatus.EnumState.PERFORM_RESPAWN));
    }

    /**
     * Deals damage to the entity. If its a EntityPlayer then will take damage from the armor first and then health
     * second with the reduced value. Args: damageAmount
     */
    protected void damageEntity(DamageSource p_70665_1_, float p_70665_2_)
    {
        if (!this.func_180431_b(p_70665_1_))
        {
            this.setHealth(this.getHealth() - p_70665_2_);
        }
    }

    /**
     * set current crafting inventory back to the 2x2 square
     */
    public void closeScreen()
    {
        this.sendQueue.addToSendQueue(new C0DPacketCloseWindow(this.openContainer.windowId));
        this.func_175159_q();
    }

    public void func_175159_q()
    {
        this.inventory.setItemStack((ItemStack)null);
        super.closeScreen();
        this.mc.displayGuiScreen((GuiScreen)null);
    }

    /**
     * Updates health locally.
     */
    public void setPlayerSPHealth(float p_71150_1_)
    {
        if (this.field_175169_bQ)
        {
            float var2 = this.getHealth() - p_71150_1_;

            if (var2 <= 0.0F)
            {
                this.setHealth(p_71150_1_);

                if (var2 < 0.0F)
                {
                    this.hurtResistantTime = this.maxHurtResistantTime / 2;
                }
            }
            else
            {
                this.lastDamage = var2;
                this.setHealth(this.getHealth());
                this.hurtResistantTime = this.maxHurtResistantTime;
                this.damageEntity(DamageSource.generic, var2);
                this.hurtTime = this.maxHurtTime = 10;
            }
        }
        else
        {
            this.setHealth(p_71150_1_);
            this.field_175169_bQ = true;
        }
    }

    /**
     * Adds a value to a statistic field.
     */
    public void addStat(StatBase p_71064_1_, int p_71064_2_)
    {
        if (p_71064_1_ != null)
        {
            if (p_71064_1_.isIndependent)
            {
                super.addStat(p_71064_1_, p_71064_2_);
            }
        }
    }

    /**
     * Sends the player's abilities to the server (if there is one).
     */
    public void sendPlayerAbilities()
    {
        this.sendQueue.addToSendQueue(new C13PacketPlayerAbilities(this.capabilities));
    }

    public boolean func_175144_cb()
    {
        return true;
    }

    protected void sendHorseJump()
    {
        this.sendQueue.addToSendQueue(new C0BPacketEntityAction(this, C0BPacketEntityAction.Action.RIDING_JUMP, (int)(this.getHorseJumpPower() * 100.0F)));
    }

    public void func_175163_u()
    {
        this.sendQueue.addToSendQueue(new C0BPacketEntityAction(this, C0BPacketEntityAction.Action.OPEN_INVENTORY));
    }

    public void func_175158_f(String p_175158_1_)
    {
        this.clientBrand = p_175158_1_;
    }

    public String getClientBrand()
    {
        return this.clientBrand;
    }

    public StatFileWriter getStatFileWriter()
    {
        return this.field_146108_bO;
    }

    public void addChatComponentMessage(IChatComponent p_146105_1_)
    {
        this.mc.ingameGUI.getChatGUI().printChatMessage(p_146105_1_);
    }

    protected boolean pushOutOfBlocks(double x, double y, double z)
    {
        if (this.noClip)
        {
            return false;
        }
        else
        {
            BlockPos var7 = new BlockPos(x, y, z);
            double var8 = x - (double)var7.getX();
            double var10 = z - (double)var7.getZ();

            if (!this.func_175162_d(var7))
            {
                byte var12 = -1;
                double var13 = 9999.0D;

                if (this.func_175162_d(var7.offsetWest()) && var8 < var13)
                {
                    var13 = var8;
                    var12 = 0;
                }

                if (this.func_175162_d(var7.offsetEast()) && 1.0D - var8 < var13)
                {
                    var13 = 1.0D - var8;
                    var12 = 1;
                }

                if (this.func_175162_d(var7.offsetNorth()) && var10 < var13)
                {
                    var13 = var10;
                    var12 = 4;
                }

                if (this.func_175162_d(var7.offsetSouth()) && 1.0D - var10 < var13)
                {
                    var13 = 1.0D - var10;
                    var12 = 5;
                }

                float var15 = 0.1F;

                if (var12 == 0)
                {
                    this.motionX = (double)(-var15);
                }

                if (var12 == 1)
                {
                    this.motionX = (double)var15;
                }

                if (var12 == 4)
                {
                    this.motionZ = (double)(-var15);
                }

                if (var12 == 5)
                {
                    this.motionZ = (double)var15;
                }
            }

            return false;
        }
    }

    private boolean func_175162_d(BlockPos p_175162_1_)
    {
        return !this.worldObj.getBlockState(p_175162_1_).getBlock().isNormalCube() && !this.worldObj.getBlockState(p_175162_1_.offsetUp()).getBlock().isNormalCube();
    }

    /**
     * Set sprinting switch for Entity.
     */
    public void setSprinting(boolean sprinting)
    {
        super.setSprinting(sprinting);
        this.sprintingTicksLeft = sprinting ? 600 : 0;
    }

    /**
     * Sets the current XP, total XP, and level number.
     */
    public void setXPStats(float p_71152_1_, int p_71152_2_, int p_71152_3_)
    {
        this.experience = p_71152_1_;
        this.experienceTotal = p_71152_2_;
        this.experienceLevel = p_71152_3_;
    }

    /**
     * Notifies this sender of some sort of information.  This is for messages intended to display to the user.  Used
     * for typical output (like "you asked for whether or not this game rule is set, so here's your answer"), warnings
     * (like "I fetched this block for you by ID, but I'd like you to know that every time you do this, I die a little
     * inside"), and errors (like "it's not called iron_pixacke, silly").
     */
    public void addChatMessage(IChatComponent message)
    {
        this.mc.ingameGUI.getChatGUI().printChatMessage(message);
    }

    /**
     * Returns true if the command sender is allowed to use the given command.
     */
    public boolean canCommandSenderUseCommand(int permissionLevel, String command)
    {
        return permissionLevel <= 0;
    }

    public BlockPos getPosition()
    {
        return new BlockPos(this.posX + 0.5D, this.posY + 0.5D, this.posZ + 0.5D);
    }

    public void playSound(String name, float volume, float pitch)
    {
        this.worldObj.playSound(this.posX, this.posY, this.posZ, name, volume, pitch, false);
    }

    /**
     * Returns whether the entity is in a server world
     */
    public boolean isServerWorld()
    {
        return true;
    }

    public boolean isRidingHorse()
    {
        return this.ridingEntity != null && this.ridingEntity instanceof EntityHorse && ((EntityHorse)this.ridingEntity).isHorseSaddled();
    }

    public float getHorseJumpPower()
    {
        return this.horseJumpPower;
    }

    public void func_175141_a(TileEntitySign p_175141_1_)
    {
        this.mc.displayGuiScreen(new GuiEditSign(p_175141_1_));
    }

    public void func_146095_a(CommandBlockLogic p_146095_1_)
    {
        this.mc.displayGuiScreen(new GuiCommandBlock(p_146095_1_));
    }

    /**
     * Displays the GUI for interacting with a book.
     */
    public void displayGUIBook(ItemStack bookStack)
    {
        Item var2 = bookStack.getItem();

        if (var2 == Items.writable_book)
        {
            this.mc.displayGuiScreen(new GuiScreenBook(this, bookStack, true));
        }
    }

    /**
     * Displays the GUI for interacting with a chest inventory. Args: chestInventory
     */
    public void displayGUIChest(IInventory chestInventory)
    {
        String var2 = chestInventory instanceof IInteractionObject ? ((IInteractionObject)chestInventory).getGuiID() : "minecraft:container";

        if ("minecraft:chest".equals(var2))
        {
            this.mc.displayGuiScreen(new GuiChest(this.inventory, chestInventory));
        }
        else if ("minecraft:hopper".equals(var2))
        {
            this.mc.displayGuiScreen(new GuiHopper(this.inventory, chestInventory));
        }
        else if ("minecraft:furnace".equals(var2))
        {
            this.mc.displayGuiScreen(new GuiFurnace(this.inventory, chestInventory));
        }
        else if ("minecraft:brewing_stand".equals(var2))
        {
            this.mc.displayGuiScreen(new GuiBrewingStand(this.inventory, chestInventory));
        }
        else if ("minecraft:beacon".equals(var2))
        {
            this.mc.displayGuiScreen(new GuiBeacon(this.inventory, chestInventory));
        }
        else if (!"minecraft:dispenser".equals(var2) && !"minecraft:dropper".equals(var2))
        {
            this.mc.displayGuiScreen(new GuiChest(this.inventory, chestInventory));
        }
        else
        {
            this.mc.displayGuiScreen(new GuiDispenser(this.inventory, chestInventory));
        }
    }

    public void displayGUIHorse(EntityHorse p_110298_1_, IInventory p_110298_2_)
    {
        this.mc.displayGuiScreen(new GuiScreenHorseInventory(this.inventory, p_110298_2_, p_110298_1_));
    }

    public void displayGui(IInteractionObject guiOwner)
    {
        String var2 = guiOwner.getGuiID();

        if ("minecraft:crafting_table".equals(var2))
        {
            this.mc.displayGuiScreen(new GuiCrafting(this.inventory, this.worldObj));
        }
        else if ("minecraft:enchanting_table".equals(var2))
        {
            this.mc.displayGuiScreen(new GuiEnchantment(this.inventory, this.worldObj, guiOwner));
        }
        else if ("minecraft:anvil".equals(var2))
        {
            this.mc.displayGuiScreen(new GuiRepair(this.inventory, this.worldObj));
        }
    }

    public void displayVillagerTradeGui(IMerchant villager)
    {
        this.mc.displayGuiScreen(new GuiMerchant(this.inventory, villager, this.worldObj));
    }

    /**
     * Called when the player performs a critical hit on the Entity. Args: entity that was hit critically
     */
    public void onCriticalHit(Entity p_71009_1_)
    {
        this.mc.effectRenderer.func_178926_a(p_71009_1_, EnumParticleTypes.CRIT);
    }

    public void onEnchantmentCritical(Entity p_71047_1_)
    {
        this.mc.effectRenderer.func_178926_a(p_71047_1_, EnumParticleTypes.CRIT_MAGIC);
    }

    /**
     * Returns if this entity is sneaking.
     */
    public boolean isSneaking()
    {
        boolean var1 = this.movementInput != null ? this.movementInput.sneak : false;
        return var1 && !this.sleeping;
    }

    public void updateEntityActionState()
    {
        super.updateEntityActionState();

        if (this.func_175160_A())
        {
            this.moveStrafing = this.movementInput.moveStrafe;
            this.moveForward = this.movementInput.moveForward;
            this.isJumping = this.movementInput.jump;
            this.prevRenderArmYaw = this.renderArmYaw;
            this.prevRenderArmPitch = this.renderArmPitch;
            this.renderArmPitch = (float)((double)this.renderArmPitch + (double)(this.rotationPitch - this.renderArmPitch) * 0.5D);
            this.renderArmYaw = (float)((double)this.renderArmYaw + (double)(this.rotationYaw - this.renderArmYaw) * 0.5D);
        }
    }

    protected boolean func_175160_A()
    {
        return this.mc.func_175606_aa() == this;
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public void onLivingUpdate()
    {
        if (this.sprintingTicksLeft > 0)
        {
            --this.sprintingTicksLeft;

            if (this.sprintingTicksLeft == 0)
            {
                this.setSprinting(false);
            }
        }

        if (this.sprintToggleTimer > 0)
        {
            --this.sprintToggleTimer;
        }

        this.prevTimeInPortal = this.timeInPortal;

        if (this.inPortal)
        {
            if (this.mc.currentScreen != null && !this.mc.currentScreen.doesGuiPauseGame())
            {
                this.mc.displayGuiScreen((GuiScreen)null);
            }

            if (this.timeInPortal == 0.0F)
            {
                this.mc.getSoundHandler().playSound(PositionedSoundRecord.createPositionedSoundRecord(new ResourceLocation("portal.trigger"), this.rand.nextFloat() * 0.4F + 0.8F));
            }

            this.timeInPortal += 0.0125F;

            if (this.timeInPortal >= 1.0F)
            {
                this.timeInPortal = 1.0F;
            }

            this.inPortal = false;
        }
        else if (this.isPotionActive(Potion.confusion) && this.getActivePotionEffect(Potion.confusion).getDuration() > 60)
        {
            this.timeInPortal += 0.006666667F;

            if (this.timeInPortal > 1.0F)
            {
                this.timeInPortal = 1.0F;
            }
        }
        else
        {
            if (this.timeInPortal > 0.0F)
            {
                this.timeInPortal -= 0.05F;
            }

            if (this.timeInPortal < 0.0F)
            {
                this.timeInPortal = 0.0F;
            }
        }

        if (this.timeUntilPortal > 0)
        {
            --this.timeUntilPortal;
        }

        boolean var1 = this.movementInput.jump;
        boolean var2 = this.movementInput.sneak;
        float var3 = 0.8F;
        boolean var4 = this.movementInput.moveForward >= var3;
        this.movementInput.updatePlayerMoveState();

        if (this.isUsingItem() && !this.isRiding())
        {
            this.movementInput.moveStrafe *= 0.2F;
            this.movementInput.moveForward *= 0.2F;
            this.sprintToggleTimer = 0;
        }

        this.pushOutOfBlocks(this.posX - (double)this.width * 0.35D, this.getEntityBoundingBox().minY + 0.5D, this.posZ + (double)this.width * 0.35D);
        this.pushOutOfBlocks(this.posX - (double)this.width * 0.35D, this.getEntityBoundingBox().minY + 0.5D, this.posZ - (double)this.width * 0.35D);
        this.pushOutOfBlocks(this.posX + (double)this.width * 0.35D, this.getEntityBoundingBox().minY + 0.5D, this.posZ - (double)this.width * 0.35D);
        this.pushOutOfBlocks(this.posX + (double)this.width * 0.35D, this.getEntityBoundingBox().minY + 0.5D, this.posZ + (double)this.width * 0.35D);
        boolean var5 = (float)this.getFoodStats().getFoodLevel() > 6.0F || this.capabilities.allowFlying;

        if (this.onGround && !var2 && !var4 && this.movementInput.moveForward >= var3 && !this.isSprinting() && var5 && !this.isUsingItem() && !this.isPotionActive(Potion.blindness))
        {
            if (this.sprintToggleTimer <= 0 && !this.mc.gameSettings.keyBindSprint.getIsKeyPressed())
            {
                this.sprintToggleTimer = 7;
            }
            else
            {
                this.setSprinting(true);
            }
        }

        if (!this.isSprinting() && this.movementInput.moveForward >= var3 && var5 && !this.isUsingItem() && !this.isPotionActive(Potion.blindness) && this.mc.gameSettings.keyBindSprint.getIsKeyPressed())
        {
            this.setSprinting(true);
        }

        if (this.isSprinting() && (this.movementInput.moveForward < var3 || this.isCollidedHorizontally || !var5))
        {
            this.setSprinting(false);
        }

        if (this.capabilities.allowFlying)
        {
            if (this.mc.playerController.isSpectatorMode())
            {
                if (!this.capabilities.isFlying)
                {
                    this.capabilities.isFlying = true;
                    this.sendPlayerAbilities();
                }
            }
            else if (!var1 && this.movementInput.jump)
            {
                if (this.flyToggleTimer == 0)
                {
                    this.flyToggleTimer = 7;
                }
                else
                {
                    this.capabilities.isFlying = !this.capabilities.isFlying;
                    this.sendPlayerAbilities();
                    this.flyToggleTimer = 0;
                }
            }
        }

        if (this.capabilities.isFlying && this.func_175160_A())
        {
            if (this.movementInput.sneak)
            {
                this.motionY -= (double)(this.capabilities.getFlySpeed() * 3.0F);
            }

            if (this.movementInput.jump)
            {
                this.motionY += (double)(this.capabilities.getFlySpeed() * 3.0F);
            }
        }

        if (this.isRidingHorse())
        {
            if (this.horseJumpPowerCounter < 0)
            {
                ++this.horseJumpPowerCounter;

                if (this.horseJumpPowerCounter == 0)
                {
                    this.horseJumpPower = 0.0F;
                }
            }

            if (var1 && !this.movementInput.jump)
            {
                this.horseJumpPowerCounter = -10;
                this.sendHorseJump();
            }
            else if (!var1 && this.movementInput.jump)
            {
                this.horseJumpPowerCounter = 0;
                this.horseJumpPower = 0.0F;
            }
            else if (var1)
            {
                ++this.horseJumpPowerCounter;

                if (this.horseJumpPowerCounter < 10)
                {
                    this.horseJumpPower = (float)this.horseJumpPowerCounter * 0.1F;
                }
                else
                {
                    this.horseJumpPower = 0.8F + 2.0F / (float)(this.horseJumpPowerCounter - 9) * 0.1F;
                }
            }
        }
        else
        {
            this.horseJumpPower = 0.0F;
        }

        super.onLivingUpdate();

        if (this.onGround && this.capabilities.isFlying && !this.mc.playerController.isSpectatorMode())
        {
            this.capabilities.isFlying = false;
            this.sendPlayerAbilities();
        }
    }
}