package com.jordanneil23.Friendly;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Creature;
import org.bukkit.entity.CreatureType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityListener;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.EntityTargetEvent.TargetReason;

public class FriendlyMobListener extends EntityListener {
	private final Friendly plugin;

	private List<Creature> attacked = new ArrayList<Creature>();

	public FriendlyMobListener(final Friendly Friendly) {
		this.plugin = Friendly;
	}

	@Override
	public void onEntityDeath(EntityDeathEvent event) {
		if (event.getEntity() instanceof Creature) {
			Creature c = (Creature) event.getEntity();
			attacked.remove(c);
		}
	}

	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		CreatureType cType = plugin.getCreatureType(event.getEntity());
		if (cType != null) {
			if (event.getEntity().getLocation().getWorld().getTime() < 12000
					|| event.getEntity().getLocation().getWorld().getTime() == 24000) {
				String natureLightNode = "Friendly.Mobs."
						+ cType.getName().toUpperCase() + ".Day.Nature";
				String data = plugin.getConfiguration().getString(
						natureLightNode);
				if (plugin.shouldTarget(event.getEntity(), data, true)) {
					Creature c = (Creature) event.getEntity();
					if (event.getDamager() instanceof LivingEntity) {
						c.setTarget((LivingEntity) event.getDamager());
						if (!attacked.contains(c)) {
							attacked.add(c);
						}
					}
				}
			} else {
				String natureNightNode = "SpawnMob.Mobs."
						+ cType.getName().toUpperCase() + ".Night.Nature";
				String data = plugin.getConfiguration().getString(
						natureNightNode);
				if (plugin.shouldTarget(event.getEntity(), data, true)) {
					Creature c = (Creature) event.getEntity();
					if (event.getDamager() instanceof LivingEntity) {
						c.setTarget((LivingEntity) event.getDamager());
						if (!attacked.contains(c)) {
							attacked.add(c);
						}
					}
				}
			}
		}
	}

	@Override
	public void onEntityTarget(EntityTargetEvent event) {
		if (event.getReason() == TargetReason.FORGOT_TARGET
				|| event.getReason() == TargetReason.TARGET_DIED) {
			if (event.getEntity() instanceof Creature) {
				Creature c = (Creature) event.getEntity();
				attacked.remove(c);
			}
		} else if (event.getReason() == TargetReason.TARGET_ATTACKED_ENTITY) {
			if (event.getEntity() instanceof Creature) {
				Creature c = (Creature) event.getEntity();
				if (!attacked.contains(c)) {
					attacked.add(c);
				}
			}
		} else if (!(event.getReason() == TargetReason.CUSTOM)) {
			CreatureType cType = plugin.getCreatureType(event.getEntity());
			if (cType != null) {
				if (event.getEntity().getLocation().getWorld().getTime() < 12000
						|| event.getEntity().getLocation().getWorld().getTime() == 24000) {
					String natureLightNode = "Friendly.Mobs."
							+ cType.getName().toUpperCase() + ".Day.Nature";
					String data = plugin.getConfiguration().getString(
							natureLightNode);
					Creature c = (Creature) event.getEntity();
					if (!plugin.shouldTarget(event.getEntity(), data,
							attacked.contains(c))) {
						event.setCancelled(true);
					}
				} else {
					String natureNightNode = "Friendly.Mobs."
							+ cType.getName().toUpperCase() + ".Night.Nature";
					String data = plugin.getConfiguration().getString(
							natureNightNode);
					Creature c = (Creature) event.getEntity();
					if (!plugin.shouldTarget(event.getEntity(), data,
							attacked.contains(c))) {
						event.setCancelled(true);
					}
				}
			}
		}
	}
}
