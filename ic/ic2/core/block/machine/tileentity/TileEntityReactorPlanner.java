/*     */ package ic2.core.block.machine.tileentity;
/*     */ 
/*     */ import com.google.common.base.Strings;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import ic2.api.item.IItemReactorPlanStorage;
/*     */ import ic2.api.network.INetworkFieldData;
/*     */ import ic2.api.network.INetworkTileEntityEventListener;
/*     */ import ic2.core.ContainerIC2;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.IHasGui;
/*     */ import ic2.core.block.machine.container.ContainerReactorPlanner;
/*     */ import ic2.core.block.machine.logic.ReactorLogicBase;
/*     */ import ic2.core.block.machine.logic.ReactorPlannerInv;
/*     */ import ic2.core.block.machine.logic.TickingReactorLogic;
/*     */ import ic2.core.block.machine.logic.TickingSteamReactorLogic;
/*     */ import ic2.core.block.machine.logic.encoder.EncoderRegistry;
/*     */ import ic2.core.block.machine.logic.encoder.IEncoder;
/*     */ import ic2.core.network.internal.IPayloadReceiver;
/*     */ import ic2.core.network.internal.PayloadPacket;
/*     */ import ic2.core.network.packets.IC2Packet;
/*     */ import ic2.core.network.packets.server.ReactorPlannerEncoderPacket;
/*     */ import ic2.core.util.Tuple;
/*     */ import java.io.DataInput;
/*     */ import java.io.DataOutput;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.JsonToNBT;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.StatCollector;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TileEntityReactorPlanner
/*     */   extends TileEntityElecMachine
/*     */   implements IHasGui, INetworkTileEntityEventListener, IPayloadReceiver
/*     */ {
/*  44 */   public ReactorPlannerInv components = new ReactorPlannerInv(this);
/*  45 */   public UserSettings settings = new UserSettings();
/*  46 */   public ReactorBackup backup = new ReactorBackup();
/*     */ 
/*     */   
/*  49 */   public ReactorLogicBase reactor = (ReactorLogicBase)new TickingReactorLogic(this);
/*  50 */   public ReactorLogicBase.IReactorPrediction reactorPrediction = (ReactorLogicBase.IReactorPrediction)new TickingReactorLogic.ReactorPrediction();
/*     */ 
/*     */   
/*  53 */   public ReactorLogicBase steamReactor = (ReactorLogicBase)new TickingSteamReactorLogic(this);
/*  54 */   public ReactorLogicBase.IReactorPrediction steamReactorPrediction = (ReactorLogicBase.IReactorPrediction)new TickingSteamReactorLogic.SteamReactorPrediction();
/*     */ 
/*     */   
/*  57 */   public int selectedSlot = 0;
/*  58 */   public int stackSize = 1;
/*  59 */   public int selectedView = 2;
/*  60 */   public String setupName = "";
/*     */ 
/*     */   
/*  63 */   public int reactorSize = 6;
/*     */   
/*     */   public boolean isSteamReactor = false;
/*     */   
/*     */   public TileEntityReactorPlanner() {
/*  68 */     super(0, -1, 100000, 128);
/*  69 */     addGuiFields(new String[] { "isSteamReactor", "reactorSize", "setupName", "selectedView", "stackSize", "selectedSlot", "steamReactorPrediction", "steamReactor", "reactorPrediction", "reactor", "backup", "settings", "components" });
/*     */   }
/*     */ 
/*     */   
/*     */   public ReactorLogicBase getReactorLogic() {
/*  74 */     return this.isSteamReactor ? this.steamReactor : this.reactor;
/*     */   }
/*     */ 
/*     */   
/*     */   public ReactorLogicBase.IReactorPrediction getPrediction() {
/*  79 */     return this.isSteamReactor ? this.steamReactorPrediction : this.reactorPrediction;
/*     */   }
/*     */ 
/*     */   
/*     */   public UserSettings getUserSettings() {
/*  84 */     return this.settings;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145845_h() {
/*  90 */     if (!getActive()) {
/*     */       return;
/*     */     }
/*     */     
/*  94 */     if (!hasEnergy(10)) {
/*     */       return;
/*     */     }
/*     */     
/*  98 */     useEnergy(10);
/*  99 */     ReactorLogicBase base = getReactorLogic();
/* 100 */     if (base.isFinished()) {
/*     */       
/* 102 */       setActive(false);
/*     */       return;
/*     */     } 
/* 105 */     UserSettings setting = getUserSettings();
/* 106 */     if (base.isValid() && !base.didReactorBreak()) {
/*     */       
/* 108 */       for (int i = 0; i < setting.ticksPerTick && !base.isFinished() && !base.didReactorBreak(); i++) {
/*     */         
/* 110 */         base.onTick();
/* 111 */         if (base.isFinished()) {
/*     */           
/* 113 */           base.onFinished();
/* 114 */           setActive(false);
/*     */         } 
/*     */       } 
/* 117 */       updateReactor();
/*     */     }
/*     */     else {
/*     */       
/* 121 */       setActive(false);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ContainerIC2 getGuiContainer(EntityPlayer player) {
/* 128 */     return (ContainerIC2)new ContainerReactorPlanner(player.field_71071_by, this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getGuiClassName(EntityPlayer p0) {
/* 134 */     return "block.machine.gui.GuiReactorPlanner";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onGuiClosed(EntityPlayer player) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canInteractWith(EntityPlayer player) {
/* 144 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onRightClick(EntityPlayer player, EnumFacing face, Side side) {
/* 149 */     ItemStack stack = player.func_71045_bC();
/* 150 */     if (stack != null && stack.func_77973_b() instanceof IItemReactorPlanStorage) {
/*     */       
/* 152 */       if (side == Side.CLIENT)
/*     */       {
/* 154 */         return false;
/*     */       }
/* 156 */       IItemReactorPlanStorage storage = (IItemReactorPlanStorage)stack.func_77973_b();
/* 157 */       if (!storage.isPlanStorage(stack))
/*     */       {
/* 159 */         return false;
/*     */       }
/* 161 */       if (player.func_70093_af()) {
/*     */         
/* 163 */         IEncoder encoder = EncoderRegistry.instance.getDefaultEncoder();
/* 164 */         String setup = encoder.createEncodedData(this);
/* 165 */         if (!Strings.isNullOrEmpty(setup)) {
/*     */           
/* 167 */           if (storage.setSetup(stack, setup)) {
/*     */             
/* 169 */             if (!Strings.isNullOrEmpty(this.setupName))
/*     */             {
/* 171 */               storage.setPlanName(stack, this.setupName);
/*     */             }
/* 173 */             IC2.platform.messagePlayer(player, StatCollector.func_74838_a("container.reactorplannerAction.exportToItemSuccess.name"));
/* 174 */             return true;
/*     */           } 
/* 176 */           IC2.platform.messagePlayer(player, StatCollector.func_74838_a("container.reactorplannerAction.exportToItemFail.name"));
/*     */         }
/*     */         else {
/*     */           
/* 180 */           IC2.platform.messagePlayer(player, StatCollector.func_74838_a("container.reactorplannerAction.encoderFailed.name"));
/*     */         } 
/* 182 */         return true;
/*     */       } 
/*     */ 
/*     */       
/* 186 */       if (storage.hasSetup(stack)) {
/*     */         
/* 188 */         Tuple<String, NBTTagCompound> data = EncoderRegistry.instance.createDecodedMessage(storage.getSetup(stack));
/* 189 */         if (data != null) {
/*     */           
/* 191 */           IEncoder encoder = EncoderRegistry.instance.getEncoderFromID((String)data.getFirst());
/* 192 */           int oldSize = this.reactorSize;
/* 193 */           boolean steam = this.isSteamReactor;
/* 194 */           String oldName = this.setupName;
/* 195 */           NBTTagCompound backup = new NBTTagCompound();
/* 196 */           NBTTagCompound settingBackup = new NBTTagCompound();
/* 197 */           getReactorLogic().writeToNBT(backup);
/* 198 */           getUserSettings().writeToNBT(settingBackup);
/* 199 */           encoder.processData((NBTTagCompound)data.getSecond(), this);
/* 200 */           boolean failed = false;
/* 201 */           if (!getReactorLogic().isValid()) {
/*     */             
/* 203 */             failed = true;
/* 204 */             getReactorLogic().clear();
/* 205 */             this.isSteamReactor = steam;
/* 206 */             this.reactorSize = oldSize;
/* 207 */             this.setupName = oldName;
/* 208 */             getReactorLogic().readFromNBT(backup);
/* 209 */             getUserSettings().readFromNBT(settingBackup);
/*     */           } 
/* 211 */           ReactorLogicBase.IReactorPrediction pred = getReactorLogic().createPrediction();
/* 212 */           NBTTagCompound nbt = new NBTTagCompound();
/* 213 */           pred.writeToNBT(nbt);
/* 214 */           getPrediction().readFromNBT((NBTTagCompound)nbt.func_74737_b());
/* 215 */           getNetwork().updateTileGuiField((TileEntity)this, "isSteamReactor");
/* 216 */           getNetwork().updateTileGuiField((TileEntity)this, "reactorSize");
/* 217 */           getNetwork().updateTileGuiField((TileEntity)this, "setupName");
/* 218 */           updateReactor();
/* 219 */           updatePrediction();
/* 220 */           updateContainer(player);
/* 221 */           IC2.platform.messagePlayer(player, StatCollector.func_74838_a("container.reactorplannerAction." + (failed ? "importToItemFail" : "importFromItemSuccess") + ".name"));
/*     */         } 
/* 223 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/* 227 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145829_t() {
/* 234 */     super.func_145829_t();
/* 235 */     this.components.create();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canUpdate() {
/* 241 */     return isSimulating();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145839_a(NBTTagCompound nbt) {
/* 249 */     super.func_145839_a(nbt);
/* 250 */     this.components.readFromNBT(nbt.func_74775_l("Components"));
/* 251 */     this.settings.readFromNBT(nbt.func_74775_l("Settings"));
/* 252 */     this.isSteamReactor = nbt.func_74767_n("steam");
/* 253 */     this.reactorSize = nbt.func_74762_e("ReactorSize");
/* 254 */     this.selectedSlot = nbt.func_74762_e("SelectedSlot");
/* 255 */     getReactorLogic().readFromNBT(nbt.func_74775_l("Logic"));
/* 256 */     getPrediction().readFromNBT(nbt.func_74775_l("Prediction"));
/* 257 */     this.setupName = nbt.func_74779_i("SetupName");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145841_b(NBTTagCompound nbt) {
/* 263 */     super.func_145841_b(nbt);
/* 264 */     this.components.writeToNBT(getNBT(nbt, "Components"));
/* 265 */     this.settings.writeToNBT(getNBT(nbt, "Settings"));
/* 266 */     getReactorLogic().writeToNBT(getNBT(nbt, "Logic"));
/* 267 */     getPrediction().writeToNBT(getNBT(nbt, "Prediction"));
/* 268 */     nbt.func_74757_a("steam", this.isSteamReactor);
/* 269 */     nbt.func_74768_a("ReactorSize", this.reactorSize);
/* 270 */     nbt.func_74768_a("SelectedSlot", this.selectedSlot);
/* 271 */     nbt.func_74778_a("SetupName", this.setupName);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNetworkEvent(int event) {
/* 277 */     if (event == 0) {
/*     */       
/* 279 */       EntityPlayer player = IC2.platform.getPlayerInstance();
/* 280 */       ContainerReactorPlanner planner = getContainer(player);
/* 281 */       if (planner != null) {
/*     */         
/* 283 */         planner.reset(player.field_71071_by);
/* 284 */         planner.func_75142_b();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onPayloadPacket(EntityPlayer player, PayloadPacket packet) {
/* 292 */     if (packet.getSize(1) == 1 && packet.getSize(0) == 1) {
/*     */       
/* 294 */       this.setupName = packet.getString(0);
/* 295 */       getNetwork().updateTileGuiField((TileEntity)this, "setupName");
/*     */     }
/* 297 */     else if (packet.getSize(1) == 2) {
/*     */       
/* 299 */       int event = packet.getNumber(0);
/* 300 */       int value = packet.getNumber(1);
/* 301 */       if (event == 0) {
/*     */         
/* 303 */         clearCurrentReactor();
/* 304 */         this.isSteamReactor = (value == 1);
/* 305 */         this.selectedSlot = 0;
/* 306 */         this.components.offset = 0;
/* 307 */         this.components.create();
/* 308 */         getNetwork().updateTileGuiField((TileEntity)this, "isSteamReactor");
/* 309 */         getNetwork().updateTileGuiField((TileEntity)this, "components");
/* 310 */         getNetwork().updateTileGuiField((TileEntity)this, "selectedSlot");
/* 311 */         updateContainer(player);
/*     */       } 
/* 313 */       if (event == 1) {
/*     */         
/* 315 */         this.components.applyOffset(value);
/* 316 */         this.selectedSlot = 0;
/* 317 */         getNetwork().updateTileGuiField((TileEntity)this, "components");
/* 318 */         getNetwork().updateTileGuiField((TileEntity)this, "selectedSlot");
/* 319 */         updateContainer(player);
/*     */       } 
/* 321 */       if (event == 2) {
/*     */         
/* 323 */         this.components.setNextFilter();
/* 324 */         this.selectedSlot = 0;
/* 325 */         getNetwork().updateTileGuiField((TileEntity)this, "selectedSlot");
/* 326 */         getNetwork().updateTileGuiField((TileEntity)this, "components");
/* 327 */         updateContainer(player);
/*     */       } 
/* 329 */       if (event == 3) {
/*     */         
/* 331 */         this.reactorSize += value;
/* 332 */         if (this.reactorSize < 0)
/* 333 */           this.reactorSize = 0; 
/* 334 */         if (this.reactorSize > 6)
/* 335 */           this.reactorSize = 6; 
/* 336 */         getNetwork().updateTileEntityField((TileEntity)this, "reactorSize");
/* 337 */         getReactorLogic().onSizeUpdate();
/* 338 */         ContainerReactorPlanner cont = getContainer(player);
/* 339 */         if (cont != null) {
/*     */           
/* 341 */           cont.reset(player.field_71071_by);
/* 342 */           cont.func_75142_b();
/*     */         } 
/*     */       } 
/* 345 */       if (event == 4) {
/*     */         
/* 347 */         this.stackSize += value;
/* 348 */         if (this.stackSize < 1)
/* 349 */           this.stackSize = 1; 
/* 350 */         if (this.stackSize > 64)
/* 351 */           this.stackSize = 64; 
/* 352 */         getNetwork().updateTileGuiField((TileEntity)this, "stackSize");
/*     */       } 
/* 354 */       if (event == 5)
/*     */       {
/* 356 */         setActive((value == 0));
/*     */       }
/* 358 */       if (event == 6) {
/*     */         
/* 360 */         ReactorLogicBase base = getReactorLogic();
/* 361 */         if (value == 0) {
/*     */           
/* 363 */           if (!base.isValid())
/*     */           {
/* 365 */             base.validate();
/* 366 */             NBTTagCompound data = new NBTTagCompound();
/* 367 */             ReactorLogicBase.IReactorPrediction pred = base.createPrediction();
/* 368 */             pred.writeToNBT(data);
/* 369 */             getPrediction().readFromNBT((NBTTagCompound)data.func_74737_b());
/* 370 */             updateReactor();
/* 371 */             updatePrediction();
/*     */           
/*     */           }
/*     */         
/*     */         }
/* 376 */         else if (base.isValid()) {
/*     */           
/* 378 */           base.reset();
/* 379 */           updateReactor();
/* 380 */           updatePrediction();
/*     */         } 
/*     */       } 
/*     */       
/* 384 */       if (event == 7) {
/*     */         
/* 386 */         ReactorLogicBase base = getReactorLogic();
/* 387 */         if (value == 0) {
/*     */           
/* 389 */           if (base.isValid())
/*     */           {
/* 391 */             NBTTagCompound nbt = new NBTTagCompound();
/* 392 */             base.createBackup().writeToNBT(nbt);
/* 393 */             this.backup.readFromNBT((NBTTagCompound)nbt.func_74737_b());
/* 394 */             getNetwork().updateTileGuiField((TileEntity)this, "backup");
/* 395 */             getNetwork().updateTileGuiField((TileEntity)this, "settings");
/* 396 */             updateReactor();
/* 397 */             updatePrediction();
/*     */           
/*     */           }
/*     */         
/*     */         }
/* 402 */         else if (this.backup.hasBackup) {
/*     */           
/* 404 */           base.clear();
/* 405 */           getPrediction().clear();
/* 406 */           this.isSteamReactor = this.backup.isSteam;
/* 407 */           this.reactorSize = this.backup.reactorSize;
/* 408 */           getNetwork().updateTileEntityField((TileEntity)this, "reactorSize");
/* 409 */           getNetwork().initiateTileEntityEvent((TileEntity)this, 0, false);
/* 410 */           ContainerReactorPlanner cont = getContainer(player);
/* 411 */           if (cont != null)
/*     */           {
/* 413 */             cont.reset(player.field_71071_by);
/*     */           }
/* 415 */           base = getReactorLogic();
/* 416 */           base.restoreFromBackup(this.backup);
/* 417 */           updateReactor();
/* 418 */           updatePrediction();
/* 419 */           getNetwork().updateTileGuiField((TileEntity)this, "settings");
/*     */         } 
/*     */       } 
/*     */       
/* 423 */       if (event == 8)
/*     */       {
/* 425 */         if (value == 0) {
/*     */           
/* 427 */           getReactorLogic().clear();
/* 428 */           getPrediction().clear();
/* 429 */           updateReactor();
/* 430 */           updatePrediction();
/* 431 */           updateContainer(player);
/* 432 */           this.setupName = "";
/* 433 */           getNetwork().updateTileGuiField((TileEntity)this, "setupName");
/*     */         }
/* 435 */         else if (value == 1) {
/*     */           
/* 437 */           this.backup = new ReactorBackup();
/* 438 */           getNetwork().updateTileGuiField((TileEntity)this, "backup");
/*     */         } 
/*     */       }
/*     */       
/* 442 */       if (event == 9) {
/*     */         
/* 444 */         this.selectedView = value;
/* 445 */         getNetwork().updateTileGuiField((TileEntity)this, "selectedView");
/*     */       } 
/* 447 */       if (event == 10) {
/*     */         
/* 449 */         UserSettings user = getUserSettings();
/* 450 */         ReactorLogicBase base = getReactorLogic();
/* 451 */         user.startingHeat += value;
/* 452 */         if (user.startingHeat < 0)
/* 453 */           user.startingHeat = 0; 
/* 454 */         if (user.startingHeat > base.maxHeat - 100)
/* 455 */           user.startingHeat = base.maxHeat - 100; 
/* 456 */         getNetwork().updateTileGuiField((TileEntity)this, "settings");
/*     */       } 
/* 458 */       if (event == 11) {
/*     */         
/* 460 */         ReactorLogicBase base = getReactorLogic();
/* 461 */         base.producing = !base.producing;
/* 462 */         updateReactor();
/*     */       } 
/* 464 */       if (event == 12) {
/*     */         
/* 466 */         UserSettings user = getUserSettings();
/* 467 */         ReactorLogicBase base = getReactorLogic();
/* 468 */         user.maxTicks += value;
/* 469 */         if (user.maxTicks < 0)
/* 470 */           user.maxTicks = 0; 
/* 471 */         if (user.maxTicks > 99999999)
/* 472 */           user.maxTicks = 99999999; 
/* 473 */         getNetwork().updateTileGuiField((TileEntity)this, "settings");
/*     */       } 
/* 475 */       if (event == 13)
/*     */       {
/* 477 */         int max = IC2.reactorPlannerMaxTicks;
/* 478 */         UserSettings user = getUserSettings();
/* 479 */         user.ticksPerTick += value;
/* 480 */         if (user.ticksPerTick < 1)
/* 481 */           user.ticksPerTick = 1; 
/* 482 */         if (user.ticksPerTick > max)
/* 483 */           user.ticksPerTick = max; 
/* 484 */         getNetwork().updateTileGuiField((TileEntity)this, "settings");
/*     */       }
/*     */     
/* 487 */     } else if (packet.getSize(0) == 2) {
/*     */       
/* 489 */       String key = packet.getString(0);
/* 490 */       NBTTagCompound data = getNBTFromString(packet.getString(1));
/* 491 */       if (data.func_82582_d()) {
/*     */         
/* 493 */         IC2.platform.messagePlayer(player, StatCollector.func_74838_a("container.reactorplannerAction.lostData.name"));
/*     */         return;
/*     */       } 
/* 496 */       IEncoder encoder = EncoderRegistry.instance.getEncoderFromID(key);
/* 497 */       if (encoder == null) {
/*     */         
/* 499 */         IC2.platform.messagePlayer(player, StatCollector.func_74838_a("container.reactorplannerAction.encoderNotOnServer.name"));
/*     */         return;
/*     */       } 
/* 502 */       int oldSize = this.reactorSize;
/* 503 */       boolean steam = this.isSteamReactor;
/* 504 */       String oldName = this.setupName;
/* 505 */       NBTTagCompound backup = new NBTTagCompound();
/* 506 */       NBTTagCompound settingBackup = new NBTTagCompound();
/* 507 */       getReactorLogic().writeToNBT(backup);
/* 508 */       getUserSettings().writeToNBT(settingBackup);
/* 509 */       encoder.processData(data, this);
/* 510 */       boolean failed = false;
/* 511 */       if (!getReactorLogic().isValid()) {
/*     */         
/* 513 */         failed = true;
/* 514 */         getReactorLogic().clear();
/* 515 */         this.isSteamReactor = steam;
/* 516 */         this.reactorSize = oldSize;
/* 517 */         this.setupName = oldName;
/* 518 */         getReactorLogic().readFromNBT(backup);
/* 519 */         getUserSettings().readFromNBT(settingBackup);
/*     */       } 
/* 521 */       ReactorLogicBase.IReactorPrediction pred = getReactorLogic().createPrediction();
/* 522 */       NBTTagCompound nbt = new NBTTagCompound();
/* 523 */       pred.writeToNBT(nbt);
/* 524 */       getPrediction().readFromNBT((NBTTagCompound)nbt.func_74737_b());
/* 525 */       getNetwork().updateTileGuiField((TileEntity)this, "isSteamReactor");
/* 526 */       getNetwork().updateTileGuiField((TileEntity)this, "reactorSize");
/* 527 */       getNetwork().updateTileGuiField((TileEntity)this, "setupName");
/* 528 */       updateReactor();
/* 529 */       updatePrediction();
/* 530 */       updateContainer(player);
/* 531 */       IC2.platform.messagePlayer(player, StatCollector.func_74838_a("container.reactorplannerAction." + (failed ? "importFail" : "importSuccess") + ".name"));
/*     */     }
/* 533 */     else if (packet.getSize(0) == 1) {
/*     */       
/* 535 */       IEncoder encoder = EncoderRegistry.instance.getEncoderFromID(packet.getString(0));
/* 536 */       if (encoder == null) {
/*     */         
/* 538 */         IC2.platform.messagePlayer(player, StatCollector.func_74838_a("container.reactorplannerAction.encoderNotOnServer.name"));
/*     */         return;
/*     */       } 
/* 541 */       String encoded = encoder.createEncodedData(this);
/* 542 */       if (Strings.isNullOrEmpty(encoded)) {
/*     */         
/* 544 */         IC2.platform.messagePlayer(player, StatCollector.func_74838_a("container.reactorplannerAction.encoderFailed.name"));
/*     */         return;
/*     */       } 
/* 547 */       getNetwork().sendCustomPacket(player, (IC2Packet)new ReactorPlannerEncoderPacket(encoded));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private NBTTagCompound getNBTFromString(String key) {
/*     */     try {
/* 555 */       return (NBTTagCompound)JsonToNBT.func_150315_a(key);
/*     */     }
/* 557 */     catch (Exception exception) {
/*     */ 
/*     */       
/* 560 */       return new NBTTagCompound();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void updateContainer(EntityPlayer player) {
/* 565 */     ContainerReactorPlanner cont = getContainer(player);
/* 566 */     if (cont != null)
/*     */     {
/* 568 */       cont.func_75142_b();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private ContainerReactorPlanner getContainer(EntityPlayer player) {
/* 574 */     if (player == null)
/*     */     {
/* 576 */       return null;
/*     */     }
/* 578 */     Container cont = player.field_71070_bA;
/* 579 */     if (cont instanceof ContainerReactorPlanner)
/*     */     {
/* 581 */       return (ContainerReactorPlanner)cont;
/*     */     }
/* 583 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void clearCurrentReactor() {
/* 588 */     getReactorLogic().clear();
/* 589 */     getPrediction().clear();
/* 590 */     updateReactor();
/* 591 */     updatePrediction();
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateReactor() {
/* 596 */     getNetwork().updateTileGuiField((TileEntity)this, this.isSteamReactor ? "steamReactor" : "reactor");
/*     */   }
/*     */ 
/*     */   
/*     */   public void updatePrediction() {
/* 601 */     getNetwork().updateTileGuiField((TileEntity)this, this.isSteamReactor ? "steamReactorPrediction" : "reactorPrediction");
/*     */   }
/*     */   
/*     */   public static class UserSettings
/*     */     implements INetworkFieldData {
/* 606 */     public int maxTicks = 0;
/* 607 */     public int startingHeat = 0;
/* 608 */     public int ticksPerTick = 1;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void read(DataInput stream) {
/*     */       try {
/* 615 */         this.maxTicks = stream.readInt();
/* 616 */         this.startingHeat = stream.readInt();
/* 617 */         this.ticksPerTick = stream.readInt();
/*     */       }
/* 619 */       catch (Exception exception) {}
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void write(DataOutput stream) {
/*     */       try {
/* 629 */         stream.writeInt(this.maxTicks);
/* 630 */         stream.writeInt(this.startingHeat);
/* 631 */         stream.writeInt(this.ticksPerTick);
/*     */       }
/* 633 */       catch (Exception exception) {}
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void readFromNBT(NBTTagCompound nbt) {
/* 640 */       this.maxTicks = nbt.func_74762_e("Max");
/* 641 */       this.startingHeat = nbt.func_74762_e("StartingHeat");
/* 642 */       this.ticksPerTick = nbt.func_74762_e("Ticks");
/*     */     }
/*     */ 
/*     */     
/*     */     public void writeToNBT(NBTTagCompound nbt) {
/* 647 */       nbt.func_74768_a("Max", this.maxTicks);
/* 648 */       nbt.func_74768_a("StartingHeat", this.startingHeat);
/* 649 */       nbt.func_74768_a("Ticks", this.ticksPerTick);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class ReactorBackup
/*     */     implements INetworkFieldData
/*     */   {
/* 656 */     public ItemStack[] items = new ItemStack[54];
/*     */     
/*     */     public int customTicks;
/*     */     public int customHeat;
/*     */     public int reactorSize;
/*     */     public boolean hasBackup = false;
/*     */     public boolean isSteam;
/*     */     
/*     */     public void readFromNBT(NBTTagCompound nbt) {
/* 665 */       this.customTicks = nbt.func_74762_e("CustomTicks");
/* 666 */       this.customHeat = nbt.func_74762_e("CustomHeat");
/* 667 */       this.reactorSize = nbt.func_74762_e("Size");
/* 668 */       this.hasBackup = nbt.func_74767_n("HasBackup");
/* 669 */       this.isSteam = nbt.func_74767_n("isSteam");
/* 670 */       this.items = new ItemStack[54];
/* 671 */       NBTTagList list = nbt.func_150295_c("Setup", 10);
/* 672 */       for (int i = 0; i < list.func_74745_c(); i++) {
/*     */         
/* 674 */         NBTTagCompound data = list.func_150305_b(i);
/* 675 */         this.items[data.func_74762_e("Slot")] = ItemStack.func_77949_a(data);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void writeToNBT(NBTTagCompound nbt) {
/* 681 */       nbt.func_74768_a("CustomTicks", this.customTicks);
/* 682 */       nbt.func_74768_a("CustomHeat", this.customHeat);
/* 683 */       nbt.func_74768_a("Size", this.reactorSize);
/* 684 */       nbt.func_74757_a("HasBackup", this.hasBackup);
/* 685 */       nbt.func_74757_a("isSteam", this.isSteam);
/* 686 */       NBTTagList list = new NBTTagList();
/* 687 */       for (int i = 0; i < 54; i++) {
/*     */         
/* 689 */         if (this.items[i] != null) {
/*     */           
/* 691 */           NBTTagCompound data = new NBTTagCompound();
/* 692 */           this.items[i].func_77955_b(data);
/* 693 */           data.func_74768_a("Slot", i);
/* 694 */           list.func_74742_a((NBTBase)data);
/*     */         } 
/*     */       } 
/* 697 */       nbt.func_74782_a("Setup", (NBTBase)list);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void read(DataInput stream) {
/*     */       try {
/* 705 */         this.hasBackup = stream.readBoolean();
/* 706 */         this.reactorSize = stream.readInt();
/*     */       }
/* 708 */       catch (Exception exception) {}
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void write(DataOutput stream) {
/*     */       try {
/* 718 */         stream.writeBoolean(this.hasBackup);
/* 719 */         stream.writeInt(this.reactorSize);
/*     */       }
/* 721 */       catch (Exception exception) {}
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEnergyUsage() {
/* 730 */     return 10;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] func_94128_d(int p_94128_1_) {
/* 736 */     return new int[0];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String func_145825_b() {
/* 742 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\machine\tileentity\TileEntityReactorPlanner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */