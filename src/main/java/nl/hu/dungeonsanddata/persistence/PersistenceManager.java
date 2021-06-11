package nl.hu.dungeonsanddata.persistence;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobContainerClientBuilder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import nl.hu.dungeonsanddata.domain.*;
import nl.hu.dungeonsanddata.domain.Character;

public class PersistenceManager {
    private final static String ENDPOINT = "https://dungeonsanddata.blob.core.windows.net/";
    private final static String SASTOKEN = "?sv=2020-02-10&ss=bfqt&srt=sco&sp=rwdlacuptfx&se=2021-06-10T16:53:25Z&st=2021-06-10T08:53:25Z&spr=https&sig=UzK3t9MfS5jN3ZV4SKj8fMXaTGeVEzC2fBOKQxFmNgI%3D";
    private final static String CONTAINER = "charactercontainer";

    private static BlobContainerClient blobContainer = new BlobContainerClientBuilder()
            .endpoint(ENDPOINT)
            .sasToken(SASTOKEN)
            .containerName(CONTAINER)
            .buildClient();

    public static ArrayList<Character> loadCharactersFromAzure(Account account) throws Exception {
        if (!blobContainer.exists()){
            blobContainer.create();
        }

        BlobClient blob = blobContainer.getBlobClient(account.getAccountId()+"_character_blob");

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        blob.download(baos);

        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bais);

        ArrayList<Character> characters = new ArrayList<>();

        while (true){
            try {
                Character c = (Character) ois.readObject();
                characters.add(c);
            } catch (Exception e){
                break;
            }
        }

        bais.close();
        ois.close();
        baos.close();

        return characters;
    }

    public static void saveCharactersToAzure(Account account) throws Exception {
        if (!blobContainer.exists()){
            blobContainer.create();
        }

        BlobClient blob = blobContainer.getBlobClient(account.getAccountId()+"_character_blob");

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        for (Character c : account.getCharacters()){
            oos.writeObject(c);
        }

        byte[] bytez = baos.toByteArray();

        ByteArrayInputStream bais = new ByteArrayInputStream(bytez);
        blob.upload(bais, bytez.length, true);

        bais.close();
        baos.close();
        oos.close();
    }
}
