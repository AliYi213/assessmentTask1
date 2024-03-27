package lt.viko.eif;
import javax.xml.bind.*;
import java.io.StringReader;
import java.io.StringWriter;

public class JAXBTransformer {
    public static String transformToXML(Student student) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(Student.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        StringWriter writer = new StringWriter();
        marshaller.marshal(student, writer);

        return writer.toString();
    }
}
