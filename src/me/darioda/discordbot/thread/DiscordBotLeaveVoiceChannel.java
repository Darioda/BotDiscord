package me.darioda.discordbot.thread;

import sx.blah.discord.handle.obj.IVoiceChannel;

public class DiscordBotLeaveVoiceChannel extends Thread {

	IVoiceChannel channel;
	long time;

	public DiscordBotLeaveVoiceChannel(IVoiceChannel channel, long time){
		this.channel = channel;
		this.time = time;
	}

	public void run(){
		try {
			sleep(time);
			channel.leave();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}       

}
