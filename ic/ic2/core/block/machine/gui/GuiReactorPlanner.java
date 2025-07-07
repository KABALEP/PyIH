/*     */ package ic2.core.block.machine.gui;
/*     */ 
/*     */ import com.google.common.base.Strings;
/*     */ import cpw.mods.fml.relauncher.ReflectionHelper;
/*     */ import ic2.api.reactor.IReactorPlannerComponent;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.Ic2Items;
/*     */ import ic2.core.block.machine.container.ContainerReactorPlanner;
/*     */ import ic2.core.block.machine.logic.PlannerRegistry;
/*     */ import ic2.core.block.machine.logic.ReactorLogicBase;
/*     */ import ic2.core.block.machine.logic.TickingReactorLogic;
/*     */ import ic2.core.block.machine.logic.TickingSteamReactorLogic;
/*     */ import ic2.core.block.machine.logic.encoder.EncoderRegistry;
/*     */ import ic2.core.block.machine.tileentity.TileEntityReactorPlanner;
/*     */ import ic2.core.network.internal.PayloadPacket;
/*     */ import ic2.core.network.packets.IC2Packet;
/*     */ import ic2.core.util.GuiCustomButton;
/*     */ import ic2.core.util.IDrawButton;
/*     */ import ic2.core.util.Tuple;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.datatransfer.Clipboard;
/*     */ import java.awt.datatransfer.DataFlavor;
/*     */ import java.text.DecimalFormat;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.swing.JOptionPane;
/*     */ import net.minecraft.client.audio.ISound;
/*     */ import net.minecraft.client.audio.PositionedSoundRecord;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.gui.GuiTextField;
/*     */ import net.minecraft.client.gui.inventory.GuiContainer;
/*     */ import net.minecraft.client.renderer.RenderHelper;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.Slot;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ import org.lwjgl.input.Mouse;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GuiReactorPlanner
/*     */   extends GuiContainer
/*     */ {
/*  59 */   static final ResourceLocation texture = new ResourceLocation("ic2", "textures/guiSprites/GUIReactorPlanner.png");
/*  60 */   static final String[] sortingLang = new String[] { "container.reactorplannerButton.sortButtonAll.name", "container.reactorplannerButton.sortButtonFuel.name", "container.reactorplannerButton.sortButtonCooolant.name", "container.reactorplannerButton.sortButtonCondensor.name", "container.reactorplannerButton.sortButtonHeatPack.name", "container.reactorplannerButton.sortButtonVent.name", "container.reactorplannerButton.sortButtonVentSpread.name", "container.reactorplannerButton.sortButtonSwtich.name", "container.reactorplannerButton.sortButtonPlate.name", "container.reactorplannerButton.sortButtonRelection.name", "container.reactorplannerButton.sortButtonIsotope.name" };
/*     */ 
/*     */ 
/*     */   
/*     */   TileEntityReactorPlanner planner;
/*     */ 
/*     */ 
/*     */   
/*     */   String name;
/*     */ 
/*     */ 
/*     */   
/*     */   String inv;
/*     */ 
/*     */ 
/*     */   
/*  76 */   Map<Integer, GuiButton> buttonMap = new HashMap<>();
/*     */   
/*     */   GuiTextField field;
/*     */   
/*     */   public GuiReactorPlanner(ContainerReactorPlanner container) {
/*  81 */     super((Container)container);
/*  82 */     this.planner = container.planner;
/*  83 */     this.name = StatCollector.func_74838_a("blockReactorPlanner.name");
/*  84 */     this.field_147000_g = 212;
/*  85 */     this.field_146999_f = 212;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_146979_b(int mouseX, int mouseY) {
/*  91 */     this.field_146289_q.func_78276_b(this.name, (this.field_146999_f - this.field_146289_q.func_78256_a(this.name) - 20) / 2, 6, 4210752);
/*  92 */     this.field_146289_q.func_78276_b("" + this.planner.stackSize, 240, 111, 4210752);
/*  93 */     String start = StatCollector.func_74838_a("container.reactorplannerInfo.startingHeat.name");
/*  94 */     this.field_146289_q.func_78276_b(start, 243 - this.field_146289_q.func_78256_a(start) / 2, 125, 4210752);
/*  95 */     start = StatCollector.func_74838_a("container.reactorplannerInfo.customTime.name");
/*  96 */     this.field_146289_q.func_78276_b(start, 243 - this.field_146289_q.func_78256_a(start) / 2, 149, 4210752);
/*  97 */     start = StatCollector.func_74838_a("container.reactorplannerInfo.customSimSpeed.name");
/*  98 */     this.field_146289_q.func_78276_b(start, 243 - this.field_146289_q.func_78256_a(start) / 2, 173, 4210752);
/*  99 */     DecimalFormat format = new DecimalFormat("###,###");
/* 100 */     TileEntityReactorPlanner.UserSettings settings = this.planner.getUserSettings();
/* 101 */     start = format.format(settings.startingHeat);
/* 102 */     this.field_146289_q.func_78276_b(start, 242 - this.field_146289_q.func_78256_a(start) / 2, 136, 4210752);
/* 103 */     start = format.format(settings.maxTicks);
/* 104 */     this.field_146289_q.func_78276_b(start, 242 - this.field_146289_q.func_78256_a(start) / 2, 160, 4210752);
/* 105 */     start = format.format(settings.ticksPerTick);
/* 106 */     this.field_146289_q.func_78276_b(start, 242 - this.field_146289_q.func_78256_a(start) / 2, 184, 4210752);
/*     */     
/* 108 */     ReactorLogicBase base = this.planner.getReactorLogic();
/* 109 */     this.field.func_146195_b(false);
/* 110 */     if (this.planner.isSteamReactor) {
/*     */       
/* 112 */       TickingSteamReactorLogic.SteamReactorPrediction predict = (TickingSteamReactorLogic.SteamReactorPrediction)this.planner.getPrediction();
/* 113 */       TickingSteamReactorLogic logic = (TickingSteamReactorLogic)base;
/* 114 */       if (this.planner.selectedView == 0)
/*     */       {
/* 116 */         DecimalFormat floatFormat = new DecimalFormat("#.#");
/* 117 */         this.field_146289_q.func_78276_b(StatCollector.func_74837_a("container.reactorplannerInfo.expectedSteam.name", new Object[] { format.format(predict.steamPerTick) }), 5, 130, 4210752);
/* 118 */         this.field_146289_q.func_78276_b(StatCollector.func_74837_a("container.reactorplannerInfo.totalSteam.name", new Object[] { format.format(predict.totalSteamProduced) }), 5, 139, 4210752);
/* 119 */         this.field_146289_q.func_78276_b(StatCollector.func_74837_a("container.reactorplannerInfo.totalHeat.name", new Object[] { format.format(predict.totalHeatProduced) }), 5, 148, 4210752);
/* 120 */         this.field_146289_q.func_78276_b(StatCollector.func_74837_a("container.reactorplannerInfo.explosionEffect.name", new Object[] { Float.valueOf(predict.totalExplosionPower) }), 5, 157, 4210752);
/* 121 */         this.field_146289_q.func_78276_b(StatCollector.func_74837_a("container.reactorplannerInfo.efficeny.name", new Object[] { floatFormat.format(predict.efficency), floatFormat.format(predict.totalEfficency) }), 5, 166, 4210752);
/* 122 */         this.field_146289_q.func_78276_b(StatCollector.func_74837_a("container.reactorplannerInfo.isBreeder.name", new Object[] { Boolean.valueOf(predict.breeder) }), 5, 175, 4210752);
/*     */       }
/* 124 */       else if (this.planner.selectedView == 1)
/*     */       {
/* 126 */         int excess = predict.heatPerTick - predict.coolingPerTick;
/* 127 */         boolean cooling = (excess < 0);
/* 128 */         if (cooling)
/*     */         {
/* 130 */           excess = -excess;
/*     */         }
/* 132 */         this.field_146289_q.func_78276_b(StatCollector.func_74837_a("container.reactorplannerInfo.expectedHeat.name", new Object[] { Integer.valueOf(predict.heatPerTick) }), 5, 130, 4210752);
/* 133 */         this.field_146289_q.func_78276_b(StatCollector.func_74837_a("container.reactorplannerInfo.expectedSteam.name", new Object[] { format.format(predict.steamPerTick) }), 5, 140, 4210752);
/* 134 */         this.field_146289_q.func_78276_b(StatCollector.func_74837_a("container.reactorplannerInfo.ventCooling.name", new Object[] { Integer.valueOf(predict.coolingPerTick) }), 5, 150, 4210752);
/* 135 */         this.field_146289_q.func_78276_b(StatCollector.func_74837_a("container.reactorplannerInfo.reactorCooling.name", new Object[] { Integer.valueOf(predict.reactorCoolingPerTick) }), 5, 160, 4210752);
/* 136 */         this.field_146289_q.func_78276_b(StatCollector.func_74837_a("container.reactorplannerInfo." + (cooling ? "excessCooling" : "excessHeat") + ".name", new Object[] { Integer.valueOf(excess) }), 5, 170, 4210752);
/*     */       }
/* 138 */       else if (this.planner.selectedView == 2)
/*     */       {
/* 140 */         GL11.glPushMatrix();
/* 141 */         GL11.glTranslatef(2.0F, 32.0F, 0.0F);
/* 142 */         GL11.glScalef(0.75F, 0.75F, 0.0F);
/* 143 */         this.field_146289_q.func_78276_b(StatCollector.func_74837_a("container.reactorplannerInfo.producedSteam.name", new Object[] { format.format(logic.totalProducedSteam) }), 5, 130, 4210752);
/* 144 */         this.field_146289_q.func_78276_b(StatCollector.func_74837_a("container.reactorplannerInfo.usedWater.name", new Object[] { format.format(logic.totalUsedWater) }), 5, 140, 4210752);
/* 145 */         this.field_146289_q.func_78276_b(StatCollector.func_74837_a("container.reactorplannerInfo.reactorHeat.name", new Object[] { format.format(logic.currentHeat) }), 5, 150, 4210752);
/* 146 */         this.field_146289_q.func_78276_b(StatCollector.func_74837_a("container.reactorplannerInfo.meltingHeat.name", new Object[] { format.format(logic.maxHeat) }), 5, 160, 4210752);
/* 147 */         this.field_146289_q.func_78276_b(StatCollector.func_74837_a("container.reactorplannerInfo.heatEffect.name", new Object[] { format.format((logic.explosionEffect * 100.0F)) + "%" }), 5, 170, 4210752);
/* 148 */         this.field_146289_q.func_78276_b(StatCollector.func_74837_a("container.reactorplannerInfo.SimulationTime.name", new Object[] { Integer.valueOf(logic.ticksDone) }), 5, 180, 4210752);
/* 149 */         this.field_146289_q.func_78276_b(StatCollector.func_74837_a("container.reactorplannerInfo.activeTime.name", new Object[] { Integer.valueOf(logic.ticksLeft), Integer.valueOf(logic.maxTick) }), 5, 190, 4210752);
/* 150 */         GL11.glPopMatrix();
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 155 */       TickingReactorLogic.ReactorPrediction predict = (TickingReactorLogic.ReactorPrediction)this.planner.getPrediction();
/* 156 */       TickingReactorLogic logic = (TickingReactorLogic)base;
/* 157 */       if (this.planner.selectedView == 0) {
/*     */         
/* 159 */         DecimalFormat floatFormat = new DecimalFormat("#.#");
/* 160 */         this.field_146289_q.func_78276_b(StatCollector.func_74837_a("container.reactorplannerInfo.expectedEU.name", new Object[] { format.format(predict.energyPerTick) }), 5, 130, 4210752);
/* 161 */         this.field_146289_q.func_78276_b(StatCollector.func_74837_a("container.reactorplannerInfo.totalEU.name", new Object[] { format.format(predict.totalEnergyProduced) }), 5, 139, 4210752);
/* 162 */         this.field_146289_q.func_78276_b(StatCollector.func_74837_a("container.reactorplannerInfo.totalHeat.name", new Object[] { format.format(predict.totalHeatProduced) }), 5, 148, 4210752);
/* 163 */         this.field_146289_q.func_78276_b(StatCollector.func_74837_a("container.reactorplannerInfo.explosionEffect.name", new Object[] { Float.valueOf(predict.totalExplosionPower) }), 5, 157, 4210752);
/* 164 */         this.field_146289_q.func_78276_b(StatCollector.func_74837_a("container.reactorplannerInfo.efficeny.name", new Object[] { floatFormat.format(predict.efficency), floatFormat.format(predict.totalEfficency) }), 5, 166, 4210752);
/* 165 */         this.field_146289_q.func_78276_b(StatCollector.func_74837_a("container.reactorplannerInfo.isBreeder.name", new Object[] { Boolean.valueOf(predict.breeder) }), 5, 175, 4210752);
/*     */       }
/* 167 */       else if (this.planner.selectedView == 1) {
/*     */         
/* 169 */         int excess = predict.heatPerTick - predict.coolingPerTick;
/* 170 */         boolean cooling = (excess < 0);
/* 171 */         if (cooling)
/*     */         {
/* 173 */           excess = -excess;
/*     */         }
/* 175 */         this.field_146289_q.func_78276_b(StatCollector.func_74837_a("container.reactorplannerInfo.heatCreated.name", new Object[] { Integer.valueOf(predict.heatPerTick) }), 5, 130, 4210752);
/* 176 */         this.field_146289_q.func_78276_b(StatCollector.func_74837_a("container.reactorplannerInfo.heatPackHeat.name", new Object[] { Integer.valueOf(predict.heatPackHeatPerTick) }), 5, 140, 4210752);
/* 177 */         this.field_146289_q.func_78276_b(StatCollector.func_74837_a("container.reactorplannerInfo.ventCooling.name", new Object[] { Integer.valueOf(predict.coolingPerTick) }), 5, 150, 4210752);
/* 178 */         this.field_146289_q.func_78276_b(StatCollector.func_74837_a("container.reactorplannerInfo.reactorCooling.name", new Object[] { Integer.valueOf(predict.reactorCoolingPerTick) }), 5, 160, 4210752);
/* 179 */         this.field_146289_q.func_78276_b(StatCollector.func_74837_a("container.reactorplannerInfo." + (cooling ? "excessCooling" : "excessHeat") + ".name", new Object[] { Integer.valueOf(excess) }), 5, 170, 4210752);
/*     */       }
/* 181 */       else if (this.planner.selectedView == 2) {
/*     */         
/* 183 */         GL11.glPushMatrix();
/* 184 */         GL11.glTranslatef(2.0F, 32.0F, 0.0F);
/* 185 */         GL11.glScalef(0.75F, 0.75F, 0.0F);
/* 186 */         this.field_146289_q.func_78276_b(StatCollector.func_74837_a("container.reactorplannerInfo.activeEU.name", new Object[] { format.format(logic.getReactorEUEnergyOutput()) }), 5, 130, 4210752);
/* 187 */         this.field_146289_q.func_78276_b(StatCollector.func_74837_a("container.reactorplannerInfo.producedEU.name", new Object[] { format.format(logic.totalProducedEnergy) }), 5, 140, 4210752);
/* 188 */         this.field_146289_q.func_78276_b(StatCollector.func_74837_a("container.reactorplannerInfo.reactorHeat.name", new Object[] { format.format(logic.currentHeat) }), 5, 150, 4210752);
/* 189 */         this.field_146289_q.func_78276_b(StatCollector.func_74837_a("container.reactorplannerInfo.meltingHeat.name", new Object[] { format.format(logic.maxHeat) }), 5, 160, 4210752);
/* 190 */         this.field_146289_q.func_78276_b(StatCollector.func_74837_a("container.reactorplannerInfo.heatEffect.name", new Object[] { format.format((logic.explosionEffect * 100.0F)) + "%" }), 5, 170, 4210752);
/* 191 */         this.field_146289_q.func_78276_b(StatCollector.func_74837_a("container.reactorplannerInfo.SimulationTime.name", new Object[] { Integer.valueOf(logic.ticksDone) }), 5, 180, 4210752);
/* 192 */         this.field_146289_q.func_78276_b(StatCollector.func_74837_a("container.reactorplannerInfo.activeTime.name", new Object[] { Integer.valueOf(logic.ticksLeft), Integer.valueOf(logic.maxTick) }), 5, 190, 4210752); GL11.glPopMatrix();
/*     */       } 
/*     */     } 
/* 195 */     if (this.planner.selectedView == 3) {
/*     */       
/* 197 */       Slot slot = getSlotUnderMouse();
/* 198 */       if (slot != null) {
/*     */         
/* 200 */         PlannerRegistry.ComponentStat stats = PlannerRegistry.getStatsFromItem(slot.func_75211_c());
/* 201 */         if (stats != null)
/*     */         {
/* 203 */           GL11.glPushMatrix();
/* 204 */           GL11.glTranslatef(2.0F, 32.0F, 0.0F);
/* 205 */           GL11.glScalef(0.75F, 0.75F, 0.0F);
/* 206 */           int x = 0;
/* 207 */           int y = 0;
/* 208 */           Set<IReactorPlannerComponent.ReactorComponentStat> list = stats.getKeys();
/* 209 */           for (IReactorPlannerComponent.ReactorComponentStat comp : list) {
/*     */             
/* 211 */             this.field_146289_q.func_78276_b(stats.getComponentText(comp), 5, 130 + 10 * y, 4210752);
/* 212 */             y++;
/*     */           } 
/* 214 */           GL11.glPopMatrix();
/*     */         }
/*     */         else
/*     */         {
/* 218 */           this.field_146289_q.func_78276_b(StatCollector.func_74838_a("container.reactorplannerInfo.noComponentSelected.name"), 5, 150, 4210752);
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 223 */         this.field_146289_q.func_78276_b(StatCollector.func_74838_a("container.reactorplannerInfo.noComponentSelected.name"), 5, 150, 4210752);
/*     */       }
/*     */     
/* 226 */     } else if (this.planner.selectedView == 4) {
/*     */       
/* 228 */       this.field.func_146195_b(true);
/* 229 */       this.field.func_146194_f();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_146976_a(float partialTicks, int mouseX, int mouseY) {
/* 236 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 237 */     this.field_146297_k.field_71446_o.func_110577_a(texture);
/* 238 */     func_73729_b(this.field_147003_i, this.field_147009_r, 0, 0, this.field_146999_f, this.field_147000_g);
/* 239 */     int remove = this.planner.reactorSize * 9; int i;
/* 240 */     for (i = 0; i < 3 + this.planner.reactorSize; i++)
/*     */     {
/* 242 */       drawRow(60 + i * 18 - remove, 20, 6);
/*     */     }
/* 244 */     drawTopTab(20, -28, Ic2Items.nuclearReactor.func_77946_l(), !this.planner.isSteamReactor);
/* 245 */     drawTopTab(50, -28, Ic2Items.steamReactor.func_77946_l(), this.planner.isSteamReactor);
/* 246 */     func_73729_b(this.field_147003_i + 200, this.field_147009_r, 74, 212, 40, 38);
/* 247 */     func_73729_b(this.field_147003_i + 240, this.field_147009_r, 79, 212, 43, 38);
/* 248 */     func_73729_b(this.field_147003_i + 208, this.field_147009_r + 198, 74, 241, 40, 14);
/* 249 */     func_73729_b(this.field_147003_i + 240, this.field_147009_r + 198, 79, 241, 48, 14);
/* 250 */     func_73729_b(this.field_147003_i + 200, this.field_147009_r + 170, 74, 215, 40, 36);
/* 251 */     func_73729_b(this.field_147003_i + 240, this.field_147009_r + 170, 79, 215, 43, 36);
/* 252 */     for (i = 1; i < 5; i++) {
/*     */       
/* 254 */       int offset = i * 36;
/* 255 */       func_73729_b(this.field_147003_i + 200, this.field_147009_r + offset, 74, 215, 40, 36);
/* 256 */       func_73729_b(this.field_147003_i + 240, this.field_147009_r + offset, 79, 215, 43, 36);
/*     */     } 
/* 258 */     for (i = 0; i < 3; i++)
/*     */     {
/* 260 */       drawRow(215 + i * 18, 17, 5);
/*     */     }
/* 262 */     int row = this.planner.selectedSlot / 3;
/* 263 */     int slot = this.planner.selectedSlot % 3;
/* 264 */     func_73729_b(this.field_147003_i + 215 + slot * 18, this.field_147009_r + 17 + 18 * row, 0, 230, 18, 18);
/* 265 */     drawSideTab(-28, 100, new ItemStack(Items.field_151061_bv), (this.planner.selectedView == 0));
/* 266 */     drawSideTab(-28, 70, Ic2Items.nuclearReactor.func_77946_l(), (this.planner.selectedView == 1));
/* 267 */     drawSideTab(-28, 40, new ItemStack(Blocks.field_150460_al), (this.planner.selectedView == 2));
/* 268 */     drawSideTab(-28, 130, new ItemStack(Items.field_151122_aG), (this.planner.selectedView == 3));
/* 269 */     drawSideTab(-28, 160, new ItemStack(Items.field_151121_aF), (this.planner.selectedView == 4));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_73863_a(int mouseX, int mouseY, float partialTicks) {
/* 275 */     updateButtons();
/* 276 */     super.func_73863_a(mouseX, mouseY, partialTicks);
/* 277 */     for (int i = 0; i < this.field_146292_n.size(); i++) {
/*     */       
/* 279 */       GuiButton button = this.field_146292_n.get(i);
/* 280 */       if (button instanceof IDrawButton)
/*     */       {
/* 282 */         ((IDrawButton)button).onPostDraw(this.field_146297_k, mouseX, mouseY);
/*     */       }
/*     */     } 
/* 285 */     int x = mouseX - this.field_147003_i;
/* 286 */     int y = mouseY - this.field_147009_r;
/* 287 */     if (x >= 20 && x <= 48 && y >= -28 && y <= 0) {
/*     */ 
/*     */       
/* 290 */       drawHoveringText(Arrays.asList(new String[] { StatCollector.func_74838_a("container.reactorplannerButton.reactor.name") }, ), mouseX, mouseY, this.field_146289_q);
/*     */     }
/* 292 */     else if (x >= 50 && x <= 78 && y >= -28 && y <= 0) {
/*     */ 
/*     */       
/* 295 */       drawHoveringText(Arrays.asList(new String[] { StatCollector.func_74838_a("container.reactorplannerButton.steamReactor.name") }, ), mouseX, mouseY, this.field_146289_q);
/*     */     }
/* 297 */     else if (x >= -28 && x <= 0 && y >= 160 && y <= 188) {
/*     */ 
/*     */       
/* 300 */       drawHoveringText(Arrays.asList(new String[] { StatCollector.func_74838_a("container.reactorplannerButton.setupName.name") }, ), mouseX, mouseY, this.field_146289_q);
/*     */     }
/* 302 */     else if (x >= -28 && x <= 0 && y >= 130 && y <= 158) {
/*     */ 
/*     */       
/* 305 */       drawHoveringText(Arrays.asList(new String[] { StatCollector.func_74838_a("container.reactorplannerButton.itemInfo.name") }, ), mouseX, mouseY, this.field_146289_q);
/*     */     }
/* 307 */     else if (x >= -28 && x <= 0 && y >= 90 && y <= 128) {
/*     */ 
/*     */       
/* 310 */       drawHoveringText(Arrays.asList(new String[] { StatCollector.func_74838_a("container.reactorplannerButton.predictions.name") }, ), mouseX, mouseY, this.field_146289_q);
/*     */     }
/* 312 */     else if (x >= -28 && x <= 0 && y >= 70 && y <= 98) {
/*     */ 
/*     */       
/* 315 */       drawHoveringText(Arrays.asList(new String[] { StatCollector.func_74838_a("container.reactorplannerButton.reactorSetup.name") }, ), mouseX, mouseY, this.field_146289_q);
/*     */     }
/* 317 */     else if (x >= -28 && x <= 0 && y >= 40 && y <= 68) {
/*     */ 
/*     */       
/* 320 */       drawHoveringText(Arrays.asList(new String[] { StatCollector.func_74838_a("container.reactorplannerButton.activeReactorInfo.name") }, ), mouseX, mouseY, this.field_146289_q);
/*     */     } 
/* 322 */     Slot hoverSlot = getSlotUnderMouse();
/* 323 */     if (hoverSlot instanceof ContainerReactorPlanner.SlotComponent) {
/*     */       
/* 325 */       int amount = Mouse.getDWheel();
/* 326 */       if (amount != 0.0D) {
/*     */         
/* 328 */         PayloadPacket packet = new PayloadPacket((TileEntity)this.planner, 0, 2, 0);
/* 329 */         packet.addNumber(0, 1);
/* 330 */         packet.addNumber(1, -(amount /= 120));
/* 331 */         this.planner.getNetwork().sendCustomPacket((IC2Packet)packet);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_73866_w_() {
/* 339 */     super.func_73866_w_();
/* 340 */     this.field_146292_n.clear();
/*     */     
/* 342 */     addButton(new GuiButton(0, this.field_147003_i + 207, this.field_147009_r + 5, 10, 10, "-"));
/* 343 */     addButton(new GuiButton(1, this.field_147003_i + 265, this.field_147009_r + 5, 10, 10, "+"));
/* 344 */     addButton((GuiButton)(new GuiCustomButton(2, this.field_147003_i + 220, this.field_147009_r + 5, 42, 10, StatCollector.func_74838_a("container.reactorplannerButton.sortbutton.name"))).addHoverText(StatCollector.func_74838_a(sortingLang[this.planner.components.type + 1])));
/*     */     
/* 346 */     addButton(new GuiButton(3, this.field_147003_i + 5, this.field_147009_r + 5, 10, 10, "-"));
/* 347 */     addButton(new GuiButton(4, this.field_147003_i + 157, this.field_147009_r + 5, 10, 10, "+"));
/*     */     
/* 349 */     addButton(new GuiButton(5, this.field_147003_i + 215, this.field_147009_r + 110, 10, 10, "-"));
/* 350 */     addButton(new GuiButton(6, this.field_147003_i + 260, this.field_147009_r + 110, 10, 10, "+"));
/*     */     
/* 352 */     addButton(new GuiButton(7, this.field_147003_i + 170, this.field_147009_r + 20, 43, 10, StatCollector.func_74838_a("container.reactorplannerButton.start.name")));
/* 353 */     addButton(new GuiButton(8, this.field_147003_i + 170, this.field_147009_r + 44, 43, 10, StatCollector.func_74838_a("container.reactorplannerButton.stop.name")));
/*     */ 
/*     */     
/* 356 */     addButton(new GuiButton(9, this.field_147003_i + 170, this.field_147009_r + 58, 43, 10, StatCollector.func_74838_a("container.reactorplannerButton.compile.name")));
/* 357 */     addButton(new GuiButton(10, this.field_147003_i + 170, this.field_147009_r + 70, 43, 10, StatCollector.func_74838_a("container.reactorplannerButton.reset.name")));
/*     */ 
/*     */     
/* 360 */     addButton(new GuiButton(11, this.field_147003_i + 170, this.field_147009_r + 84, 43, 10, StatCollector.func_74838_a("container.reactorplannerButton.backup.name")));
/* 361 */     addButton(new GuiButton(12, this.field_147003_i + 170, this.field_147009_r + 96, 43, 10, StatCollector.func_74838_a("container.reactorplannerButton.restore.name")));
/*     */     
/* 363 */     addButton(new GuiButton(13, this.field_147003_i + 170, this.field_147009_r + 110, 43, 10, StatCollector.func_74838_a("container.reactorplannerButton.clear.name")));
/*     */ 
/*     */     
/* 366 */     addButton(new GuiButton(14, this.field_147003_i + 205, this.field_147009_r + 135, 10, 10, "-"));
/* 367 */     addButton(new GuiButton(15, this.field_147003_i + 269, this.field_147009_r + 135, 10, 10, "+"));
/*     */     
/* 369 */     addButton(new GuiButton(16, this.field_147003_i + 170, this.field_147009_r + 32, 43, 10, StatCollector.func_74838_a("container.reactorplannerButton." + ((this.planner.getReactorLogic()).producing ? "pause" : "run") + ".name")));
/*     */ 
/*     */     
/* 372 */     addButton(new GuiButton(17, this.field_147003_i + 205, this.field_147009_r + 159, 10, 10, "-"));
/* 373 */     addButton(new GuiButton(18, this.field_147003_i + 269, this.field_147009_r + 159, 10, 10, "+"));
/*     */     
/* 375 */     addButton(new GuiButton(19, this.field_147003_i + 205, this.field_147009_r + 183, 10, 10, "-"));
/* 376 */     addButton(new GuiButton(20, this.field_147003_i + 269, this.field_147009_r + 183, 10, 10, "+"));
/*     */ 
/*     */     
/* 379 */     addButton(new GuiButton(21, this.field_147003_i + 175, this.field_147009_r + 197, 40, 10, StatCollector.func_74838_a("container.reactorplannerButton.import.name")));
/* 380 */     addButton(new GuiButton(22, this.field_147003_i + 220, this.field_147009_r + 197, 40, 10, StatCollector.func_74838_a("container.reactorplannerButton.export.name")));
/*     */     
/* 382 */     this.field = new GuiTextField(this.field_146289_q, 10, 145, 158, 20);
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateButtons() {
/* 387 */     ReactorLogicBase logic = this.planner.getReactorLogic();
/* 388 */     TileEntityReactorPlanner.UserSettings settings = this.planner.getUserSettings();
/* 389 */     (getButton(0)).field_146124_l = this.planner.components.canDecrease();
/* 390 */     (getButton(1)).field_146124_l = this.planner.components.canIncrease();
/* 391 */     ((GuiCustomButton)getButton(2)).clearText().addHoverText(StatCollector.func_74838_a(sortingLang[this.planner.components.type + 1]));
/* 392 */     (getButton(3)).field_146124_l = (this.planner.reactorSize > 0);
/* 393 */     (getButton(4)).field_146124_l = (this.planner.reactorSize < 6);
/* 394 */     (getButton(5)).field_146124_l = (this.planner.stackSize > 1);
/* 395 */     (getButton(6)).field_146124_l = (this.planner.stackSize < 64);
/* 396 */     (getButton(7)).field_146124_l = (logic.isValid() && !this.planner.getActive());
/* 397 */     (getButton(8)).field_146124_l = this.planner.getActive();
/* 398 */     (getButton(9)).field_146124_l = !logic.isValid();
/* 399 */     (getButton(10)).field_146124_l = (logic.isValid() && logic.ticksDone > 0);
/* 400 */     (getButton(11)).field_146124_l = logic.isValid();
/* 401 */     (getButton(12)).field_146124_l = this.planner.backup.hasBackup;
/* 402 */     GuiButton button = getButton(16);
/* 403 */     button.field_146126_j = StatCollector.func_74838_a("container.reactorplannerButton." + ((this.planner.getReactorLogic()).producing ? "pause" : "run") + ".name");
/* 404 */     button.field_146124_l = logic.isValid();
/* 405 */     (getButton(14)).field_146124_l = (settings.startingHeat > 0);
/* 406 */     (getButton(17)).field_146124_l = (settings.maxTicks > 0);
/* 407 */     (getButton(19)).field_146124_l = (settings.ticksPerTick > 1);
/* 408 */     (getButton(22)).field_146124_l = logic.isValid();
/* 409 */     this.field.func_146180_a(this.planner.setupName);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_146284_a(GuiButton button) {
/* 415 */     int id = button.field_146127_k;
/* 416 */     if (id == 0 || id == 1) {
/*     */       
/* 418 */       PayloadPacket packet = new PayloadPacket((TileEntity)this.planner, 0, 2, 0);
/* 419 */       packet.addNumber(0, 1);
/* 420 */       packet.addNumber(1, (id == 0) ? -1 : id);
/* 421 */       this.planner.getNetwork().sendCustomPacket((IC2Packet)packet);
/*     */     } 
/* 423 */     if (id == 2) {
/*     */       
/* 425 */       PayloadPacket packet = new PayloadPacket((TileEntity)this.planner, 0, 2, 0);
/* 426 */       packet.addNumber(0, 2);
/* 427 */       packet.addNumber(1, 0);
/* 428 */       this.planner.getNetwork().sendCustomPacket((IC2Packet)packet);
/*     */     } 
/* 430 */     if (id == 3 || id == 4) {
/*     */       
/* 432 */       this.planner.reactorSize += (id == 3) ? -1 : 1;
/* 433 */       if (this.planner.reactorSize < 0) this.planner.reactorSize = 0; 
/* 434 */       if (this.planner.reactorSize > 6) this.planner.reactorSize = 6; 
/* 435 */       this.planner.getReactorLogic().onSizeUpdate();
/* 436 */       ContainerReactorPlanner cont = getContainer();
/* 437 */       if (cont != null)
/*     */       {
/* 439 */         cont.reset(this.field_146297_k.field_71439_g.field_71071_by);
/*     */       }
/* 441 */       PayloadPacket packet = new PayloadPacket((TileEntity)this.planner, 0, 2, 0);
/* 442 */       packet.addNumber(0, 3);
/* 443 */       packet.addNumber(1, (id == 3) ? -1 : 1);
/* 444 */       this.planner.getNetwork().sendCustomPacket((IC2Packet)packet);
/*     */     } 
/* 446 */     if (id == 5 || id == 6) {
/*     */       
/* 448 */       int value = (id == 5) ? -1 : 1;
/* 449 */       if (func_146272_n()) value *= 10; 
/* 450 */       PayloadPacket packet = new PayloadPacket((TileEntity)this.planner, 0, 2, 0);
/* 451 */       packet.addNumber(0, 4);
/* 452 */       packet.addNumber(1, value);
/* 453 */       this.planner.getNetwork().sendCustomPacket((IC2Packet)packet);
/*     */     } 
/* 455 */     if (id == 7 || id == 8) {
/*     */       
/* 457 */       PayloadPacket packet = new PayloadPacket((TileEntity)this.planner, 0, 2, 0);
/* 458 */       packet.addNumber(0, 5);
/* 459 */       packet.addNumber(1, (id == 7) ? 0 : 1);
/* 460 */       this.planner.getNetwork().sendCustomPacket((IC2Packet)packet);
/*     */     } 
/* 462 */     if (id == 9 || id == 10) {
/*     */       
/* 464 */       PayloadPacket packet = new PayloadPacket((TileEntity)this.planner, 0, 2, 0);
/* 465 */       packet.addNumber(0, 6);
/* 466 */       packet.addNumber(1, (id == 9) ? 0 : 1);
/* 467 */       this.planner.getNetwork().sendCustomPacket((IC2Packet)packet);
/*     */     } 
/* 469 */     if (id == 11) {
/*     */       
/* 471 */       PayloadPacket packet = new PayloadPacket((TileEntity)this.planner, 0, 2, 0);
/* 472 */       packet.addNumber(0, 7);
/* 473 */       packet.addNumber(1, 0);
/* 474 */       this.planner.getNetwork().sendCustomPacket((IC2Packet)packet);
/*     */     } 
/* 476 */     if (id == 12) {
/*     */       
/* 478 */       TileEntityReactorPlanner.ReactorBackup backup = this.planner.backup;
/* 479 */       this.planner.getReactorLogic().clear();
/* 480 */       this.planner.getPrediction().clear();
/* 481 */       this.planner.isSteamReactor = backup.isSteam;
/* 482 */       this.planner.reactorSize = backup.reactorSize;
/* 483 */       ContainerReactorPlanner cont = getContainer();
/* 484 */       if (cont != null)
/*     */       {
/* 486 */         cont.reset(this.field_146297_k.field_71439_g.field_71071_by);
/*     */       }
/* 488 */       PayloadPacket packet = new PayloadPacket((TileEntity)this.planner, 0, 2, 0);
/* 489 */       packet.addNumber(0, 7);
/* 490 */       packet.addNumber(1, 1);
/* 491 */       this.planner.getNetwork().sendCustomPacket((IC2Packet)packet);
/*     */     } 
/* 493 */     if (id == 13) {
/*     */       
/* 495 */       PayloadPacket packet = new PayloadPacket((TileEntity)this.planner, 0, 2, 0);
/* 496 */       packet.addNumber(0, 8);
/* 497 */       packet.addNumber(1, func_146272_n() ? 1 : 0);
/* 498 */       this.planner.getNetwork().sendCustomPacket((IC2Packet)packet);
/* 499 */       if (!func_146272_n())
/*     */       {
/* 501 */         this.planner.getReactorLogic().clear();
/*     */       }
/*     */     } 
/* 504 */     if (id == 14 || id == 15) {
/*     */       
/* 506 */       int value = 1;
/* 507 */       if (func_146271_m()) value *= 1000; 
/* 508 */       if (func_146272_n()) value *= 100; 
/* 509 */       if (isAltKeyDown()) value *= 10; 
/* 510 */       if (id == 14) value = -value; 
/* 511 */       PayloadPacket packet = new PayloadPacket((TileEntity)this.planner, 0, 2, 0);
/* 512 */       packet.addNumber(0, 10);
/* 513 */       packet.addNumber(1, value);
/* 514 */       this.planner.getNetwork().sendCustomPacket((IC2Packet)packet);
/*     */     } 
/* 516 */     if (id == 16) {
/*     */       
/* 518 */       PayloadPacket packet = new PayloadPacket((TileEntity)this.planner, 0, 2, 0);
/* 519 */       packet.addNumber(0, 11);
/* 520 */       packet.addNumber(1, 0);
/* 521 */       this.planner.getNetwork().sendCustomPacket((IC2Packet)packet);
/*     */     } 
/* 523 */     if (id == 17 || id == 18) {
/*     */       
/* 525 */       int value = 1;
/* 526 */       if (func_146271_m()) value *= 1000; 
/* 527 */       if (func_146272_n()) value *= 100; 
/* 528 */       if (isAltKeyDown()) value *= 10; 
/* 529 */       if (id == 17) value = -value; 
/* 530 */       PayloadPacket packet = new PayloadPacket((TileEntity)this.planner, 0, 2, 0);
/* 531 */       packet.addNumber(0, 12);
/* 532 */       packet.addNumber(1, value);
/* 533 */       this.planner.getNetwork().sendCustomPacket((IC2Packet)packet);
/*     */     } 
/* 535 */     if (id == 19 || id == 20) {
/*     */       
/* 537 */       int value = 1;
/* 538 */       if (func_146271_m()) value = 1000; 
/* 539 */       if (func_146272_n()) value = 100; 
/* 540 */       if (isAltKeyDown()) value = 10; 
/* 541 */       if (id == 19) value = -value; 
/* 542 */       PayloadPacket packet = new PayloadPacket((TileEntity)this.planner, 0, 2, 0);
/* 543 */       packet.addNumber(0, 13);
/* 544 */       packet.addNumber(1, value);
/* 545 */       this.planner.getNetwork().sendCustomPacket((IC2Packet)packet);
/*     */     } 
/* 547 */     if (id == 21) {
/*     */       
/* 549 */       Tuple<String, NBTTagCompound> encoded = getSafeDecodedSetup();
/* 550 */       if (encoded == null) {
/*     */         
/* 552 */         IC2.platform.messagePlayer((EntityPlayer)this.field_146297_k.field_71439_g, StatCollector.func_74838_a("container.reactorplannerAction.decoderCreatingFailed.name"));
/*     */         return;
/*     */       } 
/* 555 */       PayloadPacket payload = new PayloadPacket((TileEntity)this.planner, 2, 0, 0);
/* 556 */       payload.addString(0, (String)encoded.getFirst());
/* 557 */       payload.addString(1, ((NBTTagCompound)encoded.getSecond()).toString());
/* 558 */       this.planner.getNetwork().sendCustomPacket((IC2Packet)payload);
/*     */     } 
/* 560 */     if (id == 22) {
/*     */       
/* 562 */       String defaults = EncoderRegistry.instance.getDefaultEncoderID();
/* 563 */       if (func_146272_n()) {
/*     */         
/* 565 */         Tuple<String[], String[]> cases = EncoderRegistry.instance.getChoiceList();
/* 566 */         int answer = JOptionPane.showOptionDialog(null, "Choose a Encoder", "ReactorPlanner Enocder Choice", 1, 1, null, (Object[])cases.getSecond(), EncoderRegistry.instance.getDefaultEncoder().getName());
/* 567 */         if (answer != -1)
/*     */         {
/* 569 */           defaults = ((String[])cases.getFirst())[answer];
/*     */         }
/*     */       } 
/* 572 */       PayloadPacket payload = new PayloadPacket((TileEntity)this.planner, 1, 0, 0);
/* 573 */       payload.addString(0, defaults);
/* 574 */       this.planner.getNetwork().sendCustomPacket((IC2Packet)payload);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
/* 581 */     int x = mouseX - this.field_147003_i;
/* 582 */     int y = mouseY - this.field_147009_r;
/* 583 */     if (x >= 20 && x <= 48 && y >= -28 && y <= 0) {
/*     */       
/* 585 */       this.field_146297_k.func_147118_V().func_147682_a((ISound)PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
/* 586 */       if (this.planner.isSteamReactor) {
/*     */         
/* 588 */         PayloadPacket packet = new PayloadPacket((TileEntity)this.planner, 0, 2, 0);
/* 589 */         packet.addNumber(0, 0);
/* 590 */         packet.addNumber(1, 0);
/* 591 */         this.planner.getNetwork().sendCustomPacket((IC2Packet)packet);
/*     */         
/*     */         return;
/*     */       } 
/* 595 */     } else if (x >= 50 && x <= 78 && y >= -28 && y <= 0) {
/*     */       
/* 597 */       this.field_146297_k.func_147118_V().func_147682_a((ISound)PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
/* 598 */       if (!this.planner.isSteamReactor) {
/*     */         
/* 600 */         PayloadPacket packet = new PayloadPacket((TileEntity)this.planner, 0, 2, 0);
/* 601 */         packet.addNumber(0, 0);
/* 602 */         packet.addNumber(1, 1);
/* 603 */         this.planner.getNetwork().sendCustomPacket((IC2Packet)packet);
/*     */         
/*     */         return;
/*     */       } 
/* 607 */     } else if (x >= -28 && x <= 0 && y >= 160 && y <= 188) {
/*     */       
/* 609 */       this.field_146297_k.func_147118_V().func_147682_a((ISound)PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
/* 610 */       if (this.planner.selectedView != 4) {
/*     */         
/* 612 */         PayloadPacket packet = new PayloadPacket((TileEntity)this.planner, 0, 2, 0);
/* 613 */         packet.addNumber(0, 9);
/* 614 */         packet.addNumber(1, 4);
/* 615 */         this.planner.getNetwork().sendCustomPacket((IC2Packet)packet);
/*     */         
/*     */         return;
/*     */       } 
/* 619 */     } else if (x >= -28 && x <= 0 && y >= 130 && y <= 158) {
/*     */       
/* 621 */       this.field_146297_k.func_147118_V().func_147682_a((ISound)PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
/* 622 */       if (this.planner.selectedView != 3) {
/*     */         
/* 624 */         PayloadPacket packet = new PayloadPacket((TileEntity)this.planner, 0, 2, 0);
/* 625 */         packet.addNumber(0, 9);
/* 626 */         packet.addNumber(1, 3);
/* 627 */         this.planner.getNetwork().sendCustomPacket((IC2Packet)packet);
/*     */         
/*     */         return;
/*     */       } 
/* 631 */     } else if (x >= -28 && x <= 0 && y >= 100 && y <= 128) {
/*     */       
/* 633 */       this.field_146297_k.func_147118_V().func_147682_a((ISound)PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
/* 634 */       if (this.planner.selectedView != 0) {
/*     */         
/* 636 */         PayloadPacket packet = new PayloadPacket((TileEntity)this.planner, 0, 2, 0);
/* 637 */         packet.addNumber(0, 9);
/* 638 */         packet.addNumber(1, 0);
/* 639 */         this.planner.getNetwork().sendCustomPacket((IC2Packet)packet);
/*     */         
/*     */         return;
/*     */       } 
/* 643 */     } else if (x >= -28 && x <= 0 && y >= 70 && y <= 98) {
/*     */       
/* 645 */       this.field_146297_k.func_147118_V().func_147682_a((ISound)PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
/* 646 */       if (this.planner.selectedView != 1) {
/*     */         
/* 648 */         PayloadPacket packet = new PayloadPacket((TileEntity)this.planner, 0, 2, 0);
/* 649 */         packet.addNumber(0, 9);
/* 650 */         packet.addNumber(1, 1);
/* 651 */         this.planner.getNetwork().sendCustomPacket((IC2Packet)packet);
/*     */         
/*     */         return;
/*     */       } 
/* 655 */     } else if (x >= -28 && x <= 0 && y >= 40 && y <= 68) {
/*     */       
/* 657 */       this.field_146297_k.func_147118_V().func_147682_a((ISound)PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
/* 658 */       if (this.planner.selectedView != 2) {
/*     */         
/* 660 */         PayloadPacket packet = new PayloadPacket((TileEntity)this.planner, 0, 2, 0);
/* 661 */         packet.addNumber(0, 9);
/* 662 */         packet.addNumber(1, 2);
/* 663 */         this.planner.getNetwork().sendCustomPacket((IC2Packet)packet);
/*     */         return;
/*     */       } 
/*     */     } 
/* 667 */     super.func_73864_a(mouseX, mouseY, mouseButton);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_73869_a(char p_73869_1_, int p_73869_2_) {
/* 674 */     if (this.field.func_146206_l() && this.field.func_146176_q()) {
/*     */       
/* 676 */       this.field.func_146201_a(p_73869_1_, p_73869_2_);
/* 677 */       this.planner.setupName = this.field.func_146179_b();
/* 678 */       PayloadPacket packet = new PayloadPacket((TileEntity)this.planner, 1, 1, 0);
/* 679 */       packet.addNumber(0, 0);
/* 680 */       packet.addString(0, this.field.func_146179_b());
/* 681 */       this.planner.getNetwork().sendCustomPacket((IC2Packet)packet);
/*     */       return;
/*     */     } 
/* 684 */     super.func_73869_a(p_73869_1_, p_73869_2_);
/*     */   }
/*     */ 
/*     */   
/*     */   private void drawRow(int xOffset, int yOffset, int count) {
/* 689 */     for (int i = 0; i < count; i++)
/*     */     {
/* 691 */       func_73729_b(this.field_147003_i + xOffset, this.field_147009_r + yOffset + 18 * i, 0, 212, 18, 18);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void drawTopTab(int xOffset, int yOffset, ItemStack item, boolean enabled) {
/* 697 */     GL11.glPushMatrix();
/* 698 */     func_73729_b(this.field_147003_i + xOffset, this.field_147009_r + yOffset, enabled ? 46 : 18, 212, 28, enabled ? 31 : 28);
/* 699 */     RenderHelper.func_74518_a();
/* 700 */     this.field_73735_i = 200.0F;
/* 701 */     field_146296_j.field_77023_b = 200.0F;
/* 702 */     field_146296_j.func_82406_b(this.field_146289_q, this.field_146297_k.field_71446_o, item, this.field_147003_i + xOffset + 6, this.field_147009_r + yOffset + 8);
/* 703 */     this.field_73735_i = 0.0F;
/* 704 */     field_146296_j.field_77023_b = 0.0F;
/* 705 */     RenderHelper.func_74518_a();
/* 706 */     GL11.glPopMatrix();
/* 707 */     this.field_146297_k.field_71446_o.func_110577_a(texture);
/*     */   }
/*     */ 
/*     */   
/*     */   private void drawSideTab(int xOffset, int yOffset, ItemStack item, boolean enabled) {
/* 712 */     GL11.glPushMatrix();
/* 713 */     func_73729_b(this.field_147003_i + xOffset, this.field_147009_r + yOffset, enabled ? 122 : 156, 212, enabled ? 31 : 28, 28);
/* 714 */     RenderHelper.func_74518_a();
/* 715 */     this.field_73735_i = 200.0F;
/* 716 */     field_146296_j.field_77023_b = 200.0F;
/* 717 */     field_146296_j.func_82406_b(this.field_146289_q, this.field_146297_k.field_71446_o, item, this.field_147003_i + xOffset + 8, this.field_147009_r + yOffset + 6);
/* 718 */     this.field_73735_i = 0.0F;
/* 719 */     field_146296_j.field_77023_b = 0.0F;
/* 720 */     RenderHelper.func_74518_a();
/* 721 */     GL11.glPopMatrix();
/* 722 */     this.field_146297_k.field_71446_o.func_110577_a(texture);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addButton(GuiButton button) {
/* 727 */     this.buttonMap.put(Integer.valueOf(button.field_146127_k), button);
/* 728 */     this.field_146292_n.add(button);
/*     */   }
/*     */ 
/*     */   
/*     */   public GuiButton getButton(int id) {
/* 733 */     return this.buttonMap.get(Integer.valueOf(id));
/*     */   }
/*     */ 
/*     */   
/*     */   private ContainerReactorPlanner getContainer() {
/* 738 */     if (this.field_147002_h instanceof ContainerReactorPlanner)
/*     */     {
/* 740 */       return (ContainerReactorPlanner)this.field_147002_h;
/*     */     }
/* 742 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple<String, NBTTagCompound> getSafeDecodedSetup() {
/*     */     try {
/* 749 */       return getDecodedSetup();
/*     */     }
/* 751 */     catch (Exception e) {
/*     */       
/* 753 */       e.printStackTrace();
/*     */       
/* 755 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public Tuple<String, NBTTagCompound> getDecodedSetup() throws Exception {
/* 760 */     Clipboard board = Toolkit.getDefaultToolkit().getSystemClipboard();
/* 761 */     if (board.isDataFlavorAvailable(DataFlavor.stringFlavor)) {
/*     */       
/* 763 */       String text = (String)board.getData(DataFlavor.stringFlavor);
/* 764 */       if (Strings.isNullOrEmpty(text))
/*     */       {
/* 766 */         return null;
/*     */       }
/* 768 */       return EncoderRegistry.instance.createDecodedMessage(text);
/*     */     } 
/* 770 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Slot getSlotUnderMouse() {
/*     */     try {
/* 777 */       return (Slot)ReflectionHelper.getPrivateValue(GuiContainer.class, this, new String[] { "theSlot", "field_147006_u" });
/*     */     }
/* 779 */     catch (Exception exception) {
/*     */ 
/*     */       
/* 782 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static boolean isAltKeyDown() {
/* 787 */     return (Keyboard.isKeyDown(56) || Keyboard.isKeyDown(184));
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\machine\gui\GuiReactorPlanner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */