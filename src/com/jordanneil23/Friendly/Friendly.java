package com.jordanneil23.Friendly;

import java.io.File;
import java.io.IOException;

import org.bukkit.entity.Animals;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Creature;
import org.bukkit.entity.CreatureType;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Flying;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.Giant;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Pig;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Slime;
import org.bukkit.entity.Spider;
import org.bukkit.entity.Squid;
import org.bukkit.entity.WaterMob;
import org.bukkit.entity.Zombie;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.config.Configuration;

public class Friendly extends JavaPlugin {
	private final FriendlyMobListener entityListener = new FriendlyMobListener(this);
	public void onEnable() {
		Configuration config = this.getConfiguration();
		loadSettings(config);
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvent(Type.CREATURE_SPAWN, entityListener, Priority.Highest,
				this);
		pm.registerEvent(Type.ENTITY_COMBUST, entityListener, Priority.Highest,
				this);
		pm.registerEvent(Type.ENTITY_TARGET, entityListener, Priority.Highest,
				this);
		pm.registerEvent(Type.ENTITY_DEATH, entityListener, Priority.Highest,
				this);
		pm.registerEvent(Type.ENTITY_DAMAGED, entityListener, Priority.Highest,
				this);
		pm.registerEvent(Type.ENTITY_EXPLODE, entityListener, Priority.Highest,
				this);
    	PluginDescriptionFile pdfFile = this.getDescription();
    	java.util.logging.Logger log = java.util.logging.Logger.getLogger("Minecraft");
        log.info(String.format("[" + this.getDescription().getName() + "]" + " Version " + pdfFile.getVersion() + " enabled." ));
	}

	public void onDisable() {
		PluginDescriptionFile pdfFile = this.getDescription();
		java.util.logging.Logger log = java.util.logging.Logger.getLogger("Minecraft");
    	log.info( "[" + this.getDescription().getName() + "]" + " Version " + pdfFile.getVersion() + " disabled.");
	}
    public boolean shouldTarget(Entity e, String nodeData, boolean attacked) {
		if (nodeData != null) {
			if (nodeData.equalsIgnoreCase("Passive")) {
				return false;
			} else if (nodeData.equalsIgnoreCase("Neutral")) {
				if (attacked) {
					return true;
				} else {
					return false;
				}
			} else if (nodeData.equalsIgnoreCase("Aggressive")) {
				return true;
			}
		}
		if (e instanceof Monster) {
			return true;
		} else {
			return false;
		}
	}

	public CreatureType getCreatureType(Entity entity) {
		if (entity instanceof LivingEntity) {
			if (entity instanceof Creature) {
				if (entity instanceof Animals) {
					if (entity instanceof Chicken) {
						return CreatureType.CHICKEN;
					} else if (entity instanceof Cow) {
						return CreatureType.COW;
					} else if (entity instanceof Pig) {
						return CreatureType.PIG;
					} else if (entity instanceof Sheep) {
						return CreatureType.SHEEP;
					}
				}
				// Monsters
				else if (entity instanceof Monster) {
					if (entity instanceof Zombie) {
						if (entity instanceof PigZombie) {
							return CreatureType.PIG_ZOMBIE;
						}
					} else if (entity instanceof Creeper) {
						return CreatureType.CREEPER;
					} else if (entity instanceof Giant) {
					} else if (entity instanceof Skeleton) {
						return CreatureType.SKELETON;
					} else if (entity instanceof Spider) {
						return CreatureType.SPIDER;
					} else if (entity instanceof Slime) {
						return CreatureType.SLIME;
					}
				}
				// Water Animals
				else if (entity instanceof WaterMob) {
					if (entity instanceof Squid) {
						return CreatureType.SQUID;
					}
				}
			}
			// Flying
			else if (entity instanceof Flying) {
				if (entity instanceof Ghast) {
					return CreatureType.GHAST;
				}
			}
		}
		return null;
	}		
	public void loadSettings(Configuration config) {
		File configfile = new File("plugins/Friendly");
		File yml = new File(configfile + "/config.yml");
		if (!configfile.exists()) {
			configfile.mkdirs();
		}
		if (!yml.exists()) {
			try {
				yml.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}

			// Default Settings (Makes it really messy, that's why I hand out the config file myself :)
			config.setProperty("Friendly.Mobs.PIG.Day.Nature", "Passive");
			config.setProperty("Friendly.Mobs.PIG.Night.Nature", "Passive");
			config.setProperty("Friendly.Mobs.COW.Day.Nature", "Passive");
			config.setProperty("Friendly.Mobs.PIG.Night.Nature", "Passive");
			config.setProperty("Friendly.Mobs.SHEEP.Day.Nature", "Passive");
			config.setProperty("Friendly.Mobs.SHEEP.Night.Nature", "Passive");
			config.setProperty("Friendly.Mobs.CHICKEN.Day.Nature", "Passive");
			config.setProperty("Friendly.Mobs.CHICKEN.Night.Nature", "Passive");
			config.setProperty("Friendly.Mobs.SQUID.Day.Nature", "Passive");
			config.setProperty("Friendly.Mobs.SQUID.Night.Nature", "Passive");
			config.setProperty("Friendly.Mobs.PIG_ZOMBIE.Day.Nature", "Neutral");
			config.setProperty("Friendly.Mobs.PIG_ZOMBIE.Night.Nature", "Neutral");
			config.setProperty("Friendly.Mobs.SPIDER.Day.Nature", "Neutral");
			config.setProperty("Friendly.Mobs.SPIDER.Night.Nature", "Aggressive");
			config.setProperty("Friendly.Mobs.ZOMBIE.Day.Nature", "Aggressive");
			config.setProperty("Friendly.Mobs.ZOMBIE.Night.Nature", "Aggressive");
			config.setProperty("Friendly.Mobs.SKELETON.Day.Nature", "Aggressive");
			config.setProperty("Friendly.Mobs.SKELETON.Night.Nature", "Aggressive");
			config.setProperty("Friendly.Mobs.CREEPER.Day.Nature", "Aggressive");
			config.setProperty("Friendly.Mobs.CREEPER.Night.Nature", "Aggressive");
			config.setProperty("Friendly.Mobs.SLIME.Day.Nature", "Aggressive");
			config.setProperty("Friendly.Mobs.SLIME.Night.Nature", "Aggressive");
			config.setProperty("Friendly.Mobs.GHAST.Day.Nature", "Aggressive");
			config.setProperty("Friendly.Mobs.GHAST.Night.Nature", "Aggressive");
			config.save();
		}
	}
	public CreatureType findType(String mob) {
		for (CreatureType creaturetype : CreatureType.values()) {
			if (creaturetype.name().equalsIgnoreCase(mob))
				return creaturetype;
			else if (creaturetype.name().replaceAll("_", "").equalsIgnoreCase(mob))
				return creaturetype;
		}
		return null;
	}
}
