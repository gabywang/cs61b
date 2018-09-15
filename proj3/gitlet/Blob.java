package gitlet;

/** The Blob of each file.
 * @author Xiaowen Wang*/
public class Blob extends GitletObject {

    /** The base serialization UID for the Blob object.*/
    private static final long serialVersionUID = 4865064372716910861L;
    /** The byte contents. */
    private byte[] _contents;

    /**
     * Generates a Blob.
     * @param contents the contents.*/
    public Blob(byte[] contents) {
        _contents = contents;
    }

    /**
     * @return the contents
     */
    public byte[] getContents() {
        return _contents;
    }

}
