package com.jaaakee.particlemenu;

import com.jaaakee.particlemenu.utilities.ConfigurationManager;
import org.bukkit.Material;
import org.bukkit.Particle;

public enum ParticleType {

    HEART(Particle.HEART, "heart.isEnabled", "heart.name", "heart.icon", "heart.slot", "particlemenu.activate.heart", "heart.noPermissionMessage"),
    LAVA_DRIP(Particle.DRIP_LAVA, "lavadrip.isEnabled", "lavadrip.name", "lavadrip.icon", "lavadrip.slot", "particlemenu.activate.lavadrip", "lavadrip.noPermissionMessage"),
    WATER_DRIP(Particle.DRIP_WATER, "waterdrip.isEnabled", "waterdrip.name", "waterdrip.icon", "waterdrip.slot", "particlemenu.activate.waterdrip", "waterdrip.noPermissionMessage"),
    HAPPY_VILLAGER(Particle.VILLAGER_HAPPY, "happyvillager.isEnabled", "happyvillager.name", "happyvillager.icon", "happyvillager.slot", "particlemenu.activate.happyvillager", "happyvillager.noPermissionMessage"),
    ANGRY_VILLAGER(Particle.VILLAGER_ANGRY, "angryvillager.isEnabled", "angryvillager.name", "angryvillager.icon", "angryvillager.slot", "particlemenu.activate.angryvillager", "angryvillager.noPermissionMessage"),
    NOTE(Particle.NOTE, "note.isEnabled", "note.name", "note.icon", "note.slot", "particlemenu.activate.note", "note.noPermissionMessage"),
    SLIME(Particle.SLIME, "slime.isEnabled", "slime.name", "slime.icon", "slime.slot", "particlemenu.activate.slime", "slime.noPermissionMessage"),

    ENCHANTMENT(Particle.ENCHANTMENT_TABLE, "enchantment.isEnabled", "enchantment.name", "enchantment.icon", "enchantment.slot", "particlemenu.activate.enchantment", "enchantment.noPermissionMessage"),
    CRITICAL(Particle.CRIT, "critical.isEnabled", "critical.name", "critical.icon", "critical.slot", "particlemenu.activate.critical", "critical.noPermissionMessage"),
    MAGIC(Particle.SPELL_WITCH, "magic.isEnabled", "magic.name", "magic.icon", "magic.slot", "particlemenu.activate.magic", "magic.noPermissionMessage"),
    FLAME(Particle.FLAME, "flame.isEnabled", "flame.name", "flame.icon", "flame.slot", "particlemenu.activate.flame", "flame.noPermissionMessage");

    public ConfigurationManager configurationManager = new ConfigurationManager();

    public Particle particleEffect;
    public String isEnabledPath, namePath, iconPath, slotPath, noPermissionMessagePath, permissionNodePath;

    ParticleType(Particle particleEffect, String isEnabledPath, String namePath, String iconPath, String slotPath, String permissionNodePath, String noPermissionMessagePath) {
        this.particleEffect = particleEffect;

        this.isEnabledPath = isEnabledPath;
        this.namePath = namePath;
        this.iconPath = iconPath;
        this.slotPath = slotPath;
        this.permissionNodePath = permissionNodePath;

        this.noPermissionMessagePath = noPermissionMessagePath;
    }

    public Particle getParticleEffect() {
        return particleEffect;
    }

    public boolean isEnabled(ParticleMenu particleMenu) {
        return configurationManager.getConfig("config", particleMenu).getBoolean(isEnabledPath);
    }

    public String getParticleName(ParticleMenu particleMenu) {
        return configurationManager.getConfig("config", particleMenu).getString(namePath);
    }

    public Material getParticleIcon(ParticleMenu particleMenu) {
        return Material.valueOf(configurationManager.getConfig("config", particleMenu).getString(iconPath));
    }

    public int getParticleSlot(ParticleMenu particleMenu) {
        return configurationManager.getConfig("config", particleMenu).getInt(slotPath);
    }

    public String getPermissionNode() {
        return permissionNodePath;
    }

    public String getNoPermissionMessage(ParticleMenu particleMenu) {
        return configurationManager.getConfig("config", particleMenu).getString(noPermissionMessagePath);
    }
}