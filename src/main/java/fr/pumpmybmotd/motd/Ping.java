package fr.pumpmybmotd.motd;

import net.md_5.bungee.api.Favicon;

public class Ping {

	private String line1;
	private String line2;
	
	private boolean fmlSupport;
	
	private Favicon favicon;
	
	private int maxPlayers;
	
	private Ping(String line1, String line2, Favicon favicon, boolean fml, int max) {
		
		this.line1 = line1;
		this.line2 = line2;
		this.favicon = favicon;
		this.fmlSupport = fml;
		this.maxPlayers = max;
		
	}

	public String getLine1() {
		return line1;
	}
	
	public boolean hasFmlSupport() {
		return this.fmlSupport;
	}

	public String getLine2() {
		return line2;
	}

	public Favicon getFavicon() {
		return favicon;
	}
	
	public int getMaxPlayers() {
		return maxPlayers;
	}

	public static class PingBuilder {
		
		private String line1;
		private String line2;
		private boolean fml;	
		private Favicon favicon;
		private int max;
		
		public PingBuilder setLine1(String line1) {
			this.line1 = line1;
			return this;
		}
		
		public PingBuilder setFmlSupport(boolean fml) {
			this.fml = fml;
			return this;
		}
		
		public PingBuilder setLine2(String line2) {
			this.line2 = line2;
			return this;
		}

		public PingBuilder setFavicon(Favicon favicon) {
			this.favicon = favicon;
			return this;
		}
		
		public PingBuilder setMaxPlayer(int max) {
			this.max = max;
			return this;
		}
		
		public Ping build() {
			
			return new Ping(this.line1,this.line2,this.favicon,this.fml,this.max);
			
		}
		
	}
	
}
