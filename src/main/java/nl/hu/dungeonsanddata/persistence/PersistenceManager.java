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
    private final static String SASTOKEN = "?sv=2020-02-10&ss=bfqt&srt=sco&sp=rwdlacuptfx&se=2021-09-03T20:16:38Z&st=2021-07-03T12:16:38Z&spr=https&sig=c9nZGDO1BRay%2FshkmOFLaN0Em9fqvZRfq5Wy9Fgjfy8%3D";
    private final static String CONTAINER = "accountscontainer";

    private static BlobContainerClient blobContainer = new BlobContainerClientBuilder()
            .endpoint(ENDPOINT)
            .sasToken(SASTOKEN)
            .containerName(CONTAINER)
            .buildClient();

    public static ArrayList<Account> loadAccountsFromAzure() throws Exception {
        if (!blobContainer.exists()){
            blobContainer.create();
        }

        BlobClient blob = blobContainer.getBlobClient("accounts_blob");

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        blob.download(baos);

        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bais);

        ArrayList<Account> accounts = new ArrayList<>();

        while (true){
            try{
                Account a = (Account) ois.readObject();
                accounts.add(a);
            } catch (Exception e){
                break;
            }
        }

        bais.close();
        ois.close();
        baos.close();

        Account.setAllAccounts(accounts);
        return accounts;
    }

    public static void saveAccountsToAzure() throws Exception {
        if (!blobContainer.exists()){
            blobContainer.create();
        }

        BlobClient blob = blobContainer.getBlobClient("accounts_blob");

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        for (Account a : Account.getAllAccounts()){
            oos.writeObject(a);
        }

        byte[] bytez = baos.toByteArray();

        ByteArrayInputStream bais = new ByteArrayInputStream(bytez);
        blob.upload(bais, bytez.length, true);

        bais.close();
        baos.close();
        oos.close();
    }
}
