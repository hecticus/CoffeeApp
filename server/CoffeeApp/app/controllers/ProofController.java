package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.proof.index;

public class ProofController extends Controller{

    public Result proof(String  name ){
        return ok(index.render(name));
    }

}
