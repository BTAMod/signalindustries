package sunsetsatellite.signalindustries.commands;

import com.mojang.nbt.CompoundTag;
import com.mojang.nbt.NbtIo;
import net.minecraft.client.Minecraft;
import net.minecraft.core.HitResult;
import net.minecraft.core.block.Block;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.net.command.Command;
import net.minecraft.core.net.command.CommandHandler;
import net.minecraft.core.net.command.CommandSender;
import net.minecraft.core.net.command.PlayerCommandSender;
import net.minecraft.core.world.World;
import sunsetsatellite.catalyst.core.util.BlockInstance;
import sunsetsatellite.catalyst.core.util.VarargsFunction3;
import sunsetsatellite.catalyst.core.util.Vec3i;
import sunsetsatellite.catalyst.multiblocks.Structure;
import sunsetsatellite.signalindustries.SIBlocks;
import sunsetsatellite.signalindustries.SignalIndustries;

import java.io.DataOutputStream;
import java.io.File;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.util.*;

public class StructMakerCommand extends Command {
	public static Structure currentStructure;
	public static BlockInstance origin;
	public static Set<BlockInstance> structBlocks = new HashSet<>();
	public static List<Class<?>> classes = new ArrayList<>(Arrays.asList(Block.class, SIBlocks.class));
	public static boolean autoAddRemove = false;
	public static boolean ignoreRotation = false;

	public StructMakerCommand(String name, String... alts) {
		super(name, alts);
	}

	@Override
	public boolean execute(CommandHandler commandHandler, CommandSender commandSender, String[] args) {
		World world = Minecraft.getMinecraft(Minecraft.class).theWorld;
		if (commandSender instanceof PlayerCommandSender) {
			EntityPlayer player = commandSender.getPlayer();
			if (args.length > 0) {
				for (Cmd cmd : Cmd.values()) {
					if (cmd.name.equalsIgnoreCase(args[0])) {
						return cmd.method.apply(commandHandler,commandSender,Arrays.copyOfRange(args, 1, args.length));
					}
				}
			}
		}
		return false;
	}

	private static Boolean createStructure(CommandHandler commandHandler, CommandSender commandSender, String... args){
		if(Objects.equals(args[0], "")){
			commandSender.sendMessage("Please specify a structure name!");
			return true;
		}
		CompoundTag nbt = new CompoundTag();
		nbt.putCompound("Blocks",new CompoundTag());
		nbt.putCompound("Origin",new CompoundTag());
		nbt.putCompound("Substitutions",new CompoundTag());
		nbt.putCompound("TileEntities",new CompoundTag());
		currentStructure = new Structure(SignalIndustries.MOD_ID, new Class[]{SIBlocks.class}, args[0], nbt , false, false);
		commandSender.sendMessage("Structure '"+args[0]+"' created!");
		structBlocks.clear();
		return true;
	}

	private static Boolean setOrigin(CommandHandler commandHandler, CommandSender commandSender, String... args){
		if(currentStructure == null){
			commandSender.sendMessage("No structure! Use /s create <name>!");
			return true;
		}
		Minecraft mc = commandHandler.asClient().minecraft;
		World world = mc.theWorld;
		if (mc.objectMouseOver.hitType == HitResult.HitType.TILE) {
			Vec3i posVec = new Vec3i(mc.objectMouseOver.x, mc.objectMouseOver.y, mc.objectMouseOver.z);
			internalRemoveBlock(null, world, posVec);
            origin = new BlockInstance(posVec.getBlock(world),posVec, ignoreRotation ? -1 : posVec.getBlockMetadata(world), posVec.getTileEntity(world));
			for (BlockInstance structBlock : structBlocks) {
				structBlock.offset = distanceFromOrigin(structBlock.pos);
			}
			if(origin.meta != -1 && origin.meta != 5){
				commandSender.sendMessage("Warning! The origin should be facing east (meta 5)!");
			}
			commandSender.sendMessage("Origin set at " + posVec + " with meta "+ origin.meta + " as " + origin.block.getKey() + "!");
		}
		return true;
	}

	private static Boolean addBlockToStructure(CommandHandler commandHandler, CommandSender commandSender, String... args){
		if(currentStructure == null){
			commandSender.sendMessage("No structure! Use /s create <name>!");
			return true;
		}
		Minecraft mc = commandHandler.asClient().minecraft;
		if (mc.objectMouseOver.hitType == HitResult.HitType.TILE) {
			Vec3i posVec = new Vec3i(mc.objectMouseOver.x, mc.objectMouseOver.y, mc.objectMouseOver.z);
			internalAddBlock(mc,posVec);
		}

		return true;
	}

	private static Boolean removeBlockFromStructure(CommandHandler commandHandler, CommandSender commandSender, String... args){
		if(currentStructure == null){
			commandSender.sendMessage("No structure! Use /s create <name>!");
			return true;
		}
		Minecraft mc = commandHandler.asClient().minecraft;
		if (mc.objectMouseOver.hitType == HitResult.HitType.TILE) {
			Vec3i posVec = new Vec3i(mc.objectMouseOver.x, mc.objectMouseOver.y, mc.objectMouseOver.z);
			internalRemoveBlock(mc, mc.theWorld, posVec);
		}
		return true;
	}

	public static void internalRemoveBlock(Minecraft mc, World world, Vec3i posVec){
		if(currentStructure == null){
			return;
		}
		BlockInstance block = new BlockInstance(posVec.getBlock(world), posVec, ignoreRotation ? -1 : posVec.getBlockMetadata(world), posVec.getTileEntity(world));
		if(structBlocks.remove(block) && mc != null){
			mc.thePlayer.sendMessage("Removed block at " + posVec + " with meta "+ block.meta + " from structure (" + block.block.getKey() + ")!");
		}
	}

	public static void internalAddBlock(Minecraft mc, Vec3i posVec){
		if(currentStructure == null){
			return;
		}
		World world = mc.theWorld;
		BlockInstance block = new BlockInstance(posVec.getBlock(world), posVec, ignoreRotation ? -1 : posVec.getBlockMetadata(world), posVec.getTileEntity(world));
		block.offset = distanceFromOrigin(posVec);
		structBlocks.add(block);
		mc.thePlayer.sendMessage("Added block at " + posVec + " with meta "+ block.meta + " to structure as " + block.block.getKey() + "!");
	}

    private static String getIdFromClass(Block b, Class<?> clazz) {
		List<Field> fields = new ArrayList<>(Arrays.asList(clazz.getDeclaredFields()));
		fields.removeIf((F) -> F.getType() != Block.class);
		for (Field field : fields) {
			Block fieldBlock;
			try {
			    field.setAccessible(true);
				fieldBlock = (Block) field.get(null);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			}
			if (b == fieldBlock){
			    return clazz.getName() + ":" + field.getName();
			}
		}
		return "";
    }

    private static String getId(Block b) {
        for (Class<?> clazz : classes) {
            String id = getIdFromClass(b, clazz);
            if (!id.equals("")) return id;
        }
        return "";
    }

	private static Boolean toggleAutoAddRemove(CommandHandler commandHandler, CommandSender commandSender, String... args){
		if(autoAddRemove){
			autoAddRemove = false;
			commandSender.sendMessage("Disabled automatic adding and removing!");
		} else {
			autoAddRemove = true;
			commandSender.sendMessage("Enabled automatic adding and removing!");
		}
		return true;
	}

	private static Boolean toggleIgnoreRotation(CommandHandler commandHandler, CommandSender commandSender, String... args){
		if(ignoreRotation){
			ignoreRotation = false;
			commandSender.sendMessage("Disabled rotation ignoring!");
		} else {
			ignoreRotation = true;
			commandSender.sendMessage("Enabled rotation ignoring!");
		}
		return true;
	}

	private static Boolean clearStructure(CommandHandler commandHandler, CommandSender commandSender, String... args){
		currentStructure = null;
		structBlocks.clear();
		commandSender.sendMessage("Structure data cleared!");
		return true;
	}

	private static Boolean listContents(CommandHandler commandHandler, CommandSender commandSender, String... args){
		if(currentStructure == null){
			commandSender.sendMessage("No structure! Use /s create <name>!");
			return true;
		}
		commandSender.sendMessage("Name: "+currentStructure.translateKey);
		commandSender.sendMessage("Blocks: "+structBlocks.size());
		//commandSender.sendMessage("Blocks: "+currentStructure.data.getCompound("Blocks").getValues().size());
		//commandSender.sendMessage("Substitutions: "+currentStructure.data.getCompound("Substitutions").getValues().size());
		//commandSender.sendMessage("Tile Entities: "+currentStructure.data.getCompound("TileEntities").getValues().size());
		return true;
	}

	private static void serializeOrigin(CommandSender commandSender) {
		CompoundTag blockNbt = new CompoundTag();
		CompoundTag posNbt = new CompoundTag();
		new Vec3i().writeToNBT(posNbt);
		blockNbt.putCompound("pos", posNbt);

        String id = getId(origin.block);
        blockNbt.putString("id", id);
        if (id.equals("")) {
            commandSender.sendMessage("Warning! The id is empty for this block, did you forget to add it's storage class with '/s addblockclass <class>'?");
        }

		blockNbt.putBoolean("tile",origin.tile != null);
		blockNbt.putInt("meta",origin.meta);
		currentStructure.data.putCompound("Origin",blockNbt);
	}

	private static Boolean saveStructure(CommandHandler commandHandler, CommandSender commandSender, String... args){
		if(currentStructure == null){
			commandSender.sendMessage("No structure! Use /s create <name>!");
			return true;
		}
		if(Objects.equals(args[0], "")){
			commandSender.sendMessage("Please specify a structure name!");
			return true;
		}
		Minecraft mc = commandHandler.asClient().minecraft;
		currentStructure.data.putCompound("Origin",new CompoundTag());
		currentStructure.data.putCompound("Blocks",new CompoundTag());
		currentStructure.data.putCompound("TileEntities",new CompoundTag());
		serializeOrigin(commandSender);
		for (BlockInstance block : structBlocks) {
			CompoundTag blockNbt = new CompoundTag();
			CompoundTag posNbt = new CompoundTag();
			block.offset.writeToNBT(posNbt);
			blockNbt.putCompound("pos", posNbt);

            String id = getId(block.block);
            blockNbt.putString("id", id);
            if (id.equals("")) {
                commandSender.sendMessage("Warning! The id is empty for this block, did you forget to add it's storage class with '/s addblockclass <class>'?");
            }

			blockNbt.putBoolean("tile", block.tile != null);
			blockNbt.putInt("meta", block.meta);
			int i = currentStructure.data.getCompound("Blocks").getValues().size();
			currentStructure.data.getCompound("Blocks").putCompound(String.valueOf(i), blockNbt);
			if(block.tile != null){
				currentStructure.data.getCompound("TileEntities").putCompound(String.valueOf(i), blockNbt);
			}
		}
		File file = new File(Minecraft.getMinecraft(Minecraft.class).getMinecraftDir(), args[0] + ".nbt");
		try {
			try (DataOutputStream output = new DataOutputStream(Files.newOutputStream(file.toPath()))) {
				NbtIo.writeCompressed(currentStructure.data, output);
			}
		} catch (Exception e){
			throw new RuntimeException(e);
		}
		return true;
	}

    private static Boolean addBlockClass(CommandHandler commandHandler, CommandSender commandSender, String... args) {
        if (Objects.equals(args[0], "")) {
            commandSender.sendMessage("Please specify a class! (Such as jiatan.myreallycoolmod.MyBlocks)");
            return true;
        } else if(Objects.equals(args[0], "list")) {
            commandSender.sendMessage("Added block classes: ");
            for (Class<?> clazz : classes) {
                commandSender.sendMessage("- " + clazz.getName());
            }
            return true;
        }

        try {
            Class<?> clazz = Class.forName(args[0]);
            classes.add(clazz);
        } catch (Exception e) {
            e.printStackTrace();
            commandSender.sendMessage("Failed to add class");
        }
        return true;
    }

	private static Vec3i distanceFromOrigin(Vec3i vec){
		if(origin == null) return new Vec3i();
		return origin.pos.copy().subtract(vec).multiply(-1);
	}

	private enum Cmd {
		CREATE("create", StructMakerCommand::createStructure),
		ADD("add",StructMakerCommand::addBlockToStructure),
		REMOVE("remove",StructMakerCommand::removeBlockFromStructure),
		CLEAR("clear",StructMakerCommand::clearStructure),
		ORIGIN("origin",StructMakerCommand::setOrigin),
		AUTO("auto",StructMakerCommand::toggleAutoAddRemove),
		IGNORE_ROT("ignore-rot",StructMakerCommand::toggleIgnoreRotation),
		LIST("list",StructMakerCommand::listContents),
		SAVE("save",StructMakerCommand::saveStructure),
		ADDCLASS("addblockclass",StructMakerCommand::addBlockClass);

		Cmd(String name, VarargsFunction3<CommandHandler,CommandSender,String,Boolean> method) {
			this.name = name;
			this.method = method;
		}

		public final String name;
		public final VarargsFunction3<CommandHandler,CommandSender,String,Boolean> method;
	}

	@Override
	public boolean opRequired(String[] strings) {
		return true;
	}

	@Override
	public void sendCommandSyntax(CommandHandler commandHandler, CommandSender commandSender) {
		if (commandSender instanceof PlayerCommandSender) {
			commandSender.sendMessage("/s create <name> - Starts building the structure");
			commandSender.sendMessage("/s add           - Adds the block you are looking at to the structure");
			commandSender.sendMessage("/s remove        - Removes the block you are looking at to the structure");
			commandSender.sendMessage("/s clear         - Clears all structure data");
			commandSender.sendMessage("/s origin        - Adds the origin of the structure (required)");
			commandSender.sendMessage("/s auto          - Allows for adding/removing blocks as you place/break the blocks");
			commandSender.sendMessage("/s ignore-rot    - Toggles allowing any meta for blocks");
			commandSender.sendMessage("/s list          - List the contents of the structure");
			commandSender.sendMessage("/s save <name>   - Saves the structure to .minecraft/<name>.nbt");
			commandSender.sendMessage("/s addblockclass <class> - Adds a class that blocks can come from (uses SI and MC by default)");
		}
	}
}
