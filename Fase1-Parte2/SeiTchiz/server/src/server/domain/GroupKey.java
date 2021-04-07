package server.domain;

/**
 * @author Ana Albuquerque 53512, Gonçalo Antunes 52831, Tiago Cabrita 52741
 */
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

	public void increment() {
		identifier++;
	}

}