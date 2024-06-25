import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import com.google.gson.GsonBuilder;

class Block extends Data {
    public String hash;
    public String previousHash;
    private int nonce;

    public Block(String patientUsername, String diagnosis, String timestamp, String previousHash) {
        super(patientUsername, diagnosis, timestamp);
        this.previousHash = previousHash;
        this.hash = calculateHash();
    }

    public String calculateHash() {
        String calculatedHash = StringUtil.applyHmacSha256(
                                    previousHash +
                                    getPatientUsername() + getDiagnosis() + getTimestamp() +
                                    Integer.toString(nonce),
                                    "secretKey"
                                );
        return calculatedHash;
    }

    public void mineBlock(int difficulty) {
        String target = new String(new char[difficulty]).replace('\0', '0');
        while (!hash.substring(0, difficulty).equals(target)) {
            nonce++;
            hash = calculateHash();
        }
        System.out.println("Block Mined! : " + hash);
    }
}
