package lt.viko.eif;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;
import java.util.List;

public class TransformToPOJO {
    /**
     *
     * @param inputStream
     * @return
     */
    public static Student transformToPOJO(InputStream inputStream) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Student.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            return (Student) unmarshaller.unmarshal(inputStream);
        } catch (JAXBException e) {
            e.printStackTrace();
            return null;
        }
    }

}
