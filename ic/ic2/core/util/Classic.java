/*    */ package ic2.core.util;
/*    */ 
/*    */ import com.google.common.collect.UnmodifiableIterator;
/*    */ import cpw.mods.fml.common.FMLLog;
/*    */ import cpw.mods.fml.common.Loader;
/*    */ import cpw.mods.fml.common.Mod;
/*    */ import cpw.mods.fml.common.Mod.EventHandler;
/*    */ import cpw.mods.fml.common.ModContainer;
/*    */ import cpw.mods.fml.common.event.FMLInterModComms;
/*    */ import cpw.mods.fml.common.event.FMLPreInitializationEvent;
/*    */ import ic2.core.block.generator.block.BlockGenerator;
/*    */ import java.util.List;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.nbt.NBTBase;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mod(modid = "IC2-Classic-Spmod", version = "0.0.0.0", name = "IC2 Classic Detection Helper")
/*    */ public class Classic
/*    */ {
/*    */   @EventHandler
/*    */   public void load(FMLPreInitializationEvent event) {
/* 27 */     List<ModContainer> container = Loader.instance().getActiveModList();
/* 28 */     for (ModContainer cont : container) {
/*    */       
/* 30 */       if (cont.getModId().equals("IC2")) {
/*    */         
/* 32 */         (event.getModMetadata()).parentMod = cont;
/* 33 */         (cont.getMetadata()).childMods.add(Loader.instance().activeModContainer());
/*    */         break;
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   @EventHandler
/*    */   public void onIMC(FMLInterModComms.IMCEvent event) {
/* 42 */     for (UnmodifiableIterator<FMLInterModComms.IMCMessage> unmodifiableIterator = event.getMessages().iterator(); unmodifiableIterator.hasNext(); ) { FMLInterModComms.IMCMessage message = unmodifiableIterator.next();
/*    */       
/* 44 */       if (!message.key.equals("generatorDrop")) {
/*    */         
/* 46 */         FMLLog.getLogger().info("Message Failed");
/*    */         continue;
/*    */       } 
/* 49 */       if (message.isNBTMessage()) {
/*    */         
/* 51 */         NBTTagCompound nbt = message.getNBTValue();
/* 52 */         int key = nbt.func_74762_e("Key");
/* 53 */         if (key < 0 || key > 15) {
/*    */           continue;
/*    */         }
/*    */         
/* 57 */         ItemStack stack = ItemStack.func_77949_a(nbt.func_74775_l("Value"));
/* 58 */         if (stack == null) {
/*    */           continue;
/*    */         }
/*    */         
/* 62 */         BlockGenerator.optionalDrops[key] = stack.func_77946_l();
/*    */       }  }
/*    */   
/*    */   }
/*    */ 
/*    */   
/*    */   public void examplePacket() {
/* 69 */     NBTTagCompound dataToSend = new NBTTagCompound();
/* 70 */     dataToSend.func_74768_a("Key", 12);
/* 71 */     ItemStack drop = new ItemStack(Blocks.field_150460_al);
/* 72 */     dataToSend.func_74782_a("Value", (NBTBase)drop.func_77955_b(new NBTTagCompound()));
/* 73 */     FMLInterModComms.sendMessage("IC2-Classic-Spmod", "generatorDrop", dataToSend);
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\cor\\util\Classic.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */