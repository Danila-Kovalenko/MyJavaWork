// MyClassToBePersistedTask4.java

import java.io.*;

/**
 * The MyClassToBePersisted class contains profile and group fields.
 */
class MyClassToBePersisted implements Serializable {
    private static final long serialVersionUID = 1L;

    private String profile;
    private String group;

    /**
     * Constructor for the class.
     * @param profile the profile field.
     * @param group the group field.
     */
    public MyClassToBePersisted(String profile, String group) {
        this.profile = profile;
        this.group = group;
    }

    // Getters and setters
    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    // toString method for display
    @Override
    public String toString() {
        return "MyClassToBePersisted [profile=" + profile + ", group=" + group + "]";
    }
}

/**
 * The SerializeMyClassToBePersisted class serializes an instance of MyClassToBePersisted to a file.
 */
class SerializeMyClassToBePersisted {
    public static void main(String[] args) {
        MyClassToBePersisted myObj = new MyClassToBePersisted("Profile123", "GroupABC");
        try {
            FileOutputStream fos = new FileOutputStream("myclass_persisted.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(myObj);
            oos.close();
            fos.close();
            System.out.println("Object successfully serialized to myclass_persisted.ser");
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }
}

/**
 * The DeserializeMyClassToBePersisted class deserializes an instance of MyClassToBePersisted from a file.
 */
class DeserializeMyClassToBePersisted {
    public static void main(String[] args) {
        MyClassToBePersisted myObj = null;
        try {
            FileInputStream fis = new FileInputStream("myclass_persisted.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            myObj = (MyClassToBePersisted) ois.readObject();
            ois.close();
            fis.close();
        } catch(IOException ioe) {
            ioe.printStackTrace();
            return;
        } catch(ClassNotFoundException cnfe) {
            System.out.println("Class MyClassToBePersisted not found");
            cnfe.printStackTrace();
            return;
        }
        System.out.println("Deserialized object: " + myObj);
    }
}

/**
 * The MyClassToBePersistedTask4 class combines serialization and deserialization for demonstration.
 */
public class MyClassToBePersisted {
    public static void main(String[] args) {
        // Serialize the object
        SerializeMyClassToBePersisted.main(args);

        // Deserialize the object
        DeserializeMyClassToBePersisted.main(args);
    }
}
