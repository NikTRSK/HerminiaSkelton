package utilities;

import java.io.Serializable;

public class DataPacket<V> implements Serializable {
	private static final long serialVersionUID = -5314880493109698625L;
	private String command; // key
	private V data;	// the actual data
	
	public DataPacket(String command, V data) {
		this.command = command;
		this.data = data;
	}
	
	public String getCommand() { return command; }
	public V getData() { return data; }
	
	public void setCommand(String command) { this.command = command; }
	public void setData(V data) { this.data = data; }
}