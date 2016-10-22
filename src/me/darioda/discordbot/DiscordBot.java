package me.darioda.discordbot;

import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventDispatcher;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.RateLimitException;

public class DiscordBot {

	public static IDiscordClient getClient(String token) { // Returns an instance of the Discord client
		ClientBuilder clientBuilder = new ClientBuilder(); // Creates the ClientBuilder instance
		try {
			return clientBuilder.withToken(token).login();
		} catch (DiscordException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public static void main(String[] args) {
		IDiscordClient client = getClient("MjM4NDQ2MzA2NDkyNjc4MTQ0.CupfFQ.H64eMQBdEYLS3mxBcrU62nKFXVY"); // Gets the client object (from the first example)
		try {
			client.changeUsername("DJ");
		} catch (DiscordException e) {
			e.printStackTrace();
		} catch (RateLimitException e) {
			e.printStackTrace();
		}
		EventDispatcher dispatcher = client.getDispatcher(); // Gets the EventDispatcher instance for this client instance
		dispatcher.registerListener(new DiscordBotListener()); // Registers the @EventSubscriber example class from above
	}
}
