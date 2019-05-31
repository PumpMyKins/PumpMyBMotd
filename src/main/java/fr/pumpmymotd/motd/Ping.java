package fr.pumpmymotd.motd;

import net.md_5.bungee.api.Favicon;

public class Ping {

	private String line1;
	private String line2;
	
	private boolean fmlSupport;
	
	private Favicon favicon;
	private boolean checkDispo;
	
	private Ping(String line1, String line2, Favicon favicon, boolean fml, boolean dispo) {
		
		this.line1 = line1;
		this.line2 = line2;
		this.favicon = favicon;
		this.fmlSupport = fml;
		this.checkDispo = dispo;
		
	}

	public boolean hasCheckDispo() {
		return checkDispo;
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

	public static class PingBuilder {
		
		private String line1;
		private String line2;
		private boolean fml;	
		private boolean dispo;
		private Favicon favicon;
		
		public PingBuilder setLine1(String line1) {
			this.line1 = line1;
			return this;
		}
		
		public PingBuilder setFmlSupport(boolean fml) {
			this.fml = fml;
			return this;
		}
		
		public PingBuilder setCheckDisponibility(boolean fml) {
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
		
		public Ping build() {
			
			return new Ping(this.line1,this.line2,this.favicon,this.fml,this.dispo);
			
		}
		
	}
	
}
