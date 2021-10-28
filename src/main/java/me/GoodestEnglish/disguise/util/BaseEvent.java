package me.GoodestEnglish.disguise.util;

import me.GoodestEnglish.disguise.GoodestDisguise;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class BaseEvent extends Event {

	private static final HandlerList handlers = new HandlerList();

	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

	public void call() {
		GoodestDisguise.INSTANCE.getServer().getPluginManager().callEvent(this);
	}

}
