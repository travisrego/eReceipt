package com.tr.ereceipt.ui.ereceipt;

import java.io.*;

public class SerializeData {

    /*
     * https://forums.livecode.com/viewtopic.php?t=34352
     * https://www.tutorialspoint.com/java/java_serialization.htm
     *
     * The title of this helped me find out the topic of serialization
     */

    // encrypting
    public void serializationCompany(Company company) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("company.ser");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(company);
            objectOutputStream.close();
            fileOutputStream.close();
            System.out.println("Company serialized successfully to " + fileOutputStream.getChannel());
        } catch (IOException e) {
            System.out.println("Error serializing company");
        }
    }

    // decrypting
    public Company deserializationCompany() {
        Company company = null;
        try {
            FileInputStream fileInputStream = new FileInputStream("company.ser");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            // if you get any casting error, it might be due to previous file issues,
            // delete the company.ser file and re-serialize it by going into settings
            // in the program and adding a company name
            company = (Company) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error deserializing company");
        }
        return company;
    }
}
