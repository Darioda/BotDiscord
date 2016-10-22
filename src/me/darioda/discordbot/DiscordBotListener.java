package me.darioda.discordbot;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import javax.sound.sampled.UnsupportedAudioFileException;

import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.IVoiceChannel;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;
import sx.blah.discord.util.audio.AudioPlayer;

public class DiscordBotListener  {
	
	private String botprefix = "/"; // Understand commands using "="

	// Queue audio from specified URL stream for guild
	private static void playAudioFromUrl(String s_url, IGuild guild) throws IOException, UnsupportedAudioFileException {
		System.out.println("debut url");
		URL url = new URL(s_url); // Get URL
		AudioPlayer player = AudioPlayer.getAudioPlayerForGuild(guild); // Get AudioPlayer for guild
		player.queue(url); // Queue URL stream
		System.out.println("fin url");
	}

	// Change AudioPlayer volume for guild
	private static void setVolume(float vol, IGuild guild){
		AudioPlayer player = AudioPlayer.getAudioPlayerForGuild(guild);
		player.setVolume(vol);
	}
	
	private static boolean hasPermissions(IUser author, IGuild guild) {
		List<IRole> list = author.getRolesForGuild(guild);
		for(IRole role : list)
			if(role.getName().contentEquals("DJ"))
				return true;
		return false;
	}

	@EventSubscriber
	// Receive messages
	public void OnMesageEvent(MessageReceivedEvent event) throws IOException, UnsupportedAudioFileException, RateLimitException, MissingPermissionsException, DiscordException {
		IMessage message = event.getMessage(); // Get message from event
		IUser author = message.getAuthor();
		setVolume(0.1f, message.getGuild());
		if(message.getContent().startsWith(botprefix) && hasPermissions(author, message.getGuild())){
			String command = message.getContent().replaceFirst(botprefix, ""); // Remove prefix

			String[] args = command.split(" "); // Split command into arguments

			// Check for "summon" as command
			if(args[0].equalsIgnoreCase("summon")){
				// Get the user's voice channel
				IVoiceChannel voicechannel = message.getAuthor().getConnectedVoiceChannels().get(0);
				// Join the channel
				voicechannel.join();
				// Send message
				message.getChannel().sendMessage("Joined `" + voicechannel.getName() + "`.");
			} else if(args[0].equalsIgnoreCase("leave")){
				// Get the user's voice channel
				IVoiceChannel voicechannel = message.getAuthor().getConnectedVoiceChannels().get(0);
				// Join the channel
				voicechannel.leave();
				// Send message
				message.getChannel().sendMessage("Left `" + voicechannel.getName() + "`.");
			} else if(args[0].equalsIgnoreCase("play")){
				// Queue up test file from URL
				playAudioFromUrl(args[1], message.getGuild());
				// Send message
				message.getChannel().sendMessage("Queued test URL.");
				// Check for "setvol" as command
			} else if(args[0].equalsIgnoreCase("vol")){
				// Read first argument as float value
				float vol = Float.parseFloat(args[1]);
				if(vol > 1 && vol != 0)
					vol /= 100;
				// Set volume for guild
				setVolume(vol, message.getGuild());
				// Send message
				message.getChannel().sendMessage("Set volume to " + vol + ".");
			} else if(args[0].equalsIgnoreCase("skip")){
				// Set volume for guild
				AudioPlayer player = AudioPlayer.getAudioPlayerForGuild(message.getGuild()); // Get AudioPlayer for guild
				player.skip(); // Skip file
				// Send message
				message.getChannel().sendMessage("Song skipped.");
			}
		}
	}
}