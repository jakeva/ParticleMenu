package com.jaaakee.particlemenu;

import com.jaaakee.particlemenu.utilities.ConfigurationManager;
import org.bukkit.Material;
import org.bukkit.Particle;

public enum ParticleType {

    HEART(Particle.HEART, "heart.isEnabled", "heart.particleName", "heart.particleIcon", "heart.particleSlot", "particlemenu.activate.heart", "heart.noPermissionMessage"),
    LAVA_DRIP(Particle.DRIP_LAVA, "lavadrip.isEnabled", "lavadrip.particleName", "lavadrip.particleIcon", "lavadrip.particleSlot", "particlemenu.activate.lavadrip", "lavadrip.noPermissionMessage"),
    WATER_DRIP(Particle.DRIP_WATER, "waterdrip.isEnabled", "waterdrip.particleName", "waterdrip.particleIcon", "waterdrip.particleSlot", "particlemenu.activate.waterdrip", "waterdrip.noPermissionMessage"),
    HAPPY_VILLAGER(Particle.VILLAGER_HAPPY, "happyvillager.isEnabled", "happyvillager.particleName", "happyvillager.particleIcon", "happyvillager.particleSlot", "particlemenu.activate.happyvillager", "happyvillager.noPermissionMessage"),
    ANGRY_VILLAGER(Particle.VILLAGER_ANGRY, "angryvillager.isEnabled", "angryvillager.particleName", "angryvillager.particleIcon", "angryvillager.particleSlot", "particlemenu.activate.angryvillager", "angryvillager.noPermissionMessage"),
    NOTE(Particle.NOTE, "note.isEnabled", "note.particleName", "note.particleIcon", "note.particleSlot", "particlemenu.activate.note", "note.noPermissionMessage"),
    SLIME(Particle.SLIME, "slime.isEnabled", "slime.particleName", "slime.particleIcon", "slime.particleSlot", "particlemenu.activate.slime", "slime.noPermissionMessage"),

    ENCHANTMENT(Particle.ENCHANTMENT_TABLE, "enchantment.isEnabled", "enchantment.particleName", "enchantment.particleIcon", "enchantment.particleSlot", "particlemenu.activate.enchantment", "enchantment.noPermissionMessage"),
    CRITICAL(Particle.CRIT, "critical.isEnabled", "critical.particleName", "critical.particleIcon", "critical.particleSlot", "particlemenu.activate.critical", "critical.noPermissionMessage"),
    MAGIC(Particle.SPELL_WITCH, "magic.isEnabled", "magic.particleName", "magic.particleIcon", "magic.particleSlot", "particlemenu.activate.magic", "magic.noPermissionMessage"),
    FLAME(Particle.FLAME, "flame.isEnabled", "flame.particleName", "flame.particleIcon", "flame.particleSlot", "particlemenu.activate.flame", "flame.noPermissionMessage");

    public ConfigurationManager configurationManager = new ConfigurationManager();

    public Particle particleEffect;
    public String isEnabledPath, particleNamePath, particleIconPath, particleSlotPath, noPermissionMessagePath, permissionNodePath;

    ParticleType(Particle particleEffect, String isEnabledPath, String particleNamePath, String particleIconPath, String particleSlotPath, String permissionNodePath, String noPermissionMessagePath) {
        this.particleEffect = particleEffect;

        this.isEnabledPath = isEnabledPath;
        this.particleNamePath = particleNamePath;
        this.particleIconPath = particleIconPath;
        this.particleSlotPath = particleSlotPath;
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
        return configurationManager.getConfig("config", particleMenu).getString(particleNamePath);
    }

    public Material getParticleIcon(ParticleMenu particleMenu) {
        return Material.valueOf(configurationManager.getConfig("config", particleMenu).getString(particleIconPath));
    }

    public int getSlot(ParticleMenu particleMenu) {
        return configurationManager.getConfig("config", particleMenu).getInt(particleSlotPath);
    }

    public String getPermissionNode() {
        return permissionNodePath;
    }

    public String getNoPermissionMessage(ParticleMenu particleMenu) {
        return configurationManager.getConfig("config", particleMenu).getString(noPermissionMessagePath);
    }
}