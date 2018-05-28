package sk.tuke.gamestudio.service;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/Test")
public class TestService {
    @EJB
    private TestServiceJPA testService;

    @GET
    public int test(){
        testService.test();
        return 1;
    }

}
