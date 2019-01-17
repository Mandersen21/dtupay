//import org.glassfish.jersey.server.ResourceConfig;
//import org.glassfish.jersey.test.JerseyTest;
//import org.junit.Test;
//
//import beijing.customerservice.domain.Customer;
//import beijing.customerservice.rest.CustomerRestService;
//
//import javax.ws.rs.client.Entity;
//import javax.ws.rs.client.Invocation;
//import javax.ws.rs.client.WebTarget;
//import javax.ws.rs.core.Application;
//import javax.ws.rs.core.Form;
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.Future;
//
//public class CustomerRestServiceTest extends JerseyTest {
//
//    @Override
//    protected Application configure() {
//        return new ResourceConfig(CustomerRestService.class);
//    }
//
//    @Test
//    public void testPost() throws Exception {
//        Form form1 = new Form();
//        Form form2 = new Form();
//        form1.param("name", "Alyssa William").param("cpr", "3433433433").param("tokenList", "123123");
//        form2.param("name", "Alyssa").param("cpr", "3433433431").param("tokenList", "1414213");
//        
//        // Post
//        WebTarget target1 = target("customers");
//        Future<String> response1 = target1.
//                   request(MediaType.APPLICATION_FORM_URLENCODED)
//                  .accept(MediaType.TEXT_PLAIN)
//                  .buildPost(Entity.form(form1)).submit(String.class);
//        System.out.println(response1.get());
//        
//        Future<String> response2 = target1.
//                request(MediaType.APPLICATION_FORM_URLENCODED)
//               .accept(MediaType.TEXT_PLAIN)
//               .buildPost(Entity.form(form2)).submit(String.class);
//        System.out.println(response2.get());
//
//        // Get all
//        String s1 = target1.request(MediaType.APPLICATION_JSON).get(String.class);
//        System.out.println(s1);
//        
//        // Get one
//        String s2 = target1.path("1").request(MediaType.APPLICATION_JSON).get(String.class);
//        System.out.println(s2);
//        
//        // Delete one
////        String s3 = target1.path("2").
////        		resolveTemplate("id",2).request(MediaType.APPLICATION_JSON).delete(String.class);
////        System.out.println(s3);
//        
//    }
//    
//}