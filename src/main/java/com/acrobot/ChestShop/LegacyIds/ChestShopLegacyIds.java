package com.acrobot.chestshop.legacyids;

/*
 * ChestShop-LegacyIds
 * Copyright (C) 2018 Max Lee aka Phoenix616 (mail@moep.tv)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

import com.Acrobot.Breeze.Utils.NumberUtil;
import com.Acrobot.ChestShop.Events.MaterialParseEvent;
import de.themoep.idconverter.IdMappings;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public final class ChestShopLegacyIds extends JavaPlugin implements Listener {

    private static final String DELIMITER = ":";

    private Map<String, Material> cache = new HashMap<>();
    private boolean convertNumericIds;
    private boolean convertLegacyNames;

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        saveDefaultConfig();
        reloadConfig();
        convertNumericIds = getConfig().getBoolean("convert.numeric-ids");
        convertLegacyNames = getConfig().getBoolean("convert.legacy-names");
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onMaterialParse(MaterialParseEvent event) {
        if (event.hasMaterial()) {
            return;
        }

        String matKey = event.getMaterialString().toUpperCase(Locale.ENGLISH) + DELIMITER + event.getData();
        if (cache.containsKey(matKey)) {
            event.setMaterial(cache.get(matKey));
            return;
        }

        IdMappings.Mapping mapping = null;
        if (convertNumericIds && NumberUtil.isInteger(event.getMaterialString())) {
            if (event.getData() > 0) {
                mapping = IdMappings.get(IdMappings.IdType.NUMERIC, matKey, DELIMITER);
            }
            if (mapping == null || mapping.getNote() != null) {
                mapping = IdMappings.getById(event.getMaterialString());
            }
        } else if (convertLegacyNames) {
            String matStr = event.getMaterialString().replaceAll("([a-z])([A-Z1-9])", "$1_$2").replace(' ', '_');
            if (event.getData() > 0) {
                mapping = IdMappings.get(IdMappings.IdType.LEGACY, matKey, DELIMITER);
            }
            if (mapping == null || mapping.getNote() != null) {
                mapping = IdMappings.getByLegacyType(matStr);
            }
        }
        Material material = null;
        if (mapping != null) {
            material = Material.getMaterial(mapping.getFlatteningType().toUpperCase(Locale.ENGLISH));
        }
        if (material != null) {
            event.setMaterial(material);
        }
        cache.put(matKey, material);
    }
}
