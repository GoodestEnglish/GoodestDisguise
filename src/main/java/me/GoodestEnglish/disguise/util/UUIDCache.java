package me.GoodestEnglish.disguise.util;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UUIDCache {

    private static Map<String, UUID> nameToUuid = new HashMap<>();
    private static Map<UUID, String> uuidToName = new HashMap<>();

    public static String getName(final UUID uuid) {
        if (uuidToName.containsKey(uuid))
            return uuidToName.get(uuid);

        return "Unknown";
    }

    public static UUID getUuid(final String name) {
        if (nameToUuid.containsKey(name.toLowerCase()))
            return nameToUuid.get(name.toLowerCase());
        return null;
    }

    public static void update(final String name, final UUID uuid) {
        if (nameToUuid.containsKey(name)) {
            nameToUuid.replace(name, uuid);
        } else {
            nameToUuid.put(name, uuid);
        }

        if (uuidToName.containsKey(uuid)) {
            uuidToName.replace(uuid, name);
        } else {
            uuidToName.put(uuid, name);
        }
    }

}
