package server.domain;

public class GroupKey{


    private int identifier;
    private byte[] encryptedKey;

    public GroupKey(int identifier, byte[] encryptedKey) {
        this.identifier = identifier;
        this.encryptedKey = encryptedKey;
    }

    public GroupKey() {
	}

	public int getIdentifier() {
        return identifier;
    }

    public byte[] getEncryptedKey() {
        return encryptedKey;
    }
    
    public String toString() {
        return identifier+"-"+encryptedKey;
    }
    
    public GroupKey StringToGroupKey(String str) {
    	String[] split=str.split("-");
    	this.identifier=Integer.parseInt(split[0]);
    	this.encryptedKey=split[1].getBytes();
        return new GroupKey(identifier,encryptedKey);
    }

}