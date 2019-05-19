package marcing.iotproject.inConnectionServlet.control;

import marcing.iotproject.basicElements.AttributesDictionary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import java.text.MessageFormat;

@Singleton
public class ManagementCoreApi {

    private final Logger LOG = LoggerFactory.getLogger(ManagementCoreApi.class);

    //    private static final String PREPARE_DATA_ENDTPOINT = "http://ec2-35-156-20-198.eu-central-1.compute.amazonaws.com:6969/management-core/preparedata/{" + AttributesDictionary.ID + "}";
    private static final String PREPARE_DATA_ENDPOINT = "http://127.0.0.1:8080/management-core/preparedata/{id}";
    private static final String WEBTARGET_REQUEST = "Send request to URL: {0} with id: {1}";


    private WebTarget webTarget;

    public ManagementCoreApi(){
        this.webTarget = ClientBuilder.newClient()
                .target(PREPARE_DATA_ENDPOINT);
    }

    public void prepareData(String id){
        LOG.info(MessageFormat.format(WEBTARGET_REQUEST, PREPARE_DATA_ENDPOINT, id));
        webTarget.resolveTemplate(AttributesDictionary.ID, id)
                .request()
                .async()
                .post(Entity.json(id));
    }
}
