package me.darioda.discordbot;

import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IVoiceChannel;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.audio.AudioPlayer;
import sx.blah.discord.util.audio.AudioPlayer.Track;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

import me.darioda.discordbot.thread.DiscordBotLeaveVoiceChannel;

public class DiscordBotAudio {

	public DiscordBotAudio(File soundFile, MessageReceivedEvent event) throws Exception {
		IVoiceChannel voiceChannel = null;
		try {
			voiceChannel = event.getMessage().getAuthor().getConnectedVoiceChannels().get(0);
			voiceChannel.join();
		} catch (MissingPermissionsException e1) {
			e1.printStackTrace();
		}
		
		AudioPlayer audio = AudioPlayer.getAudioPlayerForGuild(event.getMessage().getGuild());
		System.out.println("Playing " + soundFile.getName());
		audio.queue(soundFile);
		
		//new DiscordBotLeaveVoiceChannel(voiceChannel, track.getTotalTrackTime()).run();
	}

}